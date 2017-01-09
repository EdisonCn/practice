package com.edison.basic;

/**
 * 文件名:com.edison.basic.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2017-01-09
 * 修改记录:
 */
public class StaticBlock {

    public static final String name = "Jack";

    public static final String hello = "Hello,"+getName();

    static {
        System.out.println("this is static block.");
    }

    {
        System.out.println("this is instance block.");
    }

    public StaticBlock(){

    }

    public static void printName(){
        System.out.println(name);
    }

    public static String getName(){
        return name;
    }

}
