package com.hyjk.im.server.component.im;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyjk.im.common.utils.CommonResult;
import com.hyjk.im.server.enums.ChatType;
import com.hyjk.im.server.model.ChatMessage;
import com.hyjk.im.server.service.AskForAddFriendService;
import com.hyjk.im.server.service.AskForAddGroupService;
import com.hyjk.im.server.service.ChatRecordService;
import com.hyjk.im.server.service.UserService;
import com.hyjk.im.server.service.impl.GroupMessageHandler;
import com.hyjk.im.server.service.impl.PersonMessageHandler;
import com.hyjk.im.server.util.Constant;
import com.hyjk.im.server.util.MessageReturnUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author yangzl 2021.05.31
 * @version 1.00.00
 * @Description: TextWebSocketFrame是netty用来处理websocket发来的文本对象
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Component
@ChannelHandler.Sharable
public class IMTextHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final static Log _logger = LogFactory.getLog(IMTextHandler.class);
    //所有正在连接的channel都会存在这里面，所以也可以间接代表在线的客户端,String:userOpId
    public static ConcurrentMap<String, Channel> userChannels = new ConcurrentHashMap<>();
    //所有正在连接的channel都会存在这里面，所以也可以间接代表在线的客户端,key表示channelId,value表示userOpId
    public static ConcurrentMap<String, String> innerChannels = new ConcurrentHashMap<>();
    //在线人数
    public static int online = 0;

    @Autowired
    private MessageReturnUtils _messageReturnUtils;

    @Autowired
    private GroupMessageHandler _groupMessageHandler;

    @Autowired
    private PersonMessageHandler _personMessageHandler;

    @Autowired
    private ChatRecordService _chatRecordService;

    @Autowired
    private AskForAddFriendService _askForAddFriendService;

    @Autowired
    private AskForAddGroupService _askForAddGroupService;

    @Autowired
    private UserService _userService;

    /**
     * 图片、音频转为base64，然后这边直接接受到字符串
     * 接收到客户都发送的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        String message = msg.text();
        _logger.info(message);
        JSONObject json = JSONObject.fromObject(message);
        ChatMessage chatMessage = ( ChatMessage) JSONObject.toBean(json, ChatMessage.class);
        json = null;
        ChatType chatType = ChatType.getChatType(chatMessage.getType());

        switch (chatType) {

            case ACK:
                ctx.channel().writeAndFlush(new TextWebSocketFrame(_messageReturnUtils.returnToHeartAck(true)));
                break;
            case LOGIN:
                login(chatMessage.getUserOpId(), ctx.channel());
                break;
            case GROUP:
                //校验是否在群内
                if(_askForAddGroupService.checkIsGroupMember(chatMessage.getFrom(), chatMessage.getTo())) {
                    _groupMessageHandler.sendMessage(chatMessage, ctx.channel());
                }else{
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(_messageReturnUtils.returnToErrorGroupRelation()));
                }
                break;
            case TEMP_GROUP:
                _groupMessageHandler.sendMessage(chatMessage, ctx.channel());
                break;
            case PERSON:
                //校验是不是好友
                if(_userService.checkRelationShip(chatMessage.getFrom(), chatMessage.getTo())) {
                    _personMessageHandler.sendMessage(chatMessage, ctx.channel());
                }else{
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(_messageReturnUtils.returnToErrorFriendRelation()));
                }
                break;
            case QUERY_ALL:
                _chatRecordService.queryAll(ctx.channel(), chatMessage);
                break;
            case QUERY_GROUP:
                _chatRecordService.queryGroup(ctx.channel(), chatMessage);
                break;
            case QUERY_PERSON:
                _chatRecordService.queryPerson(ctx.channel(), chatMessage);
                break;
            case QUERY_CHAT_LIST:
                _chatRecordService.queryChatList(ctx.channel(), chatMessage);
                break;
            case DELETE_CHAT_LIST:
                _chatRecordService.deleteChat(ctx.channel(), chatMessage);
                break;
            case SEND_ADD_FRIEND:
                _askForAddFriendService.addFriend(chatMessage);
                break;
            case ACK_ADD_FRIEND:
                _askForAddFriendService.ackAddFriend(chatMessage);
                break;
            case SEND_ADD_GROUP:
                _askForAddGroupService.addGroup(chatMessage);
                break;
            case ACK_ADD_GROUP:
                _askForAddGroupService.ackAddGroup(chatMessage);
                break;
            case MY_FRIEND:
                _userService.queryMyFriendList(chatMessage);
                break;
            case DEL_FRIEND:
                _userService.delFriend(chatMessage);
                break;
            case CREATE_GROUP:
                _askForAddGroupService.createGroup(chatMessage.getUserOpId(),
                        chatMessage.getGroupType(),
                        chatMessage.getApplyJoinOption(),
                        chatMessage.getGroupName());
                break;
            case GET_APPLY_LIST:
                CommonResult result = _userService.getApplyList(chatMessage.getUserOpId(), chatMessage.getApplyType(), chatMessage.getType());
                result.setType(chatMessage.getType());
                _messageReturnUtils.writeToChannel(ctx.channel(), result);
                break;
            case QUIT_GROUP:
                CommonResult commonResult = _askForAddGroupService.quitGroup(chatMessage.getUserOpId(), chatMessage.getGroupId());
                commonResult.setType(chatMessage.getType());
                _messageReturnUtils.writeToChannel(ctx.channel(), commonResult);
                break;
            case QUERY_MY_GROUP:
                CommonResult myGroupResult = _askForAddGroupService.queryMyGroup(chatMessage.getUserOpId());
                myGroupResult.setType(chatMessage.getType());
                _messageReturnUtils.writeToChannel(ctx.channel(), myGroupResult);
                break;
            case QUERY_GROUP_LIST:
                CommonResult myGroupListResult = _askForAddGroupService.queryGroupList(chatMessage.getGroupId());
                myGroupListResult.setType(chatMessage.getType());
                _messageReturnUtils.writeToChannel(ctx.channel(), myGroupListResult);
                break;
            case LOGOUT:
                logout(chatMessage.getUserOpId());
                break;
            default:
                //ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
                throw new RuntimeException("type值异常");
        }
    }

    private void logout(String userOpId) {

        if(StringUtils.isEmpty(userOpId)) {
            throw new RuntimeException("userOpId不能为空");
        }

        Channel channel = userChannels.get(userOpId);
        userChannels.remove(userOpId);
        online = userChannels.size();

        if(channel != null) {
            innerChannels.remove(channel.id().asLongText());
        }

        channel.writeAndFlush(new TextWebSocketFrame(_messageReturnUtils.returnToMessage(true, Constant.Type.LOGOUT)));
        channel = null;//置为空表示废弃
    }

    /**
     * 内部上线
     * @param userOpId
     * @param channel
     */
    private void login(String userOpId, Channel channel) {

        if(StringUtils.isEmpty(userOpId)) {
            throw new RuntimeException("userOpId不能为空");
        }

        if(!userChannels.containsKey(userOpId)) {

            userChannels.put(userOpId, channel);
            innerChannels.put(channel.id().asLongText(), userOpId);
            online = userChannels.size();
            channel.writeAndFlush(new TextWebSocketFrame(_messageReturnUtils.returnToLogin(true)));
        }else{
            //channel.writeAndFlush(new TextWebSocketFrame(_messageReturnUtils.returnToLogin(false)));
            Channel oldChannel = userChannels.get(userOpId);
            //移除缓存中的用户
            userChannels.remove(userOpId);
            innerChannels.remove(oldChannel.id().asLongText());
            //发送强制下线的报文
            oldChannel.writeAndFlush(new TextWebSocketFrame(_messageReturnUtils.offline(userOpId)));
            //再次登录后清除原来的channle
            oldChannel.close();
            //重新登录
            userChannels.put(userOpId, channel);
            innerChannels.put(channel.id().asLongText(), userOpId);
            online = userChannels.size();
            channel.writeAndFlush(new TextWebSocketFrame(_messageReturnUtils.returnToLogin(true)));
        }
    }

    /**
     * 用户下线
     * @param ctx
     */
    private void userDown(ChannelHandlerContext ctx) {

        _logger.info(ctx.channel().remoteAddress() + "  下线");
        String channelId = ctx.channel().id().asLongText();
        String userOpId = innerChannels.get(channelId);

        if(StringUtils.isNotEmpty(userOpId)) {
            userChannels.remove(userOpId);
        }

        if(StringUtils.isNotEmpty(channelId)) {
            innerChannels.remove(channelId);
        }

        online = userChannels.size();
        ctx.channel().close();
        ctx.close();
    }

    /**表示连接处于活动状态*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    /**表示连接处于不活动状态*/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        userDown(ctx);
    }

    //出现异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //userDown(ctx);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(_messageReturnUtils.renderError(cause.getMessage())));
        _logger.error("", cause);
    }

    public String getJSON(Object obj) {

        ObjectMapper mapper = new ObjectMapper();
        String msg = "";
        try {
            msg = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            _logger.error("", e);
        }
        _logger.info(msg);
        return msg ;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {

            System.out.println("web socket 握手成功。");
            System.out.println("channelId = " + ctx.channel().id().toString());
            WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String requestUri = handshakeComplete.requestUri();
            System.out.println("requestUri:[{" + requestUri + "}]");
            String subproTocol = handshakeComplete.selectedSubprotocol();
            System.out.println("subproTocol:[{" + subproTocol + "}]");
            handshakeComplete.requestHeaders().forEach(entry -> System.out.println("header key:[{" + entry.getKey() + "}] value:[{" + entry.getValue() + "}]"));
        } else {
            super.userEventTriggered(ctx, evt);
        }

//        if(evt instanceof IdleStateEvent) {
//
//            //将  evt 向下转型 IdleStateEvent
//            IdleStateEvent event = (IdleStateEvent) evt;
//            String eventType = null;
//            switch (event.state()) {
//                case READER_IDLE:
//                    eventType = "读空闲";
//                    break;
//                case WRITER_IDLE:
//                    eventType = "写空闲";
//                    break;
//                case ALL_IDLE:
//                    eventType = "读写空闲";
//                    break;
//            }
//            System.out.println(ctx.channel().remoteAddress() + "--超时时间--" + eventType);
//            System.out.println("服务器做相应处理..");
//
//            //如果发生空闲，我们关闭通道
//            // ctx.channel().close();
//        }

    }
}
