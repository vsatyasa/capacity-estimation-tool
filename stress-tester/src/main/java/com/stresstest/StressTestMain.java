
package com.stresstest;

import java.util.*;
import okhttp3.OkHttpClient;
import com.mongodb.client.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class StressTestMain {

    public static void main(String[] args) throws Exception{

        Map<Integer, Map<Integer, ApiExecuteRecord>> log = new HashMap<>();
        for (int i = 0; i <= UsersContext.NUM_OF_USERS; i++) {
            log.put(i, new ConcurrentHashMap<>());
        }

        UserThreadSignalCommunicator comm = new UserThreadSignalCommunicator();
        MongoConnector conn = new MongoConnector();

        // batch of user created each second
        int BATCH_SIZE = UsersContext.SPAWN_RATE;

        // creating a barrier so that the users can start in a syncronized way
        CyclicBarrier barrier = new CyclicBarrier(UsersContext.SPAWN_RATE);

        for (int i = 0; i < BATCH_SIZE; i++) {
            Runnable manager = new UsersManager(i, barrier, log, comm, conn);
            Thread t = new Thread(manager);
            t.start();
        }

        int WAIT_TIME = (UsersContext.NUM_OF_USERS / UsersContext.SPAWN_RATE) * UsersContext.SPAWN_GAP; // MS
        int BUFFER_TIME = 4 * 1000; //MS

        // wait for wait + buffer time and stop all the users and record results.
        Thread.sleep(WAIT_TIME + BUFFER_TIME);
        comm.stopUsers();
    }
}
