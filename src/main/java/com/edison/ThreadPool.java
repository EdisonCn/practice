package com.edison;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangzhengfei on 16/7/5.
 */
public class ThreadPool {

    protected static final int corePoolSize = 5;

    protected static final int maximumPoolSize = 5;

    protected static final long keepAliveTime = 10 * 1000;


    private static ExecutorService getExecutor() {

        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue(5), new NamedThreadFactory("AAA", false), new RejectedExecutionHandlerA()/*ThreadPoolExecutor.AbortPolicy()*/);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executor = getExecutor();
        List<Future> futures = new ArrayList<Future>();
        int totalTaskCount = 18;
        for (int i = 0; i < totalTaskCount; i++) {
            futures.add(executor.submit(new Task(i)));
        }
        //System.out.println(futures.size());
        //executor.shutdown();
        //System.out.println(futures);



        if (executor.isTerminated()) {
            for (int i = 0; i < totalTaskCount; i++) {
                Future future = futures.get(i);
                System.out.println("current future index is " + i + " isDone is " + future.isDone());
                if(future.isDone()){
                    System.out.println(future.get() + ",future status :" + future.isDone());
                }
            }
        }
        executor.shutdown();
    }

    static class Task implements Callable<Map<String, String>> {
        private int i;
        private Map<String, String> result = new HashMap<String, String>();

        public Task(int i) {
            this.i = i;
        }

        public Map<String, String> call() throws Exception {
            String tName = Thread.currentThread().getName();
            System.out.println(tName + "正在执行任务[" + i + "]的任务。。。");
            Thread.sleep(2000L);
            result.put("taskId", String.valueOf(i));
            result.put("taskName", tName);
            result.put("rnum", String.valueOf(new Random().nextLong()));
            return result;
        }


    }

    static class RejectedExecutionHandlerA implements RejectedExecutionHandler {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("线程池拒绝执行。。。");
            executor.purge();

        }
    }

    static class NamedThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        private final String mPrefix;

        private final boolean daemon;

        private final ThreadGroup mGroup;

        public NamedThreadFactory() {
            this("pool-" + POOL_SEQ.getAndIncrement(), false);
        }

        public NamedThreadFactory(String prefix) {
            this(prefix, false);
        }

        public NamedThreadFactory(String prefix, boolean daemon) {
            mPrefix = prefix + "-thread-";
            this.daemon = daemon;
            SecurityManager s = System.getSecurityManager();
            mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
        }

        public Thread newThread(Runnable runnable) {
            String name = mPrefix + mThreadNum.getAndIncrement();
            Thread ret = new Thread(mGroup, runnable, name, 0);
            ret.setDaemon(daemon);
            return ret;
        }

        public ThreadGroup getThreadGroup() {
            return mGroup;
        }
    }
}
