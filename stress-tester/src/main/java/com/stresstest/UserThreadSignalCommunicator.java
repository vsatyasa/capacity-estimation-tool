package com.stresstest;

import java.util.concurrent.atomic.AtomicBoolean;

public class UserThreadSignalCommunicator {

    private AtomicBoolean shouldUserStop;

    public UserThreadSignalCommunicator() {
        shouldUserStop = new AtomicBoolean(false);
    }

    public void stopUsers() {
        shouldUserStop.set(true);
    }

    public boolean shouldUsersStop () {
        return shouldUserStop.get();
    }

}
