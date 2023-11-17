package com.kang.jdk;

/**
 * User:
 * Description:
 * Date: 2023-06-22
 * Time: 18:51
 */
public class LambdaGrammar {

    public static void main(String[] args) {
        //类型声明
        MathOperation addOperation = (int i,int j) ->  i + j;
        int addResult = addOperation.operation(1, 2);
        System.out.println(addResult);
        // 无需类型声明
        MathOperation subOperation = (i,j) ->  i - j;
        int subResult = subOperation.operation(1, 2);
        System.out.println(subResult);
        // 大括号返回
        MathOperation multiplication = (i,j) ->  {return i * j;};
        int mulResult = multiplication.operation(1, 2);
        System.out.println(mulResult);
        //无大括号返回
        MathOperation division = (i,j) ->  i/j;
        int divResult = division.operation(2, 2);
        System.out.println(divResult);

        LambdaGrammar lambdaGrammar = new LambdaGrammar();
        int operate = lambdaGrammar.operate(3, 4, addOperation);
        System.out.println(operate);

        // 无类型、无大括号
        GreetingService greetingService = message -> System.out.println(message);
        greetingService.sayMessage("hello");
        // 声明类型
        GreetingService greetingService2 = (String message) -> System.out.println(message);
        greetingService2.sayMessage("hello2");
        // 声明类型+大括号
        GreetingService greetingService3 = (String message) ->{System.out.println(message);};
        greetingService3.sayMessage("hello3");

        //仅仅打印的极简模式
        GreetingService greetingService4 = System.out::println;
        greetingService4.sayMessage("hello4");
    }

    interface MathOperation{
        int operation(int i,int j);
    }

    interface GreetingService{
        void sayMessage(String message);
    }

    private  int operate(int i,int j,MathOperation mathOperation){
        return  mathOperation.operation(i,j);
    }

}
