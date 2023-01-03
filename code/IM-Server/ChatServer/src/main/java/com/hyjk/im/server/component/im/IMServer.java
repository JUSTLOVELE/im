package com.hyjk.im.server.component.im;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangzl 2021.05.32
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Component
public class IMServer {

    private final static Log _logger = LogFactory.getLog(IMServer.class);

    int NUMBER = Runtime.getRuntime().availableProcessors() * 2;

    @Autowired
    private IMServerInitializer _imServerInitializer;

    public void start() {
        //主线程,接收请求
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //创建从线程组,处理主线程分配的io操作
        EventLoopGroup workerGroup = new NioEventLoopGroup(NUMBER);
        //
        try{

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);//设置队列大小
            // // 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            serverBootstrap.group(bossGroup, workerGroup) //设置线程组
//                    EpollServerSocketChannel.class
                .channel(NioServerSocketChannel.class) //设置通道
                .childHandler(_imServerInitializer); //子处理器用于处理workgroup的工作
            ChannelFuture channelFuture = serverBootstrap.bind(12006).syncUninterruptibly();
            //主线程执行到这里就 wait 子线程结束，子线程才是真正监听和接受请求的，
            // closeFuture()是开启了一个channel的监听器，负责监听channel是否关闭的状态，
            // 如果监听到channel关闭了，子线程才会释放，
            // syncUninterruptibly()让主线程同步等待子线程结果
            channelFuture.channel().closeFuture().syncUninterruptibly();

        }catch (Exception e) {
            _logger.error("", e);
        }
    }
}
