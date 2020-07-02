package com.guo.single;

// 静态内部类
public class Holder {

    private Holder(){

    }

    public static class InnerClass {
        private static final Holder HOLDER = new Holder();
    }

    public static Holder getInstance() {
        return InnerClass.HOLDER;
    }
}
