package main.java.com.dispatcher.queue;

import main.java.com.dispatcher.Process;

public class RealTimeQueue extends Queue {
    String resource;

    public RealTimeQueue(String resource) {
        this.resource = resource;
    }

    @Override
    public void addProcess(Process process) {
        System.out.println(String.format("Process %s added", process.getId()));
        procesess.add(process);
    }

    @Override
    public boolean process(Integer tickTakTime) {
        // System.out.println("Real Time Queue " + procesess.size());
        Process process = procesess.stream().findFirst().orElse(null);

        if (process == null)
            return false;

        // process bitecek
        if (process.processTime - 1 == 0) {
            System.out.println(String.format("Process(%s) finish", process.getId()));
            procesess.remove(0);
        }

        // eger process calismasi basarisiz ise process silinir
        if (!process.run(tickTakTime)) {
            System.out.println(
                    String.format("Process(%s) not enough resource",
                            process.getId()));
            procesess.remove(0);
            return false;
        }

        return true;
    }

}
