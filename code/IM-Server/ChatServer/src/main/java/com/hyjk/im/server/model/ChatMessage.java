package com.hyjk.im.server.model;

import io.netty.channel.Channel;

/**
 * @author yangzl 2021.06.02
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public class ChatMessage {

    private String type;

    private String userOpId;

    private String messageType;

    private String message;

    private String thumpImage;

    private String from;

    private String to;

    private String createTime;

    private String receiveUserOpId;

    private Integer isRead;

    private String originalFilename;

    private String saveFileName;

    private String contentType;

    private Integer isSend;

    private Integer limit;

    private Integer page;

    private String startTime;

    private String endTime;

    private String sendOrReceive;

    private String targetOpId;

    private String groupId;

    private String fromName;

    private String toName;

    private String headImage;

    private String userName;

    private String phone;

    private Integer length = 0;

    private Integer groupType;

    private Integer applyJoinOption;

    private String groupName;

    private String returnAckType;

    private Integer applyType;

    private String fromUserName;

    public Integer getApplyType() {
        return applyType;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getReturnAckType() {
        return returnAckType;
    }

    public void setReturnAckType(String returnAckType) {
        this.returnAckType = returnAckType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Integer getApplyJoinOption() {
        return applyJoinOption;
    }

    public void setApplyJoinOption(Integer applyJoinOption) {
        this.applyJoinOption = applyJoinOption;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUserOpId() {
        return userOpId;
    }

    public void setUserOpId(String userOpId) {
        this.userOpId = userOpId;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSendOrReceive() {
        return sendOrReceive;
    }

    public void setSendOrReceive(String sendOrReceive) {
        this.sendOrReceive = sendOrReceive;
    }

    public String getTargetOpId() {
        return targetOpId;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setTargetOpId(String targetOpId) {
        this.targetOpId = targetOpId;
    }

    public String getThumpImage() {
        return thumpImage;
    }

    public void setThumpImage(String thumpImage) {
        this.thumpImage = thumpImage;
    }
}
