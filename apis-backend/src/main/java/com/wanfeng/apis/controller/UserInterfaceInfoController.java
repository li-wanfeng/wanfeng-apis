package com.wanfeng.apis.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanfeng.apis.common.BaseResponse;
import com.wanfeng.apis.common.DeleteRequest;
import com.wanfeng.apis.common.ErrorCode;
import com.wanfeng.apis.common.ResultUtils;
import com.wanfeng.apis.exception.ThrowUtils;
import com.wanfeng.apis.model.dto.userInterfaceInfo.interfaceInfo.UserInterfaceInfoQueryRequset;
import com.wanfeng.apis.model.dto.userInterfaceInfo.interfaceInfo.UserInterfaceInfoUpdateRequset;
import com.wanfeng.apis.model.entity.UserInterfaceInfo;
import com.wanfeng.apis.model.vo.UserInterfaceInfoVO;
import com.wanfeng.apis.service.UserInterfaceInfoService;
import io.swagger.annotations.Api;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 风车下跑
 * @create 2023-08-25
 */
@Api(tags = "调用接口信息记录模块")
@RestController
@RequestMapping("/userInterface")
public class UserInterfaceInfoController {
    @Resource
    private UserInterfaceInfoService service;

    /**
     * 分页查询接口
     *
     * @param queryRequset
     * @return
     */
    @PostMapping("/page")
    public BaseResponse<Page<UserInterfaceInfoVO>> PageUserInterfaceInfoVO(@RequestBody UserInterfaceInfoQueryRequset queryRequset) {
        ThrowUtils.throwIf(ObjectUtils.isEmpty(queryRequset), ErrorCode.PARAMS_ERROR);
        long current = queryRequset.getCurrent();
        long pageSize = queryRequset.getPageSize();
        Page<UserInterfaceInfo> infoPage = new Page<>(current, pageSize);
        Page<UserInterfaceInfo> page = service.page(infoPage, service.getQueryWarpper(queryRequset));

        Page<UserInterfaceInfoVO> infoVOPage = new Page<>(current, pageSize, page.getTotal());
        List<UserInterfaceInfoVO> list = service.getPageVO(page.getRecords());
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
    public BaseResponse<UserInterfaceInfoVO> getUserInterfaceInfoVOById(@RequestParam Long id) {
        ThrowUtils.throwIf((null == id || id == 0), ErrorCode.PARAMS_ERROR);
        UserInterfaceInfoVO interfaceInfoVO = service.getUserInterfaceInfoVOById(id);
        return ResultUtils.success(interfaceInfoVO, "获取接口成功");
    }

    /**
     * 新增接口
     *
     * @param info
     * @return
     */
    @PostMapping()
    public BaseResponse addUserInterface(@RequestBody UserInterfaceInfo info) {
        ThrowUtils.throwIf(ObjectUtils.isEmpty(info), ErrorCode.PARAMS_ERROR);
        boolean isadd = service.addUserInterface(info);
        return ResultUtils.success("新增接口数据成功");
    }

    /**
     * 删除接口
     */
    @DeleteMapping()
    public BaseResponse deleteUserInterface(@RequestBody DeleteRequest request) {
        ThrowUtils.throwIf((ObjectUtils.isEmpty(request) || null == request.getId()), ErrorCode.PARAMS_ERROR);
        boolean isdel = service.removeById(request.getId());
        ThrowUtils.throwIf(!isdel, ErrorCode.DELETE_ERROR);
        return ResultUtils.success("接口数据删除成功");
    }

    /**
     * 更新接口
     */
    @PutMapping()
    public BaseResponse updateUserInterface(@RequestBody UserInterfaceInfoUpdateRequset requset) {
        ThrowUtils.throwIf(ObjectUtils.isEmpty(requset), ErrorCode.PARAMS_ERROR);
        boolean isup = service.updateUserInterface(requset);
        ThrowUtils.throwIf(!isup, ErrorCode.UPDATE_ERROR);
        return ResultUtils.success("接口数据更新成功");
    }


}
