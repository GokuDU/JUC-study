package com.guo.unsale_collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *  和 ArrayList 出现一样的问题     java.util.ConcurrentModificationException
 *
 *  解决方案：
 *  1. Set<String> set = Collections.synchronizedSet(new HashSet<>());
 *  2. Set<String> set = new CopyOnWriteArraySet<>();
 */
public class SetTest {
    public static void main(String[] args) {
        // HashSet 的底层就是 HashMap
        Set<String> set = new HashSet<>();
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
//        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30 ; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(set);
            },String.valueOf(i)).start();
        }

    }
}
