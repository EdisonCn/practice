package com.edison.io.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 文件名:com.edison.io.netty.server.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-10
 * 修改记录:
 */
public class Server {

    private int PORT;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    public Server(int boss,int worker,int port) {
        bossGroup = new NioEventLoopGroup(boss);
        workerGroup = new NioEventLoopGroup(worker);
        this.PORT = port;
    }

    public void start(boolean async) throws InterruptedException {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        //p.addLast("frameDecode",new LengthFieldBasedFrameDecoder(1024,0,4));
                        //p.addLast("dfs_decoder",new DFSMessageDecoder());
                        //p.addLast("decoder",new StringDecoder(Charset.forName("utf-8")));
                        p.addLast("decoder", new SimpleDFSMessageDecoder());
                        p.addLast("encoder", new DFSMessageEncoder());
                        p.addLast("handler", new DFSServerHandler());
                    }
                });
        //启动服务
        ChannelFuture f = b.bind(PORT).sync();
        if (async) {
            Thread t = new Thread(() -> {
                try {
                    f.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                }
            });
            t.setName("Thread-xDFS-Main");
            t.start();
        } else {
            f.channel().closeFuture().sync();
        }
    }

    public void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
