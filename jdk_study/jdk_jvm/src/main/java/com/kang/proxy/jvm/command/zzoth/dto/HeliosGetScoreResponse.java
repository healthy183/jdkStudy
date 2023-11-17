package com.kang.proxy.jvm.command.zzoth.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * User:
 * Description:
 * Date: 2023-11-11
 * Time: 18:36
 */
public class HeliosGetScoreResponse {

    private List<Score> values = new ArrayList<>();

    private List<String> dates;


    public static class Score {

        private String appId;

        private String namespace;

        private List<Integer> scoreList;


        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public void add(Integer val,Object o){
            scoreList.add(val);
        }

        public List<Integer> getScores() {
            return scoreList;
        }

        public void setScores(List<Integer> scoreList) {
            this.scoreList = scoreList;
        }
    }

    public List<Score> getValues() {
        return values;
    }


    public void setValues(List<Score> values) {
        this.values = values;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
}


