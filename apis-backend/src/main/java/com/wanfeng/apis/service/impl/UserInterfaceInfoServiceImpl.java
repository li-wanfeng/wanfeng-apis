package com.wanfeng.apis.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfeng.apis.common.ErrorCode;
import com.wanfeng.apis.exception.ThrowUtils;
import com.wanfeng.apis.mapper.UserInterfaceInfoMapper;
import com.wanfeng.apis.model.dto.userInterfaceInfo.interfaceInfo.UserInterfaceInfoQueryRequset;
import com.wanfeng.apis.model.dto.userInterfaceInfo.interfaceInfo.UserInterfaceInfoUpdateRequset;
import com.wanfeng.apis.model.entity.UserInterfaceInfo;
import com.wanfeng.apis.model.vo.UserInterfaceInfoVO;
import com.wanfeng.apis.service.UserInterfaceInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 85975
* @description 针对表【user_interface_info(用户调用接口信息表)】的数据库操作Service实现
* @createDate 2023-08-27 21:48:48
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {
    @Override
    public QueryWrapper<UserInterfaceInfo> getQueryWarpper(UserInterfaceInfoQueryRequset queryRequset) {

        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        Long id = queryRequset.getId();
        Long userid = queryRequset.getUserid();
        Long interfaceinfoid = queryRequset.getInterfaceinfoid();
        if (null != id && null != userid && null != interfaceinfoid) {
            queryWrapper.eq("id", id).eq("userId", userid).eq("interfaceInfoId", interfaceinfoid);
        }
        Long totalnum = queryRequset.getTotalnum();
        Long residuenum = queryRequset.getResiduenum();
        if (null != totalnum && totalnum >=0){
            queryWrapper.eq("totalNum", totalnum);
        }
        if (null != residuenum && residuenum >=0){
            queryWrapper.eq("residueNum", residuenum);
        }
        Integer status = queryRequset.getStatus();
        if (null != status) {
            queryWrapper.eq("status", status);
        }

        Date createtime = queryRequset.getCreatetime();
        Date updatetime = queryRequset.getUpdatetime();

        return queryWrapper;
    }

    @Override
    public List<UserInterfaceInfoVO> getPageVO(List<UserInterfaceInfo> records)  {
        //判断是否为空
        if (CollectionUtils.isEmpty(records)) {
            return new ArrayList<>();
        }
        return records.stream().map(this::getUserInterfaceVO).collect(Collectors.toList());
    }

    @Override
    public UserInterfaceInfoVO getUserInterfaceInfoVOById(Long id)  {
        UserInterfaceInfo byId = this.getById(id);
        ThrowUtils.throwIf(ObjectUtils.isEmpty(byId), ErrorCode.OPERATION_ERROR, "接口调用数据不存在");
        UserInterfaceInfoVO interfaceInfoVO = new UserInterfaceInfoVO();
        BeanUtils.copyProperties(byId,interfaceInfoVO);
        return interfaceInfoVO;
    }

    @Override
    public boolean addUserInterface(UserInterfaceInfo info)  {
        ThrowUtils.throwIf(info.getStatus() != 0,ErrorCode.OPERATION_ERROR,"不可正常调用该接口");
        Long userid = info.getUserid();
        Long interfaceinfoid = info.getInterfaceinfoid();
        ThrowUtils.throwIf((null == userid || null == interfaceinfoid), ErrorCode.PARAMS_ERROR);
        return this.save(info);
    }

    @Override
    public boolean updateUserInterface(UserInterfaceInfoUpdateRequset requset) {
        UserInterfaceInfo interfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(requset, interfaceInfo);
        UserInterfaceInfo byId = this.getById(requset.getId());
        ThrowUtils.throwIf(ObjectUtils.isEmpty(byId), ErrorCode.OPERATION_ERROR, "接口调用数据不存在");
        return this.updateById(interfaceInfo);
    }

    public UserInterfaceInfoVO getUserInterfaceVO(UserInterfaceInfo info) {
        if (null == info) {
            return null;
        }
        UserInterfaceInfoVO interfaceInfoVO = new UserInterfaceInfoVO();
        BeanUtils.copyProperties(info, interfaceInfoVO);
        return interfaceInfoVO;
    }

}




