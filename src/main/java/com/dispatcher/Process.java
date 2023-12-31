package main.java.com.dispatcher;

import main.java.com.dispatcher.resource.Resource;

public class Process {
    public Long id;
    public Integer priority = 0;
    public Integer arriveTime = 0;
    public Integer memorySize = 0;
    public Integer processTime = 0;

    public Integer cdCount = 0;
    public Integer modemCount = 0;
    public Integer printerCount = 1;
    public Integer scannerCount = 0;

    public Integer memoryStartAddr;

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

    public Integer getMemoryStartAddr() {
        return memoryStartAddr;
    }

    public void setMemoryStartAddr(Integer memoryStartAddr) {
        this.memoryStartAddr = memoryStartAddr;
    }
}
