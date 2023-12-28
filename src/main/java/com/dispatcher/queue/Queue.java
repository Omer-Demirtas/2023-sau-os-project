package main.java.com.dispatcher.queue;

import java.util.ArrayList;
import java.util.List;

import main.java.com.dispatcher.Process;

public abstract class Queue {
    List<Process> procesess = new ArrayList<>();

    public abstract void addProcess(Process process); 
    
    public abstract boolean process(Integer tickTakTime);

    Integer getSize() {
        return procesess.size();
    }

    Process getProcess() {
        return procesess.get(0);
    }
}
