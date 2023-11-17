package com.kang.proxy.jvm.classload;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * User:
 * Description:
 * Date: 2023-09-17
 * Time: 22:50
 */
public class MyClassLoadTest {

   static class MyClassLoader extends ClassLoader {

        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            try (FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class")) {
                int len = fis.available();
                byte[] data = new byte[len];
                fis.read(data);
                return data;
            }
        }

        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                //defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }
    }

    public static void main(String args[]) throws Exception {
        // 初始化自定义类加载器，会先初始化父类ClassLoader，
        // 其中会把自定义类加载器的父加载器设置为应用程序类加载器AppClassLoaders
        MyClassLoader classLoader = new MyClassLoader("D:/test");
        // D盘创建 com/kang/proxy/jvm/classload 几级目录，将User.class放进去
        Class clazz = classLoader.loadClass("com.kang.proxy.jvm.classload.User1");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout", null);
        method.invoke(obj, null);
        // 打印的是Launcher$AppClassLoader,因为有双亲委派机制，所以优先是使用AppClassLoader
        // 当你删掉本项目的User1.java和User1.class再调用就可以看到MyClassLoader
        System.out.println(clazz.getClassLoader().getClass().getName());
    }
}
