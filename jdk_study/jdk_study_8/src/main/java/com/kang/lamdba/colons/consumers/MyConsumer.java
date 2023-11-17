package com.kang.lamdba.colons.consumers;

import java.util.function.Consumer;

/**
 * User:
 * Description:
 * Date: 2022-09-11
 * Time: 13:48
 */
public class MyConsumer<String>  implements Consumer<String> {
    @Override
    public void accept(String str) {
        System.out.println(str);
    }


}
