package com.hyjk.im.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * TbaDoctor entity. @author MyEclipse Persistence Tools
 */
@TableName("public_org_doctor")
public class PublicOrgDoctor implements java.io.Serializable {

	@TableId
	private String opId;
	private String orgId;
	private String deptId;
	private String orgName;
	private String doctorCode;
	private String doctorName;
	private String deptNmae;
	private Date workTime;
	private String phone;
	private String info;
	private String photoRes;
	private String practiceCert;
	private String technicalNo;
	private String technicalName;
	private String technicalNameLabel;
	private Integer positionalTitle;
	private Date birthday;
	private String subjectNo;
	private String idCard;
	private String photographPath;
	private Date registTime;
	private Date lastLogin;
	private Integer isStop;
	private String expert;
	private Integer sex;
	private Integer status;
	private Integer examState;
	private String examResult;
	private String practiceRes;
	private Integer phoneOpen;
	private Integer emailOpen;
	private Integer qqOpen;
	private String syncHisDeptId;
	private String syncHisDoctorId;
	private String syncHisDeptCode;
	private String syncHisDoctorCode;
	private String practiceInfoId;
	private String teacherQualificationId;
	private String teacherQualificationName;
	private String sciAchievement;
	private String acAchievement;
	private String personalSign;
	private String positionId;
	private String positionName;
	private String traineeId;

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDeptNmae() {
		return deptNmae;
	}

	public void setDeptNmae(String deptNmae) {
		this.deptNmae = deptNmae;
	}

	public Date getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPhotoRes() {
		return photoRes;
	}

	public void setPhotoRes(String photoRes) {
		this.photoRes = photoRes;
	}

	public String getPracticeCert() {
		return practiceCert;
	}

	public void setPracticeCert(String practiceCert) {
		this.practiceCert = practiceCert;
	}

	public String getTechnicalNo() {
		return technicalNo;
	}

	public void setTechnicalNo(String technicalNo) {
		this.technicalNo = technicalNo;
	}

	public String getTechnicalName() {
		return technicalName;
	}

	public void setTechnicalName(String technicalName) {
		this.technicalName = technicalName;
	}

	public String getTechnicalNameLabel() {
		return technicalNameLabel;
	}

	public void setTechnicalNameLabel(String technicalNameLabel) {
		this.technicalNameLabel = technicalNameLabel;
	}

	public Integer getPositionalTitle() {
		return positionalTitle;
	}

	public void setPositionalTitle(Integer positionalTitle) {
		this.positionalTitle = positionalTitle;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSubjectNo() {
		return subjectNo;
	}

	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhotographPath() {
		return photographPath;
	}

	public void setPhotographPath(String photographPath) {
		this.photographPath = photographPath;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Integer getIsStop() {
		return isStop;
	}

	public void setIsStop(Integer isStop) {
		this.isStop = isStop;
	}

	public String getExpert() {
		return expert;
	}

	public void setExpert(String expert) {
		this.expert = expert;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getExamState() {
		return examState;
	}

	public void setExamState(Integer examState) {
		this.examState = examState;
	}

	public String getExamResult() {
		return examResult;
	}

	public void setExamResult(String examResult) {
		this.examResult = examResult;
	}

	public String getPracticeRes() {
		return practiceRes;
	}

	public void setPracticeRes(String practiceRes) {
		this.practiceRes = practiceRes;
	}

	public Integer getPhoneOpen() {
		return phoneOpen;
	}

	public void setPhoneOpen(Integer phoneOpen) {
		this.phoneOpen = phoneOpen;
	}

	public Integer getEmailOpen() {
		return emailOpen;
	}

	public void setEmailOpen(Integer emailOpen) {
		this.emailOpen = emailOpen;
	}

	public Integer getQqOpen() {
		return qqOpen;
	}

	public void setQqOpen(Integer qqOpen) {
		this.qqOpen = qqOpen;
	}

	public String getSyncHisDeptId() {
		return syncHisDeptId;
	}

	public void setSyncHisDeptId(String syncHisDeptId) {
		this.syncHisDeptId = syncHisDeptId;
	}

	public String getSyncHisDoctorId() {
		return syncHisDoctorId;
	}

	public void setSyncHisDoctorId(String syncHisDoctorId) {
		this.syncHisDoctorId = syncHisDoctorId;
	}

	public String getSyncHisDeptCode() {
		return syncHisDeptCode;
	}

	public void setSyncHisDeptCode(String syncHisDeptCode) {
		this.syncHisDeptCode = syncHisDeptCode;
	}

	public String getSyncHisDoctorCode() {
		return syncHisDoctorCode;
	}

	public void setSyncHisDoctorCode(String syncHisDoctorCode) {
		this.syncHisDoctorCode = syncHisDoctorCode;
	}

	public String getPracticeInfoId() {
		return practiceInfoId;
	}

	public void setPracticeInfoId(String practiceInfoId) {
		this.practiceInfoId = practiceInfoId;
	}

	public String getTeacherQualificationId() {
		return teacherQualificationId;
	}

	public void setTeacherQualificationId(String teacherQualificationId) {
		this.teacherQualificationId = teacherQualificationId;
	}

	public String getTeacherQualificationName() {
		return teacherQualificationName;
	}

	public void setTeacherQualificationName(String teacherQualificationName) {
		this.teacherQualificationName = teacherQualificationName;
	}

	public String getSciAchievement() {
		return sciAchievement;
	}

	public void setSciAchievement(String sciAchievement) {
		this.sciAchievement = sciAchievement;
	}

	public String getAcAchievement() {
		return acAchievement;
	}

	public void setAcAchievement(String acAchievement) {
		this.acAchievement = acAchievement;
	}

	public String getPersonalSign() {
		return personalSign;
	}

	public void setPersonalSign(String personalSign) {
		this.personalSign = personalSign;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(String traineeId) {
		this.traineeId = traineeId;
	}
}