package main.java.com.dispatcher;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import main.java.com.dispatcher.queue.Queue;
import main.java.com.dispatcher.queue.RunTimeQueue;
import main.java.com.dispatcher.queue.UserJobQueue;

public class Dispatacher {
    private final Integer TICK_TAK_TIME = 1;

    List<Process> processes = new ArrayList<>();

    List<Queue> queues = List.of(
        new RunTimeQueue(null),
        new UserJobQueue()
    );

    private void dispatch(Integer currentTime) {
        System.out.println(String.format("Dispatch(%d)", currentTime));

        processes.stream().filter(p -> p.getArriveTime() == currentTime).forEach(process -> {
            if (process.getPriority() == 0) {
                queues.get(0).addProcess(process);
            } else {
                queues.get(1).addProcess(process);
            }
        });

        processes.removeIf(process -> process.getArriveTime() == currentTime);
    }

    public void start() {
        int time=0;
        while (true) {
            System.out.println("time " + time);
            dispatch(time++);

            for (Queue queue : queues) {
                if (queue.process(TICK_TAK_TIME)) break;
            }

            if (time == 100) {
                break;
            }
        }
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
                    Long.parseLong(tokens[0].trim()),
                    nums[1],
                    nums[2],
                    nums[3]
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
