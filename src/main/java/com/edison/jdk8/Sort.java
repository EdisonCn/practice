package com.edison.jdk8;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名:com.edison.jdk8.Sort
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-07-31
 * 修改记录:
 */
public class Sort {

    public static void main(String[] args) {

        List<String> strs = new ArrayList<String>();
        strs.add("a");
        strs.add("ab");
        strs.add("abc");
        strs.add("str01");
        strs.add("str02");
        strs.add("str03");
        strs.add("str04");
        System.out.println(strs.stream().filter(str -> str.length() > 2));;
    }

}
