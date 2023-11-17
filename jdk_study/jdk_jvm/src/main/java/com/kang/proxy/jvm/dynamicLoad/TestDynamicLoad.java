package com.kang.proxy.jvm.dynamicLoad;

/**
 * User:
 * Description:
 * Date: 2023-09-17
 * Time: 21:29
 */
public class TestDynamicLoad {

     static {
         System.out.println("=====load dynamicLoad========");
     }

    public static void main(String[] args) {
        new A();
        System.out.println("====loadin test=======");
        B b = null;
    }

}

class A {
    static {
        System.out.println("=====load A========");
    }

    public  A(){
        System.out.println("=====initial A========");
    }
}

class B {
    static {
        System.out.println("=====load B========");
    }

    public  B(){
        System.out.println("=====initial B========");
    }
}
