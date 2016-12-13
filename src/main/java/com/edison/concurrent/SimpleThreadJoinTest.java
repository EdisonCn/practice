package com.edison.concurrent;

/**
 * 文件名:com.edison.concurrent.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-04
 * 修改记录:
 */
public class SimpleThreadJoinTest {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(()->{
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            System.out.println("take perform finished.");
        });
        t.setDaemon(false);
        t.start();
        t.interrupt();
        t.join();

    }
}
