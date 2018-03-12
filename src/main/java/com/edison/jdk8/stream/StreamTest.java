package com.edison.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by wangzhengfei on 17/6/9.
 */
public class StreamTest {

    public static void main(String[] args) {
        listOP();
    }

    private static void listOP(){
        List<String> strs = Arrays.asList("aaa","bbb","ccc","123","acb","bac","acd","cba","abc","321","cba");
        System.out.println("原链表长度:"+strs.stream().count());
        System.out.println("distinct后的链表长度:"+strs.stream().distinct().count());
        //strs.parallelStream().distinct().forEach((s)-> System.out.println(s));
        Optional<String> result = strs.stream().map((s)->{
            System.out.println("ssss:"+s);
            return s+"2321";
        }).findAny();
        System.out.println("result get:"+result.get());
    }
}
