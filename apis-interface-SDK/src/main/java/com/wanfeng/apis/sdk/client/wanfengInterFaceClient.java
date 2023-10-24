package com.wanfeng.apis.sdk.client;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.util.Map;

public class wanfengInterFaceClient {
    private String url;
    private  String accessKty;
    private  String sercetKey;

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

    public String get(String url,Map<String, Object> params) {
        String s = HttpUtil.get(url, params);
        return s;
    }

    public String post(String url,Map<String, Object> params) {
        String jsonStr = JSONUtil.toJsonStr(params);
        String post = HttpUtil.post(url, jsonStr);
        return post;
    }
    public String post(String url,String params) {
//        String jsonStr = JSONUtil.toJsonStr(params);
        String post = HttpUtil.post(url, params);
        return post;
    }
}
