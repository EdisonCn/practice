package com.edison.basic;

/**
 * 文件名:com.edison.basic.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2017-01-09
 * 修改记录:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(StaticBlock.name);
        System.out.println(StaticBlock.hello);
        System.out.println("===============================");
        StaticBlock.printName();
        /*new StaticBlock();
        System.out.println("===============================");
        new StaticBlock();*/
    }

}
