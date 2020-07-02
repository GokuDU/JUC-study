package com.guo.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 *  求和计算
 *
 *  使用 ForkJoin
 *  1. ForkJoinPool   通过它来执行
 *  2. 计算任务     forkJoinPool.execute(ForkJoinTask<?> task)
 */
public class ForkJoinDemo extends RecursiveTask<Long> {

    // 起始值  结束值
    private Long start;
    private Long end;

    // 临界值
    public static final Long temp = 10000L;

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    // 计算方法
    @Override
    protected Long compute() {
        // 判断是否是拆分完毕  如果拆分完毕就相加
        if ((end - start) <= temp ) {
            Long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else {
            // forkJoin   递归
            // 没有拆分完毕就开始拆分
            Long middle = (start + end) / 2;    // 中间值
            ForkJoinDemo leftTask = new ForkJoinDemo(start, middle);
            ForkJoinDemo rightTask = new ForkJoinDemo(middle+1, end);

            // 拆分任务 把任务压人线程队列
            leftTask.fork();
            rightTask.fork();

            // 合并
            return leftTask.join() + rightTask.join();
        }
    }
}
