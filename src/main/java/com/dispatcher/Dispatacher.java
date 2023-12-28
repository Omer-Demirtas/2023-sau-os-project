package main.java.com.dispatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.com.dispatcher.queue.Queue;
import main.java.com.dispatcher.queue.RunTimeQueue;
import main.java.com.dispatcher.queue.UserJobQueue;

public class Dispatacher {
    private final Integer TICK_TAK_TIME = 1;

    List<Process> proMapIsmailReis = new ArrayList<>(
        Arrays.asList(
        new Process(1L, 1, 1, 0),
        new Process(2L, 3, 1, 0),
        new Process(3L, 1, 1, 0),
        new Process(4L, 1, 2, 0),
        new Process(5L, 1, 2, 0),
        new Process(6L, 1, 3, 0),
        new Process(7L, 1, 3, 0),
        new Process(8L, 1, 0, 0),
        new Process(9L, 1, 0, 0),
        new Process(10L, 1, 0, 0),
        new Process(11L, 1, 0, 0)
    ));

    List<Queue> queues = List.of(
        new RunTimeQueue(null),
        new UserJobQueue()
    );

    private void dispatch(Integer currentTime) {
        System.out.println(String.format("Dispatch(%d)", currentTime));

        proMapIsmailReis.stream().filter(p -> p.getArriveTime() == currentTime).forEach(process -> {
            if (process.getPriority() == 0) {
                queues.get(0).addProcess(process);
            } else {
                queues.get(1).addProcess(process);
            }
        });

        proMapIsmailReis.removeIf(process -> process.getArriveTime() == currentTime);
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
}
