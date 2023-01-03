package com.hyjk.im.server.service;

import com.hyjk.im.common.utils.CommonResult;
import com.hyjk.im.server.model.ChatMessage;

/**
 * @author yangzl 2021.07.09
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public interface AskForAddGroupService {

    /**
     * 查询群成员列表
     * @param groupId
     * @return
     */
    public CommonResult queryGroupList(String groupId);

    /**
     * 查询我加入的群聊信息
     * @param userOpId
     * @return
     */
    public CommonResult queryMyGroup(String userOpId);

    /**
     * 退群
     * @param userOpId
     * @param groupOpId
     * @return
     */
    public CommonResult quitGroup(String userOpId, String groupOpId);

    /**
     * 确认用户是否是群成员
     * @param userOpId
     * @param groupOpId
     */
    public boolean checkIsGroupMember(String userOpId, String groupOpId);

    /**
     *
     * 创建群聊
     * @param userOpId
     * @param groupType 1:公开群;2:学组群
     * @param applyJoinOption 0:需要验证;1:自由加入;2:禁止加群
     * @param groupName
     */
    public void createGroup(String userOpId, Integer groupType, Integer applyJoinOption, String groupName);

    /**
     * 邀请入群
     * @param chatMessage
     */
    public void ackAddGroup(ChatMessage chatMessage);

    /**
     * 确认入群
     * @param chatMessage
     */
    public void addGroup(ChatMessage chatMessage);
}
