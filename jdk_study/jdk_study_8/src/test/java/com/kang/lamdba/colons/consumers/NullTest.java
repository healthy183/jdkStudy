package com.kang.lamdba.colons.consumers;

import org.junit.Test;

/**
 * User:
 * Description:
 * Date: 2023-04-11
 * Time: 22:04
 */
public class NullTest {

    @Test
    public void transform(){
        Object  obj  = null;
        String objStr = (String) obj;
        System.out.println(objStr);
        System.out.println( objStr instanceof Object);

    }
}
