package com.hyjk.im.server.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzl 2021.09.27
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@RestController
public class TestAction {

    @RequestMapping("/test")
    public String test() {
        return "hello world!";
    }
}
