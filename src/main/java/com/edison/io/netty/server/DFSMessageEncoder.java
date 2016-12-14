package com.edison.io.netty.server;

import com.edison.io.netty.protocol.Cmd;
import com.edison.io.netty.protocol.response.Response;
import com.edison.io.netty.util.ByteBufUtils;
import com.edison.io.netty.util.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.util.StringUtils;

import java.io.File;

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
        ByteBufUtils.write(msg.getRequestId(),out);
        ByteBufUtils.write(msg.getMd5Key(),out);
        ByteBufUtils.write(msg.getCode(),out);
        out.writeBytes(ByteUtils.wrap(msg.getMessage(),256,(byte)32));
        if(msg.getCmd() == Cmd.DOWLOAD){
            ByteBufUtils.writeFile(msg.getTfp(),out);
        }
    }



    private int calTotalLen(Response msg){
        int len = 260;//cmd(4) message(fixed 256)
        if(!StringUtils.isEmpty(msg.getRequestId())){
            len += msg.getRequestId().getBytes().length;
        }
        if(!StringUtils.isEmpty(msg.getMd5Key())){
            len += msg.getMd5Key().getBytes().length;
        }
        if(!StringUtils.isEmpty(msg.getCode())){
            len += msg.getCode().getBytes().length;
        }

        if(msg.getCmd() == Cmd.DOWLOAD){
            len += (new File(msg.getTfp())).length();
        }
        return len;
    }


}
