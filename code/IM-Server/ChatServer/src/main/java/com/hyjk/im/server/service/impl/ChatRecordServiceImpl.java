package com.hyjk.im.server.service.impl;

import com.hyjk.im.common.utils.CommonResult;
import com.hyjk.im.server.core.Base;
import com.hyjk.im.server.document.ChatDocEntity;
import com.hyjk.im.server.document.ChatListDocEntity;
import com.hyjk.im.server.model.ChatMessage;
import com.hyjk.im.server.service.ChatRecordService;
import com.hyjk.im.server.util.Constant;
import com.hyjk.im.server.util.MongoDBUtils;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.result.DeleteResult;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzl 2021.06.10
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChatRecordServiceImpl extends Base implements ChatRecordService {

    private final static Log _logger = LogFactory.getLog(ChatRecordServiceImpl.class);

    @Autowired
    private MongoDBUtils _mongoDBUtils;

    @Autowired
    private MongoTemplate _mongoTemplate;

    @Override
    public void deleteChat(Channel channel, ChatMessage chatMessage) {

        Query query = new Query();
        query.addCriteria(Criteria.where(Constant.Key.FROM).is(chatMessage.getFrom()).and(Constant.Key.TO).is(chatMessage.getTo()));
        _mongoTemplate.remove(query, ChatListDocEntity.class, Constant.MongoDoc.CHAT_LIST);
        _mongoTemplate.remove(query, ChatDocEntity.class, Constant.MongoDoc.CHAT_MESSAGE);
        CommonResult result = CommonResult.success("", null, 0L);
        result.setType(chatMessage.getType());
        channel.writeAndFlush(new TextWebSocketFrame(getJSON(result)));
    }

    @Override
    public void queryChatList(Channel channel, ChatMessage chatMessage) {

        Query query = new Query();
        query.addCriteria(Criteria.where(Constant.Key.FROM).is(chatMessage.getUserOpId()));
        List<ChatListDocEntity> chatListDocEntities = _mongoTemplate.find(query, ChatListDocEntity.class, Constant.MongoDoc.CHAT_LIST);
        CommonResult result = CommonResult.success("", chatListDocEntities, Long.valueOf(chatListDocEntities.size()));
        result.setType(chatMessage.getType());
        channel.writeAndFlush(new TextWebSocketFrame(getJSON(result)));
    }

    @Override
    public void queryPerson(Channel channel, ChatMessage chatMessage) {

        List<Criteria> criterias = new ArrayList<>();
        //criterias.add(Criteria.where(Constant.Key.RECEIVEUSEROPID).is(chatMessage.getReceiveUserOpId()));
        criterias.add(Criteria.where(Constant.Key.TYPE).is(Constant.Key.PERSON));//查询私聊
        criterias.add(Criteria.where(Constant.Key.FROM).in(chatMessage.getUserOpId(), chatMessage.getTargetOpId()));
        criterias.add(Criteria.where(Constant.Key.TO).in(chatMessage.getUserOpId(), chatMessage.getTargetOpId()));
        criterias.add(Criteria.where(Constant.Key.SENDORRECEIVE).is(Constant.Key.SEND));
        queryAndWrite(criterias, chatMessage, channel);
        updateNumber(chatMessage.getUserOpId(), chatMessage.getTargetOpId());
    }

    @Override
    public void queryGroup(Channel channel, ChatMessage chatMessage) {

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(Criteria.where(Constant.Key.TO).is(chatMessage.getTargetOpId()));
        criterias.add(Criteria.where(Constant.Key.TYPE).is(Constant.Key.GROUP));//查询群聊
        queryAndWrite(criterias, chatMessage, channel);
        updateNumber(chatMessage.getUserOpId(), chatMessage.getTargetOpId());
    }

    /**
     * 更新这对聊天的未读数为0
     * @param from
     * @param to
     */
    private void updateNumber(String from, String to) {

        Query query = new Query();
        query.addCriteria(Criteria.where(Constant.Key.FROM).is(from).and(Constant.Key.TO).is(to));
        Update update = new Update().set(Constant.Key.NUMBER, 0);
        _mongoTemplate.updateFirst(query, update, ChatListDocEntity.class);
    }

    @Override
    public void queryAll(Channel channel, ChatMessage chatMessage) {

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(Criteria.where(Constant.Key.FROM).is(chatMessage.getUserOpId()));
        queryAndWrite(criterias, chatMessage, channel);
    }

    private void queryAndWrite(List<Criteria> criterias, ChatMessage chatMessage, Channel channel) {

        if(StringUtils.isNotEmpty(chatMessage.getSendOrReceive())) {
            criterias.add(Criteria.where(Constant.Key.SENDORRECEIVE).is(chatMessage.getSendOrReceive()));
        }

        if(StringUtils.isNotEmpty(chatMessage.getStartTime())) {
            criterias.add(Criteria.where(Constant.Key.START_TIME).gt(chatMessage.getStartTime()));
        }

        if(StringUtils.isNotEmpty(chatMessage.getEndTime())) {
            criterias.add(Criteria.where(Constant.Key.END_TIME).lt(chatMessage.getEndTime()));
        }

        CommonResult result =  _mongoDBUtils.queryWithDesc(criterias,
                ChatDocEntity.class,
                chatMessage.getPage(),
                chatMessage.getLimit(),
                Constant.Key.CREATE_TIME);
        result.setType(chatMessage.getType());
        channel.writeAndFlush(new TextWebSocketFrame(getJSON(result)));
    }

    //        Query query = new Query();
//        query.addCriteria();
//
//        if(StringUtils.isNotEmpty(chatMessage.getSendOrReceive())) {
//            query.addCriteria(Criteria.where(Constant.Key.SENDORRECEIVE).is(chatMessage.getSendOrReceive()));
//        }
//
//        long count = _mongoTemplate.count(query, ChatDocEntity.class);
//        Pageable pageable = PageRequest.of(chatMessage.getPage(), chatMessage.getLimit());
//        Sort sort = Sort.by(Sort.Direction.DESC, Constant.Key.CREATE_TIME);
//        List<ChatDocEntity> chatDocEntities = _mongoTemplate.find(query.with(pageable).with(sort), ChatDocEntity.class);
//        System.out.println();
}
