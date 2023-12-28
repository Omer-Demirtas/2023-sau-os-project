package main.java.com.dispatcher;

import java.util.List;
import java.util.Map;

import main.java.com.dispatcher.queue.Queue;
import main.java.com.dispatcher.queue.RunTimeQueue;
import main.java.com.dispatcher.queue.UserJobQueue;

public class Dispatacher {
    Map<Integer, Process> proMapIsmailReis = Map.of(
        0, new Process(1L, 1, 0),
        0, new Process(2L, 1, 0),
        1, new Process(3L, 1, 0),
        1, new Process(4L, 1, 0),
        2, new Process(5L, 1, 0),
        2, new Process(6L, 1, 0),
        3, new Process(7L, 1, 0),
        3, new Process(8L, 1, 0)
    );

    List<Queue> queues = List.of(
        new RunTimeQueue(null),
        new UserJobQueue()
    );

    private void dispatch(Integer currentTime) {
        proMapIsmailReis.forEach((arriveTime, process) -> {
            if (process.getPriority() == 0) {
                queues.get(0).addProcess(process);
            } else {
                queues.get(1).addProcess(process);
            }
        });
    }

    public void start() {
    
        int time=0;

        while (true) {
            dispatch(time++);

            for (Queue queue : queues) {
                if (queue.process()) break;  
            }
        
            if (time == 10) {
                break;
            }
        }

    }
}
