package com.imooc.enums;

public enum YesOrNo {

    NO(0,"是"),
    YES(1,"否");

    public final int type;
    public final String value;

    YesOrNo(int type, String value) {
        this.type=type;
        this.value=value;
    }
}
