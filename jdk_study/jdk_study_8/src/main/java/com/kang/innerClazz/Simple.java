package com.kang.innerClazz;

/**
 * User:
 * Description:
 * Date: 2023-07-09
 * Time: 23:47
 */
public class Simple {

    private String name;

    public Simple() {
        this.name = "con";
        Inner inner = new Inner();
        System.out.println(inner.getClazzName());
    }

    public Simple(String name) {
        this.name = name;
        Inner inner = new Inner();
        String name1 = Simple.this.name; // 在构造方法中可以拿到值
        inner.setClazzName(name1);
        System.out.println(inner.getClazzName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private class Inner{
        private String clazzName;

        public Inner(){
            String clazzName = Simple.this.name;
            this.clazzName = clazzName;
        }

        public Inner(String clazzName) {
            this.clazzName = clazzName;
        }

        public String getClazzName() {
            return clazzName;
        }

        public void setClazzName(String clazzName) {
            this.clazzName = clazzName;
        }
    }

    public  void create(String arg){
        Simple simple = new Simple();
        simple.setName(arg);
        Inner inner = new Inner();
        inner.setClazzName(Simple.this.name);
        System.out.println(inner.getClazzName());
    }


    public static void main(String[] args) {
        Simple simple1 = new Simple(); // 在构造方法中可以拿到值
        Simple simple2 = new Simple("abc"); // 在构造方法中可以拿到值
        new Simple().create("efg"); //构造方法外拿不到
    }
}
