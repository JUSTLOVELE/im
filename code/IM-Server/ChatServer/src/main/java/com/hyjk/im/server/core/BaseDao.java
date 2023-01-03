package com.hyjk.im.server.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author yangzl 2021.07.26
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public abstract class BaseDao extends Base {

    @Autowired
    protected JdbcTemplate _jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return this._jdbcTemplate;
    }
}
