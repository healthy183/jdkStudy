package com.kang.proxy.jvm.command.jmap;

/**
 1，运行 JmapTest
 2，jps 查看JmapTest的进程id*/

/**
 * 3.1，-histo  打印内存信息，实例个数以及占用内存大小
 * jmap  -histo 14104(进程id)   >  ./jmapHisto.txt
 *
 * 3.2，-heap 打印堆信息
 *  jmap -heap   14104(进程id)  > ./jmapHeap.txt
 */



public class JmapTest {

    public static void main(String[] args) {
        int i = 0;
        while (true){
            try {
                System.out.println("sleep"+i++);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
