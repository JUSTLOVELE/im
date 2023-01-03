package com.hyjk.im.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hyjk.im.common.utils.CommonConstant;
import com.hyjk.im.common.utils.CommonResult;
import com.hyjk.im.server.component.im.IMTextHandler;
import com.hyjk.im.server.core.Base;
import com.hyjk.im.server.dao.UserDao;
import com.hyjk.im.server.entity.ImApplyListEntity;
import com.hyjk.im.server.entity.ImRelationEntity;
import com.hyjk.im.server.entity.UserTab;
import com.hyjk.im.server.enums.ApplyStatus;
import com.hyjk.im.server.enums.ApplyType;
import com.hyjk.im.server.mapper.ImApplyListMapper;
import com.hyjk.im.server.mapper.ImRelationMapper;
import com.hyjk.im.server.mapper.TbaUserMapper;
import com.hyjk.im.server.model.ChatMessage;
import com.hyjk.im.server.service.AskForAddFriendService;
import com.hyjk.im.server.util.Constant;
import com.hyjk.im.server.util.MessageReturnUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class AskForAddFriendServiceImpl extends Base implements AskForAddFriendService {

    @Autowired
    private MessageReturnUtils _messageReturnUtils;

    @Autowired
    private ImApplyListMapper _imApplyListMapper;

    @Autowired
    private ImRelationMapper _imRelationMapper;

    @Autowired
    private TbaUserMapper _userMapper;

    @Autowired
    private UserDao _userDao;

    @Override
    public void ackAddFriend(ChatMessage chatMessage) {
        //通知申请人，通知from就可以了
        if(StringUtils.isEmpty(chatMessage.getFrom())) {
            return ;
        }
        //确认添加后更新相关状态
        UpdateWrapper<ImApplyListEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(Constant.Column.APPLY_STATUS, ApplyStatus.YES.getValue())
                .eq(Constant.Column.FROM_USER_OP_ID, chatMessage.getTo())//看接口文档这里的from对应to,to对应之前的from
                .eq(Constant.Column.TO_USER_OP_ID, chatMessage.getFrom())
                .eq(Constant.Column.APPLY_TYPE, ApplyType.FRIEND.getValue());
        _imApplyListMapper.update(null, updateWrapper);
        //先清除好友关系再保存好友关系
        _userDao.delImFriend(chatMessage.getFrom(), chatMessage.getTo());
        UserTab user = _userMapper.selectById(chatMessage.getFrom());
        UserTab friend = _userMapper.selectById(chatMessage.getTo());
        ImRelationEntity userRelation = new ImRelationEntity(user, friend);
        _imRelationMapper.insert(userRelation);
        ImRelationEntity friendRelation = new ImRelationEntity(friend, user);
        _imRelationMapper.insert(friendRelation);
        Channel channel = IMTextHandler.userChannels.get(chatMessage.getFrom());

        if(channel != null) {

            Map<String, Object> map = new HashMap<>();
//            map.put(Constant.Key.TYPE, Constant.Type.ACK_ADD_FRIEND);
            map.put(Constant.Key.FROM, chatMessage.getFrom());
            map.put(Constant.Key.FROM_NAME, chatMessage.getFromName());
            map.put(Constant.Key.HEAD_IMAGE, chatMessage.getHeadImage());
            map.put(Constant.Key.TO, chatMessage.getTo());
            map.put(Constant.Key.CREATE_TIME, chatMessage.getCreateTime());
            List<Map<String, Object>> datas = new ArrayList<>();
            datas.add(map);
            CommonResult result = CommonResult.success(CommonConstant.SUCCESS_SAVE_MSG, datas, Long.valueOf(datas.size()), Constant.Type.ACK_ADD_FRIEND);
            channel.writeAndFlush(new TextWebSocketFrame(getJSON(result)));
        }
    }

    @Override
    public void addFriend(ChatMessage chatMessage) {
        //这里添加好友其实直接通知to就可以了
        if(StringUtils.isEmpty(chatMessage.getTo())) {
            return ;
        }

        ImApplyListEntity imApplyListEntity = new ImApplyListEntity(chatMessage.getFrom(), chatMessage.getTo(), ApplyType.FRIEND.getValue());
        _imApplyListMapper.insert(imApplyListEntity);
        Channel channel = IMTextHandler.userChannels.get(chatMessage.getTo());

        if(channel != null) {

            Map<String, Object> map = new HashMap<>();
            map.put(Constant.Key.TYPE, Constant.Type.SEND_ADD_FRIEND);
            map.put(Constant.Key.FROM, chatMessage.getFrom());
            map.put(Constant.Key.FROM_NAME, chatMessage.getFromName());
            map.put(Constant.Key.HEAD_IMAGE, chatMessage.getHeadImage());
            map.put(Constant.Key.TO, chatMessage.getTo());
            map.put(Constant.Key.CREATE_TIME, chatMessage.getCreateTime());
            channel.writeAndFlush(new TextWebSocketFrame(getJSON(map)));
        }
    }
}
