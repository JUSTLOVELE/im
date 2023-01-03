package com.hyjk.im.server.service;

import com.hyjk.im.server.model.ChatMessage;
import io.netty.channel.Channel;

/**
 * @author yangzl 2021.06.10
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public interface ChatRecordService {

    public void deleteChat(Channel channel, ChatMessage chatMessage);

    /**
     * 查询聊天列表
      * @param channel
     * @param chatMessage
     */
    public void queryChatList(Channel channel, ChatMessage chatMessage);

    /**
     * 查询私聊
     * @param channel
     * @param chatMessage
     */
    public void queryPerson(Channel channel, ChatMessage chatMessage);


    /**
     * 查询群聊
     * @param channel
     * @param chatMessage
     */
    public void queryGroup(Channel channel, ChatMessage chatMessage);

    /**
     * 查询所有聊天记录
     * @param channel
     * @param chatMessage
     */
    public void queryAll(Channel channel, ChatMessage chatMessage);

}
