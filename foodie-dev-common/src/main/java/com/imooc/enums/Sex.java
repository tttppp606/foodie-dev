package com.imooc.enums;

/**
 * @Desc:性别枚举
 */
public enum Sex {
    woman(0,"女"),
    man(1,"男"),
    secret(2,"保密");

    public final int type;
    public final String value;

    Sex(int type, String value) {
        this.type=type;
        this.value=value;
    }
}
