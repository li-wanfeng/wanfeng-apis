package com.wanfeng.apis.model.dto.user;

import lombok.Data;

/**
 * @author 风车下跑
 * @create 2023-07-31
 */
@Data
public class UserLoginRequest {

    private String userAccount;

    private String userPassword;
}
