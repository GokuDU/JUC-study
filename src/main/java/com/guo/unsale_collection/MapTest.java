package com.guo.unsale_collection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// java.util.ConcurrentModificationException
public class MapTest {
    public static void main(String[] args) {
        // 工作中不用 HashMap
        // 默认等价于什么  new HashMap<>(16,0.75);
//        Map<String, String> map = new HashMap<>();
        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 30 ; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
