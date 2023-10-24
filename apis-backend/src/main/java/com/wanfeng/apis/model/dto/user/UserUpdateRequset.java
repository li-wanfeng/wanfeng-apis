package com.wanfeng.apis.model.dto.user;


import com.wanfeng.apis.model.dto.base.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 风车下跑
 * @create 2023-08-09
 * 新增封装 DT
 */
@Data
public class UserUpdateRequset extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 登录账号
     */
    private String useraccount;

    /**
     * 昵称
     */
    private String username;


    /**
     * 头像
     */
    private String avatarurl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

}
