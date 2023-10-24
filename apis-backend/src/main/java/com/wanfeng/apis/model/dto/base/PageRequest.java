package com.wanfeng.apis.model.dto.base;


import com.wanfeng.apis.constant.CommonConstant;
import lombok.Data;

/**
 * @author 风车下跑
 * @create 2023-08-09
 * 分页请求封装
 */
@Data
public class PageRequest {
    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
