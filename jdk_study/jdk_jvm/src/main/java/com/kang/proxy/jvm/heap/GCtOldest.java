package com.kang.proxy.jvm.heap;

/**
 * User:
 * Description: 大对象直接加入老年代
 * Date: 2023-10-01
 * Time: 17:55
 */
public class GCtOldest {
    // -XX:PretenureSizeThreshold=7000000 -XX:+UseSerialGC -XX:+PrintGCDetails
    // -XX:PretenureSizeThreshold，大于此值的对象直接在老年代分配，避免在 Eden 和 Survivor 之间的大量内存复制。
    // 这个参数只在 Serial 和ParNew两个收集器下有效，PretenureSizeThreshold单位是字节，这里指定是7mb
    // -XX:+UseSerialGC 它指定了使用串行垃圾收集器（Serial Garbage Collector）。
    // 串行垃圾收集器是Java虚拟机中最古老的垃圾收集器，它使用单线程进行垃圾回收操作。
    public static void main(String[] args) {
        //8m超过设置的7m，直接到了老年代 tenured generation  total 86016K, used 8192K
        byte[] allocation1 = new byte[8*1024*1024];
        /*
        Heap
         def new generation   total 38720K, used 4142K [0x0000000082200000, 0x0000000084c00000, 0x00000000ac150000)
          eden space 34432K,  12% used [0x0000000082200000, 0x000000008260bba0, 0x00000000843a0000)
          from space 4288K,   0% used [0x00000000843a0000, 0x00000000843a0000, 0x00000000847d0000)
          to   space 4288K,   0% used [0x00000000847d0000, 0x00000000847d0000, 0x0000000084c00000)
         tenured generation   total 86016K, used 8192K [0x00000000ac150000, 0x00000000b1550000, 0x0000000100000000)
           the space 86016K,   9% used [0x00000000ac150000, 0x00000000ac950010, 0x00000000ac950200, 0x00000000b1550000)
         Metaspace       used 3251K, capacity 4496K, committed 4864K, reserved 1056768K
          class space    used 352K, capacity 388K, committed 512K, reserved 1048576K
         */
   }
}
