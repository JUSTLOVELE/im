package com.hyjk.im.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2019-02-12
 * @version 1.00.00
 * @history:
 */

@TableName("doctor_moment_comment_tbl")
public class DoctorMomentCommentTbl implements java.io.Serializable {

	private static final long serialVersionUID = 662152566506950840L;

	@TableId
	private String opId;
	
	private String momentId;
	
	private String userId;
	
	private String userName;
	
	private String doctorId;
	
	private String content;
	
	private Integer floorNumber;
	
	private String replyId;
	
	private String replyUserId;
	
	private String replyUserName;
	
	private Integer status;
	
	private Date createTime;
	
	public DoctorMomentCommentTbl() {}
	
	public DoctorMomentCommentTbl(String uuid, Date createTime, String content, String momentId) {
		
		this.momentId = momentId;
		this.opId = uuid;
		this.createTime = createTime;
		/*this.userId = user.getOpId();
		this.userName = user.getName();
		this.doctorId = user.getRefEntityId();*/
		this.content = content;
		this.status = 0;
	}
	
	public DoctorMomentCommentTbl(String uuid, Date createTime, UserTab user, String content, String momentId) {
		
		this.momentId = momentId;
		this.opId = uuid;
		this.createTime = createTime;
		this.userId = user.getOpId();
		this.userName = user.getName();
		this.doctorId = user.getRefEntityId();
		this.content = content;
		this.status = 0;
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getMomentId() {
		return momentId;
	}

	public void setMomentId(String momentId) {
		this.momentId = momentId;
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

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}

	public String getReplyUserName() {
		return replyUserName;
	}

	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
