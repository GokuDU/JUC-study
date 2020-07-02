package com.guo.functional_interface;

import java.util.function.Supplier;

/**
 *  Supplier 供给式接口     没有参数，只有返回值
 */
public class SupplierTest {
    public static void main(String[] args) {
//        Supplier supplier = new Supplier<String>() {
//            @Override
//            public String get() {
//                return "abcde";
//            }
//        };

        Supplier supplier = () -> { return "abcde"; };
        Supplier supplierInteger = () -> { return 1024; };

        System.out.println(supplier.get());
        System.out.println(supplierInteger.get());
    }
}
