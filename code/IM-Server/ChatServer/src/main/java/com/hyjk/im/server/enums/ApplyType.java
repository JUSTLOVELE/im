package com.hyjk.im.server.enums;

/**
 * @author yangzl 2021.07.28
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public enum ApplyType {

    FRIEND("好友", 1),
    GROUP("群", 2)
    ;

    private String text;

    private int value;

    ApplyType(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
