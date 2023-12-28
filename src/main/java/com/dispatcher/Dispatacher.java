package main.java.com.dispatcher;

import java.util.List;

public class Dispatacher {
    
    static List<Queue> queues = List.of(
        new RunTimeQueue(null),
        new RunTimeQueue(null),
        new RunTimeQueue(null),
        new RunTimeQueue(null)
    );

    public static void main(String[] args) {
    
        int time=0;

        while (true) {
            //dagiciti(time++);
            time++;
            for (Queue queue : queues) {
                System.out.println(queue.getSize());
                if (queue.process()) break;  
                System.out.println(queue.getSize());                  
            }
        
            if (time == 10) {
                break;
            }
        }

    }
}
