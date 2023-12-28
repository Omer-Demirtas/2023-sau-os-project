package main.java.com.dispatcher;

import java.util.List;

public abstract class Queue {
    List<Process> procesess = List.of(new Process(1));

    abstract Process addProcess(Process process); 
    
    abstract boolean process();

    Process getProcess() {
        return procesess.get(0);
    }
}
