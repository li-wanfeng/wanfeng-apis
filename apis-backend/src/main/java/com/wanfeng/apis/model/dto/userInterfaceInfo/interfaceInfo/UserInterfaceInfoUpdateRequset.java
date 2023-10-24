package com.wanfeng.apis.model.dto.userInterfaceInfo.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 风车下跑
 * @create 2023-08-09
 * 新增封装 DT
 */
@Data
public class UserInterfaceInfoUpdateRequset implements Serializable {
    /**
     * 用户调用接口信息表的主键
     */
    private Long id;

    /**
     * 用户id`
     */
    private Long userid;

    /**
     * 接口id`
     */
    private Long interfaceinfoid;

    /**
     * 所有接口总调用次数
     */
    private Long totalnum;

    /**
     * 该接口剩余调用次数
     */
    private Long residuenum;

    /**
     * 0-可以正常调用该接口 1-不可以调用
     */
    private Integer status;


}
