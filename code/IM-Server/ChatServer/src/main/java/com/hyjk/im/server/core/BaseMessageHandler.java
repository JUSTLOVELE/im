package com.hyjk.im.server.core;

import com.hyjk.im.common.utils.Base64;
import com.hyjk.im.common.utils.FileUtil;
import com.hyjk.im.common.utils.UUIDGenerator;
import com.hyjk.im.server.component.im.IMTextHandler;
import com.hyjk.im.server.document.ChatDocEntity;
import com.hyjk.im.server.entity.UserTab;
import com.hyjk.im.server.enums.ChatMessageType;
import com.hyjk.im.server.enums.MessageIsSend;
import com.hyjk.im.server.env.YmlProjectConfig;
import com.hyjk.im.server.mapper.TbaUserMapper;
import com.hyjk.im.server.model.ChatMessage;
import com.hyjk.im.server.util.Constant;
import com.hyjk.im.server.util.MessageReturnUtils;
import com.hyjk.im.server.util.MinioService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
public abstract class BaseMessageHandler {

    private final static Log _logger = LogFactory.getLog(BaseMessageHandler.class);

    @Autowired
    private MongoTemplate _mongoTemplate;

    @Autowired
    private MessageReturnUtils _messageReturnUtils;

    @Autowired
    private MinioService _minioService;

    @Autowired
    private YmlProjectConfig _ymlProjectConfig;

    @Autowired
    private TbaUserMapper _userMapper;

    /**
     * 处理消息
     * @param chatMessage
     */
    public abstract void handlerMessage(ChatMessage chatMessage);

    /**
     * 保存聊天列表数据到mongodo中
     * @param chatMessage
     */
    public abstract void saveChatListToMongoDB(ChatMessage chatMessage, List<Map<String, Object>> users);

    /**
     * 保存数据到mongodb中
     * @param chatMessage
     * @param users
     */
    public abstract void saveDataToMongoDB(ChatMessage chatMessage, List<Map<String, Object>> users);

    /**
     * 查询等待被发送的用户数据
     * @param to
     * @return
     */
    public abstract List<Map<String, Object>> querySendUsers(String to);

    /**
     * 发送语音文件
     * @param chatMessage
     * @param users
     */
    public void sendSpeechMessage(ChatMessage chatMessage, List<Map<String, Object>> users){
        this.sendImageMessage(chatMessage, users);
    }

    /**
     * 发送视频文件
     * @param chatMessage
     * @param users
     */
    public void sendVideoMessage(ChatMessage chatMessage, List<Map<String, Object>> users) {
        this.sendImageMessage(chatMessage, users);
    }

    /**
     * 发送图片文件
     * @param users
     */
    public void sendImageMessage(ChatMessage chatMessage, List<Map<String, Object>> users) {

        String format = FileUtil.getFormat(chatMessage.getOriginalFilename());
        UserTab userTab = _userMapper.selectById(chatMessage.getFrom());
        String fileName = UUIDGenerator.getUUID() + "." + format;
        //保存文件
        String fileUrl  =  sendImageMessage(chatMessage.getMessage(), format, userTab, fileName, chatMessage.getContentType());
        chatMessage.setMessage(fileUrl);
        chatMessage.setSaveFileName(fileName);

        if(StringUtils.isNotEmpty(chatMessage.getThumpImage())) {
            //保存缩略图,要重新生成文件名,并且格式固定为jpg
            String thumpUrl = sendImageMessage(chatMessage.getThumpImage(), format, userTab, UUIDGenerator.getUUID() + ".jpg", "image/jpeg");
            chatMessage.setThumpImage(thumpUrl);
        }

        sendTextMessage(chatMessage, users);
    }



    private String sendImageMessage(String base64Code, String format, UserTab userTab, String fileName, String contentType) {

        InputStream inputStream = null;

        try{

            byte[] b = Base64.decode(base64Code);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

            inputStream =  new ByteArrayInputStream(b);
            return  _minioService.uploadFile(userTab.getOrgId(),
                    userTab.getRefEntityId(),
                    fileName,
                    contentType,
                    inputStream);
        }catch (Exception e) {
            _logger.error("", e);
            return null;
        }finally {
            try {
                inputStream.close();
            }catch (Exception e2) {
                _logger.error("", e2);
            }
        }
    }

//    public static boolean GenerateImage(String imgStr) {
//        if (imgStr == null) {
//            // 图像数据为空
//            return false;
//        }
//        try {
//            // Base64解码
//            byte[] b = Base64.decode(imgStr);
//            for (int i = 0; i < b.length; ++i) {
//                if (b[i] < 0) {// 调整异常数据
//                    b[i] += 256;
//                }
//            }
//            // 生成jpeg图片
//            String imgFilePath = "C:\\logs\\1.jpg";// 新生成的图片
//            OutputStream out = new FileOutputStream(imgFilePath);
//            out.write(b);
//            out.flush();
//            out.close();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    /**
     * 发送文本信息
     * @param users
     */
    public void sendTextMessage(ChatMessage chatMessage, List<Map<String, Object>> users) {

        String userOpId = chatMessage.getFrom();
        List<Map<String, Object>> currentUsers = new ArrayList<>();
        //成功写入channel的用户数据要标记已读,否则未读
        for(Map<String, Object> user: users) {

            String receiveUserOpId = (String) user.get(Constant.Key.USEROPID);
            Channel channel = IMTextHandler.userChannels.get(receiveUserOpId);

            if(channel != null) {
                //当前用户就不要再次写了,标记一下后面移除
                //if(true){ //测试就先都写,后面再开启下面的代码
                if(!userOpId.equals(receiveUserOpId)) {
                    //群聊要对群用户进行要广播,但是聊天列表不用
                    user.put(Constant.Key.IS_SEND, MessageIsSend.SEND.getValue());
                    chatMessage.setReceiveUserOpId(receiveUserOpId);
                    chatMessage.setIsSend(MessageIsSend.SEND.getValue());
                    channel.writeAndFlush(new TextWebSocketFrame(_messageReturnUtils.returnToMessage(chatMessage)));
                }else{
                    currentUsers.add(user);
                }

            }else{
                user.put(Constant.Key.IS_SEND, MessageIsSend.NO_SEND.getValue());
            }
        }

        if(currentUsers.size() > 0) {
            users.removeAll(currentUsers);
        }
    }

    public String sendMessage(ChatMessage chatMessage, Channel channel) {

        String messageType = chatMessage.getMessageType();
        String from = chatMessage.getFrom();
        String to = chatMessage.getTo();
        String createTime = chatMessage.getCreateTime();
        handlerMessage(chatMessage);

        if(StringUtils.isEmpty(to)) {
            _logger.error("to为空");
            return null;
        }

        if(StringUtils.isEmpty(messageType)) {
            _logger.error("messageType为空");
            return null;
        }

        if(StringUtils.isEmpty(from)) {
            _logger.error("from为空");
            return null;
        }

        if(StringUtils.isEmpty(createTime)) {
            _logger.error("createTime为空");
            return null;
        }

        if(!IMTextHandler.userChannels.containsKey(from)) {
            throw new RuntimeException("当前发送用户不在channel中");
        }

        ChatMessageType chatMessageType = ChatMessageType.getChatMessageType(messageType);
        List<Map<String, Object>> users = querySendUsers(to);

        switch (chatMessageType) {
            case TEXT:
            case QUESTIONNAIRE_SURVEY_MESSAGE:
            case MEDICAL_RECORD_MESSAGE:
                this.sendTextMessage(chatMessage, users);
                break;
            case IMAGE:
                this.sendImageMessage(chatMessage, users);
                break;
            case VIDEO:
                this.sendVideoMessage(chatMessage, users);
                break;
            case SPEECH:
                this.sendSpeechMessage(chatMessage, users);
                break;
            default:
                _logger.error("找不到对应的ChatMessageType类型");
                break;
        }
        //消息数据推送到消息队列再推送到mongodb,这里直接推送到mongodb即可
        this.saveDataToMongoDB(chatMessage, users);
        this.saveChatListToMongoDB(chatMessage, users);
        channel.writeAndFlush(new TextWebSocketFrame(_messageReturnUtils.returnToMessage(true, chatMessage.getReturnAckType())));
        return null;
    }

    protected ChatDocEntity getSendChatDocEntity(ChatMessage chatMessage) {

        ChatDocEntity entity = getChatDocEntity(chatMessage);
        entity.setSendOrReceive(Constant.Key.SEND);
        return entity;
    }

    protected ChatDocEntity getReceiveChatDocEntity(ChatMessage chatMessage, String userOpId) {

        ChatDocEntity entity = getChatDocEntity(chatMessage);
        entity.setReceiveUserOpId(userOpId);
        entity.setIsRead(1);//先固定写1已读
        entity.setIsSend(chatMessage.getIsSend());
        entity.setSendOrReceive(Constant.Key.RECEIVE);
        return entity;
    }

    private ChatDocEntity getChatDocEntity(ChatMessage chatMessage) {

        ChatDocEntity entity = new ChatDocEntity();
        entity.setType(chatMessage.getType());
        entity.setMessageType(chatMessage.getMessageType());
        entity.setMessage(chatMessage.getMessage());
        entity.setFrom(chatMessage.getFrom());
        entity.setTo(chatMessage.getTo());
        entity.setCreateTime(chatMessage.getCreateTime());
        entity.setLength(chatMessage.getLength());
        entity.setFromUserName(chatMessage.getFromUserName());

        if(StringUtils.isNotEmpty(chatMessage.getOriginalFilename())) {
            entity.setOriginalFilename(chatMessage.getOriginalFilename());
        }

        if(StringUtils.isNotEmpty(chatMessage.getSaveFileName())) {
            entity.setFileName(chatMessage.getSaveFileName());
        }

        if(StringUtils.isNotEmpty(chatMessage.getThumpImage())) {
            entity.setThumpImage(chatMessage.getThumpImage());
        }

        return entity;
    }
}
