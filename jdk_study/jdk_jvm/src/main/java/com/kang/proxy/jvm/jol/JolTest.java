package com.kang.proxy.jvm.jol;

import org.openjdk.jol.info.ClassLayout;

public class JolTest {
        // -XX:+UseCompressedOops 默认开启的压缩所有指针
        // -XX:+UseCompressedClassPointers 默认开启的压缩对象头里的类型指针Klass Pointer
        // Oops : Ordinary Object Pointers
    public static void main(String[] args) {
        ClassLayout layout = ClassLayout.parseInstance(new Object());
        System.out.println(layout.toPrintable());
        /**
        java.lang.Object object internals:
        OFFSET边界  SIZE   TYPE DESCRIPTION                               VALUE
        0           4        (object header)mark word                      01 00 00 00 (00000001 00000000 00000000 00000000) (1)
        4           4        (object header)mark word                      00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        8           4        (object header)Klass pointer                  e5 01 00 20 (11100101 00000001 00000000 00100000) (536871397)
        12          4        (loss due to the next object alignment)对齐到8的倍数
        Instance size: 16 bytes
        Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */
        System.out.println();
        ClassLayout layoutInt = ClassLayout.parseInstance(new int[]{});
        System.out.println(layoutInt.toPrintable());
        /**
         [I object internals:
         OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         0       4        (object header)mark word                     01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         4       4        (object header)mark word                     00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         8       4        (object header)Klass pointer                 6d 01 00 20 (01101101 00000001 00000000 00100000) (536871277)
         12      4        (object header)数组长度                       00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         16      0    int [I.<elements>                             N/A
         Instance size: 16 bytes
         Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
         */
        System.out.println();
        ClassLayout layoutA = ClassLayout.parseInstance(new A());
        System.out.println(layoutA.toPrintable());
        /**
         com.kang.proxy.jvm.jol.JolTest$A object internals:
         OFFSET  SIZE               TYPE DESCRIPTION                         VALUE
         0      4                    (object header)mark word                01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         4      4                    (object header)mark word                00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         8      4                    (object header)Klass pointer            63 cc 00 20 (01100011 11001100 00000000 00100000) (536923235)
         12     4                    int A.id                                 0
         16     1                    byte A.b                                 0
         17     3                   (alignment/padding gap)
         20     4   java.lang.String A.name                                    null
         24     4   java.lang.Object A.o                                       null
         28     4                    (loss due to the next object alignment)
         Instance size: 32 bytes
         Space losses: 3 bytes internal + 4 bytes external = 7 bytes total
         */
    }
    public static class A {
        //8B mark word
        //4B Klass Pointer 如果关闭压缩  -XX:-UseCompressedOops 或 -XX:-UseCompressedClassPointers，则占用8B
        int id; //4B
        String name; //4B 如果关闭压缩 -XX:-UseCompressedOops 则占用8B
        byte b; //1B
        Object o; //4B 如果关闭压缩 -XX:-UseCompressedOops 则占用8B
    }
}
