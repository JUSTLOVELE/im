package com.hyjk.im.server.dao.impl;

import com.hyjk.im.server.core.BaseDao;
import com.hyjk.im.server.dao.GroupDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

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
@Repository
public class GroupDaoImpl extends BaseDao implements GroupDao {

    private final static Log _logger = LogFactory.getLog(UserDaoImpl.class);

    @Override
    public List<Map<String, Object>> queryGroupList(String groupId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("         (SELECT ");
        sb.append("                         (SELECT b.`photo_res` FROM public_org_doctor b WHERE b.op_id = c.`ref_entity_id`) ");
        sb.append("                 FROM tba_user c WHERE c.op_id = a.`user_op_id` ) img, ");
        sb.append("          a.`user_op_id` userOpId, ");
        sb.append("          (SELECT `name` FROM tba_user d WHERE d.op_id = a.`user_op_id` ) userName ");
        sb.append("    FROM im_group_person_relation_tbl a WHERE a.`group_op_id` = ? ");
        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, new Object[]{groupId});
    }

    @Override
    public List<Map<String, Object>> queryMyGroup(String userOpId) {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append(" a.`op_id` groupId,");
        sb.append("         a.`name` groupName,");
        sb.append("          a.`head_img` img,");
        sb.append("          a.`owner_opId` ownerOpId,");
        sb.append("          a.`owner_name` ownerName,");
        sb.append("         a.`type` groupType,");
        sb.append("          a.`apply_join_option` applyJoinOption ");
        sb.append("   FROM im_group a, im_group_person_relation_tbl b ");
        sb.append("   WHERE a.`op_id` = b.`group_op_id` AND b.`user_op_id` = ? ");
        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, new Object[]{userOpId});
    }
}
