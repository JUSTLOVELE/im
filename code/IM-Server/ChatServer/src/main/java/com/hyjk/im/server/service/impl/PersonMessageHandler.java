package com.hyjk.im.server.service.impl;

import com.hyjk.im.server.core.BaseMessageHandler;
import com.hyjk.im.server.document.ChatListDocEntity;
import com.hyjk.im.server.entity.UserTab;
import com.hyjk.im.server.enums.MessageIsSend;
import com.hyjk.im.server.mapper.PublicOrgDoctorMapper;
import com.hyjk.im.server.mapper.TbaUserMapper;
import com.hyjk.im.server.model.ChatMessage;
import com.hyjk.im.server.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2021.06.08
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Component
public class PersonMessageHandler extends BaseMessageHandler {

    private final static Log _logger = LogFactory.getLog(PersonMessageHandler.class);

    @Autowired
    private MongoTemplate _mongoTemplate;

    @Autowired
    private TbaUserMapper _tbaUserMapper;

    @Autowired
    private PublicOrgDoctorMapper _publicOrgDoctorMapper;

    @Override
    public void handlerMessage(ChatMessage chatMessage) {
        chatMessage.setReturnAckType(Constant.Type.PERSON_ACK);
    }

    @Override
    public void saveChatListToMongoDB(ChatMessage chatMessage, List<Map<String, Object>> users) {
        //先刷新A->B的列表
        Query query = new Query();
        query.addCriteria(Criteria.where(Constant.Key.FROM).is(chatMessage.getFrom())
                .and(Constant.Key.TO).is(chatMessage.getTo()));
        Update update = new Update();
        update.set(Constant.Key.TYPE, chatMessage.getType());
        update.set(Constant.Key.MESSAGE_TYPE, chatMessage.getMessageType());
        update.set(Constant.Key.MESSAGE, chatMessage.getMessage());
        update.set(Constant.Key.FROM, chatMessage.getFrom());
        update.set(Constant.Key.TO, chatMessage.getTo());
        update.set(Constant.Key.CREATE_TIME, chatMessage.getCreateTime());
        update.set(Constant.Key.SENDORRECEIVE, Constant.Key.SEND);//这个地方只能是send,因为一定是A->B

        if(StringUtils.isNotEmpty(chatMessage.getOriginalFilename())) {
            update.set(Constant.Key.ORIGINAL_FILE_NAME, chatMessage.getOriginalFilename());
        }

        if(StringUtils.isNotEmpty(chatMessage.getSaveFileName())) {
            update.set(Constant.Key.FILE_NAME, chatMessage.getSaveFileName());
        }
        //因为是A给B发消息所以A一定看到了所有的消息
        update.set(Constant.Key.NUMBER, 0);
        //查询用户名
        UserTab userTabfrom = _tbaUserMapper.selectById(chatMessage.getFrom());
        UserTab userTabTo = _tbaUserMapper.selectById(chatMessage.getTo());

        if(userTabTo != null) {

            update.set(Constant.Key.CHAT_NAME, userTabTo.getName());

            if(StringUtils.isNotEmpty(userTabTo.getRefEntityId())) {

                update.set(Constant.Key.HEAD_IMAGE, _publicOrgDoctorMapper.getPhotoResByOpId(userTabTo.getRefEntityId()));
            }
        }

        _mongoTemplate.upsert(query, update, ChatListDocEntity.class, Constant.MongoDoc.CHAT_LIST);
        //再刷新B->A的列表
        //重新实例化query分配参数,from和to变化了
        query = new Query();
        //需要交换from和to
        query.addCriteria(Criteria.where(Constant.Key.FROM).is(chatMessage.getTo())
                .and(Constant.Key.TO).is(chatMessage.getFrom()));
        //update可以复用
        if(chatMessage.getIsSend() != null && chatMessage.getIsSend() == MessageIsSend.NO_SEND.getValue()) {
            //没有送达
            List<ChatListDocEntity> chatListDocEntities = _mongoTemplate.find(query, ChatListDocEntity.class);

            if(chatListDocEntities != null && chatListDocEntities.size() == 1) {

                Integer number = chatListDocEntities.get(0).getNumber() + 1;
                update.set(Constant.Key.NUMBER, number);
            }else if(chatListDocEntities != null && chatListDocEntities.size() > 1) {
                //只能有一对,超过就要删除后面再插入,正常情况下是不会执行这段代码的
                Integer number = 1;//因为isSend=0;所以初始值为1

                for(ChatListDocEntity c: chatListDocEntities) {
                    number = number + c.getNumber();
                }
                //累计完未读之后删除记录
                _mongoTemplate.remove(query, Constant.MongoDoc.CHAT_LIST);
                update.set(Constant.Key.NUMBER, number);
            }
        }else{
            //如果当前消息已达说明所有消息都已读了
            update.set(Constant.Key.NUMBER, 0);
        }
        //覆盖更新的参数

        if(userTabfrom != null) {

            update.set(Constant.Key.CHAT_NAME, userTabfrom.getName());

            if(StringUtils.isNotEmpty(userTabfrom.getRefEntityId())) {

                update.set(Constant.Key.HEAD_IMAGE, _publicOrgDoctorMapper.getPhotoResByOpId(userTabfrom.getRefEntityId()));
            }
        }

        update.set(Constant.Key.FROM, chatMessage.getTo());//交换from和to
        update.set(Constant.Key.TO, chatMessage.getFrom());
        update.set(Constant.Key.SENDORRECEIVE, Constant.Key.RECEIVE);//这个地方只能是receive,因为一定是A->B,B只能是接收
        _mongoTemplate.upsert(query, update, ChatListDocEntity.class, Constant.MongoDoc.CHAT_LIST);
    }

    @Override
    public void saveDataToMongoDB(ChatMessage chatMessage, List<Map<String, Object>> users) {
        //保存发送方数据
        _mongoTemplate.save(this.getSendChatDocEntity(chatMessage), Constant.MongoDoc.CHAT_MESSAGE);
        //保存接收方数据
        for(Map<String, Object> user: users) {
            _mongoTemplate.save(this.getReceiveChatDocEntity(chatMessage, (String) user.get(Constant.Key.USEROPID)), Constant.MongoDoc.CHAT_MESSAGE);
        }
    }

    @Override
    public List<Map<String, Object>> querySendUsers(String to) {

        List<Map<String, Object>> users = new ArrayList<>();
        Map<String, Object> user = new HashMap<>();
        users.add(user);
        user.put(Constant.Key.USEROPID, to);

        return users;
    }
}
