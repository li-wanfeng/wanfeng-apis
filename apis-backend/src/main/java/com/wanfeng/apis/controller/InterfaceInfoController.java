package com.wanfeng.apis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.wanfeng.apis.annotation.AuthCheck;
import com.wanfeng.apis.common.*;
import com.wanfeng.apis.exception.ThrowUtils;
import com.wanfeng.apis.model.dto.interfaceInfo.InterfaceInfoInvokeRequset;
import com.wanfeng.apis.model.dto.interfaceInfo.InterfaceInfoQueryRequset;
import com.wanfeng.apis.model.dto.interfaceInfo.InterfaceInfoUpdateRequset;
import com.wanfeng.apis.model.entity.InterfaceInfo;
import com.wanfeng.apis.model.vo.InterfaceInfoVO;
import com.wanfeng.apis.service.InterfaceInfoService;
import io.swagger.annotations.Api;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 风车下跑
 * @create 2023-08-25
 */
@Api(tags = "接口模块")
@RestController
@RequestMapping("/interface")
public class InterfaceInfoController {
    @Resource
    private InterfaceInfoService service;

    /**
     * 分页查询接口
     *
     * @param queryRequset
     * @return
     */
    @PostMapping("/page")
    public BaseResponse<Page<InterfaceInfoVO>> PageInterfaceInfoVO(@RequestBody InterfaceInfoQueryRequset queryRequset) {
        ThrowUtils.throwIf(ObjectUtils.isEmpty(queryRequset), ErrorCode.PARAMS_ERROR);
        long current = queryRequset.getCurrent();
        long pageSize = queryRequset.getPageSize();
        Page<InterfaceInfo> infoPage = new Page<>(current, pageSize);
        Page<InterfaceInfo> page = service.page(infoPage, service.getQueryWarpper(queryRequset));
        Page<InterfaceInfoVO> infoVOPage = new Page<>(current, pageSize, page.getTotal());
        List<InterfaceInfoVO> list = service.getPageVO(page.getRecords());
        infoVOPage.setRecords(list);
        return ResultUtils.success(infoVOPage);
    }

    /**
     * 查询单条接口信息
     *
     * @param id 接口id
     * @return
     */
    @GetMapping()
    public BaseResponse<InterfaceInfoVO> getInterfaceInfoVOById(@RequestParam Long id) {
        ThrowUtils.throwIf((null == id || id == 0), ErrorCode.PARAMS_ERROR);
        InterfaceInfoVO interfaceInfoVO = service.getInterfaceInfoVOById(id);
        return ResultUtils.success(interfaceInfoVO, "获取接口成功");
    }

    /**
     * 新增接口
     *
     * @param info
     * @return
     */
    @PostMapping()
    public BaseResponse addInterface(@RequestBody InterfaceInfo info) {
        ThrowUtils.throwIf(ObjectUtils.isEmpty(info), ErrorCode.PARAMS_ERROR);
        boolean isadd = service.addInterface(info);
        return ResultUtils.success("新增接口数据成功");
    }

    /**
     * 删除接口
     */
    @DeleteMapping()
    public BaseResponse deleteInterface(@RequestBody DeleteRequest request) {
        ThrowUtils.throwIf((ObjectUtils.isEmpty(request) || null == request.getId()), ErrorCode.PARAMS_ERROR);
        boolean isdel = service.removeById(request.getId());
        ThrowUtils.throwIf(!isdel, ErrorCode.DELETE_ERROR);
        return ResultUtils.success("接口数据删除成功");
    }

    /**
     * 更新接口
     */
    @PutMapping()
    public BaseResponse updateInterface(@RequestBody InterfaceInfoUpdateRequset requset) {
        ThrowUtils.throwIf(ObjectUtils.isEmpty(requset), ErrorCode.PARAMS_ERROR);
        boolean isup = service.updateInterface(requset);
        ThrowUtils.throwIf(!isup, ErrorCode.UPDATE_ERROR);
        return ResultUtils.success("接口数据更新成功");
    }

    /**
     * 接口发布
     */
    @AuthCheck(role = 1)
    @PutMapping("/online")
    public BaseResponse onlineInterface(@RequestBody IDRequest requset) {
        ThrowUtils.throwIf(ObjectUtils.isEmpty(requset), ErrorCode.PARAMS_ERROR);
        boolean isup = service.onlineInterface(requset);
        ThrowUtils.throwIf(!isup, ErrorCode.UPDATE_ERROR);
        return ResultUtils.success("接口发布成功");
    }

    /**
     * 接口下线
     */
    @AuthCheck(role = 1)
    @PutMapping("/offline")
    public BaseResponse offlineInterface(@RequestBody IDRequest requset) {
        ThrowUtils.throwIf(ObjectUtils.isEmpty(requset), ErrorCode.PARAMS_ERROR);
        boolean isup = service.offlineInterface(requset);
        ThrowUtils.throwIf(!isup, ErrorCode.UPDATE_ERROR);
        return ResultUtils.success("接口下线成功");
    }

    /**
     * 在线调用接口
     * @param invokeRequset
     * @param request
     * @return
     */
    @PostMapping("invoke")
    public BaseResponse<Object> interfaceInfoinvoke(@RequestBody InterfaceInfoInvokeRequset invokeRequset, HttpServletRequest request){
        ThrowUtils.throwIf(ObjectUtils.isEmpty(invokeRequset) || ObjectUtils.isEmpty(invokeRequset.getId()), ErrorCode.PARAMS_ERROR);
        Object res = service.invokeClient(invokeRequset,request);
        return ResultUtils.success(res);
    }
}
