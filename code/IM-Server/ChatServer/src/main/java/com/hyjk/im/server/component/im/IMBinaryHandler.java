package com.hyjk.im.server.component.im;


import com.hyjk.im.common.utils.Base64;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author yangzl 2021.05.31
 * @version 1.00.00
 * @Description: TextWebSocketFrame是netty用来处理websocket发来的文本对象
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Deprecated
@Component
@ChannelHandler.Sharable
public class IMBinaryHandler  extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
        System.out.println("IMBinaryHandler channelRead");
        System.out.println("服务器接收到二进制消息,消息长度:[{" + msg.content().capacity() + "}]");

        String s = convertByteBufToString(msg.content());
        System.out.println(s);
//        String[] array = s.split(",");
//        GenerateImage(array[1]);


//        ByteBuf byteBuf = Unpooled.directBuffer(msg.content().capacity());
//        byteBuf.writeBytes(msg.content());
//        ctx.writeAndFlush(new BinaryWebSocketFrame(byteBuf));


//        ByteBuf byteBuf = Unpooled.wrappedBuffer(decode);
//        ctx.writeAndFlush(new BinaryWebSocketFrame(byteBuf));


//        System.out.println(ctx.channel().id().toString());
//        ByteBuf content = msg.content();
//        System.out.println(content.capacity());
//        content.markReaderIndex();
//        int flag = content.readInt();//前四位是标志位
//        System.out.println(flag);
//        content.resetReaderIndex();
//        System.out.println(content.capacity());
//        ByteBuf byteBuf = Unpooled.directBuffer(content.capacity());
//        byteBuf.writeBytes(content);
//
//        ctx.writeAndFlush(new BinaryWebSocketFrame(byteBuf));
    }

    public String convertByteBufToString(ByteBuf buf) {
        String str;
        if (buf.hasArray()) { // 处理堆缓冲区
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            str = new String(bytes, 0, buf.readableBytes());
        }
        return str;
    }

    public static boolean GenerateImage(String imgStr) {
        if (imgStr == null) {
            // 图像数据为空
            return false;
        }
        try {
            // Base64解码
            byte[] b = Base64.decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String imgFilePath = "C:\\logs\\1.jpg";// 新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
