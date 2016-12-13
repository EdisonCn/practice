package com.edison.redis;

import com.edison.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by wangzhengfei on 16/7/7.
 */
public class RedisTrans {

    private static final String host = "127.0.0.1";

    private static final int port = 6379;

    private static JedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        config.setMaxIdle(500);
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(5);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(1000 * 100);
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "127.0.0.1", 6379);

    }

    public static void main(String[] args) {

        try{
            int maxThreads = 10;
            ExecutorService service = Executors.newFixedThreadPool(maxThreads);
            for (int i = 0; i < maxThreads; i++) {
                if (i % 2 == 0) {
                    service.submit(new TaskA());
                } else {
                    service.submit(new TaskB());
                }
            }
            service.shutdown();
            //multiA();
        }finally {
            //pool.destroy();
        }
    }


    public static void testHash() {
        Map<String, String> user = new HashMap<String, String>();
        user.put("name", "xinxin");
        user.put("age", "22");
        user.put("qq", "123456");
        pool.getResource().hmset("user", user);
        System.out.println(pool.getResource().hgetAll("user").keySet());
    }

    private static class TaskA implements Callable<String> {
        private Logger logger = new Logger();
        public String call() throws Exception {
            logger.info("任务处理开始");
            long start = System.currentTimeMillis();
            Transaction tx = pool.getResource().multi();
            for (int i = 0; i < 100000; i++) {
                tx.set("t" + i, "t" + i);
            }
            List<Object> results = tx.exec();
            long end = System.currentTimeMillis();
            System.out.println("Transaction SET: " + ((end - start)/1000.0) + " seconds");
            logger.info("任务处理结束");
            return "OK";
        }
    }

    private static class TaskB implements Callable<String> {

        private Logger logger = new Logger();
        public String call() throws Exception {
            logger.info("任务处理开始");
            long start = System.currentTimeMillis();
            Transaction tx = pool.getResource().multi();
            for (int i = 0; i < 100000; i++) {
                tx.set("t" + i, "t" + i);
            }
            List<Object> results = tx.exec();
            long end = System.currentTimeMillis();
            System.out.println("Transaction SET: " + ((end - start)/1000.0) + " seconds");
            logger.info("任务处理结束");
            return "OK";
        }
    }

    public static void multiA() {
        /*Transaction tx = jedis.multi();
        System.out.println(tx.get("A").toString());
        tx.set("A", "aaaa");
        tx.set("B", "bbbb");
        tx.exec();*/
        long start = System.currentTimeMillis();
        Transaction tx = pool.getResource().multi();
        for (int i = 0; i < 100000; i++) {
            tx.set("t" + i, "t" + i);
        }
        List<Object> results = tx.exec();
        long end = System.currentTimeMillis();
        System.out.println("Transaction SET: " + ((end - start)/1000.0) + " seconds");
        //jedis.disconnect();
    }

}
