package com.edison;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by wangzhengfei on 16/7/3.
 */
public class Test {

    public static void main(String[] args)
    {
      //  Parent.parentStaticMethod();
//        Parent child = new Child();
//        System.out.println("==============");
//        Parent child2 = new Child();
        AtomicStampedReference<Integer> asr = new AtomicStampedReference<Integer>(100,1);
        asr.compareAndSet(100,101,1,2);
        System.out.println(asr.getReference());
        asr.compareAndSet(101,102,1,3);
        System.out.println("value:"+asr.getReference()+",stamp:"+asr.getStamp());
    }

}
