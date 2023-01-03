package com.hyjk.im.server.component.im;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author yangzl 2021.05.30
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Component
public class IMServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private IMTextHandler _imHandler;

    @Autowired
    private IMBinaryHandler _imBinaryHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();
        //以下三个是Http的支持
        // HttpRequestDecoder和HttpResponseEncoder的一个组合，针对http协议进行编解码
        pipeline.addLast(new HttpServerCodec());
        //支持写大数据流;分块向客户端写数据，防止发送大文件时导致内存溢
        pipeline.addLast(new ChunkedWriteHandler());
        //http聚合器
        //HttpMessage和HttpContents聚合到一个完成的 FullHttpRequest或FullHttpResponse中,
        // 具体是FullHttpRequest对象还是FullHttpResponse对象取决于是请求还是响应
        pipeline.addLast(new HttpObjectAggregator(1024*62));
        // webSocket 数据压缩扩展，当添加这个的时候WebSocketServerProtocolHandler的第三个参数需要设置成true
        pipeline.addLast(new WebSocketServerCompressionHandler());
        //websocket支持,设置路由
        //pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new WebSocketServerProtocolHandler("/chat", null, true, 10485760));
        //添加自定义的助手类
        //通过调用(触发)下一个handler 的 userEventTiggered , 在该方法中去处理 IdleStateEvent(读空闲，写空闲，读写空闲)
        //pipeline.addLast(new IdleStateHandler(7000,7000,10, TimeUnit.SECONDS));
        pipeline.addLast(_imHandler);
        //pipeline.addLast(_imBinaryHandler);
    }
}
