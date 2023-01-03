package com.hyjk.im.server.service;

/**
 * @author yangzl 2021.08.06
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public interface DoctorGroupService {

    /**
     * 推送删除的医生圈评论的消息
     * 二级评论：通知发该条医生圈的人以及被评论者
     * 一级评论：通知发该条医生圈的人
     * @param doctorMomentCommentTblOpId
     */
    public void sendDoctorGroupDeleteComment(String doctorMomentCommentTblOpId);


    /**
     * 推送新增的医生圈评论的消息
     * 一级评论:比如B评论A，假如A和B是同一人则不推送,若不为同一人除了给A发送消息外，还要给评论这条消息的人且这些人还要为B的好友发消息
     * 二级评论推送给B和被评论人
     * @param
     */
    public void sendDoctorGroupComment(String doctorMomentCommentTblOpId);

    /**
     * 推送医生圈点赞的消息
     * 假设B给A点赞,除了给A发送消息外，还要给点赞这条消息的人且这些人还要为B的好友发消息
     * @param groupMessageId:好友圈id
     * @param userOpId:本条好友圈用户opid
     * @param thumpUpUserOpId:点赞的用户opid
     * @param thumpUserName:点赞的用户名
     */
    public void sendDoctorGroupThumpUp(String groupMessageId, String userOpId, String thumpUpUserOpId, String thumpUserName);

    /**    推送删除医生圈点赞的消息
     *      * 假设A删除了对B的点赞
     *      * 则推送给B即可取消点
     * @param groupMessageId
     * @param userOpId
     * @param cancelThumpUpUserOpId
     * @param cancelthumpUserName
     */
    public void sendDoctorGroupCancelThumpUp(String groupMessageId, String userOpId, String cancelThumpUpUserOpId, String cancelthumpUserName);

    /**
     * 好友圈发送新消息通知IM
     * @param userOpId
     */
    public void sendNewMessage(String userOpId);
}
