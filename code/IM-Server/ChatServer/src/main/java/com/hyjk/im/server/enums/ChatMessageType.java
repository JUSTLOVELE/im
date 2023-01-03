package com.hyjk.im.server.enums;

/**
 * @author yangzl 2021.06.04
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public enum ChatMessageType {


    TEXT("text", "文本"),
    IMAGE("image", "图片"),
    VIDEO("video", "视频"),
    SPEECH("speech", "语音"),
    QUESTIONNAIRE_SURVEY_MESSAGE("questionnaire_survey_message", "问卷"),
    MEDICAL_RECORD_MESSAGE("medical_record_message", "病历")
    ;

    private String value;

    private String desc;

    ChatMessageType(String value, String desc) {

        this.value = value;
        this.desc = desc;
    }

    public static ChatMessageType getChatMessageType(String value) {

        for (ChatMessageType message: ChatMessageType.values()) {

            if(value.equals(message.getValue())) {
                return message;
            }
        }
        //默认返回文本消息
        return TEXT;
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
