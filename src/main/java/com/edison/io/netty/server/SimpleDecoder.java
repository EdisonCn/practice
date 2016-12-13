package com.edison.io.netty.server;

import com.edison.io.netty.protocol.request.UploadRequest;
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
public class SimpleDecoder extends ByteToMessageDecoder {


    private final static int UPLOAD_CMD = 0x01;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int len = in.readInt();
        int cmd = in.readInt();
        if(cmd == UPLOAD_CMD){
            UploadRequest request = new UploadRequest();
            request.setCmd(cmd);
            request.setRequestId(in.readBytes(32).toString(Charset.forName("utf-8")));
            request.setFileName(in.readBytes(len-36).toString(Charset.forName("utf-8")));
            out.add(request);
        }
    }
}
