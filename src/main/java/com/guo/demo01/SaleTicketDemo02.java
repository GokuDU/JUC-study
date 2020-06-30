package com.guo.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  真正的多线程开发
 *  线程就是一个单独的资源类，没有任何附属操作
 *
 *    1. 属性、方法
 */
public class SaleTicketDemo02 {
    public static void main(String[] args) {
        // 并发 ： 多线程操作同一个资源类，把资源丢入线程
        Ticket2 ticket = new Ticket2();

        // @FunctionalInterface     函数式接口
        // 可new一个匿名内部类  接口不可以实例化，但是可以使用匿名内部类来实现它
        // jdk1.8   lambda表达式 简化  ()=>{}   () 是从方法体开始  (方法的参数)->{  代码 }
        new Thread(()->{ for (int i = 0; i < 40 ; i++) ticket.sale(); } ,"A").start();
        new Thread(()->{ for (int i = 0; i < 40 ; i++) ticket.sale(); } ,"B").start();
        new Thread(()->{ for (int i = 0; i < 40 ; i++) ticket.sale(); } ,"C").start();

    }
}

// 资源类  OOP
//  Lock三步
// 1. new ReentrantLock();
// 2. lock.lock();  // 加锁
// 3. finally ==》 lock.unlock();  // 解锁
class Ticket2 {
    // 属性 、 方法
    private int number = 30;

    Lock lock = new ReentrantLock();

    public void sale(){

        lock.lock();  // 加锁

        try {
            // 业务代码
            if( number > 0 ) {
                System.out.println(Thread.currentThread().getName() + "卖出来第" + (number--) + "张票，剩余："+number + "张");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();  // 解锁
        }
    }
}