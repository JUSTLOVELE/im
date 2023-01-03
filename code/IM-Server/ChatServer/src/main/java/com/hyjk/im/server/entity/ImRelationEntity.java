package com.hyjk.im.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hyjk.im.common.utils.UUIDGenerator;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangzl 2021.07.28
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@TableName("im_relationship")
public class ImRelationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    public String opId;

    @TableField("user_opId")
    public String userOpId;

    public String userId;

    public String userName;

    @TableField("friend_opId")
    public String friendOpId;

    public String friendId;

    public String friendName;

    public Integer isBlack = 0;

    public Integer isStop = 1;

    public String groupName;

    public Date createTime;

    public ImRelationEntity() {

    }

    public ImRelationEntity(UserTab user, UserTab friend) {

        this.opId = UUIDGenerator.getUUID();
        this.userOpId = user.getOpId();
        this.userId = user.getUserId();
        this.userName = user.getName();
        this.friendOpId = friend.getOpId();
        this.friendId = friend.getUserId();
        this.friendName = friend.getName();
        this.createTime = new Date();

    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getUserOpId() {
        return userOpId;
    }

    public void setUserOpId(String userOpId) {
        this.userOpId = userOpId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFriendOpId() {
        return friendOpId;
    }

    public void setFriendOpId(String friendOpId) {
        this.friendOpId = friendOpId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Integer getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(Integer isBlack) {
        this.isBlack = isBlack;
    }

    public Integer getIsStop() {
        return isStop;
    }

    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
