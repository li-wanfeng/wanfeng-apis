package com.wanfeng.apis.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.wanfeng.apis.common.BaseResponse;
import com.wanfeng.apis.common.DeleteRequest;

import com.wanfeng.apis.common.ErrorCode;
import com.wanfeng.apis.common.ResultUtils;
import com.wanfeng.apis.exception.BusinessException;
import com.wanfeng.apis.exception.ThrowUtils;
import com.wanfeng.apis.model.dto.user.UserLoginRequest;
import com.wanfeng.apis.model.dto.user.UserQueryRequset;
import com.wanfeng.apis.model.dto.user.UserUpdateRequset;
import com.wanfeng.apis.model.entity.User;
import com.wanfeng.apis.model.vo.user.UserLoginVO;
import com.wanfeng.apis.model.vo.user.UserVO;
import com.wanfeng.apis.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 风车下跑
 * @create 2023-07-30
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<UserLoginVO> userlogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserLoginVO userLoginVO = userService.tologin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPassword(), request);
        return ResultUtils.success(userLoginVO);
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @GetMapping("/info")
    public BaseResponse<UserLoginVO> getloginUser(HttpServletRequest request) {
        UserLoginVO userLoginVO = userService.getloginUserVO(request);
        return ResultUtils.success(userLoginVO);
    }

    /**
     * 分页查询所有用户信息
     * @param queryRequset
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequset queryRequset) {
        ThrowUtils.throwIf(ObjectUtils.isEmpty(queryRequset), ErrorCode.PARAMS_ERROR);
        long current = queryRequset.getCurrent();
        long pageSize = queryRequset.getPageSize();
        Page<User> userPage = new Page<>(current, pageSize);
        Page<User> page = userService.page(userPage, userService.getQueryWarpper(queryRequset));
        //实体转vo
        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        List<UserVO> pageVO = userService.getPageVO(page.getRecords());
        userVOPage.setRecords(pageVO);
        return ResultUtils.success(userVOPage);
    }

    public BaseResponse registerUser(){
        return null;
    }
    /**
     * 新增用户
     * @param userAddRequset
     * @return
     */
    @PostMapping("/")
    public BaseResponse addUser(@RequestBody User userAddRequset) {
        ThrowUtils.throwIf(ObjectUtils.isEmpty(userAddRequset), ErrorCode.PARAMS_ERROR);
        boolean isadd = userService.addUser(userAddRequset);
        if (isadd) {
            return ResultUtils.success("用户创建成功,初始密码为12346");
        }
        return ResultUtils.error(ErrorCode.USER_EXSIT);
    }

    /**
     * 用户退出登录
     * @param request
     * @return
     */
    @GetMapping("/")
    public BaseResponse logout(HttpServletRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        boolean res = userService.logout(request);
        return ResultUtils.success(res);
    }

    /**
     * 更新用户
     * @param requset
     * @return
     */
    @PutMapping("/")
    public BaseResponse updateUser(@RequestBody UserUpdateRequset requset){
        ThrowUtils.throwIf((ObjectUtils.isEmpty(requset) || null == requset.getId()),ErrorCode.PARAMS_ERROR);
        boolean res = userService.updateUser(requset);
        ThrowUtils.throwIf(!res,ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(res);
    }

    /**
     * 删除用户
     * @param deleteRequest
     * @param request
     * @return
     */
    @DeleteMapping("/")
    public BaseResponse deleteUserById(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request){
        ThrowUtils.throwIf(null == request,ErrorCode.NOT_LOGIN_ERROR);
        ThrowUtils.throwIf((null == deleteRequest || null == deleteRequest.getId()),ErrorCode.PARAMS_ERROR);
        boolean isdel = userService.removeUserById(deleteRequest.getId(), request);
        ThrowUtils.throwIf(!isdel,ErrorCode.DELETE_ERROR);
        return ResultUtils.success(isdel);
    }


}
