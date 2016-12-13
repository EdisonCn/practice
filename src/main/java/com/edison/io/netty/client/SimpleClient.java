package com.edison.io.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 文件名:com.edison.io.netty.client.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-10
 * 修改记录:
 */
public class SimpleClient {

    public static final String HOST = "127.0.0.1";

    public static final int PORT = 1234;

    public static void main(String[] args) throws InterruptedException {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup(8);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.DEBUG));
                            p.addLast(new LengthFieldBasedFrameDecoder(1024,0,4));
                            p.addLast(new DFSMessageDecoder());
                            p.addLast(new EchoClientHandler());
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }
}
