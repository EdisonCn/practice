package com.edison.io.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.Instant;

/**
 * 文件名:com.edison.io.netty.client.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-10
 * 修改记录:
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf header;

    private final static String requestId = "ed791554bed311e68ebb2a2f70a4e590";

    //小文件
    //private final static File file = new File("C:\\dev_tools\\neon_1a\\artifacts.xml");

    //大文件
    private final static File file = new File("C:\\dev_tools\\mysql-5.7.14-winx64.zip");

    private final static int UPLOAD_CMD = 0x01;

    private long start;

    private byte[] names;
    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler() {
        String fileName = file.getName();
        names = warpFileName(fileName);
        Integer len = (int)(36 + names.length+file.length());
        header = Unpooled.buffer(len.intValue());
        header.writeInt(len);
        header.writeInt(UPLOAD_CMD);
        header.writeBytes(requestId.getBytes());
        header.writeBytes(names);
    }

    private byte[] warpFileName(String fileName){

        byte[] bytes = new byte[64];
        byte[] original = fileName.getBytes();
        System.arraycopy(original,0,bytes,0,original.length);
        for(int i=0;i<original.length -bytes.length;i++){
            bytes[i+original.length] = 32;
        }
        return bytes;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        start = Instant.now().toEpochMilli();
        ctx.writeAndFlush(header);


        /*//使用zero copy ~1s
        DefaultFileRegion dfr = new DefaultFileRegion(file,0,file.length());
        ctx.writeAndFlush(dfr);

        */


        //使用管道传输方式 ~ [3,5] s
        /*RandomAccessFile raf = new RandomAccessFile(file,"rw");
        FileChannel channel = raf.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(channel.read(buffer) != -1){
            buffer.flip();
            ctx.write(Unpooled.copiedBuffer(buffer));
        }
        channel.close();
        raf.close();
        ctx.flush();*/


        //使用传统流读取方式 ~ [3,5] s.
        InputStream is = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int available;
        int total = 0;
        while((available = is.read(buffer)) != -1){
            ctx.writeAndFlush(Unpooled.copiedBuffer(buffer,0,available));
            total+=available;
        }
        is.close();
        System.out.println("发送完毕...，累计发送"+total);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
        System.out.println("接收到服务端处理结果:" + msg);
        long end = Instant.now().toEpochMilli();
        System.out.println("处理耗时："+(end -start)/1000f+" sec.");
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
