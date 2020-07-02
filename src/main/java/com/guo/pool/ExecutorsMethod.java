package com.guo.pool;

import java.util.concurrent.*;

// Executors 工具类  3大方法
// 使用线程池之后  应该使用线程池来创建线程


/**
 *   new ThreadPoolExecutor.AbortPolicy()           最大承载满了，还有线程进来，不处理这个线程直接抛出异常
 *   new ThreadPoolExecutor.CallerRunsPolicy()      队列满了，哪来的去哪里   这里是线程进不来 当前线程就是imain线程
 *   new ThreadPoolExecutor.DiscardPolicy()         队列满了,丢掉任务，不会抛出异常
 *   new ThreadPoolExecutor.DiscardOldestPolicy()   队列满了,尝试和最早的线程竞争，不会抛出异常
 */
public class ExecutorsMethod {
    public static void main(String[] args) {
//        ExecutorService threadPool = Executors.newSingleThreadExecutor(); // 单个线程
//        ExecutorService threadPool = Executors.newFixedThreadPool(5); // 创建一个固定的线程池大小
//        ExecutorService threadPool = Executors.newCachedThreadPool(); // cached 缓存 可伸缩的 并发线程数多少，线程池的线程数量就多少

        // 自定义线程池  工作中使用 ThreadPoolExecutor
        // 最大承载  maxPoolSize + Deque    5 + 3
        // * 使用 AbortPolicy() 拒绝策略   当超出最大承载时
        //      * 抛出 java.util.concurrent.RejectedExecutionException
        //      * 最大承载满了，还有线程进来，不处理这个线程直接抛出异常

        // 如何定义最大线程数
        // 1. cpu密集型  cpu多少核，线程池最大值就开启多少条  这样可以保持cpu的效率最高
        //        Runtime.getRuntime().availableProcessors()
        // 2. io密集型  判断程序中十分耗io的线程有多少个  线程池最大值设置要大于这个数，一般设置为这个数的两倍
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
//                5,
                Runtime.getRuntime().availableProcessors(),
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 0; i < 30 ; i++) {
    //            new Thread(()->{},"oldWay").start();
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() + " ok");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 线程池用完  程序结束，关闭线程池
            threadPool.shutdown();
        }
    }
}
