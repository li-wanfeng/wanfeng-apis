package com.wanfeng.apis.model.vo;

import com.wanfeng.apis.model.vo.user.UserDescVO;
import lombok.Data;

import java.util.Date;

/**
 * @author 风车下跑
 * @create 2023-08-25
 */
@Data
public class InterfaceInfoVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

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
     * 响应数据类型
     */
    private String responsetype;

    /**
     * 响应参数
     */
    private String responseparams;

    /**
     * 请求头
     */
    private String requestheader;

    /**
     * 响应头
     */
    private String responseheader;

    /**
     * 接口状态 0-关闭 1-开启
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建人
     */
    private UserDescVO user;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

}
