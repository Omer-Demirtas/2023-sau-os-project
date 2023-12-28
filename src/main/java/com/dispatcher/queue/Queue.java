package main.java.com.dispatcher.queue;

import java.util.ArrayList;
import java.util.List;

import main.java.com.dispatcher.Process;

public abstract class Queue {
    List<Process> procesess = new ArrayList<>(List.of(new Process(1L, 1)));

    abstract Process addProcess(Process process); 
    
    abstract boolean process();

    Integer getSize() {
        return procesess.size();
    }

    Process getProcess() {
        return procesess.get(0);
    }
}
