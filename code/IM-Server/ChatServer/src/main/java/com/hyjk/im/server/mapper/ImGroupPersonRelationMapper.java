package com.hyjk.im.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyjk.im.server.entity.ImGroupPersonRelationEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author yangzl 2021.06.04
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Repository
public interface ImGroupPersonRelationMapper extends BaseMapper<ImGroupPersonRelationEntity> {


    @Select("SELECT COUNT(*) FROM im_group_person_relation_tbl a WHERE a.`group_op_id` = #{groupId} AND a.`user_op_id` = #{userOpId}")
    public Integer checkIsGroupMember(@Param("userOpId") String userOpId, @Param("groupId") String groupId);

}
