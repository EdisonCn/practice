package com.edison.io.netty.server;

import com.edison.io.netty.protocol.Cmd;
import com.edison.io.netty.protocol.request.AbstractRequest;
import com.edison.io.netty.protocol.request.DownloadRequest;
import com.edison.io.netty.protocol.request.UploadRequest;
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
 * 创建日期: 2016-12-11
 * 修改记录:
 */
public class SimpleDFSMessageDecoder extends ByteToMessageDecoder {

    private int len;

    private int receivedLen;

    private boolean headRead;

    private int minHeadLen = 36;

    private RandomAccessFile raf;

    private FileChannel channel;

    private AbstractRequest request;



    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readable = in.readableBytes();
        if (headRead) {
            if (request.getCmd() == Cmd.UPLOAD) {
                readContent(in);
            }else if(request.getCmd() == Cmd.DOWLOAD){
                readDownloadContent(in);
            }
        } else {
            //如果还没达到头的长度
            if (readable < minHeadLen) {
                return;
            }
            len = in.readInt();
            int cmd = in.readInt();
            if (cmd == Cmd.UPLOAD) {
                request = new UploadRequest();
            }else if(cmd == Cmd.DOWLOAD){
                request = new DownloadRequest();
            }
            request.setCmd(cmd);
            request.setRequestId(in.readBytes(32).toString(Charset.forName("utf-8")));
            receivedLen = minHeadLen;
            headRead = true;
        }

        if (len == receivedLen) {
            if(channel != null) channel.close();
            if(raf != null) raf.close();
            out.add(request);
        }
    }


    private void readDownloadContent(ByteBuf in) throws IOException {
        int readable = in.readableBytes();
        if(readable < 32) return;
        DownloadRequest r = (DownloadRequest) request;
        r.setMd5Key(in.readBytes(32).toString(Charset.forName("utf-8")).trim());
        receivedLen += 32;
    }

    protected void readContent(ByteBuf in) throws IOException {
        UploadRequest r = (UploadRequest) request;
        int readable = in.readableBytes();
        if (StringUtils.isEmpty(r.getFileName())) {
            if (readable < 64) return;
            r.setFileName(in.readBytes(64).toString(Charset.forName("utf-8")).trim());
            receivedLen += 64;
        } else {
            if (channel == null) {
                File tmpFile = new File(System.getProperty("java.io.tmpdir") + "/" + Instant.now().toEpochMilli() + ".tmp");
                tmpFile.createNewFile();
                r.setTfp(tmpFile.getAbsolutePath());
                raf = new RandomAccessFile(tmpFile, "rw");
                channel = raf.getChannel();
            }
            channel.write(in.readBytes(readable).nioBuffer());
            receivedLen += readable;
        }
    }


}
