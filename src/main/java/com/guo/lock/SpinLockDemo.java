package com.guo.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 *  自旋锁
 */
public class SpinLockDemo {

    // int      默认：0
    // Thread   默认：null
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    // 加锁
    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"==>myLock()");
        // 自旋锁
        while(! atomicReference.compareAndSet(null, thread)) {

        }
    }

    // 解锁
    public void unMyLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"==>unMyLock()");
        atomicReference.compareAndSet(thread, null);
    }
}
