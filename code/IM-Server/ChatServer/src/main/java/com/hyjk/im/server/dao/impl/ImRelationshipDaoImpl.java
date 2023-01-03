package com.hyjk.im.server.dao.impl;

import com.hyjk.im.server.core.BaseDao;
import com.hyjk.im.server.dao.ImRelationshipDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

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
@Repository
public class ImRelationshipDaoImpl extends BaseDao implements ImRelationshipDao {

    private static final Log _logger = LogFactory.getLog(ImRelationshipDaoImpl.class);

    /**
     * 查询两个好友的共同好友
     *
     * @param userAOpId:用户A
     * @param userBOpId:用户B
     * @return
     */
    @Override
    public List<Map<String, Object>> querySameRelation(String userAOpId, String userBOpId) {

        StringBuffer sb = new StringBuffer();
        //sb.append("SELECT a.`friend_opId`,");
        //sb.append("(SELECT b.name FROM tba_user b WHERE b.op_id = a.`friend_opId`) `thumbUpName`,");
        sb.append("select ANY_VALUE(a.`friend_id`) phone, a.`friend_opId` friendOpId ");
        sb.append("  FROM im_relationship a WHERE a.`user_opId` IN(?, ?)");
        sb.append(" GROUP BY a.`friend_opId` HAVING COUNT(a.`friend_opId`) > 1");
        String sql = sb.toString();
        _logger.info(sql);
        return this.getJdbcTemplate().queryForList(sql, new Object[] {userAOpId, userBOpId});
    }

    @Override
    public List<Map<String, Object>> queryFriend(String userOpId) {

        String sql = "SELECT a.`friend_opId` userOpId FROM im_relationship a WHERE a.`user_opId` = ?";
        _logger.info(sql);
        return this.getJdbcTemplate().queryForList(sql, new Object[] {userOpId});
    }
}
