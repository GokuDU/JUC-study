package com.guo.quene;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 *  同步队列
 *  和其他的BlockingQuene 不同  SynchronousQueue 本身不存储元素
 *  put 一个元素进去，必须先 take 取出来  否则不能再次 put 元素进去
 */
public class SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
//        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>(); // 同步队列
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();  // 同步队列

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+" put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName()+" put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName()+" put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName()+" getValue: "+blockingQueue.take());
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName()+" getValue: "+blockingQueue.take());
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName()+" getValue: "+blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
