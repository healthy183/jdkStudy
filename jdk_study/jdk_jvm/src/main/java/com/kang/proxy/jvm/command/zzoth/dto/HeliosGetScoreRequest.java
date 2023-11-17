package com.kang.proxy.jvm.command.zzoth.dto;

import java.util.Date;

/**
 * User:
 * Description:
 * Date: 2023-11-11
 * Time: 18:37
 */
public class HeliosGetScoreRequest {

    private Date startTime;

    private Date endTime;


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
