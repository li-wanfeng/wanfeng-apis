package com.wanfeng.apis.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 风车下跑
 * @create 2023-08-06
 * 菜单信息
 */
@Data
public class Menu implements Serializable {
    private int id;
    private String menuName;
    private int type;
    private String url;
    private String icon;
    private int sort;
    private Menu[] children;

}
