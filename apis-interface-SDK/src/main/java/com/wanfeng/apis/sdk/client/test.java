package com.wanfeng.apis.sdk.client;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wanfeng.apis.sdk.common.BaseResponse;


import java.util.HashMap;

public class test {
    public static void main(String[] args) {
        String url = "http://localhost:8666/v1/name/post";
        wanfengInterFaceClient client = new wanfengInterFaceClient();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "1");
        BaseResponse post = client.post(url,map);
        System.out.println("post = " + post);
    }
}
