package com.wanfeng.apis.aop;


import com.wanfeng.apis.common.ErrorCode;
import com.wanfeng.apis.exception.ThrowUtils;
import com.wanfeng.apis.model.entity.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.wanfeng.apis.constant.UserConstant.USER_LOGIN_STATE;


/**
 * @author 风车下跑
 * @create 2023-08-24
 */
@Aspect
@Component
public class LoginInterceptor {

    @Before("execution(* com.wanfeng.apis.controller.UserController..*(..)) && !execution(* com.wanfeng.apis.controller.UserController.userlogin(..))")
    public void isLogin(){
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User attribute = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(attribute), ErrorCode.NOT_LOGIN_ERROR);
        System.out.println(request);
//        ThrowUtils.throwIf(!(null !=attribute.getUserrole() && attribute.getUserrole() >=2) ,ErrorCode.NO_AUTH_ERROR);
    }

}
