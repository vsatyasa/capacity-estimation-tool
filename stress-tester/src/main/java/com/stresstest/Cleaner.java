package com.stresstest;

import java.util.Map;

public class Cleaner implements Runnable {

    int toBeCleanedIdx = 1;

    private int threadId = -1;
    private Map<Integer, Map<Integer, ApiExecuteRecord>> log;
    private UserThreadSignalCommunicator comm;
    MongoConnector conn;

    public Cleaner(
            int threadId,
            Map<Integer, Map<Integer, ApiExecuteRecord>> log,
            UserThreadSignalCommunicator comm,
            MongoConnector conn
    ) {
        this.threadId = threadId;
        this.log = log;
        this.comm =comm;
        this.conn = conn;
    }

    public void run() {
        while (!comm.shouldUsersStop() || log.get(threadId).size() > 0) {
            if (log.get(threadId).containsKey(toBeCleanedIdx)) {
                ApiExecuteRecord logEntry = log.get(threadId).get(toBeCleanedIdx);
                conn.insertRequestLog(threadId, logEntry.startTime, logEntry.endTime, logEntry.status);
                log.get(threadId).remove(toBeCleanedIdx);
                toBeCleanedIdx++;
            }
        }
    }

}
