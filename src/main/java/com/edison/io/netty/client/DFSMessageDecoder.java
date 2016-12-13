package com.edison.io.netty.client;

import com.edison.io.netty.protocol.response.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 文件名:com.edison.io.netty.server.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-10
 * 修改记录:
 */
public class DFSMessageDecoder extends ByteToMessageDecoder {


    private final static int UPLOAD_CMD = 0x01;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int len = in.readInt();
        int cmd = in.readInt();
        Response response = new Response();
        response.setCmd(cmd);
        response.setRequestId(in.readBytes(32).toString(Charset.forName("utf-8")).trim());
        response.setMd5Key(in.readBytes(32).toString(Charset.forName("utf-8")).trim());
        response.setCode(in.readBytes(3).toString(Charset.forName("utf-8")).trim());
        response.setMessage(in.readBytes(len - 71).toString(Charset.forName("utf-8")).trim());
        out.add(response);
    }
}
