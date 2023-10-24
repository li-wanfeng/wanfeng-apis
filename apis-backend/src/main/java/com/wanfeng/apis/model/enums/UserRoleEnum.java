package com.wanfeng.apis.model.enums;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 风车下跑
 * @create 2023-07-30
 */
public enum UserRoleEnum {

    USER("用户", 0),
    ADMIN("管理员", 1),
    BAN("被封号", 2);

    private final String text;

    private final Integer value;

    UserRoleEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static UserRoleEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value == (value)) {
                return anEnum;
            }
        }
        return null;
    }
    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
