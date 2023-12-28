package main.java.com.dispatcher;

public class Process {
    Long id;
    Integer priority;
    Integer arriveTime;
    Integer memorySize;
    Integer processTime;

    Integer cdCount;
    Integer modemCount;
    Integer printerCount;
    Integer scannerCount;

    public void run() {
        System.out.println(String.format("Process %s running...",  id));
    }

    public Process(Long id, Integer processTime) {
        this.id = id;
        this.processTime = processTime;
    }

    public Process(Long id, Integer priority, Integer arriveTime, Integer memorySize, Integer cdCount, Integer modemCount, Integer printerCount, Integer scannerCount) {
        this.id = id;
        this.cdCount = cdCount;
        this.printerCount = printerCount;
        this.priority = priority;
        this.arriveTime = arriveTime;
        this.memorySize = memorySize;
        this.modemCount = modemCount;
        this.scannerCount = scannerCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
