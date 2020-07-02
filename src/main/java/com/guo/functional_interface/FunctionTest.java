package com.guo.functional_interface;

import java.util.function.Function;

/**
 *  Function  函数式接口   有一个输入参数，有一个输出
 *        只要是函数式接口  就可以使用 lambda表达式 简化
 */
public class FunctionTest {
    public static void main(String[] args) {
        // 输入什么值，返回什么值
//        Function<String,String> function = new Function<String,String>() {
//            @Override
//            public String apply(String str) {
//                return str;
//            }
//        };

//        Function function = (str) -> {
//            return str;
//        };

        Function<String,String> function = str -> { return str; };

        System.out.println(((Function<String, String>) function).apply("abcd"));
    }
}
