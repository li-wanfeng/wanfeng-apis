package com.wanfeng.apis.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfeng.apis.common.ErrorCode;
import com.wanfeng.apis.exception.ThrowUtils;
import com.wanfeng.apis.mapper.UserMapper;
import com.wanfeng.apis.model.dto.user.UserQueryRequset;
import com.wanfeng.apis.model.dto.user.UserUpdateRequset;
import com.wanfeng.apis.model.entity.User;
import com.wanfeng.apis.model.vo.user.UserLoginVO;
import com.wanfeng.apis.model.vo.user.UserVO;
import com.wanfeng.apis.service.UserService;
import com.wanfeng.apis.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wanfeng.apis.constant.UserConstant.USER_LOGIN_STATE;


/**
 * @author 85975
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-07-30 18:26:37
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserLoginVO tologin(String useraccount, String userpassword, HttpServletRequest request) {
        ThrowUtils.throwIf(!(StringUtils.hasText(useraccount) && StringUtils.hasText(userpassword)), ErrorCode.PARAMS_ERROR);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", useraccount);
        User one = this.getOne(queryWrapper);
        String saltpassword = UserUtils.getMD5(userpassword);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(one) || !one.getUserpassword().equals(saltpassword), ErrorCode.LOGIN_PARAMS_ERROR);
        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(one, userLoginVO);

        //存储用户态
        request.getSession().setAttribute(USER_LOGIN_STATE, one);
        return userLoginVO;
    }

    @Override
    public UserLoginVO getloginUserVO(HttpServletRequest request) {
        //查询当前登录态
        User attribute = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(attribute), ErrorCode.NOT_LOGIN_ERROR);
        //根据登录态中的id查询一遍数据库
        // TODO 如果想追求性能 可以走缓存
        User byId = this.getById(attribute.getId());
        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(byId, userLoginVO);
        return userLoginVO;
    }

    /**
     * 根据前端返回的dto返回查询条件
     *
     * @param queryRequset
     * @return
     */
    @Override
    public QueryWrapper<User> getQueryWarpper(UserQueryRequset queryRequset) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Long id = queryRequset.getId();
        queryWrapper.eq(id != null, "id", id);
        String useraccount = queryRequset.getUseraccount();
        queryWrapper.eq(StringUtils.hasText(useraccount), "userAccount", useraccount);
        String username = queryRequset.getUsername();
        queryWrapper.like(StringUtils.hasText(username), "userName", username);
        String phone = queryRequset.getPhone();
        queryWrapper.eq(StringUtils.hasText(phone), "phone", phone);
        Integer userstatus = queryRequset.getUserstatus();
        queryWrapper.eq(null != userstatus, "userStatus", userstatus);
//        String[] createtime = queryRequset.getCreatetime();
//        if (null != createtime && createtime.length>0){
//            String startTime = createtime[0];
//            String endTime = createtime[1];
//            queryWrapper.between("createtime",startTime,endTime);
//        }
        return queryWrapper;
    }

    @Override
    public List<UserVO> getPageVO(List<User> records) {
        if (CollectionUtils.isEmpty(records)) {
            return new ArrayList<>();
        }
        return records.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public boolean addUser(User userAddRequset) {
        String useraccount = userAddRequset.getUseraccount();
        ThrowUtils.throwIf(!StringUtils.hasText(useraccount), ErrorCode.PARAMS_ERROR);
        long userAccount = this.count(new QueryWrapper<User>().eq("userAccount", useraccount));
        ThrowUtils.throwIf(userAccount > 0, ErrorCode.USER_EXSIT);
        userAddRequset.setUserpassword(UserUtils.getMD5("123456"));
        boolean save = this.save(userAddRequset);
        return save;
    }

    @Override
    public boolean logout(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(attribute), ErrorCode.NOT_LOGIN_ERROR);
        //移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public boolean updateUser(UserUpdateRequset requset) {
        ThrowUtils.throwIf((null == requset || null == requset.getId()), ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf((null == requset.getUseraccount()), ErrorCode.PARAMS_ERROR);
        //现根据id查询出用户信息，判断数据库中account和传来的一致吗
        User byId = this.getById(requset.getId());
        ThrowUtils.throwIf(ObjectUtils.isEmpty(byId),ErrorCode.USER_NOT_CREATE_ERROR);
        ThrowUtils.throwIf(!byId.getUseraccount().equals(requset.getUseraccount()),ErrorCode.OPERATION_ERROR,"账号不允许修改");
        User user = new User();
        BeanUtils.copyProperties(requset, user);
        return this.updateById(user);

    }

    @Override
    public boolean removeUserById(Long id, HttpServletRequest request) {
        User attribute = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        User byId = this.getById(id);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(byId),ErrorCode.USER_NOT_CREATE_ERROR);
        if (attribute.getId() == byId.getId()){
            //删除的是自己
            request.getSession().removeAttribute(USER_LOGIN_STATE);
        }
        return this.removeById(byId);
    }

    public UserVO getUserVO(User user) {
        if (null == user) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}

