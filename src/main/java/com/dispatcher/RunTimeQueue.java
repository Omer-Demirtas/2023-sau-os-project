package main.java.com.dispatcher;

public class RunTimeQueue extends Queue {
    String resource;

    public RunTimeQueue(String resource) {
        this.resource = resource;
    }

    @Override
    Process addProcess(Process process) {
        return null;
    }

    @Override
    boolean process() {        
        Process process = procesess.get(0);
        
        if (process == null) return false;
        
        process.run();

        return true;
    }
    
}
