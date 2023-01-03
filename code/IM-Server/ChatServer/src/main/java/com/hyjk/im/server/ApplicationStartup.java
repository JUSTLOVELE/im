package com.hyjk.im.server;

import com.hyjk.im.server.component.im.IMServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description:初始组件
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @author yangzl 2019-11-06
 * @version 1.00.00
 * @history:
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    private static final Log _logger = LogFactory.getLog(ApplicationStartup.class);

    @Autowired
    private IMServer _imServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        _imServer.start();
    }
}