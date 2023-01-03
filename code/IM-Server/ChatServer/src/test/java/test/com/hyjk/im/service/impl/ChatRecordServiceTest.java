package test.com.hyjk.im.service.impl;

import com.hyjk.im.server.ChatMain;
import com.hyjk.im.server.model.ChatMessage;
import com.hyjk.im.server.service.ChatRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yangzl 2021.06.10
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@RunWith(SpringJUnit4ClassRunner.class)
//指定启动类
@SpringBootTest(classes={ChatMain.class,ChatRecordServiceTest.class})
public class ChatRecordServiceTest {
    //4159c16b741041629eb7f07f3ef11111

    @Autowired
    private ChatRecordService _chatRecordService;

    @Test
    public void queryAllTest() {

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserOpId("4159c16b741041629eb7f07f3ef938ee");
        chatMessage.setUserOpId("ChatRecordServiceTest");
        _chatRecordService.queryAll(null, chatMessage);
    }
}
