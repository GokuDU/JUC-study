package com.guo.stream;

import java.util.Arrays;
import java.util.List;

/**
 *  要求  只能用一行代码实现
 *
 *  现在有5个用户！ 筛选：
 *  1. id为偶数
 *  2. 年龄大于25
 *  3. 用户名转为大写字母
 *  4. 用户名字母倒序排列
 *  5. 只输出一个用户
 */
public class StreamTest {
    public static void main(String[] args) {
        User userA = new User(1, "a", 21);
        User userB = new User(2, "b", 22);
        User userC = new User(3, "c", 27);
        User userD = new User(4, "d", 26);
        User userE = new User(6, "e", 30);

        // 集合就是存储
        List<User> userList = Arrays.asList(userA, userB, userC, userD, userE);

        //计算交个Stream流
        userList.stream()
                .filter(user -> {return user.getId()%2==0;})
                .filter(user -> {return user.getAge()>25;})
                .map(user -> {return user.getName().toUpperCase();})
                .sorted((user1,user2)->{return user2.compareTo(user1);})
                .limit(1)
                .forEach(System.out::println);
    }
}
