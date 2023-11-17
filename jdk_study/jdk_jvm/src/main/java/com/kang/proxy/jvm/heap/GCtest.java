package com.kang.proxy.jvm.heap;

/**
 * User:
 * Description: 简单创建数组观察minor gc各个区情况
 * Date: 2023-10-01
 * Time: 17:55
 */
public class GCtest {

    //添加运行JVM参数： -XX:+PrintGCDetails
    public static void main(String[] args) {
        byte[] allocation1 = new byte[28*1024*1024];
        // 仅仅一个数组的堆信息(allocation1的大小要根据实际情况调整，目标是eden space达到99%左右，而ParOldGen是0%)
        /*
         PSYoungGen      total 37888K, used 32610K [0x00000000d6100000, 0x00000000d8b00000, 0x0000000100000000)
          eden space 32768K, 99% used [0x00000000d6100000,0x00000000d80d89d0,0x00000000d8100000)
          from space 5120K, 0% used [0x00000000d8600000,0x00000000d8600000,0x00000000d8b00000)
          to   space 5120K, 0% used [0x00000000d8100000,0x00000000d8100000,0x00000000d8600000)
         ParOldGen       total 86016K, used 0K [0x0000000082200000, 0x0000000087600000, 0x00000000d6100000)
          object space 86016K, 0% used [0x0000000082200000,0x0000000082200000,0x0000000087600000)
         Metaspace       used 3250K, capacity 4496K, committed 4864K, reserved 1056768K
          class space    used 352K, capacity 388K, committed 512K, reserved 1048576K
        * */
        byte[]  allocation2 = new byte[2*1024*1024];
        // 两个数组超过 PSYoungGen 空间，触发minor gc，无法清空的对象就去到 ParOldGen
        /**
         Heap
         PSYoungGen      total 37888K, used 3344K [0x00000000d6100000, 0x00000000dab00000, 0x0000000100000000)
         eden space 32768K, 7% used [0x00000000d6100000,0x00000000d6352040,0x00000000d8100000)
         from space 5120K, 18% used [0x00000000d8100000,0x00000000d81f2020,0x00000000d8600000)
         to   space 5120K, 0% used [0x00000000da600000,0x00000000da600000,0x00000000dab00000)
         ParOldGen       total 86016K, used 28680K [0x0000000082200000, 0x0000000087600000, 0x00000000d6100000)
         object space 86016K, 33% used [0x0000000082200000,0x0000000083e02010,0x0000000087600000)
         Metaspace       used 3251K, capacity 4496K, committed 4864K, reserved 1056768K
         class space    used 352K, capacity 388K, committed 512K, reserved 1048576K
         */
        byte[] allocation3 = new byte[1*1024*1024];
        byte[] allocation4 = new byte[1*1024*1024];
        byte[] allocation5 = new byte[1*1024*1024];
        byte[] allocation6 = new byte[1*1024*1024];

        // 备注: Allocation Failure 表明本次引起GC的原因是因为在年轻代中没有足够的空间能够存储新的数据了。
        /**
         [GC (Allocation Failure) [PSYoungGen: 31954K->872K(37888K)] 31954K->29552K(123904K), 0.0155987 secs] [Times: user=0.03 sys=0.00, real=0.02 secs]
         Heap
         PSYoungGen      total 37888K, used 8011K [0x00000000d6100000, 0x00000000dab00000, 0x0000000100000000)
            eden space 32768K, 21% used [0x00000000d6100000,0x00000000d67f0cf8,0x00000000d8100000)
            from space 5120K, 17% used [0x00000000d8100000,0x00000000d81e2020,0x00000000d8600000)
            to   space 5120K, 0% used [0x00000000da600000,0x00000000da600000,0x00000000dab00000)
         ParOldGen       total 86016K, used 28680K [0x0000000082200000, 0x0000000087600000, 0x00000000d6100000)
            object space 86016K, 33% used [0x0000000082200000,0x0000000083e02010,0x0000000087600000)
         Metaspace       used 3252K, capacity 4496K, committed 4864K, reserved 1056768K
            class space    used 352K, capacity 388K, committed 512K, reserved 1048576K
         */
    }
}
