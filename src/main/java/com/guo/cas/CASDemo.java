package com.guo.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  CAS   compareAndSet(int expect, int update) ：比较并交换
 */
public class CASDemo {
    public static void main(String[] args) {
//        AtomicInteger atomicInteger = new AtomicInteger(200);
        // 原子引用
        // AtomicStampedReference 如果泛型是包装类，注意对象的引用问题
        // 实际业务中  这里比较多是一个个对象
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(20,1);

        new Thread(()->{
            // 获取当前版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println("a1==>" + stamp);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("a2 executeing "+atomicStampedReference.compareAndSet(20, 25,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("a2==>" + atomicStampedReference.getStamp());

            System.out.println("a3 executeing "+atomicStampedReference.compareAndSet(25, 20,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("a3==>" + atomicStampedReference.getStamp());

        },"a").start();


        // 和乐观锁的原理相似
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("b1==>" + stamp);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("b2 executeing "+atomicStampedReference.compareAndSet(20, 6,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("b2==>" + atomicStampedReference.getStamp());

        },"b").start();

        // public final boolean compareAndSet(int expect, int update)
        // 参数   期望，更新
        // 如果和期望的值相同，就更新  否则，就不更新
        // CAS 是CPU的并发原语
        // ============================== 捣乱的线程 ==============================
//        System.out.println(atomicInteger.compareAndSet(200, 201));
//        System.out.println(atomicInteger.get());
//
//        System.out.println(atomicInteger.compareAndSet(201, 200));
//        System.out.println(atomicInteger.get());
//
//        // ============================== 期望的线程 ==============================
//        System.out.println(atomicInteger.compareAndSet(200, 1024));
//        System.out.println(atomicInteger.get());
    }
}
