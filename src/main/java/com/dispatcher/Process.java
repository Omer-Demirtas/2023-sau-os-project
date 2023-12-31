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


    public boolean isRealTime() {
        return this.priority == 0;
    }

    /**
     * Run process
     *
     * @param ticktakTime
     * @return true if process ran successfully, false otherwise
     */
    public boolean run(Integer ticktakTime) {
        this.processTime -= ticktakTime;
        System.out.println(String.format("Process(%s) running...", id));

        return Resource.allocate(this);
    }

    public boolean run(Integer ticktakTime, Integer priority) {
        this.priority = priority;
        return run(ticktakTime);
    }

    public Process(Integer arriveTime, Integer priority, Integer processTime, Integer memorySize, Integer printerCount, Integer scannerCount, Integer modemCount, Integer cdCount) {
        this.arriveTime = arriveTime;
        this.priority = priority;
        this.processTime = processTime;
        this.memorySize = memorySize;
        this.printerCount = printerCount;
        this.scannerCount = scannerCount;
        this.modemCount = modemCount;
        this.cdCount = cdCount;
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
