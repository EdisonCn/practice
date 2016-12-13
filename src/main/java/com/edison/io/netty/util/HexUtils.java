package com.edison.io.netty.util;

/**
 * 文件名:com.edison.io.netty.protocol.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-11
 * 修改记录:
 */
public class HexUtils {

    public static String toHexString(int i) {
        String str = Integer.toHexString(i);
        int len = str.length();
        StringBuilder builder = new StringBuilder("0x");
        for (int k = len; k < 2; k++) {
            builder.append("0");
        }
        builder.append(str);
        return builder.toString();
    }
}
