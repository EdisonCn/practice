package com.edison;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by wangzhengfei on 16/7/4.
 */
public class ExecutorTest {

    private static final int max_concurrent_counts = 10;

    public static void main(String[] args) throws Exception {
        //ThreadPoolExecutor executor = new ThreadPoolExecutor();
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<Future<String>>();    //Future 相当于是用来存放Executor执行的结果的一种容器
        long start = System.currentTimeMillis();
        for (int i = 0; i < max_concurrent_counts; i++) {
            //results.add(exec.submit(new FutureTask<String>(new CallableA(i))));
            results.add(exec.submit(new CallableA(i)));
        }
        long mark = System.currentTimeMillis();

        while (true) {
            CountDownLatch cdl = new CountDownLatch(max_concurrent_counts);
            for (Future future : results) {
                if (future.isDone()) {
                    cdl.countDown();
                } else if (System.currentTimeMillis() - mark > 2000) {
                    future.cancel(true);
                    System.out.println("attempt cancel task " + future.toString());
                }
            }
            if (cdl.getCount() == 0) break;
        }
        exec.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("totol cost : " + (end - start) / 1000f + " sec.");
    }

    private static ThreadFactory getFactory() {
        ThreadFactory factory = Executors.defaultThreadFactory();

        return factory;
    }

    static class Task implements Runnable {
        private int i;

        public Task(int i) {
            this.i = i;
        }

        public void run() {
            System.out.println("Task [" + i + "] is running...");
        }
    }

    static class CallableA implements Callable<String> {
        public CallableA(int i) {
            this.i = i;
        }

        private int i;

        public String call() throws Exception {
            System.out.println("invoke into callable[" + i + "]'s call method...");
            Random random = new Random();
            Long sleepMills = random.nextLong();
            if (sleepMills < 0) sleepMills = Math.abs(sleepMills);
            sleepMills = sleepMills % 10000 / 2; //控制在5秒以内
            System.out.println("Thread[" + Thread.currentThread().getName() + "] sleep :" + sleepMills);
            Thread.sleep(sleepMills);
            System.out.println("Thread[" + Thread.currentThread().getName() + "] finished.");
            return "callable[" + i + "]";
        }
    }
}
