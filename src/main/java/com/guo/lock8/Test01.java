package com.guo.lock8;

import java.util.concurrent.TimeUnit;

/**
 *  关于锁的8个问题
 *  1. 标准情况下，两个线程谁先打印    Send sms？  Phone call？
 *              1 Send sms --> 2 Phone call
 *  2. 如果让 sendSms() 延迟2秒呢？
 *              结果是一样的，还是   1 Send sms --> 2 Phone call
 *  原因是：锁的对象是方法的调用者  当Phone对象调用 sendSms() 方法后，其他线程需要等它释放锁
 */
public class Test01 {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendSms();
        },"A").start();
        new Thread(() -> {
            phone.call();
        },"B").start();
    }
}

class Phone {
    // 锁的对象是方法的调用者
    // 两个方法用的是同一个锁 （Phone对象） ==》 谁先拿到谁执行
    public synchronized  void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Send sms");
    }

    public synchronized void call() {
        System.out.println("Phone call");
    }
}
