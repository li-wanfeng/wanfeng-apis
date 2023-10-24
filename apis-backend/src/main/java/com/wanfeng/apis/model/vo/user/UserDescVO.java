package com.wanfeng.apis.model.vo.user;

import lombok.Data;

/**
 * @author 风车下跑
 * @create 2023-08-25
 */
@Data
public class UserDescVO {
    /**
     * 登录账号
     */
    private String useraccount;

    /**
     * 昵称
     */
    private String username;
    /**
     * 用户状态
     */
    private Integer userstatus;

    /**
     * 0表示默认用户,1表示管理员
     */
    private Integer userrole;

}
