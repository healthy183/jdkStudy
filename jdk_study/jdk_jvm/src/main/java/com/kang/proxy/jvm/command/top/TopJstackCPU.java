package com.kang.proxy.jvm.command.top;

/**
 * User:
 * Description: 运行此代码，cpu会飙高
 * Date: 2023-10-14
 * Time: 8:42
 */
public class TopJstackCPU {

    public static final int initData = 666;

    //public static User user = new User();
    public int compute() { //一个方法对应一块栈帧内存区域
        int i = 0;
        int j = 0;
        int result = (i + j)*10;
        return  result;
    }
    public static void main(String[] args) {
        TopJstackCPU jstackCPU = new TopJstackCPU();
        while (true){
            jstackCPU.compute();
        }
    }
}
