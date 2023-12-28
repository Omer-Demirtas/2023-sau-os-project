package main.java.com.dispatcher.queue;

import main.java.com.dispatcher.Process;

public class RunTimeQueue extends Queue {
    String resource;

    public RunTimeQueue(String resource) {
        this.resource = resource;
    }

    @Override
    public void addProcess(Process process) {
        System.out.println(String.format("Process %s added", process.getId()));
        procesess.add(process);
    }

    @Override
    public boolean process(Integer tickTakTime) {        
        //System.out.println("Real Time Queue " + procesess.size());
        Process process = procesess.stream().findFirst().orElse(null);
        
        if (process == null) return false;

        // process bitecek
        if (process.processTime - 1 == 0) {       
            System.out.println(String.format("Process(%s) finish", process.getId()));
            procesess.remove(0);
        } 

        process.run(tickTakTime);

        return true;
    }
    
}
