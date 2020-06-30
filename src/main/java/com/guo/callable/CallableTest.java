package com.guo.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        new Thread(new Runnable()).start();
//        new Thread(new FutureTask<V>()).start();
//        new Thread(new FutureTask<V>(Callable)).start();

//        new Thread(new FutureTask<Integer>(new MyThread()){
//
//        },"A").start();

        MyThread myThread = new MyThread();
        // FutureTask 实现了 Runnable ，FutureTask 能调用 Callable
        FutureTask futureTask = new FutureTask(myThread);

        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start();     // 结果会被缓存，提高效率

        // 这个 get方法 可能会产生阻塞 需要把它放在最后面  或者使用异步通信来处理
        Integer o = (Integer) futureTask.get();
        System.out.println(o);
    }
}

class MyThread implements Callable {

    @Override
    public Integer call() throws Exception {
        System.out.println("call()");
        return 1024;
    }
}