package com.guo.lock8;

import java.util.concurrent.TimeUnit;

/**
 *  5. 增加了两个静态的同步方法，只有一个对象     先执行 Send sms？  Phone call？
 *     注意：这里依然让 sendSms() 延迟2秒
 *          类一加载就有了   锁的是class      1 Send sms --> 2 Phone call
 *
 *  6. 同样是两个静态的同步方法，但是有两个对象     先执行 Send sms？  Phone call？
 *          结果依然是      1 Send sms --> 2 Phone call
 *     原因：因为static 锁的是 class对象，而 Phone4 两个对象的 class类模板只有一个
 */
public class Test04 {
    public static void main(String[] args) {
        //  测试 问题5
//        Phone4 phone = new Phone4();
//
//        new Thread(() -> {
//            phone.sendSms();
//        },"A").start();
//        new Thread(() -> {
//            phone.call();
//        },"B").start();

        //  测试 问题6
        // 两个对象的 class类模板只有一个， 锁的是class
        Phone4 phoneA = new Phone4();
        Phone4 phoneB = new Phone4();

        new Thread(() -> {
            phoneA.sendSms();
        },"A").start();
        new Thread(() -> {
            phoneB.call();
        },"B").start();
    }
}

// Phone4 只有唯一的一个 Class 对象
class Phone4 {
    // synchronized 锁的对象是方法的调用者
    //     两个方法用的是同一个锁 （Phone对象） ==》 谁先拿到谁执行
    // static 静态方法
    //     类一加载就有了   锁的是class
    public synchronized static void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Send sms");
    }

    public synchronized static void call() {
        System.out.println("Phone call");
    }
}

