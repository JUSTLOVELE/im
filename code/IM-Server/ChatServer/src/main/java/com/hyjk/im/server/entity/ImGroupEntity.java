package com.hyjk.im.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangzl 2021.06.04
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@TableName("im_group")
public class ImGroupEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String opId;

    @TableField("`name`")
    private String name;

    private String introduction;

    private String notification;

    private String imId;

    @TableField("`type`")
    private Integer type;

    private String typeName;

    private Integer maxMemberCount;

    private Integer applyJoinOption;

    private String ownerId;

    @TableField("owner_opId")
    private String ownerOpId;

    private String ownerName;

    private Date createTime;

    private Date updateTime;

    private String headImg;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getMaxMemberCount() {
        return maxMemberCount;
    }

    public void setMaxMemberCount(Integer maxMemberCount) {
        this.maxMemberCount = maxMemberCount;
    }

    public Integer getApplyJoinOption() {
        return applyJoinOption;
    }

    public void setApplyJoinOption(Integer applyJoinOption) {
        this.applyJoinOption = applyJoinOption;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerOpId() {
        return ownerOpId;
    }

    public void setOwnerOpId(String ownerOpId) {
        this.ownerOpId = ownerOpId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
