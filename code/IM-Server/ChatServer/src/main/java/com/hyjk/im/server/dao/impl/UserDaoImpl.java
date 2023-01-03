package com.hyjk.im.server.dao.impl;

import com.hyjk.im.server.core.BaseDao;
import com.hyjk.im.server.dao.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

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
@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

    private final static Log _logger = LogFactory.getLog(UserDaoImpl.class);

    @Override
    public List<Map<String, Object>> queryTrainInfoByUserOpId(String userOpId) {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("u.name `name`, d.photo_res headImage ");
        sb.append("FROM `public_org_doctor` d, `tba_user` u  ");
        sb.append("where u.`ref_entity_id` = d.`trainee_id` ");
        sb.append("and u.`op_id` = ?");
        String sql = sb.toString();
        _logger.info(sql);
        return this.getJdbcTemplate().queryForList(sql, new Object[]{userOpId});
    }

    @Override
    public List<Map<String, Object>> queryDoctorInfoByUserOpId(String userOpId) {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("u.name `name`, d.photo_res headImage ");
        sb.append("FROM `public_org_doctor` d, `tba_user` u  ");
        sb.append("where u.`ref_entity_id` = d.`op_id` ");
        sb.append("and u.`op_id` = ?");
        String sql = sb.toString();
        _logger.info(sql);
        return this.getJdbcTemplate().queryForList(sql, new Object[]{userOpId});
    }

    @Override
    public List<Map<String, Object>> getApplyList(String userOpId, Integer applyType) {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("      (SELECT ");
        sb.append("                        (SELECT b.`photo_res` FROM public_org_doctor b WHERE b.op_id = c.`ref_entity_id`) ");
        sb.append("                FROM tba_user c WHERE c.op_id = a.`from_user_op_id` ) headImage, ");
        sb.append("         (SELECT `name` FROM tba_user d WHERE d.op_id = a.from_user_op_id) userName ,");
        sb.append("         a.`from_user_op_id` userOpId,");
        sb.append("         a.`apply_type` applyType, apply_status applyStatus,");
        sb.append("         a.`group_op_id` groupId,");
        sb.append("         (SELECT `name` FROM im_group e WHERE e.op_id = a.group_op_id) groupName ");
        sb.append(" FROM im_apply_list a WHERE a.`to_user_op_id` = ? AND a.`apply_type` = ? order by a.create_time desc ");
        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, new Object[]{userOpId, applyType});
    }

    @Override
    public Integer checkRelationShip(String userOpId, String friendOpId) {

        String sql = "SELECT COUNT(*) FROM im_relationship WHERE user_opId = '" + userOpId + "' AND friend_opId = '" + friendOpId + "'";
        _logger.info(sql);
        return this.getJdbcTemplate().queryForObject(sql, Integer.class);
    }

    @Override
    public void delImFriend(String userOpId, String friendOpId) {

        String sql = "DELETE FROM im_relationship WHERE user_opId = '" + userOpId + "' AND friend_opId = '" + friendOpId + "'";
        _logger.info(sql);
        this.getJdbcTemplate().update(sql);
        sql = "DELETE FROM im_relationship WHERE user_opId = '" + friendOpId + "' AND friend_opId = '" + userOpId + "'";
        _logger.info(sql);
        this.getJdbcTemplate().update(sql);
    }

    @Override
    public List<Map<String, Object>> queryMyFriend(String userOpId, String name, String phone) {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("a.friend_id friendId,");
        sb.append(" a.`friend_opId` friendOpId, ");
        sb.append("        a.`friend_name` friendName, ");
        sb.append("          (SELECT ");
        sb.append("                 (SELECT b.`photo_res` FROM public_org_doctor b WHERE b.op_id = c.`ref_entity_id`) ");
        sb.append("  FROM tba_user c WHERE c.op_id = a.`friend_opId` ) friendHeadImage ");
        sb.append("  FROM im_relationship a WHERE a.`user_opId` = ? ");

        if(StringUtils.isNotEmpty(name)) {
            sb.append("  AND a.`friend_name` LIKE '" + name + "%' ");
        }

        if(StringUtils.isNotEmpty(phone)) {
            sb.append("  AND a.`friend_id` LIKE '" + phone +"%' ");
        }

        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, new Object[]{userOpId});
    }
}
