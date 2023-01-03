package com.hyjk.im.server.util;

import com.hyjk.im.server.core.Base;
import com.hyjk.im.server.enums.ReturnErrorType;
import com.hyjk.im.server.model.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
public class MessageReturnUtils extends Base {

    /**
     * 返回给to的信息也就是发送给接收端的用户信息
     * @param chatMessage
     * @return
     */
    public String returnToMessage(ChatMessage chatMessage) {

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.TYPE, chatMessage.getType());
        param.put(Constant.Key.MESSAGE_TYPE, chatMessage.getMessageType());
        param.put(Constant.Key.MESSAGE, chatMessage.getMessage());
        param.put(Constant.Key.FROM, chatMessage.getFrom());
        param.put(Constant.Key.FROM_USER_NAME, chatMessage.getFromUserName());
        param.put(Constant.Key.TO, chatMessage.getTo());
        param.put(Constant.Key.CREATE_TIME, chatMessage.getCreateTime());
        param.put(Constant.Key.RECEIVEUSEROPID, chatMessage.getReceiveUserOpId());
        param.put(Constant.Key.IS_SEND, chatMessage.getIsSend());
        param.put(Constant.Key.LENGTH, chatMessage.getLength());
        param.put(Constant.Key.THUMP_IMAGE, chatMessage.getThumpImage());

        return this.getJSON(param);
    }

    public String offline(String userOpId) {

        Map<String, Object> map = new HashMap<>();
        map.put(Constant.Key.TYPE, Constant.Type.OFFLINE);
        map.put(Constant.Key.USEROPID, userOpId);

        return getJSON(map);
    }

    public String returnToMessage(boolean flag, String type) {

        Map<String, Object> map = new HashMap<>();
        map.put(Constant.Key.TYPE, type);
        map.put(Constant.Key.SUCCESS, flag);

        return getJSON(map);
    }

    public String returnToErrorMessage(boolean flag, String type, Integer errorType) {

        Map<String, Object> map = new HashMap<>();
        map.put(Constant.Key.TYPE, type);
        map.put(Constant.Key.SUCCESS, flag);
        map.put(Constant.Key.ERROR_TYPE, errorType);

        return getJSON(map);
    }

    public String returnToErrorGroupRelation() {
        return returnToErrorMessage(false, Constant.Type.GROUP_ACK, ReturnErrorType.NOT_GROUP.getValue());
    }

    public String returnToErrorFriendRelation() {
        return returnToErrorMessage(false, Constant.Type.PERSON_ACK, ReturnErrorType.NOT_FRIEND.getValue());
    }

    public String returnToAck(boolean flag) {
        return returnToMessage(flag, Constant.Type.ACK);
    }

    public String returnToHeartAck(boolean flag) {
        return returnToMessage(flag, Constant.Type.HEART_ACK);
    }

    public String returnToLogin(boolean flag) {
        return returnToAck(flag);
    }
}
