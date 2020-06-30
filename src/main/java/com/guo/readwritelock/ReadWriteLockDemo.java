package com.guo.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  独占锁 （写锁） 一次只能被一个线程占有
 *  共享锁 （读锁） 多个线程可以同时占有
 *
 *  ReadWriteLock
 *  读-读  可以共存
 *  读-写  不能共存
 *  写-写  不能共存
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
//        MyCache myCache = new MyCache();
        MyCacheLock  myCache = new MyCacheLock();

        // 写入
        for (int i = 1; i <= 5 ; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.put(temp+"",temp+"");
            },String.valueOf(i)).start();
        }

        // 读取
        for (int i = 1; i <= 5 ; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.get(String.valueOf(temp));
            },String.valueOf(i)).start();
        }
    }
}

/**
 *  加锁 的自定义缓存
 */
class MyCacheLock {
    private volatile Map<String,Object> map = new HashMap<>();
    // 读写锁  更加细粒度的控制
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 存  写入的时候，只希望同时只有一个线程 写
    public void put(String key,Object value) {
        // 写锁 获得锁
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"写入"+key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"写入OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 写锁 释放锁
            readWriteLock.writeLock().unlock();
        }
    }

    // 取  读的时候，允许所有线程 读
    public void get(String key) {
        // 加读锁之后  正在读的时候就不能写，而多个线程可以同时读
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"读取"+key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName()+"读取OK,读到的值为"+o);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

/**
 *  自定义缓存   不加锁，可能某个线程正在写，未完成就被其他线程插入了
 */
class MyCache {
    private volatile Map<String,Object> map = new HashMap<>();

    // 存  写的过程
    public void put(String key,Object value) {
        System.out.println(Thread.currentThread().getName()+"写入"+key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName()+"写入完成");
    }

    // 取  读的过程
    public void get(String key) {
        System.out.println(Thread.currentThread().getName()+"读取"+key);
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName()+"读取完成");
    }
}