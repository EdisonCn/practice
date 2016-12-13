package com.edison.io.netty.server;

import com.edison.io.netty.protocol.response.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.util.StringUtils;

/**
 * 文件名:com.edison.io.netty.server.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-11
 * 修改记录:
 */
public class DFSMessageEncoder extends MessageToByteEncoder<Response> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
        out.writeInt(calTotalLen(msg));
        out.writeInt(msg.getCmd());
        writeBytes(msg.getRequestId(),out);
        writeBytes(msg.getMd5Key(),out);
        writeBytes(msg.getCode(),out);
        writeBytes(msg.getMessage(),out);

    }

    private void writeBytes(String value, ByteBuf out){
        if(!StringUtils.isEmpty(value)){
            out.writeBytes(value.getBytes());
        }
    }

    private int calTotalLen(Response msg){
        int len = 4;//cmd length
        if(!StringUtils.isEmpty(msg.getRequestId())){
            len += msg.getRequestId().getBytes().length;
        }
        if(!StringUtils.isEmpty(msg.getMd5Key())){
            len += msg.getMd5Key().getBytes().length;
        }
        if(!StringUtils.isEmpty(msg.getCode())){
            len += msg.getCode().getBytes().length;
        }
        if(!StringUtils.isEmpty(msg.getMessage())){
            len += msg.getMessage().getBytes().length;
        }
        return len;
    }


}
