package com.edison.io.netty.server;

import com.edison.io.netty.protocol.request.UploadRequest;
import com.edison.io.netty.protocol.response.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 文件名:com.edison.io.netty.server.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-10
 * 修改记录:
 */
public class DFSServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接建立。。。");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接关闭...");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UploadRequest request = (UploadRequest)msg;
        System.out.println("业务层准备处理请求:"+ request);
        Response response = new Response();
        response.setCmd(request.getCmd());
        response.setRequestId(request.getRequestId());
        response.setCode("000");
        response.setMessage("处理成功!");
        response.setMd5Key(request.getRequestId().replaceAll("a","x"));
        ctx.writeAndFlush(response);
    }
}
