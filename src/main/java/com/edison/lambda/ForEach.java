package com.edison.lambda;

import java.util.stream.IntStream;

/**
 * 文件名:com.edison.lambda.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-04
 * 修改记录:
 */
public class ForEach {

    public static void foreach(int min, int max) {
        System.out.println(IntStream.range(min, max).count());
        System.out.println(IntStream.range(min, max).sum());
        System.out.println(IntStream.range(min, max).anyMatch(i -> i % 19 == 0));;
    }

    public static void main(String[] args) {
        foreach(1, 20);
    }
}
