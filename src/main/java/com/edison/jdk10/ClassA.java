package com.edison.jdk10;

import java.util.ArrayList;

public class ClassA {

    public static void main(String[] args) {
        var a = new ArrayList<ClassA>();
        System.out.printf("a:"+a);
    }
}
