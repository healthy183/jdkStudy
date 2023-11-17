package com.kang.lamdba.colons.consumers;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User:
 * Description:
 * Date: 2022-09-11
 * Time: 13:53
 */
public class LamdbaMethod {

    /**
     * R r = Consumer.apply(T) 入参T，返回R
     */
    public static void function(){
        // 创建一个Function实现调用 new String("abc").toUpperCase();
        //有入参，有出参用 Function<T,R>
        Function<String,String> toUpperCaseRetFun =  DoubleColon::toUpperCaseRet;
        String abcResult = toUpperCaseRetFun.apply("abc");
        System.out.println(abcResult);
    }

    public static void functionError(){
        List<String> list = Arrays.asList("a","b","c");
        //stream内完成转大写
        Stream<String> stringStream =
                list.stream().map(s -> s.toUpperCase());
        System.out.println(stringStream);
        //通过Consumer打印list内元素
        list.forEach(new MyConsumer<>());
        //通过stream转大写
        Function<String,String> func =  String::toUpperCase;
        List<String> collectList = list.stream().map(func).collect(Collectors.toList());
        System.out.println(collectList);
        //作用等于上面forEach
        list.forEach(s -> System.out.println(s));
        //提示错误，因为accept()不是静态方法
        //list.forEach(MyConsumer::accept);
    }

    /**
     * Consumer.accept(T) 入参T，无返回值
     */
    public static void  consumer(){
        Consumer<String> printStrConsumer = DoubleColon::printStr;
        //直接调用的是DoubleColon#printStr，参数是"printStrConsumer"
        printStrConsumer.accept("printStrConsumer");
        //toUpper不是静态方法，所以需要new DoubleColon()
        Consumer<DoubleColon> toUpperConsumer = DoubleColon::toUpper;
        toUpperConsumer.accept(new DoubleColon());
    }

    /**
     * BiConsumer代表了一个接受两个输入参数的操作，并且不返回任何结果
     */
    public static void  biConsumer(){
        //toLower 非静态方法，需要在accept中 new DoubleColon()
        BiConsumer<DoubleColon,String> toLowerConsumer = DoubleColon::toLower;
        toLowerConsumer.accept(new DoubleColon(),"LOW");
    }

    /**
     * 代表了一个接受两个输入参数的方法，并且返回一个结果
     */
    public static void  biFunction(){
        BiFunction<DoubleColon,String,Integer> toIntFunction = DoubleColon::toInt;
        int intVal = toIntFunction.apply(new DoubleColon(),"toInt");
        System.out.println(intVal);
    }

    /**
     *  T get(); // 无入参，返回泛型T
     */
    public static void  supplier(){
        Supplier<String> toLowerConsumer = DoubleColon::toLowerStr;
        String returnStr = toLowerConsumer.get();
        System.out.println(returnStr);
        //System.out.println(toLowerConsumer);
    }

    public void supplier2(){
        // toLowerStr2非静态方法，所以需要 new DoubleColon()
        supRun(new DoubleColon()::toLowerStr2);
    }

    private void supRun(Supplier<String> supplier){
        System.out.println(supplier.get());
    }

    /**
     * //Predicate<T> -T作为输入，返回的boolean值作为输出
     */
    public static void predicate(){
        Predicate<String> pre = (x) -> x.startsWith("a");
        boolean abc = pre.test("abc");
        System.out.println(abc);
    }

    // BinaryOperator<T> -两个T作为输入，返回一个T作为输出，对于“reduce”操作很有用
    public static void binaryOperator(){
        BinaryOperator<String> bina = (x,y) -> x+y;
        String apply = bina.apply("a", "b");
        System.out.println(apply);
    }
}
