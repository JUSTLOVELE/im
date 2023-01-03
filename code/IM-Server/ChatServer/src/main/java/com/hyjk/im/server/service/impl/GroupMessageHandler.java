package com.hyjk.im.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyjk.im.server.core.BaseMessageHandler;
import com.hyjk.im.server.document.ChatDocEntity;
import com.hyjk.im.server.document.ChatListDocEntity;
import com.hyjk.im.server.entity.ImGroupEntity;
import com.hyjk.im.server.entity.ImGroupPersonRelationEntity;
import com.hyjk.im.server.entity.UserTab;
import com.hyjk.im.server.enums.MessageIsSend;
import com.hyjk.im.server.mapper.ImGroupMapper;
import com.hyjk.im.server.mapper.ImGroupPersonRelationMapper;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2021.06.03
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Component
public class GroupMessageHandler extends BaseMessageHandler {

    private final static Log _logger = LogFactory.getLog(GroupMessageHandler.class);

    @Autowired
    private ImGroupPersonRelationMapper _imGroupPersonRelationMapper;

    @Autowired
    private MongoTemplate _mongoTemplate;

    @Autowired
    private ImGroupMapper _imGroupMapper;

    @Autowired
    private TbaUserMapper _tbaUserMapper;

    @Override
    public void handlerMessage(ChatMessage chatMessage) {

        UserTab userTab = _tbaUserMapper.selectById(chatMessage.getFrom());
        chatMessage.setFromUserName(userTab.getName());
        chatMessage.setReturnAckType(Constant.Type.GROUP_ACK);
    }

    @Override
    public void saveChatListToMongoDB(ChatMessage chatMessage, List<Map<String, Object>> users) {
        //把当前用户也加入到集合中
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.Key.USEROPID, chatMessage.getFrom());
        map.put(Constant.Key.IS_SEND, MessageIsSend.SEND.getValue());
        users.add(map);
//        //保存至聊天列表
        for(Map<String, Object> user: users) {
            //刷新每个群聊用户对该群聊的聊天列表
            String userOpId = (String) user.get(Constant.Key.USEROPID);
            Query query = new Query();
            query.addCriteria(Criteria.where(Constant.Key.FROM).is(userOpId)
                    .and(Constant.Key.TO).is(chatMessage.getTo()));
            Update update = new Update();
            update.set(Constant.Key.TYPE, chatMessage.getType());
            update.set(Constant.Key.MESSAGE_TYPE, chatMessage.getMessageType());
            update.set(Constant.Key.MESSAGE, chatMessage.getMessage());
            update.set(Constant.Key.FROM, userOpId);
            update.set(Constant.Key.TO, chatMessage.getTo());
            update.set(Constant.Key.LAST_MESSAGE_USER_OPID, chatMessage.getFrom());
            update.set(Constant.Key.CREATE_TIME, chatMessage.getCreateTime());

            if(userOpId.equals(chatMessage.getFrom())) {
                update.set(Constant.Key.SENDORRECEIVE, Constant.Key.SEND);//这个地方只能是send,因为一定是A->群C
            }else {
                //这个地方只能是receive,因为A->群C然后通知各个用户,各个用户只能是接收
                update.set(Constant.Key.SENDORRECEIVE, Constant.Key.RECEIVE);
            }

            if(StringUtils.isNotEmpty(chatMessage.getOriginalFilename())) {
                update.set(Constant.Key.ORIGINAL_FILE_NAME, chatMessage.getOriginalFilename());
            }

            if(StringUtils.isNotEmpty(chatMessage.getSaveFileName())) {
                update.set(Constant.Key.FILE_NAME, chatMessage.getSaveFileName());
            }

            //        //查询群名
            ImGroupEntity imGroupEntity = _imGroupMapper.selectById(chatMessage.getTo());

            if(imGroupEntity != null) {

                update.set(Constant.Key.CHAT_NAME, imGroupEntity.getName());
                update.set(Constant.Key.HEAD_IMAGE, imGroupEntity.getHeadImg());
            }

            Integer isSend = (Integer) user.get(Constant.Key.IS_SEND);

            if(isSend == MessageIsSend.NO_SEND.getValue()) {

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
            }else {
                //已送达
                update.set(Constant.Key.NUMBER, 0);
            }

            _mongoTemplate.upsert(query, update, ChatListDocEntity.class, Constant.MongoDoc.CHAT_LIST);
        }
    }

    @Override
    public void saveDataToMongoDB(ChatMessage chatMessage, List<Map<String, Object>> users) {
        //保存发送方数据
        _mongoTemplate.save(this.getSendChatDocEntity(chatMessage), Constant.MongoDoc.CHAT_MESSAGE);
        //保存接收方数据
//        for(Map<String, Object> user: users) {
//            _mongoTemplate.save(this.getReceiveChatDocEntity(chatMessage, (String) user.get(Constant.Key.USEROPID)), Constant.MongoDoc.CHAT_MESSAGE);
//        }
    }

    /**
     * 查询群聊成员
     * @param groupOpId
     * @return
     */
    @Override
    public List<Map<String, Object>> querySendUsers(String groupOpId) {

        QueryWrapper<ImGroupPersonRelationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_op_id as userOpId");
        queryWrapper.eq("group_op_id", groupOpId);

        return _imGroupPersonRelationMapper.selectMaps(queryWrapper);
    }
}
