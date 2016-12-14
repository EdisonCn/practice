package com.edison.io.netty.client;

import com.edison.io.netty.protocol.Cmd;
import com.edison.io.netty.protocol.response.Response;
import com.edison.io.netty.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.List;

/**
 * 文件名:com.edison.io.netty.server.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-10
 * 修改记录:
 */
public class DFSMessageDecoder extends ByteToMessageDecoder {


    private boolean headReceived;

    private int len;

    private int cmd;

    private int received;

    private Response response;

    private RandomAccessFile raf;

    private FileChannel channel;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(len == 0 && in.readableBytes() < 36){
            return;
        }
        if(!headReceived){
            readHead(in);
        }else{
            readContent(in);
        }
        if(len == received){
            close();
            out.add(response);
        }
    }

    private void readHead(ByteBuf in){
        response = new Response();
        len = in.readInt();
        cmd = in.readInt();
        response.setCmd(cmd);
        response.setRequestId(ByteBufUtils.toString(in,32));
        headReceived = true;
        received = 36;
    }

    private void readContent(ByteBuf in) throws IOException {
        int readable = in.readableBytes();
        if(StringUtils.isEmpty(response.getMd5Key())){
            if(readable < 32) return;
            response.setMd5Key(ByteBufUtils.toString(in,32));
            readable = in.readableBytes();
            received  += 32;
        }

        if(StringUtils.isEmpty(response.getCode())){
            if(readable < 3) return;
            response.setCode(ByteBufUtils.toString(in,3));
            readable = in.readableBytes();
            received  += 3;
        }

        if(StringUtils.isEmpty(response.getMessage())){
            if(readable < 256) return;
            response.setMessage(ByteBufUtils.stringTrim(in,256));
            readable = in.readableBytes();
            received  += 256;
        }

        if(response.getCmd() == Cmd.DOWLOAD && len > received){
            touchFile();
            channel.write(in.readBytes(readable).nioBuffer());
            received  += readable;
            readable = in.readableBytes();
        }

        if(readable > 0){
            in.skipBytes(readable);
            received  += readable;
        }
    }

    private void touchFile() throws IOException {
        if(raf != null) return;
        File file = new File(System.getProperty("java.io.tmpdir")+"/"+ Instant.now().toEpochMilli()+".tmp");
        file.createNewFile();
        response.setTfp(file.getAbsolutePath());
        raf = new RandomAccessFile(file,"rw");
        channel = raf.getChannel();
    }

    private void close() throws IOException {
        if(channel != null) channel.close();
        if(raf != null) raf.close();
    }
}
