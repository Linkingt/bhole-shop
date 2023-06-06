package com.bhole.shop.common.base.enums;

/**
 * @program: bhole-shop-common-base
 * @description:
 * @author: joke
 * @date: 2023/6/5 17:54
 * @version: 1.0
 */
public enum YesNoEnum {

    /**
     * 是
     */
    YES(1, "是"),

    /**
     * 否
     */
    NO(2, "否");

    private Integer code;

    private String name;


    YesNoEnum(int code, String name){
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getName(Integer code) {
        for (YesNoEnum c : YesNoEnum.values()) {
            if (c.code.equals(code)) {
                return c.getName();
            }
        }
        return null;
    }
}
