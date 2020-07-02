package com.guo.volatile_test;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class JMMTest {

    // 加 volatile 保证可见性
    private static volatile int num = 0;

    public static void main(String[] args) throws InterruptedException {

        // 除了main线程 再开启一条线程
        new Thread(() -> {
            // 当 number == 0 时线程一直执行
            while (num == 0){

            }
        }).start();


        TimeUnit.SECONDS.sleep(3);
        num = 1;
        System.out.println(num);
    }
}
