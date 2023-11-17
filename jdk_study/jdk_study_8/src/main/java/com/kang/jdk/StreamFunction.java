package com.kang.jdk;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User:
 * Description:
 * Date: 2023-06-23
 * Time: 10:14
 */
public class StreamFunction {

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("abc", "","", "jkl");
        // 筛选非空元素并组成新list
        List<String> filtered = strings.stream()
                .filter(string -> !string.isEmpty())
                .collect(Collectors.toList());
        filtered.forEach(System.out::println);
        // 仅筛选非空元素
        Stream<String> stringStream = strings.stream()
                .filter(string -> !string.isEmpty());
        stringStream.forEach(System.out::println);

        // 获取对应的平方数
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.stream()
                .map(i-> i*i)
                .distinct() // 去重
                .collect(Collectors.toList());
        squaresList.forEach(System.out::println);
        // limit方法用于获取指定数量的流
        Random random = new Random();
        random.ints().limit(3).forEach(System.out::println);
        // 获取空字符串的数量
        long count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
        //仅筛选非空元素并组成新String
        String collect = strings.stream()
                .filter(string -> !string.isEmpty())
                .collect(Collectors.joining(","));
        System.out.println(collect);

        IntSummaryStatistics intSummaryStatistics =
                numbers.stream()
                .mapToInt(x -> x).summaryStatistics();
        System.out.println("列表中最大的数 : " + intSummaryStatistics.getMax());
        System.out.println("列表中最小的数 : " + intSummaryStatistics.getMin());
        System.out.println("所有数之和 : " + intSummaryStatistics.getSum());
        System.out.println("平均数 : " + intSummaryStatistics.getAverage());


        List<String> strList = Arrays.asList("a", "ba", "bb");
        Map<Integer, String> strMap = strList.stream()
                .collect(Collectors.toMap(str -> strList.indexOf(str), str -> str ) );
        strMap.forEach((key, value) -> {
            System.out.println(key + "::" + value);
        });

        StreamFunction streamFunction = new StreamFunction();
        streamFunction.demo();
    }

    public  void demo() {
        Brid greenBrid = new Brid("green",1);
        Brid redBrid =  new Brid("red",6);
        Brid yellowBrid =  new Brid("yellow",5);
        List<Brid> list = Arrays.asList(greenBrid,redBrid,yellowBrid);
        //统计体重大于4的鸟的总体重
        int sum = list.stream()
                .filter(bird -> bird.weight > 4)
                .mapToInt(Brid::getWeight)
                .sum();
        System.out.println(sum);
        //体重大于4的鸟并排序
        Stream<Brid> sorted = list.stream()
                .filter(bird -> bird.weight > 4)
                //.sorted((x, y) -> x.getWeight() - y.getWeight());
                .sorted((x, y) -> y.getWeight() - x.getWeight());
        sorted.forEach(bird ->{
            System.out.println(bird.getWeight());
        });

        //是否有空字符串
        boolean anyMatch = list.stream().anyMatch(brid ->"red".equals(brid.getColor()));
        System.out.println(anyMatch);

        boolean allMatch = list.stream().allMatch(brid ->"red".equals(brid.getColor()));
        System.out.println(allMatch);

        boolean noneMatch = list.stream().noneMatch(brid ->"blue".equals(brid.getColor()));
        System.out.println(noneMatch);

        Optional<Integer> max = list.stream().map(w -> w.getWeight()).max(Integer::compare);
        System.out.println(max.isPresent()?max.get():null);

        Optional<Integer> min = list.stream().map(w -> w.getWeight()).min(Integer::compare);
        System.out.println(min.isPresent()?min.get():null);

        //利用reduce求和
        Integer reduce = list.stream().map(w -> w.getWeight()).reduce(0, Integer::sum);
        System.out.println(reduce);

        Optional<Integer> reduce1 = list.stream().map(w -> w.getWeight()).reduce(Integer::sum);
        System.out.println(reduce1.isPresent()?reduce:null);
    }

    class Brid{
        private String color;
        private int weight;

        public Brid(String color, int weight) {
            this.color = color;
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

}
