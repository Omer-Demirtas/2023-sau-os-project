package main.java.com.dispatcher.queue;

import java.util.ArrayList;
import java.util.List;

import main.java.com.dispatcher.Process;
import main.java.com.dispatcher.Resource;

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
        System.out.println(String.format("Process(%d) added", process.getId()));
        processPriorityList.get(process.getPriority() - 1).add(process);
    }

    @Override
    public boolean process(Integer tickTakTime) {
        // System.out.println("User Job Queue");

        processPriorityList.forEach(t -> System.out.println("-> " + t.size()));

        for (int i = 0; i < processPriorityList.size(); i++) {
            List<Process> processList = processPriorityList.get(i);

            for (int j = 0; j < processList.size(); j++) {
                Process process = processList.get(j);

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
                    Integer processTime = process.getProcessTime();

                    if (!runProcess(process, tickTakTime, i, j)) {
                        return false;
                    }

                    tickTakTime -= processTime;
                    processPriorityList.get(i).remove(j);
                }

                if (tickTakTime <= 0) {
                    return true;
                }

                if (process.getProcessTime() <= 0) {
                    removeProcess(process);  // Process tamamlandığında kuyruktan çıkar
                    System.out.println(generateCompletedStatus(process));
                } else if (process.getArriveTime() <= 0) {
                    removeProcess(process);  // Zaman aşımına uğrayan process kuyruktan çıkar
                    System.out.println("HATA - Proses zaman aşımı (20 sn de tamamlanamadı)");
                }
            }
        }

        return true;
    }

    private String generateCompletedStatus(Process process) {
        return String.format("%d %d %d %d %d %d %d %d COMPLETED",
                process.getId(), process.getPriority(), process.getArriveTime(),
                process.getMemorySize(), process.getPrinterCount(),
                process.getScannerCount(), process.getModemCount(), process.getCdCount());
    }
    boolean runProcess(Process process, int tickTakTime, int i, int j) {
        if (process.isRealTime()) {
            if (!process.run(tickTakTime, process.getPriority())) {
                if (process.getMemorySize() > Resource.REMAINING_MEMORY_SIZE) {
                    System.out.println(String.format("HATA - Gerçek-zamanlı proses (%dMB) tan daha fazla bellek talep ediyor - proses silindi", process.getMemorySize()));
                } else {
                    System.out.println("HATA - Gerçek-zamanlı proses çok sayıda kaynak talep ediyor - proses silindi");
                }
                processPriorityList.get(i).remove(j);
                return false;
            }
        } else {
            if (!process.run(tickTakTime, process.getPriority() + 1)) {
                if (process.getMemorySize() > Resource.REMAINING_MEMORY_SIZE) {
                    System.out.println(String.format("HATA - Proses (%dMB) tan daha fazla bellek talep ediyor – proses silindi", process.getMemorySize()));
                } else {
                    System.out.println("HATA - Proses çok sayıda kaynak talep ediyor - proses silindi");
                }
                processPriorityList.get(i).remove(j);
                return false;
            }
        }

        return true;
    }

    boolean runProcess(Process process, int priority, int tickTakTime, int i, int j) {
        if (!process.run(tickTakTime, priority)) {
            System.out.println(String.format("Process(%s) not enough resource", process.getId()));
            processPriorityList.get(i).remove(j);
            return false;
        }

        return true;
    }

}
