package com.guo.functional_interface;

import java.util.function.Consumer;

/**
 *  Consumer 消费式接口  只有输入 没有返回值
 */
public class ConsumerTest {
    public static void main(String[] args) {
//        Consumer<String> consumer = new Consumer<String>() {
//            // 打印字符串
//            @Override
//            public void accept(String str) {
//                System.out.println(str);
//            }
//        };

        Consumer<String> consumer = (str) -> {
            System.out.println(str);
        };

        consumer.accept("abc");
    }
}
