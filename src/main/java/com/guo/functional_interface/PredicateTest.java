package com.guo.functional_interface;

import java.util.function.Predicate;

/**
 *  Predicate  断定式接口    有一个输入参数，返回值只能是 boolean
 */
public class PredicateTest {
    public static void main(String[] args) {
//        Predicate<String> predicate = new Predicate<String>(){
//            // 判断字符串是否为空
//            @Override
//            public boolean test(String str) {
//                return str.isEmpty();
//            }
//        };

        Predicate<String> predicate = (str) -> { return str.isEmpty(); };

        System.out.println(predicate.test(""));
    }
}
