package com.hyjk.im.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hyjk.im.common.utils.UUIDGenerator;

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
@TableName("im_group_person_relation_tbl")
public class ImGroupPersonRelationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String opId;

    private String groupOpId;

    private String userOpId;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public ImGroupPersonRelationEntity() {

    }

    public ImGroupPersonRelationEntity(String groupOpId, String userOpId) {

        this.opId = UUIDGenerator.getUUID();
        this.groupOpId = groupOpId;
        this.userOpId = userOpId;
        this.createTime = new Date();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getGroupOpId() {
        return groupOpId;
    }

    public void setGroupOpId(String groupOpId) {
        this.groupOpId = groupOpId;
    }

    public String getUserOpId() {
        return userOpId;
    }

    public void setUserOpId(String userOpId) {
        this.userOpId = userOpId;
    }
}
