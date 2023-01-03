package com.hyjk.im.server.util;

/**
 * @author yangzl 2021.06.02
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public class Constant {

    public interface MongoDoc {

        String CHAT_LIST = "chat_list";

        String CHAT_MESSAGE = "chat_message";
    }

    public interface Type {

        String LOGIN = "login";

        String ACK = "ack";

        String HEART_ACK = "heartAck";

        String SEND_ADD_GROUP = "sendAddGroup";

        String ACK_ADD_GROUP = "ackAddGroup";

        String SEND_ADD_FRIEND = "sendAddFriend";

        String CREATE_GROUP = "createGroup";

        String ACK_ADD_FRIEND = "AckAddFriend";

        String GROUP_ACK = "groupAck";

        String PERSON_ACK = "personAck";

        String GET_DOCTOR_GROUP_NEW_MESSAGE = "getDoctorGroupNewMessage";

        String CANCEL_THUMP_UP = "cancelThumpUp";

        String THUMP_UP = "thumpUp";

        String GET_COMMENT = "getComment";

        String DEL_COMMENT = "delComment";

        String LOGOUT = "logout";

        String OFFLINE = "offline";
    }

    public interface Column {

        String APPLY_ID = "apply_id";

        String APPLY_TYPE = "apply_type";

        String FROM_USER_OP_ID = "from_user_op_id";

        String TO_USER_OP_ID = "to_user_op_id";

        String APPLY_STATUS = "apply_status";

        String GROUP_OP_ID = "group_op_id";

        String USER_OP_ID = "user_op_id";
    }


    public interface Key {

        String COMMENTID = "commentId";

        String USER_ID = "userId";

        String CONTENT = "content";

        String FRIEND_OPID = "friendOpId";

        String NAME = "name";

        String THUMBUP_NAME = "thumbUpName";

        String THUMBUP_USER_OPID = "thumbUpUserOpId";
        /**医生动态ID**/
        String GROUPMESSAGEID = "groupMessageId";

        String DELETE = "delete";

        String UUID = "UUID";

        String GROUP_NAME = "groupName";

        String CHAT_NAME = "chatName";

        String GROUP_ID = "groupId";

        String HEAD_IMAGE = "headImage";

        String RECEIVEUSEROPID = "receiveUserOpId";

        String CONTENTTYPE = "contentType";

        String FORMAT = "format";

        String TYPE = "type";

        String ERROR_TYPE = "errorType";

        String ISLOGIN = "isLogin";

        String USEROPID = "userOpId";

        String USER_NAME = "userName";

        String TO = "to";

        String TO_NAME = "toName";

        String MESSAGE_TYPE = "messageType";

        String MESSAGE = "message";

        String FROM = "from";

        String FROM_USER_NAME = "fromUserName";

        String FROM_NAME = "fromName";

        String CREATE_TIME = "createTime";

        String REPLYUSERID = "replyUserId";

        String REPLYUSERNAME = "replyUserName";

        String START_TIME = "startTime";

        String END_TIME = "endTime";

        String IS_READ = "isRead";

        String IS_SEND = "isSend";

        String SEND = "send";

        String RECEIVE = "receive";

        String SENDORRECEIVE = "sendOrReceive";

        String SUCCESS = "success";

        String PERSON = "person";

        String GROUP = "group";

        String ORIGINAL_FILE_NAME = "originalFilename";

        String FILE_NAME = "fileName";

        String NUMBER = "number";

        String LAST_MESSAGE_USER_OPID = "lastMessageUserOpId";

        String LENGTH = "length";

        String THUMP_IMAGE = "thumpImage";
        /**问卷id**/
        String MOMENT_ID = "momentId";
        /**问卷title**/
        String DEMAND_INDEX_TITLE = "demandIndexTitle";
        /**问卷创建者**/
        String DEMAND_INDEX_USERNAME = "demandIndexUserName";
    }
}
