package com.edison.uitl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by admin on 2016/12/15.
 */
public class NumberUtils {

    public static byte[] int2Bytes(int i) {
        byte[] bits = new byte[4];
        bits[0] = (byte) (i >> 24);
        bits[1] = (byte) (i >> 16);
        bits[2] = (byte) (i >> 8);
        bits[3] = (byte) (i >> 0);
        return bits;
    }

    public static int bytes2Int(byte[] bits) {
        if (bits == null || bits.length == 0) {
            throw new RuntimeException("the array is null or empty.");
        }
        if (bits.length < 4) {
            throw new RuntimeException("length of the array less than 4.");
        }
        return ((bits[0] & 0xFF) << 24) + ((bits[1] & 0xFF) << 16) + ((bits[2] & 0xFF) << 8) + bits[3];
    }

    private static void printBits(byte[] bits) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bits.length; i++) {
            builder.append(bits[i]).append(" ");
        }
        System.out.println(builder.toString());
    }

    public static void main(String[] args) {
        int i = 1024;
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(i);
        for (int k = 0; k < 4; k++) {
            System.out.print(buf.readByte());
        }
        System.out.println("\n==============================");
        printBits(int2Bytes(123456789));
        System.out.println(bytes2Int(int2Bytes(123456789)));
    }
}
