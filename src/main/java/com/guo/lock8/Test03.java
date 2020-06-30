package com.guo.lock8;

import java.util.concurrent.TimeUnit;

/**
 *  4 两个对象 两个同步方法     先执行 Send sms？  Phone call？
 *    注意：这里依然让 sendSms() 延迟2秒
 *         1 Phone call --> 2 Send sms
 */
public class Test03 {
    public static void main(String[] args) {
        // 两个对象，代表两个调用者
        Phone3 phoneA = new Phone3();
        Phone3 phoneB = new Phone3();

        new Thread(() -> {
            phoneA.sendSms();
        },"A").start();
        new Thread(() -> {
            phoneB.call();
        },"B").start();
    }
}

class Phone3 {
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

