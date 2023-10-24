package com.wanfeng.apis.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户调用接口信息表
 * @TableName user_interface_info
 */
@TableName(value ="user_interface_info")
@Data
public class UserInterfaceInfo implements Serializable {
    /**
     * 用户调用接口信息表的主键
     */
    @TableId(type = IdType.AUTO)
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

    /**
     * 创建时间`
     */
    private Date createtime;

    /**
     * 更新时间`
     */
    private Date updatetime;

    /**
     * 是否删除 0-未删 1-删除`
     */
    @TableLogic
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}