package com.kang.proxy.jvm.classload;

/**
 * User:
 * Description:
 * Date: 2023-09-24
 * Time: 10:54
 */
public class User {

    private Integer Id;
    private String name;

    public void sout(){
        System.out.println("com.kang.proxy.jvm.classload.User#sout(...)");
    }

    public static void main(String[] args) {
        new User().sout();
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
