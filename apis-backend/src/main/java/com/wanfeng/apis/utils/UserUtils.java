package com.wanfeng.apis.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.wanfeng.apis.constant.UserConstant.SALT;


/**
 * @author 风车下跑
 * @create 2023-07-31
 */
public class UserUtils {
    public static String getMD5(String password){
        return DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));
    }
}
