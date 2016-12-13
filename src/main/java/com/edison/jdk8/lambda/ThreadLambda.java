package com.edison.jdk8.lambda;

import java.time.Instant;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 文件名:com.edison.jdk8.lambda.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-08
 * 修改记录:
 */
public class ThreadLambda {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(5, 100, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
        for (int i = 0; i < 100; i++) {
            service.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                long sleep = Instant.now().toEpochMilli() % 1000;
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1,TimeUnit.MINUTES);
    }
}