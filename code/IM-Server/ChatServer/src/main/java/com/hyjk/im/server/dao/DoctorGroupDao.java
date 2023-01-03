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
public interface DoctorGroupDao {

    /**
     * 查询一级评论
     * @param groupMessageId
     * @return
     */
    public List<Map<String, Object>> queryFirstOrderComment(String groupMessageId);

    /**
     * 根据医生动态id搜索点赞人
     * @param groupMessageId
     * @return
     */
    public List<Map<String, Object>> queryMomentGood(String groupMessageId);
}
