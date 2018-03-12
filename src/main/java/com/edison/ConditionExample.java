package com.edison;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {

    private static final Lock lock = new ReentrantLock();

    private static final Condition conditionA = lock.newCondition();

    private static final Condition conditionB = lock.newCondition();

    private static final Condition conditionC = lock.newCondition();


    public static void main(String[] args) {


        Thread t1 = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    Thread.sleep(500l);
                    System.out.println("A");
                    conditionB.signal();
                    conditionA.await();
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    lock.lock();
                    Thread.sleep(500l);
                    System.out.println("B");
                    conditionC.signal();
                    conditionB.await();
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
            }

        });

        Thread t3 = new Thread(() -> {
            while (true) {
                try {
                    lock.lock();
                    Thread.sleep(500l);
                    System.out.println("C");
                    conditionA.signal();
                    conditionC.await();
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
            }

        });

        t1.start();
        t2.start();
        t3.start();
    }
}
