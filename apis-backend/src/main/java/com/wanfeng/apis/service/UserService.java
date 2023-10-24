package com.wanfeng.apis.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfeng.apis.model.dto.user.UserQueryRequset;
import com.wanfeng.apis.model.dto.user.UserUpdateRequset;
import com.wanfeng.apis.model.entity.User;
import com.wanfeng.apis.model.vo.user.UserLoginVO;
import com.wanfeng.apis.model.vo.user.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 85975
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-07-30 18:26:37
*/
public interface UserService extends IService<User> {

    UserLoginVO tologin(String useraccount, String userpassword, HttpServletRequest request);

    UserLoginVO getloginUserVO(HttpServletRequest request);

    QueryWrapper<User> getQueryWarpper(UserQueryRequset queryRequset);

    List<UserVO> getPageVO(List<User> records);

    boolean addUser(User userAddRequset);

    boolean logout(HttpServletRequest request);

    boolean updateUser(UserUpdateRequset requset);

    boolean removeUserById(Long id, HttpServletRequest request);
}
