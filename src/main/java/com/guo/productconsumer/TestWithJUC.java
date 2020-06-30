package com.guo.productconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestWithJUC {
    public static void main(String[] args) {
        Data2 data = new Data2();

        new Thread(() -> {
            for (int i = 0; i < 10 ; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
class Data2 {

    private int number = 0;

    Lock lock = new ReentrantLock();
    //  Condition 的使用
    //    condition.await();      // 等待
    //    condition.signalAll();  // 唤醒全部
    Condition condition = lock.newCondition();

    //+1  生产者
    public void increment () throws InterruptedException {
        lock.lock();    // 加锁
        try {
            // 业务代码
            while (number != 0) {
                // 等待
                condition.await();
            }
            number ++;
            System.out.println(Thread.currentThread().getName() + "==>" + number);
            // 通知其他线程，生产者 +1 完毕了
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();  //释放锁
        }

    }

    // -1  消费者
    public void decremnet () throws InterruptedException {
        lock.lock();    // 加锁
        try {
            // 业务代码
            while (number == 0) {
                // 等待
                condition.await();
            }
            number --;
            System.out.println(Thread.currentThread().getName() + "==>" + number);
            // 通知其他线程，消费者 -1 完毕了
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();  //释放锁
        }
    }

}

