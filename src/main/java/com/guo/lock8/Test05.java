package com.guo.lock8;

import java.util.concurrent.TimeUnit;

/**
 *  7. 一个静态的同步方法，一个普通的同步方法， 只有一个对象     先执行 Send sms？  Phone call？
 *     注意：这里依然让 sendSms() 延迟2秒
 *                1 Phone call --> 2 Send sms
 *     类是先加载进来的，main是从上到下执行的，线程A先获取到锁，线程B后获取到锁，具体哪个先执行看cpu调度，但是这里线程A睡了2秒
 *     所以结果是 线程B 先执行
 *
 *  8. 一个静态的同步方法，一个普通的同步方法， 两个对象     先执行 Send sms？  Phone call？
 *          结果和问题7是一样的     1 Phone call --> 2 Send sms
 *     new 两个对象，同样是一个 锁的是class ， 另一个 锁的是对象
 *
 */
public class Test05 {
    public static void main(String[] args) {
        // 问题 7
        // 调用静态的同步方法    class类模板只有一个， 锁的是class
        // 调用普通的同步方法    锁的是对象
//        Phone5 phone = new Phone5();
//
//        new Thread(() -> {
//            phone.sendSms();
//        },"A").start();
//        new Thread(() -> {
//            phone.call();
//        },"B").start();

        // 问题 8
        Phone5 phoneA = new Phone5();
        Phone5 phoneB = new Phone5();

        new Thread(() -> {
            phoneA.sendSms();
        },"A").start();
        new Thread(() -> {
            phoneB.call();
        },"B").start();
    }
}

// Phone5 只有唯一的一个 Class 对象
class Phone5 {
    // 静态的同步方法  锁的是class类模板 （类锁）
    public synchronized static void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Send sms");
    }

    // 普通的同步方法  锁的是调用者 （对象锁）
    public synchronized void call() {
        System.out.println("Phone call");
    }

}

