package com.wanfeng.apis.service;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfeng.apis.model.dto.userInterfaceInfo.interfaceInfo.UserInterfaceInfoQueryRequset;
import com.wanfeng.apis.model.dto.userInterfaceInfo.interfaceInfo.UserInterfaceInfoUpdateRequset;
import com.wanfeng.apis.model.entity.UserInterfaceInfo;
import com.wanfeng.apis.model.vo.UserInterfaceInfoVO;

import java.util.List;

/**
* @author 85975
* @description 针对表【user_interface_info(用户调用接口信息表)】的数据库操作Service
* @createDate 2023-08-27 21:48:48
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    QueryWrapper<UserInterfaceInfo> getQueryWarpper(UserInterfaceInfoQueryRequset queryRequset);
    List<UserInterfaceInfoVO> getPageVO(List<UserInterfaceInfo> records);

    UserInterfaceInfoVO getUserInterfaceInfoVOById(Long id);

    boolean addUserInterface(UserInterfaceInfo info);

    boolean updateUserInterface(UserInterfaceInfoUpdateRequset requset);
}
