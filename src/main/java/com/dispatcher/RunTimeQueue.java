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
        Process process = procesess.stream().findFirst().orElse(null);
        
        if (process == null) return false;

        // process bitecek
        if (process.processTime - 1 == 0) {
            procesess.remove(0);
        } 

        process.run();

        return true;
    }
    
}
