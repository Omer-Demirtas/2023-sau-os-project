package main.java.com.dispatcher.queue;

import java.util.ArrayList;
import java.util.List;

import main.java.com.dispatcher.Process;

public class UserJobQueue extends Queue {

    /**
     * 0 en öncelikli
     */
    private List<List<Process>> processPriorityList = new ArrayList<>();

    /*
     * Queue oluşturulurken 3 öncelik listesi oluşturulur.
     */
    public UserJobQueue() { 
        processPriorityList.add(new ArrayList<>());
        processPriorityList.add(new ArrayList<>());
        processPriorityList.add(new ArrayList<>());
    }

    @Override
    public void addProcess(Process process) {
        processPriorityList.get(process.getPriority() - 1).add(process);
    }

    @Override
    public boolean process() {
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
