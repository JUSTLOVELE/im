package com.hyjk.im.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Description:初始组件
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @author yangzl 2019-7-11
 * @version 1.00.00
 * @history:
 */
@Component
@Lazy(value=false)
public class InitStartComponent {

	private static final Log _logger = LogFactory.getLog(InitStartComponent.class);
	
	public InitStartComponent() {
		setSystemParam();
	}
	
	@PostConstruct
	public void start() {
		
		_logger.info("项目启动成功！");
	}
	
	private void setSystemParam() {
		_logger.info("设置系统级参数");
	}
	
	@PreDestroy
	public void stop(){
		_logger.info("关闭成功！");
	}
}
