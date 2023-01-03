package com.hyjk.im.server.entity;

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
@TableName("im_apply_list")
public class ImApplyListEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String opId;

    public Integer applyType;

    private String fromUserOpId;

    private String toUserOpId;

    private String groupOpId;

    private String applyId;

    private Integer applyStatus = 0;

    private Date createTime;

    public ImApplyListEntity() {

    }

    public ImApplyListEntity(String fromUserOpId, String toUserOpId, Integer applyType, String groupOpId) {

        this.opId = UUIDGenerator.getUUID();
        this.createTime = new Date();
        this.toUserOpId = toUserOpId;
        this.fromUserOpId = fromUserOpId;
        this.applyType = applyType;
        this.groupOpId = groupOpId;
    }

    public ImApplyListEntity(String fromUserOpId, String toUserOpId, Integer applyType) {

        this.opId = UUIDGenerator.getUUID();
        this.createTime = new Date();
        this.toUserOpId = toUserOpId;
        this.fromUserOpId = fromUserOpId;
        this.applyType = applyType;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getFromUserOpId() {
        return fromUserOpId;
    }

    public void setFromUserOpId(String fromUserOpId) {
        this.fromUserOpId = fromUserOpId;
    }

    public String getToUserOpId() {
        return toUserOpId;
    }

    public void setToUserOpId(String toUserOpId) {
        this.toUserOpId = toUserOpId;
    }

    public String getGroupOpId() {
        return groupOpId;
    }

    public void setGroupOpId(String groupOpId) {
        this.groupOpId = groupOpId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
