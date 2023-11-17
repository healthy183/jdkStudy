package com.kang.proxy.jvm.command.zzoth.util;

import java.util.Collection;

/**
 * User:
 * Description:
 * Date: 2023-11-11
 * Time: 18:41
 */
public class CollectionUtils {

    public  static  boolean isEmpty(Collection collection){
        return collection == null || collection.isEmpty();
    }
}
