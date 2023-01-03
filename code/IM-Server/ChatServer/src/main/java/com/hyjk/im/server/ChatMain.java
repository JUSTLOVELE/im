package com.hyjk.im.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description:启动
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @author yangzl 2021-05-28
 * @version 1.00.00
 * @history:
 */
@EnableScheduling
@SpringBootApplication
@ServletComponentScan
public class ChatMain {

    public static void main(String[] args) {
        SpringApplication.run(ChatMain.class);
    }
}
