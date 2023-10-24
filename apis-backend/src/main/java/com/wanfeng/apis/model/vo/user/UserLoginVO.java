package com.wanfeng.apis.model.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 风车下跑
 * @create 2023-07-30
 */
@Data
public class UserLoginVO implements Serializable {
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

    /**
     * 用户状态
     */
    private Integer userstatus;

    /**
     * 0表示默认用户,1表示管理员
     */
    private Integer userrole;
    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否删除
     */
    private Integer isdelete;
}
