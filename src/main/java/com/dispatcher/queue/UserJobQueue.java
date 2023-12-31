package main.java.com.dispatcher.queue;

import java.util.ArrayList;
import java.util.List;

import main.java.com.dispatcher.process.ColorfulLogger;
import main.java.com.dispatcher.process.Process;
import main.java.com.dispatcher.process.Status;

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
    public boolean process(Integer tickTakTime) {
        // System.out.println("User Job Queue");

        //processPriorityList.forEach(t -> System.out.println("-> " + t.size()));

        for (int i = 0; i < processPriorityList.size(); i++) {
            List<Process> processList = processPriorityList.get(i);

            for (int j = 0; j < processList.size(); j++) {
                Process process = processList.get(j);
                if (process.getId() == 21) {
                    System.out.println("asdsada");
                }
                // İlgili process bitmiyorsa
                if (process.getProcessTime() - tickTakTime > 0) {
                    if (process.getPriority() != processPriorityList.size()) {
                        Integer processTime = process.getProcessTime();

                        if (!runProcess(process, process.getPriority() + 1, tickTakTime, i, j)) {
                            return false;
                        }

                        tickTakTime -= processTime;

                        processPriorityList.get(i).remove(j);
                        processPriorityList.get(i + 1).add(process);
                    } else { // Process en düşük öncelikteyse
                        Integer processTime = process.getProcessTime();

                        if (!runProcess(process, tickTakTime, i, j)) {
                            return false;
                        }

                        tickTakTime -= processTime;
                    }
                } else { // İlgili process bitiyor ise
                    process.status = Status.TERMINATED;

                    Integer processTime = process.getProcessTime();

                    if (!runProcess(process, tickTakTime, i, j)) {
                        return false;
                    }

                    tickTakTime -= processTime;
                }

                if (tickTakTime <= 0) {
                    return true;
                }

            }
        }

        return true;
    }

    boolean runProcess(Process process, int tickTakTime, int i, int j) {
        if (!process.run(tickTakTime, process.getPriority())) {
//            System.out.println(String.format("Process(%s) not enough resource", process.getId()));
            processPriorityList.get(i).remove(j);
            return false;
        }

        return true;
    }

    boolean runProcess(Process process, int priority, int tickTakTime, int i, int j) {
        if (!process.run(tickTakTime, priority)) {
            return false;
        }

        return true;
    }

    @Override
    public void checkTimeOut(int currentTime) {
        for (List<Process> processList : processPriorityList) {
            for (int j = 0; j < processList.size(); j++) {
                Process process = processList.get(j);

                if (currentTime - process.getArriveTime() > 20) {
                    ColorfulLogger.logError(process, String.format("Zaman aşımına uğradı!", process.getId()));
                    processList.remove(j);
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return processPriorityList.stream().allMatch(List::isEmpty);
    }

}
