package com.edison;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by wangzhengfei on 16/7/5.
 */
public class ExecutorCompletionServiceTest extends ThreadPool {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(ThreadLocalRandom.current().nextLong());
        ExecutorCompletionService service = new ExecutorCompletionService(getExecutor(), new LinkedBlockingQueue<Future>());
        List<Future> futures = new ArrayList<Future>();
        int totalTaskCount = 18;
        for (int i = 0; i < totalTaskCount; i++) {
            service.submit(new Task(i));
        }
        for(;;){
            System.out.println(service.take().get());
        }

    }

    private static Executor getExecutor() {

        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue(5), new NamedThreadFactory("AAA", false), new RejectedExecutionHandlerA()/*ThreadPoolExecutor.AbortPolicy()*/);
    }
}
