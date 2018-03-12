package com.edison.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2017-03-09
 * 修改记录:
 */
public class MapByteBufferTest {


    public static void main(String[] args) throws IOException {
        String path = "/Users/wangzhengfei/Documents/51JK.sql", mode = "rw";
        RandomAccessFile raf = new RandomAccessFile(path, mode);
        long position = raf.length();
        String content = "这是我通过MappedByteBuffer新增的内容";
        int size = content.getBytes().length;
        MappedByteBuffer mbb = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, position, size*1000);
        for(int i=0;i<1000;i++){

            mbb.put(content.getBytes(),0,size);
        }
        raf.close();
    }
}
