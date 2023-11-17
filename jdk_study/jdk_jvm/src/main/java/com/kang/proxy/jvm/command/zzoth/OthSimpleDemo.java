package com.kang.proxy.jvm.command.zzoth;

import com.kang.proxy.jvm.command.zzoth.dto.HeliosGetScoreRequest;
import com.kang.proxy.jvm.command.zzoth.dto.HeliosGetScoreResponse;
import com.kang.proxy.jvm.command.zzoth.dto.HeliosScore;
import com.kang.proxy.jvm.command.zzoth.util.CollectionUtils;
import com.kang.proxy.jvm.command.zzoth.util.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * User: arthas官网的优化案例
 * Description:
 * Date: 2023-11-11
 * Time: 18:33
 */
public class OthSimpleDemo {

    /**原始版本
     * 有 3 个循环
     * 第一层循环的数量为 appId 的数量约为 140
     * 第二层是查出来的数据条数，一天的数据已经归并了所以这里应该是 1
     * 第三层是时间区间的分钟数，一天的话就是 1440 个。
     */
    private HeliosGetScoreResponse queryScores(HeliosGetScoreRequest request) {
        HeliosGetScoreResponse response = new HeliosGetScoreResponse();

       /* List<HeliosScore> heliosScores = heliosService.queryScoresTimeBetween
                (request.getStartTime(), request.getEndTime(), request.getFilterByAppId());*/
        List<HeliosScore> heliosScores = new ArrayList<>(); // 模拟查询
        if (CollectionUtils.isEmpty(heliosScores)) {
            return response;
        }

        Set<String> dateSet = new HashSet<>();
        Map<String, List<HeliosScore>> groupByAppIdHeliosScores =
                heliosScores.stream().collect(Collectors.groupingBy(HeliosScore::getAppId));
        // 第一层循环的数量为 appId 的数量约为 140
        for (List<HeliosScore> value : groupByAppIdHeliosScores.values()) {
            value.sort(Comparator.comparing(HeliosScore::getTimeFrom)); //排序
            HeliosGetScoreResponse.Score score = new HeliosGetScoreResponse.Score();
            score.setNamespace(value.get(0).getNamespace());
            score.setAppId(value.get(0).getAppId());
            // 第二层是查出来的数据条数，一天的数据已经归并了所以这里应该是 1
            for (HeliosScore heliosScore : value) {
                List<HeliosScore> splitHeliosScores = heliosScore.split();
                // 第三层是时间区间的分钟数，一天的话就是 1440 个
                for (HeliosScore splitHeliosScore : splitHeliosScores) {
                    if (splitHeliosScore.getTimeFrom().compareTo(request.getStartTime()) < 0) {
                        continue;
                    }
                    if (splitHeliosScore.getTimeFrom().compareTo(request.getEndTime()) > 0) {
                        break;
                    }
                    // Trace 中可以看到消耗最多的是封装的一个
                    //dateSet.add(DateUtils.yyyyMMddHHmm.formatDate(splitHeliosScore.getTimeFrom()));
                    dateSet.add(DateUtils.yyyyMMddHHmm.format(splitHeliosScore.getTimeFrom()));
                    if (splitHeliosScore.getScores() == null) {
                        splitHeliosScore.setScores("100");
                        //log.error("查询时发现数据缺失: {}", heliosScore);
                    }
                    score.add(Math.max(0, Integer.parseInt(splitHeliosScore.getScores())), null);
                }
            }
            response.getValues().add(score);
        }

        response.setDates(new ArrayList<>(dateSet).stream().sorted().collect(Collectors.toList()));
        return response;
    }

    /**
     * 遍历每个时间点的思路改变，把合并过的大对象拆分成一个个小对象直接遍历，改成先合并起来，通过时间点逻辑上遍历。这样会减少创建几十万个对象。
     * 将时间点集合 Set<String> dateSet 改为 Set<Date>，这样减少反复 formatDate() 的开销。
     * 优化字符串转数字的过程，减少 Integer.parseInt方法调用，改为用 Map<String, Integer> 提前创建出 0~100 的字符串数字字典。
     * （后来经过 JMH 测试，还是 Integer.parseInt 最快）
     *
     * 总结：
     * 这一步实际上执行时间优化了 50ms 左右。
     * 从 Trace 中看耗时时间最长的是 Date 的 compareTo，
     * 也就是代码中的 if (splitHeliosScore.getTimeFrom().compareTo(request.getStartTime()) < 0)
     * @param request
     * @return
     */
    private HeliosGetScoreResponse queryScoresV2(HeliosGetScoreRequest request) {
        HeliosGetScoreResponse response = new HeliosGetScoreResponse();

          /* List<HeliosScore> heliosScores = heliosService.queryScoresTimeBetween
                (request.getStartTime(), request.getEndTime(), request.getFilterByAppId());*/
        List<HeliosScore> heliosScoresRecord = new ArrayList<>(); // 模拟查询
        if (CollectionUtils.isEmpty(heliosScoresRecord)) {
            return response;
        }
        // 将时间点集合 Set<String> dateSet 改为 Set<Date>，这样减少反复 formatDate() 的开销。
        Set<Date> dateSet = new HashSet<>();
        // 遍历每个时间点的思路改变，把合并过的大对象拆分成一个个小对象直接遍历，
        // 改成先合并起来，通过时间点逻辑上遍历。这样会减少创建几十万个对象(也逻辑优化)
        //List<HeliosScore> heliosScores = HeliosDataMergeJob.mergeData(heliosScoresRecord);
        List<HeliosScore> heliosScores = new ArrayList<>();

        Map<String, List<HeliosScore>> groupByAppIdHeliosScores =
                heliosScores.stream().collect(Collectors.groupingBy(HeliosScore::getAppId));
        for (List<HeliosScore> scores : groupByAppIdHeliosScores.values()) {
            HeliosScore heliosScore = scores.get(0);
            HeliosGetScoreResponse.Score score = new HeliosGetScoreResponse.Score();
            score.setNamespace(heliosScore.getNamespace());
            score.setAppId(heliosScore.getAppId());
            score.setScores(new ArrayList<>());
            response.getValues().add(score);

           // List<Integer> scoreIntList = HeliosHelper.splitScores(heliosScore);
            List<Integer> scoreIntList = new ArrayList<>();
            // 以 requestTime 为准
            Calendar indexDate = DateUtils.roundDownMinute(request.getStartTime().getTime());
            int index = 0;
            // 如果 timeFrom < requestTime，则增加 timeFrom 到 requestTime
            while (indexDate.getTime().compareTo(heliosScore.getTimeFrom()) > 0) {
                heliosScore.getTimeFrom().setTime(heliosScore.getTimeFrom().getTime() + 60_000);
                index++;
            }

            while (indexDate.getTime().compareTo(request.getEndTime()) <= 0
                    && indexDate.getTime().compareTo(heliosScore.getTimeTo()) <= 0
                    && index < scoreIntList.size()) {
                Integer scoreInt = scoreIntList.get(index++);
                score.getScores().add(scoreInt);
                dateSet.add(indexDate.getTime());
                indexDate.add(Calendar.MINUTE, 1);
            }
        }
        response.setDates(new ArrayList<>(dateSet).stream().sorted()
                .map(DateUtils.yyyyMMddHHmm::format).collect(Collectors.toList()));
        return response;
    }

    /**
     *  放弃 compareTo，将 Date 对象的换成 long 型时间戳进行比较
     * 将 Date 对象反复 getTime、setTime，改为 long 型时间戳 += 60_000 实现，得到结果后只 setTime 一次。
     * 每次填充数据都往 Set<String> dateSet 放入数据，改为通过标识判断只放入一次。
     * 存放分数的 ArrayList 在第一次循环之后，可以确认大小，之后循环创建 ArrayList 时直接填入固定的大小，减少内存创建。
     *
     *优化总结：
     * 这一步将执行时间又优化了 80ms 左右。现在还剩是 160ms 了。
     * 从 Trace 中看耗时时间最长的是三个方法：
     * getScores。直接 get 了属性啥也没干，但是积少成多
     * list.size()、 list.get(index)也就是说虽然这几个函数里也没干什么东西，但是函数调用、指针寻址本身也是有开销的。
     */
    private HeliosGetScoreResponse queryScoresv2(HeliosGetScoreRequest request) {
        HeliosGetScoreResponse response = new HeliosGetScoreResponse();

      /* List<HeliosScore> heliosScores = heliosService.queryScoresTimeBetween
            (request.getStartTime(), request.getEndTime(), request.getFilterByAppId());*/
        List<HeliosScore> heliosScoresRecord = new ArrayList<>(); // 模拟查询

        if (CollectionUtils.isEmpty(heliosScoresRecord)) {
            return response;
        }

        Set<Date> dateSet = new HashSet<>();
        boolean isDateSetInitial = false;
        int scoreSize = 16;

       // List<HeliosScore> heliosScores = HeliosDataMergeJob.mergeData(heliosScoresRecord);
        List<HeliosScore> heliosScores = new ArrayList<>();

        Map<String, List<HeliosScore>> groupByAppIdHeliosScores =
                heliosScores.stream().collect(Collectors.groupingBy(HeliosScore::getAppId));

        for (List<HeliosScore> scores : groupByAppIdHeliosScores.values()) {
            HeliosScore heliosScore = scores.get(0);
            HeliosGetScoreResponse.Score score = new HeliosGetScoreResponse.Score();
            score.setNamespace(heliosScore.getNamespace());
            score.setAppId(heliosScore.getAppId());
            score.setScores(new ArrayList<>(scoreSize));
            response.getValues().add(score);
           // List<Integer> scoreIntList = HeliosHelper.splitScores(heliosScore);
            List<Integer> scoreIntList = new ArrayList<>();
            // 以 requestTime 为准
            long indexDateMills = request.getStartTime().getTime(); // 只get一次，然后复用
            int index = 0;
            // 如果 timeFrom < requestTime，则增加 timeFrom 到 requestTime
            long heliosScoreTimeFromMills = heliosScore.getTimeFrom().getTime();
            while (indexDateMills > heliosScoreTimeFromMills) { // 不用Date.compareTo()方法
                heliosScoreTimeFromMills += 60_000;
                index++;
            }
            heliosScore.getTimeFrom().setTime(heliosScoreTimeFromMills);

            long requestEndTimeMills = request.getEndTime().getTime();
            long heliosScoreTimeToMills = heliosScore.getTimeTo().getTime();
            // 循环条件为 (当前时间 <= 请求最大时间) && (当前时间 <= 数据最大时间) && (index < 数据条数)
            while (indexDateMills <= requestEndTimeMills
                    && indexDateMills <= heliosScoreTimeToMills &&
                    index < scoreIntList.size()) {
                score.getScores().add(scoreIntList.get(index++));
                if (!isDateSetInitial) {
                    dateSet.add(new Date(indexDateMills)); // 改为通过标识判断只放入一次，减少最后转换的性能耗损
                }
                indexDateMills += 60_000;
            }
            // 性能优化，减少重复放入的次数
            isDateSetInitial = true;
            // 性能优化，初始化足够的 size 减少扩容次数。 x1.1 为了万一数据数量不一致，留出一点 buffer。
            scoreSize = (int) (score.getScores().size() * 1.1);
        }

        response.setDates(new ArrayList<>(dateSet).stream().sorted()
                .map(DateUtils.yyyyMMddHHmm::format).collect(Collectors.toList()));
        return response;
    }


    /**
     * 减少 list 属性的调用,例如 list.size()、list.get(index)，一次次 list.add 方法改成 subList 一次性放入
     * 这里是标记好原本list的索引，然后一次性subList，一次性atALL
     * 也就是说循环中不做任何耗时操作，不做任何指针/引用
     * @param request
     * @return
     */
    private HeliosGetScoreResponse queryScoresV3(HeliosGetScoreRequest request) {
        HeliosGetScoreResponse response = new HeliosGetScoreResponse();
       // List<HeliosScore> heliosScoresRecord = heliosService.queryScoresTimeBetween(request.getStartTime(), request.getEndTime(), request.getFilterByAppId());
        List<HeliosScore> heliosScoresRecord = new ArrayList<>();

        if (CollectionUtils.isEmpty(heliosScoresRecord)) {
            return response;
        }

        Set<Date> dateSet = new HashSet<>();
        boolean isDateSetInitial = false;
        int scoreSize = 16;

        List<HeliosScore> heliosScores = new ArrayList<>();
       // List<HeliosScore> heliosScores = HeliosDataMergeJob.mergeData(heliosScoresRecord);

        Map<String, List<HeliosScore>> groupByAppIdHeliosScores
                = heliosScores.stream().collect(Collectors.groupingBy(HeliosScore::getAppId));

        for (List<HeliosScore> scores : groupByAppIdHeliosScores.values()) {
            HeliosScore heliosScore = scores.get(0);
            HeliosGetScoreResponse.Score score = new HeliosGetScoreResponse.Score();
            score.setNamespace(heliosScore.getNamespace());
            score.setAppId(heliosScore.getAppId());
            score.setScores(new ArrayList<>(scoreSize));
            response.getValues().add(score);

            // List<Integer> scoreIntList = HeliosHelper.splitScores(heliosScore);
            List<Integer> scoreIntList = new ArrayList<>();
            // List<Integer> scoreIntList = HeliosHelper.splitScores(heliosScore);

            // 以 requestTime 为准
            long indexDateMills = request.getStartTime().getTime();
            int index = 0;
            // 如果 timeFrom < requestTime，则增加 timeFrom 到 requestTime
            long heliosScoreTimeFromMills = heliosScore.getTimeFrom().getTime();
            while (indexDateMills > heliosScoreTimeFromMills) {
                heliosScoreTimeFromMills += 60_000;
                index++;
            }
            heliosScore.getTimeFrom().setTime(heliosScoreTimeFromMills);

            long requestEndTimeMills = request.getEndTime().getTime();
            long heliosScoreTimeToMills = heliosScore.getTimeTo().getTime();

            // 循环条件为 (当前时间 <= 请求最大时间) && (当前时间 <= 数据最大时间) && (index < 数据条数)
            int scoreIntListSize = scoreIntList.size(); // 不要把size()放在while中
            int indexStart = index;
            while (indexDateMills <= requestEndTimeMills
                    && indexDateMills <= heliosScoreTimeToMills && index++ < scoreIntListSize) {
                if (!isDateSetInitial) {
                    dateSet.add(new Date(indexDateMills));
                }
                indexDateMills += 60_000;
            }
            score.getScores().addAll(scoreIntList.subList(indexStart, index - 1));
            // 性能优化，减少重复放入的次数
            isDateSetInitial = true;
            // 性能优化，初始化足够的 size 减少扩容次数。 x1.1 为了万一数据数量不一致，留出一点 buffer。
            scoreSize = (int) (score.getScores().size() * 1.1);
        }

        response.setDates(new ArrayList<>(dateSet).stream().sorted()
                .map(DateUtils.yyyyMMddHHmm::format).collect(Collectors.toList()));
        return response;
    }
}
