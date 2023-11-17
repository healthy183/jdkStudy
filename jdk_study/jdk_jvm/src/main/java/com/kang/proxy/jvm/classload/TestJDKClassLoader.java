package com.kang.proxy.jvm.classload;

import sun.misc.Launcher;

import java.net.URL;

/**
 * User:
 * Description:
 * Date: 2023-09-17
 * Time: 21:40
 */
public class TestJDKClassLoader {

    public static void main(String[] args) {
        System.out.println(String.class.getClassLoader());
        System.out.println(com.sun.crypto.provider.DESKeyFactory.class
                .getClassLoader().getClass().getName());
        System.out.println(TestJDKClassLoader.class.getClassLoader().getClass().getName());
        System.out.println();
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassLoader = appClassLoader.getParent();
        ClassLoader bootstrapLoader = extClassLoader.getParent();
        System.out.println("appClassLoader:"+appClassLoader);
        System.out.println("extClassLoader:"+extClassLoader);
        System.out.println("bootstrapLoader:"+bootstrapLoader);
        System.out.println();
        System.out.println("bootstrapLoader 加载以下文件:");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urLs.length; i++) {
            System.out.println(urLs[i]);
        }
        System.out.println();
        System.out.println("extClassLoader加载以下文件:");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println();
        System.out.println("appClassLoader加载以下文件:");
        System.out.println(System.getProperty("java.class.path"));
    }

}
