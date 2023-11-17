package com.kang.proxy.jvm.command.heapDumpPath;

import com.kang.proxy.jvm.command.dto.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User:
 * Description:
 * Date: 2023-10-12
 * Time: 22:32
 */
public class HeapDumpOOMTest {

    /**
     * 1,实时查看堆内存dump
     * jmap -dump:format=b,file=eureka.hprof 20368(pid)
     *
     * 2,生产环境打印jvm.dump
     * -Xms2M -Xmx2M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\home\jvm.dump
     *
     * 可以通过jvisualvm查看jvm.dump，打开文件后可以查看菜单列"类"关键哪些类占用内存最多。
     */
    public static List<Object> list = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        int i = 0;
        int j = 0;
        try {
            List<Object> list = new ArrayList<>();
            while (true) {
                list.add(new User(i++, UUID.randomUUID().toString()));
                new User(j--, UUID.randomUUID().toString());
            }
        } catch (Exception e) {
            System.out.println("iUserId:"+i+" jUserId:"+j);
            throw new Exception(e);
        }
    }
}


