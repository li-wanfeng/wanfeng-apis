package com.wanfeng.gatetway.filter;


import com.google.gson.Gson;
import com.wanfeng.apis.common.BaseResponse;
import com.wanfeng.apis.common.ErrorCode;
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

import java.nio.charset.StandardCharsets;

@Configuration
public class invockFilter {
    @Bean
    public RouteLocator invockLocator(RouteLocatorBuilder builder) {
        return builder.routes().route(
                "invock_accessKey",r->r.path("/api/interface/invoke")
                        .filters(f->f.filter(accessKeyFiltet()))
                        .uri("http://localhost:8027")

        ).build();
    }

    private GatewayFilter accessKeyFiltet() {
        return (exchange,chain)->{
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String accessKey = headers.getFirst("accessKey");
            if (!StringUtils.hasText(accessKey)) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.OK);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                BaseResponse baseResponse = new BaseResponse(ErrorCode.NO_AUTH_ERROR.getCode(), null,"当前帐号没有操作权限");
                Gson gson = new Gson();
                String json = gson.toJson(baseResponse);
//                String errorResponse = "{\"error\": \"Access key is missing.\"}";
                DataBuffer buffer = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
                return response.writeWith(Mono.just(buffer));
            }
            return chain.filter(exchange);
        };
    }
}
