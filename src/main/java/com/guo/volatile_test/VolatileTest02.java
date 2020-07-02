package com.guo.volatile_test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Volatile 不保证原子性
 */
public class VolatileTest02 {

    // Volatile 不保证原子性
//    private volatile static int num = 0;
    private volatile static AtomicInteger num = new AtomicInteger();

    public static void add() {
//        num++;  // 不是一个原子性操作
        num.getAndIncrement();  // AtomicInteger+1  底层： CAS
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20 ; i++) {
            new Thread(()->{
                for (int j = 0; j < 5000 ; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount() > 2) {  // main  gc
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()+" "+num);
    }
}
