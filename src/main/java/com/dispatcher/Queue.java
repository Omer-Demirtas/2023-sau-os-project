package main.java.com.dispatcher;

import java.util.ArrayList;
import java.util.List;

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
