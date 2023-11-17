package com.kang.lamdba.colons.consumers;

import org.junit.Test;

/**
 * User:
 * Description:
 * Date: 2022-11-20
 * Time: 9:53
 */
public class DoubleColonTest {

    @Test
    public void testConsumer(){
        DoubleColon doubleColon = new DoubleColon();
        //doubleColon.test((x,y) -> System.out.println("println something ..."));
        doubleColon.testConsumer(DoubleColon::toLower);
    }
}
