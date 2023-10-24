package com.wanfeng.apis.model.dto.userInterfaceInfo.interfaceInfo;



import com.wanfeng.apis.model.dto.base.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 风车下跑
 * @create 2023-08-09
 * 封装用户分页  条件  查询请求封装 DT
 */
@Data
public class UserInterfaceInfoQueryRequset extends PageRequest implements Serializable {

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
