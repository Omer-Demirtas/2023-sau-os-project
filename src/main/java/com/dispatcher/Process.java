package main.java.com.dispatcher;

public class Process {
    public Long id;
    public Integer priority;
    public Integer arriveTime;
    public Integer memorySize;
    public Integer processTime;

    public Integer cdCount;
    public Integer modemCount;
    public Integer printerCount;
    public Integer scannerCount;

    public void run(Integer ticktakTime) {
        this.processTime-=ticktakTime;
        System.out.println(String.format("Process(%s) running...",  id));
    }

    public void run(Integer ticktakTime, Integer priority) {
        this.priority = priority;
        run(ticktakTime);
    }

    public Process(Long id, Integer processTime, Integer priority, Integer arriveTime) {
        this.id = id;
        this.priority = priority;
        this.arriveTime = arriveTime;
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

    public Integer getPriority() {
        return priority;
    }

    public Integer getArriveTime() {
        return arriveTime;
    }

    public Integer getMemorySize() {
        return memorySize;
    }

    public Integer getProcessTime() {
        return processTime;
    }

    public Integer getCdCount() {
        return cdCount;
    }

    public Integer getModemCount() {
        return modemCount;
    }

    public Integer getPrinterCount() {
        return printerCount;
    }

    public Integer getScannerCount() {
        return scannerCount;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setArriveTime(Integer arriveTime) {
        this.arriveTime = arriveTime;
    }

    public void setMemorySize(Integer memorySize) {
        this.memorySize = memorySize;
    }

    public void setProcessTime(Integer processTime) {
        this.processTime = processTime;
    }

    public void setCdCount(Integer cdCount) {
        this.cdCount = cdCount;
    }

    public void setModemCount(Integer modemCount) {
        this.modemCount = modemCount;
    }

    public void setPrinterCount(Integer printerCount) {
        this.printerCount = printerCount;
    }

    public void setScannerCount(Integer scannerCount) {
        this.scannerCount = scannerCount;
    }

    
}
