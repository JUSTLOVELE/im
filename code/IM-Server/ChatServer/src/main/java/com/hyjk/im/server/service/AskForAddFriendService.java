package com.hyjk.im.server.service;

import com.hyjk.im.server.model.ChatMessage;
import io.netty.channel.Channel;

/**
 * @author yangzl 2021.07.09
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public interface AskForAddFriendService {

    /**
     * 确认添加好友
     * @param chatMessage
     */
    public void ackAddFriend(ChatMessage chatMessage);

    /**
     * 申请添加好友
     * @param chatMessage
     */
    public void addFriend(ChatMessage chatMessage);
}
