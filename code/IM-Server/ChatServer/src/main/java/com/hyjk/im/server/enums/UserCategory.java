package com.hyjk.im.server.enums;

/**
 * @author yangzl 2021.08.06
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public enum UserCategory {


    SYS_OPERATOR("系统运营人员", 0),
    ORG_OPERATOR("机构运营人员", 1),
    ORG_DOCTOR("医生用户", 2),
    NORMAL_CUSTOMER("普通用户(病人)", 3),
    MEDICAL_PROFESSION("医务人员", 4),
    TRAINEE("实习生", 5);
    private String text;

    private Integer value;

    UserCategory(String text, Integer value){
        this.text =  text;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String text(){
        return this.text;
    }

    public static UserCategory getUserCategory(int value) {

        switch (value) {
            case 0:
                return UserCategory.SYS_OPERATOR;
            case 1:
                return UserCategory.ORG_OPERATOR;
            case 2:
                return UserCategory.ORG_DOCTOR;
            case 3:
                return UserCategory.NORMAL_CUSTOMER;
            case 5:
                return UserCategory.TRAINEE;
            default:
                return UserCategory.MEDICAL_PROFESSION;
        }
    }
}
