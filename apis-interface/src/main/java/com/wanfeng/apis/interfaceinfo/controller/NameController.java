package com.wanfeng.apis.interfaceinfo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/name")
public class NameController {

    @PostMapping
    public String getName(@RequestParam("id") String id) {
        return id + "你的名字";
    }

    @PostMapping("/post")
    public String getNameByPost(String id) {
        return id + "post你好啊";
    }

    @GetMapping
    public String getNameByGet(String id) {
        return id + "get你好啊";
    }
}
