package com.wanfeng.apis.service;




import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfeng.apis.common.IDRequest;
import com.wanfeng.apis.model.dto.interfaceInfo.InterfaceInfoInvokeRequset;
import com.wanfeng.apis.model.dto.interfaceInfo.InterfaceInfoQueryRequset;
import com.wanfeng.apis.model.dto.interfaceInfo.InterfaceInfoUpdateRequset;
import com.wanfeng.apis.model.entity.InterfaceInfo;
import com.wanfeng.apis.model.vo.InterfaceInfoVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 85975
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-08-25 00:11:24
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    QueryWrapper<InterfaceInfo> getQueryWarpper(InterfaceInfoQueryRequset queryRequset);

    List<InterfaceInfoVO> getPageVO(List<InterfaceInfo> records);

    boolean addInterface(InterfaceInfo info);

    boolean updateInterface(InterfaceInfoUpdateRequset requset);

    boolean onlineInterface(IDRequest requset);

    boolean offlineInterface(IDRequest requset);

    InterfaceInfoVO getInterfaceInfoVOById(Long id);

    Object invokeClient(InterfaceInfoInvokeRequset invokeRequset, HttpServletRequest request);
}
