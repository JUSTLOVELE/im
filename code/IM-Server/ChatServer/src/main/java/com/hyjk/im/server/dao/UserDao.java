package com.hyjk.im.server.dao;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2021.07.26
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public interface UserDao {


    /**
     * 根据医生UserOpID,查询实习医生部分信息
     *
     * @param userOpId
     * @return
     */
    public List<Map<String, Object>> queryTrainInfoByUserOpId(String userOpId);

    /**
     * 根据医生UserOpID,查询医生部分信息
     *
     * @param userOpId
     * @return
     */
    public List<Map<String, Object>> queryDoctorInfoByUserOpId(String userOpId);

    /**
     * 获取申请好友和群的列表
     * @param userOpId
     * @param applyType
     */
    public List<Map<String,Object>> getApplyList(String userOpId, Integer applyType);

    /**
     * 查询好友关系
     * @param userOpId
     * @param friendOpId
     * @return
     */
    public Integer checkRelationShip(String userOpId, String friendOpId);

    /**
     * 删除IM好友(双向删除)
     * @param userOpId
     * @param friendOpId
     */
    public void delImFriend(String userOpId, String friendOpId);

    /**
     * 查询我的好友
     * @param userOpId
     * @param name
     * @param phone
     * @return
     */
    public List<Map<String, Object>> queryMyFriend(String userOpId, String name, String phone);
}
