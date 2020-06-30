package com.guo.assistance;

import java.util.concurrent.CountDownLatch;

// 减法计数器
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // 倒计时 总数是 5
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 1; i <= 5  ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + " go out");
                countDownLatch.countDown(); // 数量 -1
            },String.valueOf(i)).start();
        }

        // 阻塞等待计数器归零   归零后  await 方法阻塞的线程会被唤醒，继续执行
        countDownLatch.await();

        System.out.println("close door");
    }
}
