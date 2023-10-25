package com.wanfeng.apis.sdk.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.wanfeng.apis.common.BaseResponse;
import com.wanfeng.apis.common.ErrorCode;
import com.wanfeng.apis.common.ResultUtils;


import java.util.Map;

public class wanfengInterFaceClient {
    private String url;
    private String accessKty;
    private String sercetKey;

    private String params;


    public wanfengInterFaceClient(String url, String accessKty, String sercetKey, String params) {
        this.url = url;
        this.accessKty = accessKty;
        this.sercetKey = sercetKey;
        this.params = params;
    }

    public wanfengInterFaceClient(String url, String params) {
        this.url = url;
        this.params = params;
    }

    public wanfengInterFaceClient(String url, String accessKty, String sercetKey) {
        this.url = url;
        this.accessKty = accessKty;
        this.sercetKey = sercetKey;
    }

    public wanfengInterFaceClient(String url) {
        this.url = url;
    }

    public wanfengInterFaceClient() {
    }

    public BaseResponse get(String url, Map<String, Object> params) {
        String requestUrl = null;
        if (null != params && !params.isEmpty()) {
            StringBuilder query = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                query.append(key).append("=").append(value).append("&");
            }
            if (query.length() > 0) {
                query.deleteCharAt(query.length() - 1); // 移除最后一个多余的 "&"
            }
            requestUrl = url + "?" + query;
        } else {
            requestUrl = url;
        }
        HttpResponse execute = HttpRequest.get(requestUrl).execute();
        if (execute.getStatus() != 200) {
            return ResultUtils.error(ErrorCode.RUNTIME_ERROE);
        }
        return ResultUtils.success(execute.body(), null);
    }

    public BaseResponse post(String url, Map<String, Object> params) {
        String requestUrl = null;
        if (null != params && !params.isEmpty()) {
            StringBuilder query = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                query.append(key).append("=").append(value).append("&");
            }
            if (query.length() > 0) {
                query.deleteCharAt(query.length() - 1); // 移除最后一个多余的 "&"
            }
            requestUrl = url + "?" + query;
        } else {
            requestUrl = url;
        }
        HttpResponse execute = HttpRequest.post(requestUrl).execute();
        if (execute.getStatus() != 200) {
            return ResultUtils.error(ErrorCode.RUNTIME_ERROE);
        }
        return ResultUtils.success(execute.body(), null);
    }

    public BaseResponse postWithJson(String url, Map<String, Object> params) {
        String jsonStr = JSONUtil.toJsonStr(params);
        HttpResponse execute = HttpRequest.post(url).body(jsonStr).execute();
        if (execute.getStatus() != 200) {
            return ResultUtils.error(ErrorCode.RUNTIME_ERROE);
        }
        return ResultUtils.success(execute.body(), null);
    }


}
