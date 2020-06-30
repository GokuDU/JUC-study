package com.guo.unsale_collection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

// java.util.ConcurrentModificationException    并发修改异常
// 导致原因：add 方法没有加锁
public class ListTest {
    public static void main(String[] args) {
        // 并发下 ArrayList 不安全
        /**
         *  解决方案：
         *  1. List<String> list = new Vector<>();
         *  2. List<String> list = Collections.synchronizedList(new ArrayList<>());
         *  3. List<String> list = new CopyOnWriteArrayList<>();
         */

        /**
         *  CopyOnWriteArrayList
         *  CopyOnWrite 写入时复制   COW 计算机程序设计领域的一种优化策略
         *  多个线程调用的时候  list   读取是固定的  写入（可能覆盖）
         *  在写入的时候避免覆盖，造成数据问题
         *  读写分离
         *  CopyOnWriteArrayList [Lock] 相比 Vector [synchronized] 效率更高
         */
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 10 ; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
