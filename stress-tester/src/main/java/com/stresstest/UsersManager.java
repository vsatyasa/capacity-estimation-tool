package com.stresstest;

import java.util.Map;
import java.util.concurrent.CyclicBarrier;

public class UsersManager implements Runnable {

    private CyclicBarrier barrier;
    private int seed = -1;
    private UserThreadSignalCommunicator comm;
    private Map<Integer, Map<Integer, ApiExecuteRecord>> log;
    private MongoConnector conn;

    public UsersManager(
            int seed,
            CyclicBarrier barrier,
            Map<Integer, Map<Integer, ApiExecuteRecord>> log,
            UserThreadSignalCommunicator comm,
            MongoConnector conn)
    {
        this.barrier = barrier;
        this.seed = seed;
        this.comm = comm;
        this.log = log;
        this.conn = conn;
    }

    @Override
    public void run() {

        int skip = UsersContext.SPAWN_RATE;
        int threadId = seed;

        // sync all the threads by using a barrier.
        try {
            barrier.await();
        } catch (Exception e) {
            // do nothing and skip the sync
        }

        while (comm.shouldUsersStop() == false) {
            if (threadId < UsersContext.NUM_OF_USERS) {
                Runnable usr = new User(threadId, log, comm);
                Thread t1 = new Thread(usr);
                t1.start();

                Runnable cleaner = new Cleaner(threadId, log, comm, conn);
                Thread t2 = new Thread(cleaner);
                t2.start();

                threadId += skip;
                try {
                    Thread.sleep(UsersContext.SPAWN_GAP);
                } catch (Exception e) {
                    // do nothing
                }
            }
        }

    }
}
