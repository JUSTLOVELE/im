package com.hyjk.im.server.enums;

/**
 * @author yangzl 2021.06.07
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public enum MessageIsSend {

    SEND("送达", 1) ,
    NO_SEND("未送达", 2)
    ;

    private String desc;

    private int value;

    MessageIsSend(String desc, int value) {
        this.desc = desc;
        this.value =value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
