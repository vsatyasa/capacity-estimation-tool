package com.stresstest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class User implements Runnable {

    private int sequenceNum = 0;
    private int threadId = -1;
    private Map<Integer, Map<Integer, ApiExecuteRecord>> log;
    private UserThreadSignalCommunicator comm;
    OkHttpClient client;

    public User(
            int threadId,
            Map<Integer, Map<Integer, ApiExecuteRecord>> log,
            UserThreadSignalCommunicator comm
    ) {

        this.threadId = threadId;
        this.log = log;
        this.comm =comm;
        client = new OkHttpClient.Builder()
                .callTimeout(APIContext.MAX_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .build();
    }

    public void run() {
        while (!comm.shouldUsersStop()) {
            sequenceNum++;
            String startTime = Instant.now().toString();

            Request req = new Request.Builder()
                    .url(APIContext.GET_URL)
                    .build();

            Status status = Status.SUCCESS;
            try {
                Response response = client.newCall(req).execute();
            } catch (InterruptedIOException e) {
                status = Status.TIME_OUT;
            } catch (Exception e) {
                status = Status.FAILURE;
            }

            String endTime = Instant.now().toString();
            ApiExecuteRecord executeRecord = new ApiExecuteRecord(startTime, endTime, status);
            log.get(threadId).put(sequenceNum, executeRecord);
//            System.out.println("[" + threadId + "] " + startTime + " " + endTime + " " + status);
        }
    }



}
