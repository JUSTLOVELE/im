package com.hyjk.im.server.service;

import com.hyjk.im.common.utils.CommonResult;
import com.hyjk.im.server.model.ChatMessage;

/**
 * @author yangzl 2021.07.26
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public interface UserService {

    /**
     * 获取申请好友和群的列表
     * @param userOpId
     * @param applyType
     */
    public CommonResult getApplyList(String userOpId, Integer applyType, String type);

    /**
     * 确认好友关系
     * @param userOpId
     * @param friendOpId
     * @return
     */
    public boolean checkRelationShip(String userOpId, String friendOpId);

    /**
     * 删除好友
     * @param chatMessage
     */
    public void delFriend(ChatMessage chatMessage);

    /**
     * 查询我的好友列表
      * @param chatMessage
     */
    public void queryMyFriendList(ChatMessage chatMessage);
}
