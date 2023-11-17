package com.kang.proxy.jvm.stack;

/**
 * User:
 * Description:
 * Date: 2023-09-30
 * Time: 10:00
 */
public class StackOverflowTest {

    static int count = 0 ;
    // -Xss用于设置单个线程栈大小
    // 设置 -Xss128k 可以1100次，如果是默认-Xss1024k是29137次
    // Xss设置越小count值越小，说明一个线程栈里能分配的栈帧就越少，但是对JVM整体来说能开启的线程数会更多
     static void count() {
        count ++;
        count();
    }

    public static void main(String[] args) {
        try {
            StackOverflowTest.count();
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println(count);
        }
    }

}


