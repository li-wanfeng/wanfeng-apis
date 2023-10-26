package com.wanfeng.gatetway.filter;


import com.google.gson.Gson;
import com.wanfeng.apis.common.BaseResponse;
import com.wanfeng.apis.common.ErrorCode;
import com.wanfeng.gatetway.utils.RedisKeys;
import com.wanfeng.gatetway.utils.RedisService;
import com.wanfeng.gatetway.utils.ValidationUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import static com.wanfeng.gatetway.constant.GateWayConstant.CACHED_REQUEST_BODYO_BJECT;

@Configuration
public class invockFilter {
    @Resource
    private RedisService redisService;

    @Bean
    public RouteLocator invockLocator(RouteLocatorBuilder builder) {
        return builder.routes().route("invock_accessKey", r -> r.path("/api/interface/invoke").and()
                //获取缓存中的请求体，传入到自定义过滤器中进行处理(副本不能更改)
                .readBody(Object.class, readBody -> {
                    return true;
                }).filters(f -> f.filter(accessKeyFiltet())).uri("http://localhost:8027")

        ).build();
    }

    private GatewayFilter accessKeyFiltet() {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            ServerHttpResponse response = exchange.getResponse();
            //校验是否存在签名key，没有打回
            String accessKey = headers.getFirst("accessKey");
            if (!StringUtils.hasText(accessKey)) {
                response.setStatusCode(HttpStatus.OK);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                BaseResponse baseResponse = new BaseResponse(ErrorCode.OPERATION_ERROR.getCode(), null, "当前帐号没有操作权限");
                Gson gson = new Gson();
                String json = gson.toJson(baseResponse);
                DataBuffer buffer = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
                return response.writeWith(Mono.just(buffer));
            }
            //校验签名
            String sign = headers.getFirst("sign");
            LinkedHashMap attribute = exchange.getAttribute(CACHED_REQUEST_BODYO_BJECT);
            //签名+时间戳：接口时效
            if (!StringUtils.hasText(sign) || !ValidationUtil.validSign(attribute,sign)) {
                response.setStatusCode(HttpStatus.OK);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                BaseResponse baseResponse = new BaseResponse(ErrorCode.OPERATION_ERROR.getCode(), null, "存在风险，拒绝访问");
                Gson gson = new Gson();
                String json = gson.toJson(baseResponse);
                DataBuffer buffer = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
                return response.writeWith(Mono.just(buffer));
            }
            //接口防刷
            String path = exchange.getRequest().getURI().getPath();
            String ip = exchange.getRequest().getRemoteAddress().getAddress().toString();
            String redisKey = RedisKeys.BRUSH_PROOF.getkey(path, ip);
            redisService.setnxCacheObject(redisKey, 10, RedisKeys.BRUSH_PROOF222.getTime(), TimeUnit.SECONDS);
            //减1
            Long number = redisService.decrementValue(redisKey);
            if (number < 0) {
                response.setStatusCode(HttpStatus.OK);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                BaseResponse baseResponse = new BaseResponse(ErrorCode.OPERATION_ERROR.getCode(), null, "请勿频繁访问");
                Gson gson = new Gson();
                String json = gson.toJson(baseResponse);
                DataBuffer buffer = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
                return response.writeWith(Mono.just(buffer));
            }

            return chain.filter(exchange);
        };
    }
}
