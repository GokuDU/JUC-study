package com.guo.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

// 计算类型  Long
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test01();   // sum：500000000500000000===》执行时间：12032
        test02();   // sum：500000000500000000===》执行时间：6078
//        test03();   // sum：500000000500000000===》执行时间：906
    }

    // 普通计算方法
    public static void test01 () {
        Long sum = 0L;
        long startTime = System.currentTimeMillis();
        for (Long i = 0L; i <= 10_0000_0000 ; i++) {
            sum += i;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("sum："+sum+"===》执行时间：" +(endTime - startTime));
    }

    // 会使用 ForkJoin   ForkJoin 这个框架针对的是大任务执行，效率才会明显的看出来有提升
    public static void test02 () throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinDemo(0L, 10_0000_0000L);
        //  forkJoinPool.execute(task);    // 没有返回结果 public void execute(ForkJoinTask<?> task)
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);  // 提交任务  public <T> ForkJoinTask<T> submit(ForkJoinTask<T> task)
        Long sum = submit.get();

        long endTime = System.currentTimeMillis();
        System.out.println("sum："+sum+"===》执行时间：" +(endTime - startTime));
    }

    // 会使用  Stream并行流
    public static void test03 () {
        long startTime = System.currentTimeMillis();

        // Stream 并行流    (]
        Long sum = LongStream.rangeClosed(0, 10_0000_0000L).parallel().reduce(0, Long::sum);

        long endTime = System.currentTimeMillis();
        System.out.println("sum："+sum+"===》执行时间：" +(endTime - startTime));
    }
}
