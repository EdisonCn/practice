package com.edison.io.netty.util;


/**
 * Created by admin on 2016/12/14.
 */
public class ByteUtils {

    public static byte[] wrap(String orgianlValue, int totalCount, byte padding) {
        byte[] bytes = new byte[totalCount];
        byte[] original = orgianlValue.getBytes();
        System.arraycopy(original,0,bytes,0,original.length);
        for(int i=0;i<original.length -bytes.length;i++){
            bytes[i+original.length] = padding;
        }
        return bytes;
    }

    public static byte[] wrap(String orgianlValue, int totalCount, char padding) {
        return wrap(orgianlValue,totalCount,(byte)padding);
    }
}
