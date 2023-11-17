package com.kang.jdk;

import java.util.Optional;

/**
 * User:
 * Description:
 * Date: 2023-06-23
 * Time: 11:33
 */
public class OptionalFunction {

    public static void main(String[] args) {

        Integer integerNull = null;
        Integer integerTen = 10;

        Optional<Integer> optionalNull = Optional.ofNullable(integerNull);
        Optional<Integer> optionalValue = Optional.of(integerTen);
        //  判断值是否存在
        System.out.println(optionalNull.isPresent());
        System.out.println(optionalValue.isPresent());

        //optionalNull为空，所以返回2
        Integer resultNull = optionalNull.orElse(2);
        Integer resultValue = optionalValue.get();
        System.out.println(resultNull+resultValue);

        //null则通过执行Supplier返回结果
        Integer threee = optionalNull.orElseGet(()->{
           return 3;
        });
        System.out.println("输出Supplier返回结果:"+threee);

        // ifPresent如果optional值为空则不执行
        optionalNull.ifPresent((val)->{
            System.out.println("null val is "+val);
        });
        optionalValue.ifPresent((val)->{
            System.out.println("optionalValue is "+val);
        });

        //filter中返回true，get()才能存有值，否则直接返回Optional<null>
        //如果Optional本来就是null，则Supplier都不执行直接返回Optional<null>
        Optional<Integer> optionalFilter = optionalValue.filter(val -> {
            return "10".equals(val.toString());
        });
        System.out.println("filter:"+optionalFilter.get());

        //执行function，然后返回一个Optional，可以返回null
        Optional<Integer> optionalMap = optionalValue.map(val -> {
            //return 1;
           return null;
        });
        System.out.println(optionalMap.isPresent()?optionalMap.get():null);

        //执行function，然后返回一个Optional，不能返回null否则异常
        Optional<Integer> flatMapOptional = optionalValue.flatMap(val -> {
            System.out.println("arg is "+val);
            return Optional.ofNullable(22);
        });
        System.out.println(flatMapOptional.get());

        //获取person的CollegeName，免去级联空判断
        Person person = null;
        String name = Optional.ofNullable(person)
                .map(p -> p.getEduBack())
                .map(edu -> edu.getCollege())
                .map(college -> college.getName())
                .orElse(null);
        System.out.println(name);
    }

    class Person{

        private EduBack eduBack;

        public EduBack getEduBack() {
            return eduBack;
        }

        public void setEduBack(EduBack eduBack) {
            this.eduBack = eduBack;
        }
    }

    class EduBack{
        private College college;

        public College getCollege() {
            return college;
        }

        public void setCollege(College college) {
            this.college = college;
        }
    }

    class College{
        private  String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
