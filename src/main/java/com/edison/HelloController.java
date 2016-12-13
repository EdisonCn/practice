package com.edison;

import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by wangzf on 16/6/29.
 */
@RestController
public class HelloController {

    public HelloController() {
        this.new Task().run();
    }

    //你好,我是注释啊
    public static void main(String[] args) throws InterruptedException {
        HelloController controller = new HelloController();
        ArrayBlockingQueue queue = new ArrayBlockingQueue(100);

        final Object p = queue.take();
        System.out.println("236678523245343");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HelloController{");
        sb.append('}');
        return sb.toString();
    }

    class Task implements Runnable {
        public void run() {
            System.out.println("Hello");
        }
    }

}