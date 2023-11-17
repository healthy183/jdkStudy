package com.kang.proxy.jvm.command.jstackDeadLock;

/**
 *  运行以下死锁程序，通过jps查看id，然后运行以下命令打印jsack
 *  jstack 12620  > JstackDeadLock.txt
 *
 *  也可以用visualvm查看当前pid，他可以自动检测死锁
 *
 */
public class JstackDeadLockTest {

    private static Object lockA = new Object();
    private static Object lockB = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (lockA){
                try {
                    System.out.println("threadA begin");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                synchronized (lockB){
                    System.out.println("threadA end");
                }
            }

        }).start();

        new Thread(()->{
            System.out.println("threadB begin");
            synchronized (lockB){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                synchronized (lockA){
                    System.out.println("threadB end");
                }
            }
        }).start();
        System.out.println("main thread stop");
    }
}
