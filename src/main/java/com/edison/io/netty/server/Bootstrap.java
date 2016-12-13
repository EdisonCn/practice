package com.edison.io.netty.server;

/**
 * 文件名:com.edison.io.netty.server.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-10
 * 修改记录:
 */
public class Bootstrap {

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server(1,1,1234);
        server.start(true);
        server.shutdown();
    }
}
