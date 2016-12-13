package com.edison;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wangzhengfei on 16/6/30.
 */
public class RWReentryLock {

    public static void main(String[] args) {
        /*ReadWriteLock lock = new ReentrantReadWriteLock(false);
        String[] aa = (String[]) Array.newInstance(String.class,2);
        System.out.println(aa.length);*/
        LinkedList<String> links = new LinkedList<String>();
        links.add("aaaa");
        links.add("bbbb");
        System.out.println(links);
        HashMap<String,String> maps = new HashMap<String, String>(100);
        maps.put("1","aaaa");
        System.out.println(maps);
    }
}
