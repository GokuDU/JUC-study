package com.guo.productconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  A 执行完 调用 B
 *  B 执行完 调用 C
 *  C 执行完 调用 A
 */
public class TestWithJUCPrecision {
    public static void main(String[] args) {
        Data3 data = new Data3();

        new Thread(() -> {
            for (int i = 0; i < 10 ; i++) {
                data.printA();
            }
        } , "A" ).start();

        new Thread(() -> {
            for (int i = 0; i < 10 ; i++) {
                data.printB();
            }
        } , "B" ).start();

        new Thread(() -> {
            for (int i = 0; i < 10 ; i++) {
                data.printC();
            }
        } , "C" ).start();

    }
}

// 资源类  Lock
class Data3 {

    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();
    // 标志位
    // A => 1
    // B => 2
    // C => 3
    private int number = 1;

    public void printA () {
        lock.lock();
        try {
            // 业务代码
            // 判断 --》 执行 --》 通知
            while (number != 1) {
                conditionA.await();
            }
            System.out.println(Thread.currentThread().getName() + " is executing(AAAAA)");
            // 唤醒 B
            number = 2;
            conditionB.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB () {
        lock.lock();
        try {
            // 业务代码
            // 判断 --》 执行 --》 通知
            while (number != 2) {
                conditionB.await();
            }
            System.out.println(Thread.currentThread().getName() + " is executing(BBBBB)");
            // 唤醒 C
            number = 3;
            conditionC.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC () {
        lock.lock();
        try {
            // 业务代码
            // 判断 --》 执行 --》 通知
            while (number != 3) {
                conditionC.await();
            }
            System.out.println(Thread.currentThread().getName() + " is executing(CCCCC)");
            // 唤醒 A
            number = 1;
            conditionA.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

