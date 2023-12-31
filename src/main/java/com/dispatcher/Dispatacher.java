package main.java.com.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import main.java.com.dispatcher.queue.Queue;
import main.java.com.dispatcher.queue.RealTimeQueue;
import main.java.com.dispatcher.queue.UserJobQueue;

public class Dispatacher {
    private final Integer TICK_TAK_TIME = 1;

    private Map<Long, String> processStatus = new HashMap<>();

    List<Process> processes = new ArrayList<>();

    List<Queue> queues = List.of(
        new RealTimeQueue(null),
        new UserJobQueue()
    );

    private void dispatch(Integer currentTime) {
        System.out.println(String.format("Dispatch(%d)", currentTime));

        processes.stream().filter(p -> p.getArriveTime() == currentTime).forEach(process -> {
            if (process.isRealTime()) {
                queues.get(0).addProcess(process);
            } else {
                queues.get(1).addProcess(process);
            }
            process.setArriveTime(process.getArriveTime() - 1);  // Process'in varış zamanını azalt
        });

        processes.removeIf(process -> process.getArriveTime() == currentTime);

        for (Queue queue : queues) {
            if (queue.process(TICK_TAK_TIME)) break;
        }
    }

    public void start() {
        int time=0;
        while (true) {
            System.out.println("time " + time);
            dispatch(time++);

            for (Queue queue : queues) {
                if (queue.process(TICK_TAK_TIME)) break;
            }

            checkProcessStatus();
            if (time == 100) {
                break;
            }
        }
    }




    private void checkProcessStatus() {
        for (Process process : processes) {
            Long processId = process.getId();

            if (processStatus.containsKey(processId)) {
                System.out.println(processStatus.get(processId));
                processStatus.remove(processId);
            }

            if (process.getProcessTime() <= 0) {
                processStatus.put(processId, generateCompletedStatus(process));
            } else if (process.getArriveTime() <= 0) {
                processStatus.put(processId, "HATA - Proses zaman aşımı (20 sn de tamamlanamadı)");
            }
        }
    }

    private String generateCompletedStatus(Process process) {
        return String.format("%d %d %d %d %d %d %d %d COMPLETED",
                process.getId(), process.getPriority(), process.getArriveTime(),
                process.getMemorySize(), process.getPrinterCount(),
                process.getScannerCount(), process.getModemCount(), process.getCdCount());
    }
    public void readFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    break;
                }

                String[] tokens = line.split(",");
                int[] nums = new int[8];

                for (int i = 0; i < tokens.length; i++) {
                    nums[i] = Integer.parseInt(tokens[i].trim());
                }

                processes.add(new Process(
                    nums[0],
                    nums[1],
                    nums[2],
                    nums[3],
                    nums[4],
                    nums[5],
                    nums[6],
                    nums[7]
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
