package com.hyjk.im.server.service.impl;

import com.hyjk.im.common.utils.CommonConstant;
import com.hyjk.im.common.utils.CommonResult;
import com.hyjk.im.server.component.im.IMTextHandler;
import com.hyjk.im.server.core.Base;
import com.hyjk.im.server.dao.UserDao;
import com.hyjk.im.server.enums.MessageIsSend;
import com.hyjk.im.server.model.ChatMessage;
import com.hyjk.im.server.service.UserService;
import com.hyjk.im.server.util.Constant;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2021.07.26
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends Base implements UserService {

    @Autowired
    private UserDao _userDao;

    @Override
    public CommonResult getApplyList(String userOpId, Integer applyType, String type) {

        List<Map<String, Object>> applyList = _userDao.getApplyList(userOpId, applyType);
        CommonResult commonResult = CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS, applyList, Long.valueOf(applyList.size()), type);

        return commonResult;
    }

    @Override
    public boolean checkRelationShip(String userOpId, String friendOpId) {

        Integer count = _userDao.checkRelationShip(userOpId, friendOpId);

        if(count == 1) {
            return true;
        }

        return false;
    }

    @Override
    public void delFriend(ChatMessage chatMessage) {

        _userDao.delImFriend(chatMessage.getUserOpId(), chatMessage.getTargetOpId());
        Channel myChannel = IMTextHandler.userChannels.get(chatMessage.getUserOpId());
        List<Map<String, Object>> datas = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put(Constant.Key.USEROPID, chatMessage.getTargetOpId());
        datas.add(data);

        if(myChannel != null) {

            CommonResult commonResult = CommonResult.success(CommonConstant.SUCCESS_DELETE_MSG, datas, 1L, chatMessage.getType());
            this.writeToChannel(myChannel, commonResult);
        }

        Channel friendChannel = IMTextHandler.userChannels.get(chatMessage.getTargetOpId());

        if(friendChannel != null) {

            datas.clear();
            data.clear();
            data.put(Constant.Key.USEROPID, chatMessage.getUserOpId());
            datas.add(data);
            CommonResult commonResult = CommonResult.success(CommonConstant.SUCCESS_DELETE_MSG, datas, 1L, chatMessage.getType());
            this.writeToChannel(friendChannel, commonResult);
        }
    }

    @Override
    public void queryMyFriendList(ChatMessage chatMessage) {

        List<Map<String, Object>> users = _userDao.queryMyFriend(chatMessage.getUserOpId(),
                chatMessage.getUserName(),
                chatMessage.getPhone());
        Channel channel = IMTextHandler.userChannels.get(chatMessage.getUserOpId());

        if(channel != null) {

            CommonResult commonResult = CommonResult.success(CommonConstant.QUERY_MESSAGE_SUCCESS, users, Long.valueOf(users.size()), chatMessage.getType());
            this.writeToChannel(channel, commonResult);
        }else{
            throw new RuntimeException("MY_FRIEND-UserServiceImpl-queryMyFriendList:查询不到用户,禁止拿一个不在线的用户来查询好友");
        }
    }
}
