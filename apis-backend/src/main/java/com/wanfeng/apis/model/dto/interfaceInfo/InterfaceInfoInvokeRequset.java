package com.wanfeng.apis.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 风车下跑
 * @create 2023-08-09
 * 封装用户分页  条件  查询请求封装 DT
 */
@Data
public class InterfaceInfoInvokeRequset implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求数据类型
     */
    private String requesttype;

    /**
     * 请求参数
     */
    private String requestparams;

    /**
     * 请求头
     */
    private String requestheader;

    /**
     * 请求类型
     */
    private String method;

}
