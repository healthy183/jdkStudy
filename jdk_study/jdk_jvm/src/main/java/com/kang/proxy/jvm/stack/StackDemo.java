package com.kang.proxy.jvm.stack;

import com.kang.proxy.jvm.classload.User;

/**
 * User:
 * Description: 通过 javap -c  StackDemo.class  > StackDemo.txt生成txt文件
 * Date: 2023-09-29
 * Time: 23:30
 */
public class StackDemo {

    private static final int counts = 300;
    private static User user = new User();

    public int compute(){
        int a = 1;
        int b = 2;
        int c = (a+b)*10;
        return c;
    }

    public static void main(String[] args) {
        StackDemo stackDemo = new StackDemo();
        int compute = stackDemo.compute();
        System.out.println(compute);
    }
}
