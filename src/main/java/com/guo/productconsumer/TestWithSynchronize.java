package com.guo.productconsumer;

/**
 * 题目：现在四个线程，可以操作初始值为0的一个变量
 * 实现两个线程对该变量 + 1，两个线程对该变量 -1
 * 实现交替10次
 *
 * 诀窍：
 * 1. 高内聚低耦合的前提下，线程操作资源类
 * 2. 判断 、干活、通知
 * 3. 多线程交互中，必须要防止多线程的虚假唤醒，也即（判断不能用if，只能用while）
 */

public class TestWithSynchronize {
    public static void main(String[] args) {
        Data data = new Data();

        new Thread(() -> {
            for (int i = 0; i < 10 ; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ;
            }
        } ,"A").start();

        new Thread(() -> {
            for (int i = 0; i < 10 ; i++) {
                try {
                    data.decremnet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } ,"B").start();


        new Thread(() -> {
            for (int i = 0; i < 10 ; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } ,"C").start();

        new Thread(() -> {
            for (int i = 0; i < 10 ; i++) {
                try {
                    data.decremnet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } ,"D").start();
    }
}

// 判断等待，业务，通知
//  数字  资源类
class Data {

    private int number = 0;

    //+1  生产者
    public synchronized void increment () throws InterruptedException {
        while (number != 0) {
            // 等待
            this.wait();
        }
        number ++;
        System.out.println(Thread.currentThread().getName() + "==>" + number);
        // 通知其他线程，生产者 +1 完毕了
        this.notifyAll();
    }

    // -1  消费者
    public synchronized void decremnet () throws InterruptedException {
        while (number == 0) {
            // 等待
            this.wait();
        }
        number --;
        System.out.println(Thread.currentThread().getName() + "==>" + number);
        // 通知其他线程，消费者 -1 完毕了
        this.notifyAll();
    }

}
