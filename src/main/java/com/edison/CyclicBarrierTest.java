package com.edison;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangzhengfei on 16/7/6.
 */
public class CyclicBarrierTest {

    public static final int thread_count = Runtime.getRuntime().availableProcessors();


    public static void main(String[] args) {
        Logger logger = new Logger();
        ExecutorService es = Executors.newFixedThreadPool(4);
        CyclicBarrier barrier = new CyclicBarrier(thread_count);
        for (int i = 0; i < thread_count; i++) {
            es.submit(new TaskB(barrier));
        }
        logger.info("==========================");

        for (int i = 0; i < thread_count; i++) {
            es.submit(new TaskB(barrier));
        }
        es.shutdown();
    }

    static class TaskB implements Callable<Map<String, Object>> {

        private CyclicBarrier barrier;

        private Logger logger = new Logger();

        public TaskB(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public Map<String, Object> call() throws Exception {
            Map<String, Object> result = new HashMap<String, Object>();
            logger.info("线程" + Thread.currentThread().getName() + "正在写入数据...");
            try {
                Thread.sleep(2*1000);//以睡眠来模拟写入数据操作
                logger.info("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("线程写入完毕，继续处理其他任务...");
            return result;
        }
    }
}
