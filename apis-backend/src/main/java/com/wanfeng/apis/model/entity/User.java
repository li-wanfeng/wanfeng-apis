package com.wanfeng.apis.model.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@Data
public class User implements Serializable {
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
     * 登录密码
     */
    private String userpassword;

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
     * 0表示默认用户,1表示管理员，2被封号
     */
    private Integer userrole;

    /**
     * 调用的标识
     */
    private String accesskey;

    /**
     * 密钥
     */
    private String secretkey;

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
    @TableLogic
    private Integer isdelete;

    private static final long serialVersionUID = 1L;
}