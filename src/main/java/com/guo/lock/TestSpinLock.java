package com.guo.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestSpinLock {
    public static void main(String[] args) {
//        Lock lock = new ReentrantLock();
//        lock.lock();
//        lock.unlock();

        // 底层使用自旋锁 CAS
        SpinLockDemo lock = new SpinLockDemo();

        // SpinLockDemo 中的 AtomicReference 保证了 T1拿到锁之后，T2必须等T1解锁完，才能拿到锁
        new Thread(()->{
            lock.myLock();

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unMyLock();
            }

        },"T1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // T2线程 需要等待 T1 释放锁 ，这个期间 T2 一直在自旋
        //  当 T1 释放锁之后 atomicReference的期望值至null [expect ==》 null]  --》 T2才能拿到锁
        new Thread(()->{
            lock.myLock();

            try {
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unMyLock();
            }

        },"T2").start();

    }
}
