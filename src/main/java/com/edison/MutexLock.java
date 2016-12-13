package com.edison;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

/**
 * Created by wangzhengfei on 16/7/6.
 */
public class MutexLock implements Lock {

    private Sync sync;

    public MutexLock() {
        this.sync = new Sync();
    }

    public void lock() {
        sync.acquire(1);
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock() {
        try {
            return sync.tryAcquireNanos(1, 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean tryLock(long time, TimeUnit unit)
            throws InterruptedException {
        return sync.tryAcquireNanos(1, TimeUnit.NANOSECONDS.
                convert(time, unit));
    }


    public void unlock() {
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.new ConditionObject();
    }


    private static class Sync extends AbstractQueuedSynchronizer {
        private AtomicInteger state;

        public Sync() {
            state = new AtomicInteger(0);
        }

        @Override
        protected boolean tryAcquire(int arg) {
            return state.compareAndSet(0, 1);
        }

        @Override
        protected boolean tryRelease(int arg) {
            return state.compareAndSet(1, 0);
        }


    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Thread threadA = new Thread(new Task(10));
        //threadA.start();
        //new Thread(new TaskB(threadA)).start();
        int count = 1000;
        Lock lock = new MutexLock();
        for (int i = 0; i < count; i++) {
            new Thread(new LockTestTask(lock)).start();
        }
        //LockSupport.park();
    }

    private static class LockTestTask implements Runnable {
        private Lock lock;
        private Logger logger = new Logger();

        public LockTestTask(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            logger.info("execute LockTestTask begin...");
            lock.lock();
            try {
                logger.info("has get lock...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                logger.info("execute LockTestTask end...");
                lock.unlock();
            }
        }
    }

    private static class ScheduleTaskA implements Runnable {
        private Logger logger = new Logger();

        public void run() {
            logger.info("executing ScheduleTaskA.... ");
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("ScheduleTaskA execute end...");
        }
    }

    private static class Task implements Runnable {

        private Logger logger = new Logger();

        private long interval;

        public Task(long interval) {
            this.interval = interval * 1000 * 1000 * 1000;
        }

        public void run() {
            logger.info("start task ... ");
            while (true) {
                logger.info("do task.....");
                LockSupport.parkNanos(interval);
            }
            //logger.info("task end... ");
        }
    }


    private static class TaskB implements Runnable {
        private Logger logger = new Logger();

        private Thread thread;

        public TaskB(Thread thread) {
            this.thread = thread;
        }

        public void run() {
            logger.info("start taskB ... ");
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.unpark(thread);
            logger.info("taskB end... ");
        }
    }
}
