package com.hyjk.im.common.utils.model;

/**
 * @author yangzl 2021.06.01
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public class Message {

    private String type;

    private String message;

    private String messageType;

    private String userOpId;

    private String receiveOpId;
    /**yyyy-mm-dd hh:mms:ss**/
    private String createTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserOpId() {
        return userOpId;
    }

    public void setUserOpId(String userOpId) {
        this.userOpId = userOpId;
    }

    public String getReceiveOpId() {
        return receiveOpId;
    }

    public void setReceiveOpId(String receiveOpId) {
        this.receiveOpId = receiveOpId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
