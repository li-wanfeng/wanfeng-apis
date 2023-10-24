package com.wanfeng.apis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.wanfeng.apis.common.ErrorCode;
import com.wanfeng.apis.common.IDRequest;
import com.wanfeng.apis.exception.ThrowUtils;
import com.wanfeng.apis.mapper.InterfaceInfoMapper;
import com.wanfeng.apis.model.dto.interfaceInfo.InterfaceInfoInvokeRequset;
import com.wanfeng.apis.model.dto.interfaceInfo.InterfaceInfoQueryRequset;
import com.wanfeng.apis.model.dto.interfaceInfo.InterfaceInfoUpdateRequset;
import com.wanfeng.apis.model.entity.InterfaceInfo;
import com.wanfeng.apis.model.entity.User;
import com.wanfeng.apis.model.vo.InterfaceInfoVO;
import com.wanfeng.apis.model.vo.user.UserDescVO;
import com.wanfeng.apis.model.vo.user.UserLoginVO;
import com.wanfeng.apis.service.InterfaceInfoService;
import com.wanfeng.apis.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 85975
 * @description 针对表【interface_info(接口信息)】的数据库操作Service实现
 * @createDate 2023-08-25 00:11:24
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {
    @Resource
    private UserService userService;
//    @Resource
//    WanfClient wanfClient;

    /**
     * 返回处理好的条件warpper
     *
     * @param queryRequset
     * @return
     */
    @Override
    public QueryWrapper<InterfaceInfo> getQueryWarpper(InterfaceInfoQueryRequset queryRequset) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        Long id = queryRequset.getId();
        if (null != id) {
            queryWrapper.eq("id", id);
        }
        String name = queryRequset.getName();
        if (StringUtils.hasText(name)) {
            queryWrapper.like("name", name);
        }
        String description = queryRequset.getDescription();
        if (StringUtils.hasText(description)) {
            queryWrapper.like("description", description);
        }
        String url = queryRequset.getUrl();
        if (StringUtils.hasText(url)) {
            queryWrapper.like("url", url);
        }
        String requestheader = queryRequset.getRequestheader();
        if (StringUtils.hasText(requestheader)) {
            queryWrapper.like("requestHeader", requestheader);
        }
        String responseheader = queryRequset.getResponseheader();
        if (StringUtils.hasText(responseheader)) {
            queryWrapper.like("responseHeader", responseheader);
        }
        Integer status = queryRequset.getStatus();
        if (null != status) {
            queryWrapper.eq("status", status);
        }
        String method = queryRequset.getMethod();
        if (StringUtils.hasText(method)) {
            queryWrapper.like("method", method);
        }
        Long userid = queryRequset.getUserid();
        if (null != userid) {
            queryWrapper.eq("userId", userid);
        }
        Date createtime = queryRequset.getCreatetime();
        Date updatetime = queryRequset.getUpdatetime();

        return queryWrapper;
    }

    @Override
    public List<InterfaceInfoVO> getPageVO(List<InterfaceInfo> records) {
        //判断是否为空
        if (CollectionUtils.isEmpty(records)) {
            return new ArrayList<>();
        }
        return records.stream().map(this::getinterfaceVO).collect(Collectors.toList());
    }

    @Override
    public boolean addInterface(InterfaceInfo info) {
        isEmptys(info);
        Long userid = info.getUserid();
        ThrowUtils.throwIf((null == userid), ErrorCode.PARAMS_ERROR);
        //根据名称进行判断，不能相同
        long count = this.count(new QueryWrapper<InterfaceInfo>().eq("name", info.getName()));
        ThrowUtils.throwIf(count > 0, ErrorCode.USER_EXSIT);
        return this.save(info);
    }

    @Override
    public boolean updateInterface(InterfaceInfoUpdateRequset requset) {
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(requset, interfaceInfo);
        isEmptys(interfaceInfo);
        //根据名称进行判断，不能相同
        InterfaceInfo byId = this.getById(requset.getId());
        if (!byId.getName().equals(interfaceInfo.getName())) {
            long count = this.count(new QueryWrapper<InterfaceInfo>().eq("name", requset.getName()));
            ThrowUtils.throwIf(count > 0, ErrorCode.USER_EXSIT);
        }
        return this.updateById(interfaceInfo);
    }


    @Override
    public boolean onlineInterface(IDRequest requset) {
        Long id = requset.getId();
        //判断id是否存在
        InterfaceInfo byId = this.getById(id);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(byId),ErrorCode.PARAMS_ERROR,"接口不存在");
        ThrowUtils.throwIf(byId.getStatus() == 1,ErrorCode.PARAMS_ERROR,"当前接口已发布状态");
        byId.setStatus(1);
        return this.updateById(byId);
    }

    @Override
    public boolean offlineInterface(IDRequest requset) {
        Long id = requset.getId();
        //判断id是否存在
        InterfaceInfo byId = this.getById(id);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(byId),ErrorCode.PARAMS_ERROR,"接口不存在");
        ThrowUtils.throwIf(byId.getStatus() == 0,ErrorCode.PARAMS_ERROR,"当前接口已离线状态");
        byId.setStatus(0);
        return this.updateById(byId);
    }

    @Override
    public InterfaceInfoVO getInterfaceInfoVOById(Long id) {
        InterfaceInfo byId = this.getById(id);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(byId), ErrorCode.OPERATION_ERROR, "接口数据不存在");
        InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
        BeanUtils.copyProperties(byId,interfaceInfoVO);
        Long userid = byId.getUserid();
        if (null != userid && userid != 0){
            User byId1 = userService.getById(userid);
            UserDescVO userDescVO = new UserDescVO();
            BeanUtils.copyProperties(byId1,userDescVO);
            interfaceInfoVO.setUser(userDescVO);
        }
        return interfaceInfoVO;
    }

    @Override
    public Object invokeClient(InterfaceInfoInvokeRequset invokeRequset, HttpServletRequest request) {
        //查询当前用户的信息
        UserLoginVO userLoginVO = userService.getloginUserVO(request);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(request),ErrorCode.NOT_LOGIN_ERROR,"请登录后进行操作");
        User byId = (User) userService.getById(userLoginVO.getId());
        String accesskey = byId.getAccesskey();
        String secretkey = byId.getSecretkey();
        String requestparams = invokeRequset.getRequestparams();
        ThrowUtils.throwIf(!StringUtils.hasText(requestparams),ErrorCode.PARAMS_ERROR);
        //解析requestparams
//        Gson gson = new Gson();
//        com.wfapi_client.model.entity.User jsonuser = gson.fromJson(requestparams, com.wfapi_client.model.entity.User.class);
//        WanfClient wanfClient = new WanfClient("http://localhost:8127/api/name", accesskey, secretkey);
//        return wanfClient.getUserNameByPOST(jsonuser);
        return null;
    }

    public InterfaceInfoVO getinterfaceVO(InterfaceInfo info) {
        if (null == info) {
            return null;
        }
        InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
        BeanUtils.copyProperties(info, interfaceInfoVO);
        //根据id查询用户
        User byId = userService.getById(info.getUserid());
        if (null != byId) {
            UserDescVO userDescVO = new UserDescVO();
            BeanUtils.copyProperties(byId, userDescVO);
            interfaceInfoVO.setUser(userDescVO);
        }
        return interfaceInfoVO;
    }

    public void isEmptys(InterfaceInfo info) {
        String name = info.getName();
        ThrowUtils.throwIf(!StringUtils.hasText(name), ErrorCode.PARAMS_ERROR);
        String url = info.getUrl();
        ThrowUtils.throwIf(!StringUtils.hasText(url), ErrorCode.PARAMS_ERROR);
        String method = info.getMethod();
        ThrowUtils.throwIf(!StringUtils.hasText(method), ErrorCode.PARAMS_ERROR);
    }
}




