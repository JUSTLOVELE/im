package com.hyjk.im.server.document;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author yangzl 2021.06.07
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Document(collection = "chat_message")
public class ChatDocEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;

    private String messageType;

    private String message;

    private String from;

    private String fromUserName;

    private String to;

    private String createTime;

    private String receiveUserOpId;

    private Integer isRead;

    private String originalFilename;

    private String fileName;

    private Integer isSend;

    private String sendOrReceive;

    private Integer length;

    private String thumpImage;

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiveUserOpId() {
        return receiveUserOpId;
    }

    public void setReceiveUserOpId(String receiveUserOpId) {
        this.receiveUserOpId = receiveUserOpId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getSendOrReceive() {
        return sendOrReceive;
    }

    public void setSendOrReceive(String sendOrReceive) {
        this.sendOrReceive = sendOrReceive;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getThumpImage() {
        return thumpImage;
    }

    public void setThumpImage(String thumpImage) {
        this.thumpImage = thumpImage;
    }
}
