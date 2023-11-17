package com.kang.proxy.jvm.command.zzoth.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User:
 * Description:
 * Date: 2023-11-11
 * Time: 18:40
 */
public class HeliosScore {

    private String appId;

    private Date timeFrom;

    private String namespace;

    private String  scores;

    private Date timeTo;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public List<HeliosScore> split() {
        return new ArrayList<HeliosScore>();
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }
}

