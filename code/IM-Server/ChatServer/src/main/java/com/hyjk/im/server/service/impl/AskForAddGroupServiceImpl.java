package com.hyjk.im.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hyjk.im.common.utils.CommonConstant;
import com.hyjk.im.common.utils.CommonResult;
import com.hyjk.im.common.utils.UUIDGenerator;
import com.hyjk.im.server.component.im.IMTextHandler;
import com.hyjk.im.server.core.Base;
import com.hyjk.im.server.dao.GroupDao;
import com.hyjk.im.server.entity.ImApplyListEntity;
import com.hyjk.im.server.entity.ImGroupEntity;
import com.hyjk.im.server.entity.ImGroupPersonRelationEntity;
import com.hyjk.im.server.entity.UserTab;
import com.hyjk.im.server.enums.ApplyStatus;
import com.hyjk.im.server.enums.ApplyType;
import com.hyjk.im.server.enums.MessageIsSend;
import com.hyjk.im.server.mapper.ImApplyListMapper;
import com.hyjk.im.server.mapper.ImGroupMapper;
import com.hyjk.im.server.mapper.ImGroupPersonRelationMapper;
import com.hyjk.im.server.mapper.TbaUserMapper;
import com.hyjk.im.server.model.ChatMessage;
import com.hyjk.im.server.service.AskForAddGroupService;
import com.hyjk.im.server.util.Constant;
import com.hyjk.im.server.util.MessageReturnUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author yangzl 2021.07.09
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AskForAddGroupServiceImpl extends Base implements AskForAddGroupService {

    @Autowired
    private MessageReturnUtils _messageReturnUtils;

    @Autowired
    private ImGroupMapper _imGroupMapper;

    @Autowired
    private ImGroupPersonRelationMapper _imGroupPersonRelationMapper;

    @Autowired
    private TbaUserMapper _userMapper;

    @Autowired
    private ImApplyListMapper _imApplyListMapper;

    @Autowired
    private GroupDao _groupDao;

    private final static Log _logger = LogFactory.getLog(AskForAddGroupServiceImpl.class);

    @Override
    public CommonResult queryGroupList(String groupId) {

        if(StringUtils.isEmpty(groupId)) {
            return CommonResult.fail("groupId不能为空");
        }

        List<Map<String, Object>> datas = _groupDao.queryGroupList(groupId);
        return CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS, datas, Long.valueOf(datas.size()));
    }

    @Override
    public CommonResult queryMyGroup(String userOpId) {

        if(StringUtils.isEmpty(userOpId)) {
            return CommonResult.fail("userOpId不能为空");
        }

        List<Map<String, Object>> datas = _groupDao.queryMyGroup(userOpId);
        return CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS, datas, Long.valueOf(datas.size()));
    }

    @Override
    public CommonResult quitGroup(String userOpId, String groupOpId) {

        if(StringUtils.isEmpty(userOpId)) {
            return CommonResult.fail("userOpId不能为空");
        }

        if(StringUtils.isEmpty(groupOpId)) {
            return CommonResult.fail("groupOpId不能为空");
        }

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Column.GROUP_OP_ID, groupOpId);
        param.put(Constant.Column.USER_OP_ID, userOpId);
        _imGroupPersonRelationMapper.deleteByMap(param);

        return CommonResult.success(CommonConstant.SUCCESS_DELETE_MSG);
    }

    @Override
    public boolean checkIsGroupMember(String userOpId, String groupOpId) {

        Integer count = _imGroupPersonRelationMapper.checkIsGroupMember(userOpId, groupOpId);

        if(count > 0) {
            return true;
        }

        return false;
    }

    @Override
    public void createGroup(String userOpId, Integer groupType, Integer applyJoinOption, String groupName) {

        UserTab userTab = _userMapper.selectById(userOpId);
        ImGroupEntity imGroupEntity = new ImGroupEntity();
        imGroupEntity.setCreateTime(new Date());
        imGroupEntity.setOpId(UUIDGenerator.getUUID());
        imGroupEntity.setName(groupName);
        imGroupEntity.setType(groupType);
        imGroupEntity.setApplyJoinOption(applyJoinOption);
        imGroupEntity.setOwnerOpId(userOpId);
        imGroupEntity.setOwnerId(userTab.getUserId());
        imGroupEntity.setOwnerName(userTab.getName());
        _imGroupMapper.insert(imGroupEntity);
        ImGroupPersonRelationEntity imGroupPersonRelationEntity = new ImGroupPersonRelationEntity(imGroupEntity.getOpId(), userOpId);
        _imGroupPersonRelationMapper.insert(imGroupPersonRelationEntity);
        Map<String, Object> data = new HashMap<>();
        data.put(Constant.Key.GROUP_ID, imGroupEntity.getOpId());
        List<Map<String, Object>> datas = new ArrayList<>();
        datas.add(data);
        Channel channel = IMTextHandler.userChannels.get(userOpId);

        if(channel != null) {

            CommonResult commonResult = CommonResult.success(CommonConstant.SUCCESS_CREATE_MSG, datas, 1L, Constant.Type.CREATE_GROUP);
            this.writeToChannel(channel, commonResult);
        }
    }

    @Override
    public void ackAddGroup(ChatMessage chatMessage) {

        if(StringUtils.isEmpty(chatMessage.getTo())) {
            _logger.info("AskForAddGroupServiceImpl---ackAddGroup---to为空");
            return;
        }
        //确认添加后更新相关状态
        UpdateWrapper<ImApplyListEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(Constant.Column.APPLY_STATUS, ApplyStatus.YES.getValue())
                //.eq(Constant.Column.FROM_USER_OP_ID, chatMessage.getTo())
                .eq(Constant.Column.TO_USER_OP_ID, chatMessage.getFrom())//看接口文档这里的from对应to,to对应之前的from
                .eq(Constant.Column.APPLY_TYPE, ApplyType.GROUP.getValue())
                .eq(Constant.Column.GROUP_OP_ID, chatMessage.getTo());
        _imApplyListMapper.update(null, updateWrapper);
        //先删除群组关系再插入
        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Column.USER_OP_ID, chatMessage.getFrom());
        param.put(Constant.Column.GROUP_OP_ID, chatMessage.getTo());
        _imGroupPersonRelationMapper.deleteByMap(param);
        ImGroupPersonRelationEntity imGroupPersonRelationEntity = new ImGroupPersonRelationEntity();
        imGroupPersonRelationEntity.setOpId(UUIDGenerator.getUUID());
        imGroupPersonRelationEntity.setGroupOpId(chatMessage.getTo());
        imGroupPersonRelationEntity.setUserOpId(chatMessage.getFrom());
        imGroupPersonRelationEntity.setCreateTime(new Date());
        _imGroupPersonRelationMapper.insert(imGroupPersonRelationEntity);
        Map<String, Object> map = new HashMap<>();
//        map.put(Constant.Key.TYPE, Constant.Type.ACK_ADD_GROUP);
        map.put(Constant.Key.FROM, chatMessage.getFrom());
        map.put(Constant.Key.FROM_NAME, chatMessage.getFromName());
        map.put(Constant.Key.TO, chatMessage.getTo());
        map.put(Constant.Key.USER_NAME, chatMessage.getToName());
        map.put(Constant.Key.CREATE_TIME, chatMessage.getCreateTime());
        List<Map<String, Object>> datas = new ArrayList<>();
        datas.add(map);
        CommonResult result = CommonResult.success(CommonConstant.SUCCESS_SAVE_MSG, datas, Long.valueOf(datas.size()), Constant.Type.ACK_ADD_GROUP);
        String text = getJSON(result);
        QueryWrapper<ImGroupPersonRelationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_op_id as userOpId");
        queryWrapper.eq("group_op_id", chatMessage.getTo());
        List<Map<String, Object>> users = _imGroupPersonRelationMapper.selectMaps(queryWrapper);

        for(Map<String, Object> user: users) {

            String receiveUserOpId = (String) user.get(Constant.Key.USEROPID);
            Channel channel = IMTextHandler.userChannels.get(receiveUserOpId);

            if(channel != null) {
                //当前用户就不要再次写了,标记一下后面移除
                //if(true){ //测试就先都写,后面再开启下面的代码
                if(!chatMessage.getTo().equals(receiveUserOpId)) {
                    channel.writeAndFlush(new TextWebSocketFrame(text));
                }

            }else{
                user.put(Constant.Key.IS_SEND, MessageIsSend.NO_SEND.getValue());
            }
        }
    }

    @Override
    public void addGroup(ChatMessage chatMessage) {
        //这里添加好友其实直接通知to就可以了
        if(StringUtils.isEmpty(chatMessage.getTo())) {
            _logger.info("AskForAddGroupServiceImpl---ackAddGroup ---- to为空");
            return ;
        }

        ImGroupEntity imGroupEntity = _imGroupMapper.selectById(chatMessage.getGroupId());

        if(imGroupEntity == null) {
            return;
        }

        ImApplyListEntity imApplyListEntity = new ImApplyListEntity(chatMessage.getFrom(), chatMessage.getTo(), ApplyType.GROUP.getValue(), chatMessage.getGroupId());
        _imApplyListMapper.insert(imApplyListEntity);
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.Key.TYPE, Constant.Type.SEND_ADD_GROUP);
        map.put(Constant.Key.FROM, chatMessage.getFrom());
        map.put(Constant.Key.FROM_NAME, chatMessage.getFromName());
        map.put(Constant.Key.TO, chatMessage.getTo());
        map.put(Constant.Key.TO_NAME, chatMessage.getToName());
        map.put(Constant.Key.GROUP_ID, chatMessage.getGroupId());
        map.put(Constant.Key.CREATE_TIME, chatMessage.getCreateTime());
        map.put(Constant.Key.HEAD_IMAGE, imGroupEntity.getHeadImg());
        map.put(Constant.Key.GROUP_NAME, imGroupEntity.getName());
        Channel channel = IMTextHandler.userChannels.get(chatMessage.getTo());

        if(channel != null) {
            channel.writeAndFlush(new TextWebSocketFrame(getJSON(map)));
        }
    }
}
