package com.hyjk.im.server.document;

import com.hyjk.im.server.model.ChatMessage;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author yangzl 2021.06.18.
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Document(collection = "chat_list")
public class ChatListDocEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;

    private String messageType;

    private String message;

    private String from;

    private String to;

    private String finalMessageUserOpId;

    private String createTime;

    private String sendOrReceive;

    private String originalFilename;

    private String fileName;

    private String chatName;

    private String headImage;

    private Integer number = 0;

    public ChatListDocEntity() {

    }

    public ChatListDocEntity(ChatMessage chatMessage) {

        this.type = chatMessage.getType();
        this.messageType = chatMessage.getMessageType();
        this.message = chatMessage.getMessage();
        this.from = chatMessage.getFrom();
        this.to = chatMessage.getTo();
        this.createTime = chatMessage.getCreateTime();
        this.sendOrReceive = chatMessage.getSendOrReceive();
        this.originalFilename = chatMessage.getOriginalFilename();
        this.fileName = chatMessage.getSaveFileName();
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getFinalMessageUserOpId() {
        return finalMessageUserOpId;
    }

    public void setFinalMessageUserOpId(String finalMessageUserOpId) {
        this.finalMessageUserOpId = finalMessageUserOpId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSendOrReceive() {
        return sendOrReceive;
    }

    public void setSendOrReceive(String sendOrReceive) {
        this.sendOrReceive = sendOrReceive;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
