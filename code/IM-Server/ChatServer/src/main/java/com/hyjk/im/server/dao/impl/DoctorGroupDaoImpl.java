package com.hyjk.im.server.dao.impl;

import com.hyjk.im.server.core.BaseDao;
import com.hyjk.im.server.dao.DoctorGroupDao;
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
public class DoctorGroupDaoImpl extends BaseDao implements DoctorGroupDao {

    private final static Log _logger = LogFactory.getLog(DoctorGroupDaoImpl.class);

    @Override
    public List<Map<String, Object>> queryFirstOrderComment(String groupMessageId) {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT a.`user_id` userOpId ");
        sb.append(" FROM doctor_moment_comment_tbl a WHERE  ISNULL(a.`reply_user_id`) = 1 AND a.`moment_id` = ? ");
        String sql = sb.toString();
        _logger.info(sql);
        return this.getJdbcTemplate().queryForList(sql, new Object[] {groupMessageId});
    }

    @Override
    public List<Map<String, Object>> queryMomentGood(String groupMessageId) {

        String sql = "select user_name thumbUpName, op_id thumbUpOpId,user_id thumbUpUserOpId from doctor_moment_good_tbl where moment_id = ? ";
        _logger.info(sql);
        return this.getJdbcTemplate().queryForList(sql, new Object[] {groupMessageId});
    }
}
