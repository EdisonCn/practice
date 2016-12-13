package com.edison.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wangzhengfei on 16/7/8.
 */
public class TestOne {

    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("/Users/wangzhengfei/fc.txt");
        FileChannel fc = fin.getChannel();
        System.out.println("文件大小:" + fc.size());
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int len = 0;

        while ((len = fc.read(buffer)) != -1) {
            System.err.println("before flip:" + buffer);
            buffer.flip();
            System.err.println("after flip:");
            System.err.println("pos:" + buffer.position() + ",limit:" + buffer.limit() +
                    ",remain:" + buffer.remaining());
            System.err.print("\n");
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes, 0, len);
            //System.out.print(new String(bytes));
            buffer.clear();
        }

        fc.close();
    }
}
