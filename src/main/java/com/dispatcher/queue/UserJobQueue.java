package main.java.com.dispatcher.queue;

import main.java.com.dispatcher.Process;

public class UserJobQueue extends Queue {

    @Override
    Process addProcess(Process process) {
        return null;
    }

    @Override
    boolean process() {
        return false;
    }
    
}
