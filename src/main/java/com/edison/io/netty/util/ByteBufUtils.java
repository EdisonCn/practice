package com.edison.io.netty.util;

import io.netty.buffer.ByteBuf;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by admin on 2016/12/14.
 */
public class ByteBufUtils {

    private static final Charset charset = Charset.forName("utf-8");

    public static String stringTrim(ByteBuf in,int read){
        return toString(in,read).trim();
    }

    public static String stringTrim(ByteBuf in,int read,Charset charset){
        return toString(in,read,charset).trim();
    }

    public static String toString(ByteBuf in,int read){
        return toString(in,read,charset);
    }


    public static String toString(ByteBuf in,int read,Charset charset){
        return in.readBytes(read).toString(charset);
    }

    public static void write(String value, ByteBuf out) {
        if (!StringUtils.isEmpty(value)) {
            out.writeBytes(value.getBytes());
        }
    }



    public static void writeFile(String path, ByteBuf out) throws IOException {
        writeFile(new File(path),out);
    }

    public static void writeFile(File file, ByteBuf out) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel channel = raf.getChannel();
        out.writeBytes(channel,0,(int)raf.length());
        channel.close();
        raf.close();
    }
}
