package com.stresstest;

import java.time.LocalDateTime;

public class ApiExecuteRecord {

    public String startTime;
    public String endTime;
    public Status status;

    public ApiExecuteRecord (String startTime, String endTime, Status status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

}
