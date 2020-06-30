package com.guo.assistance;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *  集齐七颗龙珠召唤神龙
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, ()->{
            System.out.println("召唤成龙成功");
        });

        for (int i = 1; i <= 7 ; i++) {
            // lambda怎么操作到i
            final int temp = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"收集了第"+temp+"颗龙珠");

                try {
                    cyclicBarrier.await();      // 每次+1  等待 CyclicBarrier 变成7
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
