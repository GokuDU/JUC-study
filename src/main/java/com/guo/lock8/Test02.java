package com.guo.lock8;

import java.util.concurrent.TimeUnit;

/**
 *  3. 增加了一个普通方法sayHello()  先执行 sendSms 还是 sayHello ？
 *     注意：这里依然让 sendSms() 延迟2秒
 *          1 say hello --> 2 Send sms
 *
 */
public class Test02 {
    public static void main(String[] args) {
        Phone2 phone = new Phone2();

        new Thread(() -> {
            phone.sendSms();
        },"A").start();
        new Thread(() -> {
            phone.sayHello();
        },"B").start();
    }
}

class Phone2 {
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

    // 这里没有锁
    public void sayHello() {
        System.out.println("say hello");
    }
}