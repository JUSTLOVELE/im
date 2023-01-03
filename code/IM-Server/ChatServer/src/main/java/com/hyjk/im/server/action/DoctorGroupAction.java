package com.hyjk.im.server.action;

import com.hyjk.im.common.utils.CommonConstant;
import com.hyjk.im.common.utils.CommonResult;
import com.hyjk.im.server.core.Base;
import com.hyjk.im.server.service.DoctorGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzl 2021.08.06
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@RestController
public class DoctorGroupAction extends Base {

    @Autowired
    private DoctorGroupService _doctorGroupService;

    /**
     * 推送删除的医生圈评论的消息
     * 二级评论：通知发该条医生圈的人以及被评论者
     * 一级评论：通知发该条医生圈的人
     * @param doctorMomentCommentTblOpId
     */
    @RequestMapping(value = "/doctorGroupAction/sendDoctorGroupDeleteComment", produces = "application/json;charset=utf-8")
    public CommonResult sendDoctorGroupDeleteComment(String doctorMomentCommentTblOpId) {

        _doctorGroupService.sendDoctorGroupDeleteComment(doctorMomentCommentTblOpId);
        return CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS);
    }


    /**
     * 推送新增的医生圈评论的消息
     * 一级评论:比如B评论A，假如A和B是同一人则不推送,若不为同一人除了给A发送消息外，还要给评论这条消息的人且这些人还要为B的好友发消息
     * 二级评论推送给B和被评论人
     * @param
     */
    @RequestMapping(value = "/doctorGroupAction/sendDoctorGroupComment", produces = "application/json;charset=utf-8")
    public CommonResult sendDoctorGroupComment(String doctorMomentCommentTblOpId) {

        _doctorGroupService.sendDoctorGroupComment(doctorMomentCommentTblOpId);
        return CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS);
    }

    /**
     * 推送医生圈点赞的消息
     * 假设B给A点赞,除了给A发送消息外，还要给点赞这条消息的人且这些人还要为B的好友发消息
     * @param groupMessageId:好友圈id
     * @param userOpId:本条好友圈用户opid
     * @param thumpUpUserOpId:点赞的用户opid
     * @param thumpUserName:点赞的用户名
     */
    @RequestMapping(value = "/doctorGroupAction/sendDoctorGroupThumpUp", produces = "application/json;charset=utf-8")
    public CommonResult sendDoctorGroupThumpUp(String groupMessageId, String userOpId, String thumpUpUserOpId, String thumpUserName) {

        _doctorGroupService.sendDoctorGroupThumpUp(groupMessageId, userOpId, thumpUpUserOpId, thumpUserName);
        return CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS);
    }

    /**
     * 发送新消息
     * @param userOpId
     * @return
     */
    @RequestMapping(value = "/doctorGroupAction/sendNewMessage", produces = "application/json;charset=utf-8")
    public CommonResult sendNewMessage(String userOpId) {

        _doctorGroupService.sendNewMessage(userOpId);
        return CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS);
    }

    /**
     *      * 推送删除医生圈点赞的消息
     *      * 假设A删除了对B的点赞
     *      * 则推送给B即可
     * @param groupMessageId
     * @param userOpId
     * @param cancelThumpUpUserOpId
     * @param cancelthumpUserName
     */
    @RequestMapping(value = "/doctorGroupAction/sendDoctorGroupCancelThumpUp", produces = "application/json;charset=utf-8")
    public CommonResult sendDoctorGroupCancelThumpUp(String groupMessageId, String userOpId, String cancelThumpUpUserOpId, String cancelthumpUserName) {

        _doctorGroupService.sendDoctorGroupCancelThumpUp(groupMessageId, userOpId, cancelThumpUpUserOpId, cancelthumpUserName);
        return CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS);
    }

}
