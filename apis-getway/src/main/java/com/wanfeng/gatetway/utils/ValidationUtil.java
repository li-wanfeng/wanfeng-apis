package com.wanfeng.gatetway.utils;

import com.google.gson.Gson;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.springframework.util.DigestUtils;

import java.util.LinkedHashMap;

public class ValidationUtil {

    public static boolean validSign(LinkedHashMap attribute, String sign) {
//        attribute.keySet().forEach(item->{
//            System.out.println("item = " + item);
//            System.out.println("attribute.get(item) = " + attribute.get(item));
//            if (attribute.get(item) instanceof LinkedHashMap) {
//                ((LinkedHashMap) attribute.get(item)).keySet().forEach(item1->{
//                    System.out.println("item = " + item1);
//                    System.out.println("attribute.get(item) = " + attribute.get(item1));
//                });
//            }
//        });
        LinkedHashMap map = (LinkedHashMap) attribute.get("requestparams");
        Gson gson = new Gson();
        String json = gson.toJson(map).toString();
        String str = DigestUtils.md5DigestAsHex(json.getBytes()).toUpperCase();
        if (str.equals(sign)) {
            return true;
        }
        return false;
    }
}
