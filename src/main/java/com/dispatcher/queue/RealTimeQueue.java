package main.java.com.dispatcher.queue;

import main.java.com.dispatcher.process.Process;
import main.java.com.dispatcher.process.Status;
import main.java.com.dispatcher.resource.Resource;

import java.util.List;

public class RealTimeQueue extends Queue {
    String resource;

    public RealTimeQueue(String resource) {
        this.resource = resource;
    }

    @Override
    public void addProcess(Process process) {
        procesess.add(process);
    }

    @Override
    public boolean process(Integer tickTakTime) {
        Process process = procesess.stream().findFirst().orElse(null);

        if (process == null)
            return false;

        // process bitecek
        if (process.processTime - tickTakTime == 0) {
            process.status = Status.TERMINATED;
            
            Resource.deallocate(process);

            procesess.remove(0);
        }

        // eger process calismasi basarisiz ise process silinir
        if (!process.run(tickTakTime)) {
//            System.out.println(
//                    String.format("Process(%s) not enough resource",
//                            process.getId()));
            procesess.remove(0);
            return false;
        }

        return true;
    }

    @Override
    public void checkTimeOut(int currentTime) {
        return;
    }

    @Override
    public boolean isEmpty() {
        return procesess.isEmpty();
    }

}
