package com.hyjk.im.server.service.impl;

import com.hyjk.im.common.utils.CommonConstant;
import com.hyjk.im.common.utils.CommonResult;
import com.hyjk.im.common.utils.DateUtil;
import com.hyjk.im.common.utils.UUIDGenerator;
import com.hyjk.im.server.core.Base;
import com.hyjk.im.server.dao.DoctorGroupDao;
import com.hyjk.im.server.dao.ImRelationshipDao;
import com.hyjk.im.server.dao.UserDao;
import com.hyjk.im.server.entity.DoctorMomentCommentTbl;
import com.hyjk.im.server.entity.DoctorMomentTbl;
import com.hyjk.im.server.entity.UserTab;
import com.hyjk.im.server.enums.UserCategory;
import com.hyjk.im.server.mapper.DoctorMomentCommentMapper;
import com.hyjk.im.server.mapper.DoctorMomentMapper;
import com.hyjk.im.server.mapper.TbaUserMapper;
import com.hyjk.im.server.service.DoctorGroupService;
import com.hyjk.im.server.util.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author yangzl 2021.08.06
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DoctorGroupServiceImpl extends Base implements DoctorGroupService {


    @Autowired
    private ImRelationshipDao _imRelationshipDao;

    @Autowired
    private UserDao _userDao;

    @Autowired
    private DoctorGroupDao _doctorGroupDao;

    @Autowired
    private TbaUserMapper _tbaUserMapper;

    @Autowired
    private DoctorMomentCommentMapper _doctorMomentCommentMapper;

    @Autowired
    private DoctorMomentMapper _doctorMomentMapper;

    private final static Log _logger = LogFactory.getLog(DoctorGroupServiceImpl.class);

    @Override
    public void sendDoctorGroupDeleteComment(String doctorMomentCommentTblOpId) {

        DoctorMomentCommentTbl d = _doctorMomentCommentMapper.selectById(doctorMomentCommentTblOpId);
        _doctorMomentCommentMapper.deleteById(d);
        DoctorMomentTbl doctorMomentTbl = _doctorMomentMapper.selectById(d.getMomentId());

        if(d.getUserId().equals(doctorMomentTbl.getUserId())) {
            //自己删除自己的评论不推送
            return ;
        }

        List<Map<String,Object>> datas = new ArrayList<Map<String, Object>>();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Constant.Key.COMMENTID, d.getOpId());
        data.put(Constant.Key.GROUPMESSAGEID, doctorMomentTbl.getOpId());
        data.put(Constant.Key.DELETE, true);
        data.put(Constant.Key.CREATE_TIME, DateUtil.toString(d.getCreateTime(), DateUtil.YMDHMS));
//		List<Map<String, Object>> users = _userDao.queryDoctorInfoByUserOpId(d.getUserId());
        UserTab commentUser = _tbaUserMapper.selectById(d.getUserId());
        List<Map<String, Object>> users = null;

        if(commentUser.getCategory() == UserCategory.TRAINEE.getValue()) {
            users = _userDao.queryTrainInfoByUserOpId(d.getUserId());
        }else {
            users = _userDao.queryDoctorInfoByUserOpId(d.getUserId());
        }

        if(users != null && users.size() == 1) {

            data.put(Constant.Key.NAME, users.get(0).get(Constant.Key.NAME));
            data.put(Constant.Key.HEAD_IMAGE, users.get(0).get(Constant.Key.HEAD_IMAGE));
        }

        datas.add(data);
        CommonResult result = CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS, datas, 1L, Constant.Type.DEL_COMMENT);
        this.writeToChannel(doctorMomentTbl.getUserId(), result);
    }

    @Override
    public void sendDoctorGroupComment(String doctorMomentCommentTblOpId) {
        //睡一下 让数据插入
        try{
            Thread.sleep(1000);
        }catch (Exception e) {
            _logger.error("", e);
        }

        DoctorMomentCommentTbl d = _doctorMomentCommentMapper.selectById(doctorMomentCommentTblOpId);
        DoctorMomentTbl doctorMomentTbl = _doctorMomentMapper.selectById(d.getMomentId());
        List<Map<String,Object>> datas = new ArrayList<Map<String, Object>>();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Constant.Key.CONTENT, d.getContent());
        data.put(Constant.Key.USER_NAME, d.getUserName());
        data.put(Constant.Key.USER_ID, d.getUserId());
        data.put(Constant.Key.COMMENTID, d.getOpId());
        data.put(Constant.Key.CREATE_TIME, DateUtil.toString(d.getCreateTime(), DateUtil.YMDHMS));
        data.put(Constant.Key.REPLYUSERID, d.getReplyUserId());
        data.put(Constant.Key.REPLYUSERNAME, d.getReplyUserName());
        data.put(Constant.Key.GROUPMESSAGEID, doctorMomentTbl.getOpId());
        data.put(Constant.Key.DELETE, false);
        UserTab commentUser = _tbaUserMapper.selectById(d.getUserId());
        List<Map<String, Object>> users = null;

        if(commentUser.getCategory() == UserCategory.TRAINEE.getValue()) {
            users = _userDao.queryTrainInfoByUserOpId(d.getUserId());
        }else {
            users = _userDao.queryDoctorInfoByUserOpId(d.getUserId());
        }

        if(users != null && users.size() == 1) {

            data.put(Constant.Key.NAME, users.get(0).get(Constant.Key.NAME));
            data.put(Constant.Key.HEAD_IMAGE, users.get(0).get(Constant.Key.HEAD_IMAGE));
        }

        datas.add(data);
        CommonResult result = CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS, datas, 1L, Constant.Type.GET_COMMENT);

        if((d.getUserId().equals(doctorMomentTbl.getUserId())) &&
                d.getReplyUserId() == null || "".equals(d.getReplyUserId())

        ) {
            //自己评论自己并且是一级评论不推送
            sendFirstOrderCommentMyself(doctorMomentTbl.getOpId(), commentUser.getOpId(), result);
            return ;
        }

        if(d.getReplyUserId() != null && !"".equals(d.getReplyUserId())) {
            //二级评论,只能对方看到
            //userA = d.getUserId();
            //userB = d.getReplyUserId();
            UserTab user = _tbaUserMapper.selectById(d.getReplyUserId());

            if(user == null) {
                return ;
            }

            if(d.getUserId().equals(doctorMomentTbl.getUserId())) {
                //二级评论时发好友圈和评论人是一个人,那么只用通知被评论人
                this.writeToChannel(d.getUserId(), result);
            }else {
                sendDoctorGroupOperation(doctorMomentTbl, user, result);
            }

        }else {
            //一级评论
            List<Map<String, Object>> relations = _imRelationshipDao.querySameRelation(d.getUserId(), doctorMomentTbl.getUserId());

            if(relations == null || relations.size() == 0) {
                //没有好友不推送,没有好友其实是报错了,因为如果没好友不可能看到医生圈
                return ;
            }

            List<Map<String, Object>> comments = _doctorGroupDao.queryFirstOrderComment(doctorMomentTbl.getOpId());
            UserTab user = _tbaUserMapper.selectById(doctorMomentTbl.getUserId());//发该条医生圈的人的对象

            if(comments == null || comments.size() == 0) {
                //没人评论,就推给发送该条朋友圈的id
                this.writeToChannel(user.getOpId(), result);
            }else {
                //有人评论
                List<String> alias = new ArrayList<String>();
                alias.add(user.getOpId());//一定要推送给发该条朋友圈的人

                for(int i=0; i<comments.size(); i++) {

                    String tUserOpId = comments.get(i).get(Constant.Key.USEROPID).toString();

                    for(int j=0; j<relations.size(); j++) {

                        String friendUserOpID = relations.get(j).get(Constant.Key.FRIEND_OPID).toString();

                        if(tUserOpId.equals(friendUserOpID) && !alias.contains(friendUserOpID)) {
                            alias.add(friendUserOpID);
                        }
                    }
                }

                if(alias.size() > 0) {
                    for(String id: alias) {
                        this.writeToChannel(id, result);
                    }
                }
            }
        }
    }

    /**
     * 二级评论调用,删除评论
     * A是发朋友圈的人
     * B是给A这条朋友圈 点赞，评论的人,只让A和被评论人看到
     * 因为是只让A和被评论的人看到所有只有两条
     * @param doctorMomentTbl:A
     * @param user:B
     * @param result
     */
    private void sendDoctorGroupOperation(DoctorMomentTbl doctorMomentTbl, UserTab user, CommonResult result) {

        UserTab u = _tbaUserMapper.selectById( doctorMomentTbl.getUserId());

        if(u.getUserId().equals(user.getUserId())) {
            //如果相同说明被评论人和发朋友圈人是同一人
            this.writeToChannel(user.getOpId(), result);
        }else {
            //被评论人和发朋友圈人不是同一人
            this.writeToChannel(user.getOpId(), result);//被评论的人
            this.writeToChannel(u.getOpId(), result);//该条朋友圈id
        }
    }

    /**
     * 发好友圈人自己评论自己,推送给评论该好友圈的好友
     * @param goupId:好友圈id
     * @param userOpId:发好友圈的用userOpId(tba_user表的op_id)
     */
    private void sendFirstOrderCommentMyself(String goupId, String userOpId, CommonResult result) {

        List<Map<String, Object>> comments = _doctorGroupDao.queryFirstOrderComment(goupId);

        if (comments != null && comments.size() > 0) {
            //有人评论
            List<String> alias = new ArrayList<String>();

            for (int i = 0; i < comments.size(); i++) {

                String uOpId = comments.get(i).get(Constant.Key.USEROPID).toString();

                if (!alias.contains(userOpId) && !uOpId.equals(userOpId)) {
                    alias.add(uOpId);
                }
            }

            if (alias.size() > 0) {
                for(String id: alias) {
                    this.writeToChannel(id, result);
                }
            }
        }
    }

    @Override
    public void sendDoctorGroupThumpUp(String groupMessageId, String userOpId, String thumpUpUserOpId, String thumpUserName) {

        if(userOpId.equals(thumpUpUserOpId)) {
            //自己点赞自己不推送
            return ;
        }

        List<Map<String, Object>> relations = _imRelationshipDao.querySameRelation(userOpId, thumpUpUserOpId);
        //这里有BUG，如果两个用户的共同好友只有对方，这里就不会推送了,可以改造成直接给userOpId推送一条消息就正确了
        if(relations == null || relations.size() == 0) {
            //没有好友不推送,没有好友其实是报错了,因为如果没好友不可能看到医生圈
            return ;
        }

        List<Map<String,Object>> datas = new ArrayList<Map<String, Object>>();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Constant.Key.THUMBUP_NAME, thumpUserName);
        data.put(Constant.Key.THUMBUP_USER_OPID, thumpUpUserOpId);
        data.put(Constant.Key.GROUPMESSAGEID, groupMessageId);
        data.put(Constant.Key.DELETE, false);
        data.put(Constant.Key.CREATE_TIME, DateUtil.toString(new Date(), DateUtil.YMDHMS));
        List<Map<String, Object>> users = null;
        UserTab commentUser = _tbaUserMapper.selectById(thumpUpUserOpId);

        if(commentUser.getCategory() == UserCategory.TRAINEE.getValue()) {
            users = _userDao.queryTrainInfoByUserOpId(thumpUpUserOpId);
        }else {
            users = _userDao.queryDoctorInfoByUserOpId(thumpUpUserOpId);
        }

        if(users != null && users.size() == 1) {

            data.put(Constant.Key.NAME, users.get(0).get(Constant.Key.NAME));
            data.put(Constant.Key.HEAD_IMAGE, users.get(0).get(Constant.Key.HEAD_IMAGE));
        }

        datas.add(data);
        List<Map<String, Object>> thumpUps = _doctorGroupDao.queryMomentGood(groupMessageId);
        UserTab user = _tbaUserMapper.selectById(userOpId);
        CommonResult result = CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS, datas, 1L, Constant.Type.THUMP_UP);

        if(thumpUps == null || thumpUps.size() == 0) {
            //没人点赞,就推给发送该条朋友圈的id
            this.writeToChannel(userOpId, result);
        }else {
            //有人点赞
            List<String> alias = new ArrayList<String>();
            alias.add(user.getOpId());//一定要推送给发该条朋友圈的人

            for(int i=0; i<thumpUps.size(); i++) {

                String tUserOpId = thumpUps.get(i).get(Constant.Key.THUMBUP_USER_OPID).toString();

                for(int j=0; j<relations.size(); j++) {

                    String friendUserOpID = relations.get(j).get(Constant.Key.FRIEND_OPID).toString();

                    if(tUserOpId.equals(friendUserOpID) && !alias.contains(friendUserOpID)) {
                        alias.add(friendUserOpID);
                    }
                }
            }

            if(alias.size() > 0) {
               for(String id: alias) {
                   this.writeToChannel(id, result);
               }
            }
        }
    }

    @Override
    public void sendDoctorGroupCancelThumpUp(String groupMessageId, String userOpId, String cancelThumpUpUserOpId, String cancelthumpUserName) {

        if(userOpId.equals(cancelThumpUpUserOpId)) {
            //自己删除自己的点赞不响应
            return ;
        }

        List<Map<String,Object>> datas = new ArrayList<Map<String, Object>>();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Constant.Key.THUMBUP_NAME, cancelthumpUserName);
        data.put(Constant.Key.THUMBUP_USER_OPID, cancelThumpUpUserOpId);
        data.put(Constant.Key.GROUPMESSAGEID, groupMessageId);
        data.put(Constant.Key.DELETE, true);
        data.put(Constant.Key.CREATE_TIME, DateUtil.toString(new Date(), DateUtil.YMDHMS));
        List<Map<String, Object>> users = null;
        UserTab commentUser = _tbaUserMapper.selectById(cancelThumpUpUserOpId);

        if(commentUser.getCategory() == UserCategory.TRAINEE.getValue()) {
            users = _userDao.queryTrainInfoByUserOpId(cancelThumpUpUserOpId);
        }else {
            users = _userDao.queryDoctorInfoByUserOpId(cancelThumpUpUserOpId);
        }

        if(users != null && users.size() == 1) {

            data.put(Constant.Key.NAME, users.get(0).get(Constant.Key.NAME));
            data.put(Constant.Key.HEAD_IMAGE, users.get(0).get(Constant.Key.HEAD_IMAGE));
        }

        datas.add(data);
        CommonResult result = CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS, datas, 1L, Constant.Type.CANCEL_THUMP_UP);
        this.writeToChannel(userOpId, result);
    }

    @Override
    public void sendNewMessage(String userOpId) {

        List<Map<String, Object>> relationships = _imRelationshipDao.queryFriend(userOpId);

        if(relationships == null || relationships.size() == 0) {
            return;
        }

        List<Map<String, Object>> users = _userDao.queryDoctorInfoByUserOpId(userOpId);

        if(users != null && users.size() == 1) {

            users.get(0).put(Constant.Key.UUID, UUIDGenerator.getUUID());

            for(Map<String, Object> user: relationships) {

                String id = (String) user.get(Constant.Key.USEROPID);
                CommonResult result = CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS, users, 1L, Constant.Type.GET_DOCTOR_GROUP_NEW_MESSAGE);
                this.writeToChannel(id, result);
            }
        }
    }
}
