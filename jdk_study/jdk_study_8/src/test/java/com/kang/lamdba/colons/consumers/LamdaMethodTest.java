package com.kang.lamdba.colons.consumers;

import org.junit.Test;

/**
 * User:
 * Description:
 * Date: 2022-11-20
 * Time: 0:28
 */
public class LamdaMethodTest {


    @Test
    public  void function(){
        LamdbaMethod.function();
    }

    @Test
    public  void functionError(){
        LamdbaMethod.functionError();
    }


    @Test
    public  void  consumer(){
        LamdbaMethod.consumer();
    }

    @Test
    public  void  biConsumer(){
        LamdbaMethod.biConsumer();
    }

    @Test
    public  void  biFunction(){
        LamdbaMethod.biFunction();
    }


    @Test
    public  void  supplier(){
        LamdbaMethod.supplier();
    }

    @Test
    public  void  supplier2(){
        LamdbaMethod lamdbaMethod = new LamdbaMethod();
        lamdbaMethod.supplier2();
    }

    @Test
    public  void  predicate(){
        LamdbaMethod.predicate();
    }

    @Test
    public  void  binaryOperator(){
        LamdbaMethod.binaryOperator();
    }
}
