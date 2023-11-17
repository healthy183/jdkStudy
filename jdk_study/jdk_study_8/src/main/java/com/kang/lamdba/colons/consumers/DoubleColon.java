package com.kang.lamdba.colons.consumers;

import java.util.function.BiConsumer;

/**
 * User:
 * Description:
 * Date: 2022-09-11
 * Time: 14:04
 */
public class DoubleColon {

    public static String toUpperCaseRet(String str) {
        return str.toUpperCase();
    }

    public static void printStr(String str) {
        System.out.println("printStr : " + str);
    }

    public void toUpper(){
        System.out.println("toUpper : " + this);
    }

    public void toLower(String str){
        System.out.println("toLower : " + str);
    }

    public int toInt(String str){
        System.out.println("toInt : " + str);
        return 1;
    }

    public void testConsumer(BiConsumer<DoubleColon,String> consumer){
        consumer.accept(new DoubleColon(),"healthy consumer");
    }

    public  static String toLowerStr() {
        return "toLowerStr";
    }

    public String toLowerStr2() {
        return "toLowerStr2";
    }

}
