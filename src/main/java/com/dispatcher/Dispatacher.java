package main.java.com.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import main.java.com.dispatcher.process.Process;
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
        processes.stream().filter(p -> p.getArriveTime() == currentTime).forEach(process -> {
            if (process.isRealTime()) {
                queues.get(0).addProcess(process);
            } else {
                queues.get(1).addProcess(process);
            }
        });

        processes.removeIf(process -> process.getArriveTime() == currentTime);
    }

    public void start() throws InterruptedException {
        final AtomicInteger time = new AtomicInteger(0);
        while(!isComplete()) {
            dispatch(time.getAndIncrement());

            // check for time out
            queues.forEach(e -> e.checkTimeOut(time.get()));

            for (Queue queue : queues) {
                if (queue.process(TICK_TAK_TIME)) break;
            }

            //Thread.sleep(1000);

            if (time.get() == 100) {
                break;
            }
        }
    }

    boolean isComplete() {
        return processes.isEmpty() && queues.stream().allMatch(Queue::isEmpty);
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
