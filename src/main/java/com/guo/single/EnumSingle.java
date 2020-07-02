package com.guo.single;

import java.lang.reflect.Constructor;

// enum 本身也是一个Class类
public enum  EnumSingle {

    INSTANCE;

    public EnumSingle getInstance() {
        return INSTANCE;
    }
}

class Test {
    public static void main(String[] args) throws Exception{
        EnumSingle instance = EnumSingle.INSTANCE;
        // 反射不能破坏枚举单例
        // * 通过源码能看到构造方法没有参数  但是反射获取构造方法不填参数的情况下，并非如我们想象的
        //   * Exception in thread "main" java.lang.NoSuchMethodException  它是没有这个构造方法的
        // * 通过 jad 工具反编译，得出构造方法有参数的  EnumSingle.class.getDeclaredConstructor(String.class,int.class)
        //   * Exception in thread "main" java.lang.IllegalArgumentException: Cannot reflectively create enum objects
        //   * 这个输出才是我们想要的  Cannot reflectively create enum objects
//        Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor(null);
        Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor(String.class,int.class);
        declaredConstructor.setAccessible(true);
        EnumSingle instance2 = declaredConstructor.newInstance();
        System.out.println(instance);
        System.out.println(instance2);
    }
}