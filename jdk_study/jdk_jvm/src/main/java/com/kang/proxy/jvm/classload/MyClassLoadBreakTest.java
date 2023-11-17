package com.kang.proxy.jvm.classload;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * User:
 * Description: 打破双亲委托机制
 * Date: 2023-09-17
 * Time: 22:50
 */
public class MyClassLoadBreakTest {

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

       public Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {
           synchronized (getClassLoadingLock(name)) {
               Class<?> c = findLoadedClass(name);
               if (c == null) {
                   long t1 = System.nanoTime();
                   //指定这个路径的java需要打破双亲
                   if(name.contains("com.kang.proxy.jvm.classload")){
                       c = findClass(name);
                   }else{
                      c = this.getParent().loadClass(name);
                   }
                   sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                   sun.misc.PerfCounter.getFindClasses().increment();
               }

               if (resolve) {
                   resolveClass(c);
               }
               return c;
           }
       }
    }

    public static void main(String args[]) throws Exception {
        // 初始化自定义类加载器，会先初始化父类ClassLoader，
        // 其中会把自定义类加载器的父加载器设置为应用程序类加载器AppClassLoaders
        MyClassLoader classLoader = new MyClassLoader("D:/test");
        // D盘创建 com/kang/proxy/jvm/classload 几级目录，将User1.class放进去
        Class clazz = classLoader.loadClass("com.kang.proxy.jvm.classload.User1");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout", null);
        method.invoke(obj, null);
        // 打印com.kang.proxy.jvm.classload.User#sout(...)
        System.out.println(clazz.getClassLoader().getClass().getName());

        MyClassLoader classLoaderTest1 = new MyClassLoader("D:/test1");
        // D盘创建 com/kang/proxy/jvm/classload 几级目录，将User1.class放进去
        Class clazz1 = classLoaderTest1.loadClass("com.kang.proxy.jvm.classload.User1");
        Object obj1 = clazz1.newInstance();
        Method method1 = clazz1.getDeclaredMethod("sout", null);
        method1.invoke(obj1, null);
        // 打印com.kang.proxy.jvm.classload.User#soutv2(...)
        System.out.println(clazz1.getClassLoader().getClass().getName());
    }
}
