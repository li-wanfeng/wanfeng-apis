package com.wanfeng.apis.aop;


import com.wanfeng.apis.annotation.AuthCheck;
import com.wanfeng.apis.common.ErrorCode;
import com.wanfeng.apis.constant.UserConstant;
import com.wanfeng.apis.exception.BusinessException;
import com.wanfeng.apis.exception.ThrowUtils;
import com.wanfeng.apis.model.entity.User;
import com.wanfeng.apis.model.enums.UserRoleEnum;
import com.wanfeng.apis.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 风车下跑
 * @create 2023-07-30
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    @Around("@annotation(authCheck)")  //设置切入点
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        Integer role = authCheck.role();
        /**
         * ，使用RequestContextHolder.currentRequestAttributes()获取当前请求的属性，并将其转换为ServletRequestAttributes类型。
         * 接着，通过((ServletRequestAttributes) requestAttributes).getRequest()获取实际的HttpServletRequest对象。
         */
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //当前的系统 用户信息存储在session中的，所以先进行获取session中的用户信息
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        //查看session中是否存储了用户态，没有报错
        ThrowUtils.throwIf(ObjectUtils.isEmpty(user), ErrorCode.NOT_LOGIN_ERROR);
        // 必须有该权限才通过
        if (role != -1) {
            UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(role);
            if (mustUserRoleEnum == null) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            Integer userRole = user.getUserrole();
            // 如果被封号，直接拒绝
            if (UserRoleEnum.BAN == mustUserRoleEnum) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            // 必须有管理员权限
            if (UserRoleEnum.ADMIN == mustUserRoleEnum) {
                if (role != userRole) {
                    throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
                }
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}
