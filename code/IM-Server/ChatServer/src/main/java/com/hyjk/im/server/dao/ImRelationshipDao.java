package com.hyjk.im.server.dao;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2021.08.06
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public interface ImRelationshipDao {


    /**
     * 查询userOpId这个人的好友
     * @param userOpId
     * @return
     */
    public List<Map<String, Object>> queryFriend(String userOpId);

    /**
     * 查询两个好友的共同好友
     * @param userAOpId:用户A
     * @param userBOpId:用户B
     * @return
     */
    public List<Map<String, Object>> querySameRelation(String userAOpId, String userBOpId);
}
