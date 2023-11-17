package com.kang.jdk;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * User:
 * Description:
 * Date: 2023-06-23
 * Time: 0:44
 */
public class MethodReference {

    public static MethodReference create(final Supplier<MethodReference> supplier){
        return supplier.get();
    }

    public static void collide(final MethodReference methodReference){
        System.out.println("collide:"+methodReference.toString());
    }

    public void follow(final MethodReference methodReference){
        System.out.println("following the "+methodReference.toString());
    }

    public void repair(){
        System.out.println("repair:"+this.toString());
    }

    public static void main(String[] args) {
        //通过Supplier获取new出来的对象
        MethodReference methodReference =  MethodReference.create(MethodReference::new);
        List<MethodReference> methodReferences = Arrays.asList(methodReference);
        // 通过::执行静态方法collide
        methodReferences.forEach(MethodReference::collide);
        // 通过::执行方法repair
        methodReferences.forEach(MethodReference::repair);

        MethodReference followReference = MethodReference.create(MethodReference::new);
        // 实际还是执行了methodReference的follow方法，而不是followReference的
        methodReferences.forEach(followReference::follow);

        System.out.println("==准备执行迭代==");
        methodReferences.forEach(System.out::println);

    }

}
