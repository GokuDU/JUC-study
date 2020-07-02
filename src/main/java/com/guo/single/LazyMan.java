package com.guo.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

// 懒汉式单例
public class LazyMan {

    // 红绿灯标志位
    private static boolean flag;

    private LazyMan(){
//        System.out.println(Thread.currentThread().getName()+" is going");
        synchronized (LazyMan.class) {
            if (!flag) {
                // 第一次进入，修改标志位flag为 true
                flag = true;
            }else {
                throw new RuntimeException("请勿试图使用反射破坏异常");
            }
        }
    }

    // 加 volatile 避免指令重排
    private volatile static LazyMan lazyMan;

    // 双重检查锁模式 的 懒汉式单例      DCL懒汉式
    public static LazyMan getInstance() {
        if (lazyMan == null){
            synchronized (LazyMan.class) {
                if (lazyMan == null) {
                    lazyMan = new LazyMan();  // 不是原子性操作
                    /**
                     * 1. 分配内存空间
                     * 2. 执行构造方法，初始化对象
                     * 3. 把这个对象指向这个空间
                     *
                     * 本来是走 123
                     *
                     * 但是如果 线程A 走 132  此时LazyMan还没有完成构造 lazyMan == null
                     * 这个时候 线程B进来了  这个时候就可能出问题了，因为线程A和B都进入这儿判断了
                     *          需要对lazyman静态成员变量加个 volatile
                     *          以保证不同线程的可见性
                     */
                }
            }
        }
        return lazyMan;
    }

    // 使用反射破解
    public static void main(String[] args) throws Exception {
//        LazyMan instance = lazyMan.getInstance();
        Constructor<LazyMan> declaredConstructor = LazyMan.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyMan instance = declaredConstructor.newInstance();

        Field flag = LazyMan.class.getDeclaredField("flag");
        flag.setAccessible(true);
        // 将对象 instance 的 flag 设置为 false
        flag.set(instance,false);

        LazyMan instance2 = declaredConstructor.newInstance();

        System.out.println(instance);
        System.out.println(instance2);
    }

//    // 多线程并发
//    public static void main(String[] args) {
//        for (int i = 0; i < 10 ; i++) {
//            new Thread(()->{
//                LazyMan.getInstance();
//            }).start();
//        }
//    }
}
