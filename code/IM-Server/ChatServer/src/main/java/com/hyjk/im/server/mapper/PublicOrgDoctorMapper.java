package com.hyjk.im.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyjk.im.server.entity.PublicOrgDoctor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author yangzl 2021.06.03
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Repository
public interface PublicOrgDoctorMapper extends BaseMapper<PublicOrgDoctor> {

    @Select("select a.`photo_res` photoRes from public_org_doctor a where a.op_id = #{opId}")
    public String getPhotoResByOpId(@Param("opId") String opId);
}
