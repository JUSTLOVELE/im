package com.hyjk.im.server.util;

import com.hyjk.im.common.utils.CommonResult;
import com.hyjk.im.server.document.ChatDocEntity;
import com.hyjk.im.server.service.impl.ChatRecordServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yangzl 2021.06.10
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Component
public class MongoDBUtils {

    private final static Log _logger = LogFactory.getLog(MongoDBUtils.class);

    @Autowired
    private MongoTemplate _mongoTemplate;

    public CommonResult queryWithDesc(List<Criteria> criterias, Class entity ,Integer page, Integer limit, String... properties ) {

        Query query = new Query();

        for(Criteria c: criterias) {
            query.addCriteria(c);
        }
        //查询总数
        long count = _mongoTemplate.count(query, ChatDocEntity.class, Constant.MongoDoc.CHAT_MESSAGE);
        Pageable pageable = PageRequest.of(page-1, limit);
        Sort sort = Sort.by(Sort.Direction.DESC, properties);
        List list = _mongoTemplate.find(query.with(pageable).with(sort), entity);
        CommonResult result = CommonResult.success("", list, count);

        return result;
    }
}
