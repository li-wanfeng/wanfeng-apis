package com.wanfeng.apis.model.vo;
import lombok.Data;

import java.util.Date;

/**
 * @author 风车下跑
 * @create 2023-08-25
 */
@Data
public class UserInterfaceInfoVO {

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

    /**
     * 创建时间`
     */
    private Date createtime;

    /**
     * 更新时间`
     */
    private Date updatetime;

}
