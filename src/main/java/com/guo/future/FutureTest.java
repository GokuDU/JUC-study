package com.guo.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *  异步调用 ： CompletableFuture
 *      异步执行
 *      成功回调
 *      失败回调
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 没有返回值的 runAsync 异步回调
//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName()+" runAsync return void and sleep 2 seconds");
//        });
//
//        // 由于  CompletableFuture.runAsync（）是异步调用   延迟了2s的同时，其他的任务能执行
//        System.out.println("12345");
//
//        completableFuture.get();    // 获取阻塞执行的结果

        // 有返回值的 runAsync 异步回调
        // 在 ajax 中，有成功和失败的回调
        //     成功 ： 返回值
        //     失败 ； 返回错误信息
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName()+" supplyAsync return Integer");
            return 1024;
        });

        Integer returnMessage = completableFuture.whenComplete((t, u) -> {
            System.out.println("t==>" + t); // 正常的返回结果  Integer returnMessage
            System.out.println("u==>" + u); // 错误的信息
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return 233;  // 可以获取到 错误 的返回结果
        }).get();

        System.out.println("return message : "+returnMessage);

    }
}
