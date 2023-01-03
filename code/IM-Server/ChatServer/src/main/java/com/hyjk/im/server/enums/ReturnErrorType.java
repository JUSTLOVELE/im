package com.hyjk.im.server.enums;

/**
 * @author yangzl 2021.07.27
 * @version 1.00.00
 * @Description: 返回错误时errorType的值
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public enum ReturnErrorType {

    NOT_FRIEND(1, "不是好友关系"),
    NOT_GROUP(2, "不是群成员关系")


    ;

    ReturnErrorType(int value, String desc) {
        this.desc = desc;
        this.value =value;
    }

    public String desc;

    public int value;

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
