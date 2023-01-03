package com.hyjk.im.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2017-12-28
 * @version 1.00.00
 * @history:
 */
@TableName("tba_user")
public class UserTab implements Serializable {

	@TableId
	private String opId;

	private String userId;

	@TableField("`name`")
	private String name;

	@TableField("`password`")
	private String password;

	private Date createTime;

	private String orgId;

	private Integer category;

	private Date lastLogin;

	private String refEntityId;

	private Integer isStop;

	private Integer forumIntegration;

	@TableField("`level`")
	private Integer level;

	private Integer sources;

	private Integer categoryLevel;

	private String bgImg;

	private String wxId;

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getRefEntityId() {
		return refEntityId;
	}

	public void setRefEntityId(String refEntityId) {
		this.refEntityId = refEntityId;
	}

	public Integer getIsStop() {
		return isStop;
	}

	public void setIsStop(Integer isStop) {
		this.isStop = isStop;
	}

	public Integer getForumIntegration() {
		return forumIntegration;
	}

	public void setForumIntegration(Integer forumIntegration) {
		this.forumIntegration = forumIntegration;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSources() {
		return sources;
	}

	public void setSources(Integer sources) {
		this.sources = sources;
	}

	public Integer getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(Integer categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public String getBgImg() {
		return bgImg;
	}

	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
}
