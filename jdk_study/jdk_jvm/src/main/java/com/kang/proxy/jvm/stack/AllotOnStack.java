package com.kang.proxy.jvm.stack;

import com.kang.proxy.jvm.classload.User;

/**
 * 展示"开启逃逸分析参数"、"开启标量替换参数"对gc的影响
 * @author healthy
 */
public class AllotOnStack {
   /*
    -XX:+DoEscapeAnalysis     开启逃逸分析参数
    -XX:+EliminateAllocations 开启标量替换参数
    -XX:+PrintGC 输出GC日志*/
    /*栈上分配，标量替换
    代码调用了1亿次alloc()，如果是分配到堆上，大概需要1GB以上堆空间，如果堆空间小于该值，必然会触发GC。
    使用如下参数不会发生GC(默认) 有7次gc
    -Xmx5m -Xms5m -XX:+PrintGC -XX:+DoEscapeAnalysis -XX:+EliminateAllocations
    使用如下参数都会发生大量GC 第一种1447次，第二种1403次
   -Xmx5m -Xms5m -XX:+PrintGC -XX:-DoEscapeAnalysis -XX:+EliminateAllocations
   -Xmx5m -Xms5m -XX:+PrintGC -XX:+DoEscapeAnalysis -XX:-EliminateAllocations
*/
   public static void main(String[] args){
    long start = System.currentTimeMillis();
    for(int i = 0; i < 100000000; i++) {
        alloc();
    }
    long end = System.currentTimeMillis();
    System.out.println(end-start);
}

    private static void alloc() {
        User user = new User();
        user.setId(1);
        user.setName("zhuge");
    }
}
