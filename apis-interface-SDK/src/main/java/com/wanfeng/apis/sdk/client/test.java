package com.wanfeng.apis.sdk.client;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;

public class test {
    public static void main(String[] args) {
        String url = "http://localhost:8666/v1/name";
        wanfengInterFaceClient client = new wanfengInterFaceClient();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "8");
        String jsonStr = JSONUtil.toJsonStr(map);
        HttpResponse execute = HttpUtil.createPost(url+"?id=1").execute();
        System.out.println("execute.getStatus() = " + execute.getStatus());
        System.out.println("execute.body() = " + execute.body());
    }
}
