package com.kang.proxy.jvm.heap;

import java.util.ArrayList;

/**
 * :一个简单的堆内存溢出demo  OutOfMemoryError: Java heap space
 * eden、s0、s1是堆内存的年轻代，一次minor gc是清空eden、s0垃圾，没有清空的丢到s1
 * 下次minor gc是清空到eden、s1,没有清空的丢到s0；
 * 如此重复minor gc，s0/s1还是满，或者15次还没有清楚的垃圾就丢到old老年区
 * 老年区满就触发full gc，full gc还清不了就触发 OutOfMemoryError: Java heap space
 * full gc会触发STW(stop the world)导致程序卡顿，影响体验
 * jvm优化的本质就是减少full gc
 */
public class HeapTest {

    byte[]  bytes = new  byte[100*1024];

    public static void main(String[] args) throws InterruptedException {
        ArrayList<HeapTest> heapTests = new ArrayList<>();
        while (true){
            heapTests.add(new HeapTest());
            Thread.sleep(30);
        }
    }
}
