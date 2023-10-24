package com.wanfeng.apis.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class IDRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}