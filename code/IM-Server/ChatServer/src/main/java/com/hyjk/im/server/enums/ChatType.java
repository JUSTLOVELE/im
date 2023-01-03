package com.hyjk.im.server.enums;

/**
 * @author yangzl 2021.06.02
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public enum ChatType {

    ACK("ack", "应答"),
    LOGIN("login", "登录"),
    GROUP("group", "群消息"),
    TEMP_GROUP("tempGroup", "临时聊天群消息"),
    PERSON("person", "私聊"),
    QUERY_ALL("queryAll", "聊天记录查询"),
    QUERY_PERSON("queryPerson", "私聊记录查询"),
    QUERY_GROUP("queryGroup", "群聊记录查询"),
    QUERY_CHAT_LIST("queryChatList", "查询聊天列表"),
    DELETE_CHAT_LIST("deleteChatList", "删除聊天记录"),
    SEND_ADD_FRIEND("sendAddFriend", "申请添加好友"),
    ACK_ADD_FRIEND("AckAddFriend", "确认添加好友"),
    SEND_ADD_GROUP("sendAddGroup", "邀请入群"),
    ACK_ADD_GROUP("ackAddGroup", "确认入群"),
    MY_FRIEND("myFriend", "我的好友"),
    DEL_FRIEND("delFriend", "删除好友"),
    CREATE_GROUP("createGroup", "创建群聊"),
    GET_APPLY_LIST("getApplyList", "获取好友和群的申请列表"),
    QUIT_GROUP("quitGroup", "退群"),
    QUERY_MY_GROUP("queryMyGroup", "查询我的群聊"),
    QUERY_GROUP_LIST("queryGroupList", "查询群成员列表"),
    LOGOUT("logout", "退出"),
    ;

    private String value;

    private String desc;

    ChatType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ChatType getChatType(String value) {

        for(ChatType type: ChatType.values()){

            if(type.getValue().equals(value)) {
                return type;
            }
        }

        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
