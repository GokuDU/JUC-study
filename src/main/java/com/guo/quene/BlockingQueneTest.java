package com.guo.quene;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueneTest {
    public static void main(String[] args) throws InterruptedException {
        test04();
    }

    /**
     *  抛出异常
     */
    public static void test01() {
        // 参数是队列大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        System.out.println("========= 查看队首元素 blockingQueue.element() =========");
        System.out.println(blockingQueue.element());
        System.out.println("========================================================");
        // Exception in thread "main" java.lang.IllegalStateException: Queue full
        // System.out.println(blockingQueue.add("d"));

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        // Exception in thread "main" java.util.NoSuchElementException
        // System.out.println(blockingQueue.remove());
    }

    /**
     *  有返回值，不抛出异常
     */
    public static void test02() {
        // 参数是队列大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        // 返回 false
        System.out.println(blockingQueue.offer("d"));

        System.out.println("========= 查看队首元素 blockingQueue.peek() =========");
        System.out.println(blockingQueue.peek());
        System.out.println("========================================================");

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        // 返回 null
        System.out.println(blockingQueue.poll());
    }

    /**
     *  等待阻塞，一直阻塞
     */
    public static void test03() throws InterruptedException {
        // 参数是队列大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);


        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        // 队列没位置了   一直阻塞
        // blockingQueue.put("d");

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
    }

    /**
     *  等待阻塞，超时退出
     */
    public static void test04() throws InterruptedException {
        // 参数是队列大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);


        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        // 等待阻塞 5 秒  时间超过 2秒 就退出
        blockingQueue.offer("d", 5, TimeUnit.SECONDS);

        System.out.println("----------------------------");

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        // 等待阻塞 2 秒  时间超过 2秒 就退出
        blockingQueue.poll(2, TimeUnit.SECONDS);
    }

}
