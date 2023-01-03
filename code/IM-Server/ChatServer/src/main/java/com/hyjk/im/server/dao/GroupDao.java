package com.hyjk.im.server.dao;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2021.07.29
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public interface GroupDao {

    /**
     * 查询群成员列表
     * @param groupId
     * @return
     */
    public List<Map<String, Object>> queryGroupList(String groupId);

    /**
     * 查询我的群聊
     * @param userOpId
     * @return
     */
    public List<Map<String, Object>> queryMyGroup(String userOpId);
}
