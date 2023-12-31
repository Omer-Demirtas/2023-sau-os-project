package main.java.com.dispatcher.resource;

import main.java.com.dispatcher.Process;

public class Resource {
    public static Integer REMAINING_CD_COUNT = 2;
    public static Integer REMAINING_MODEM_COUNT = 1;
    public static Integer REMAINING_PRINTER_COUNT = 2;
    public static Integer REMAINING_SCANNER_COUNT = 1;
    public static Integer TOTAL_MEMORY_SIZE = 1024;
    public static Integer RESERVED_MEMORY_SIZE = 64;
    public static Memory memory = new Memory(TOTAL_MEMORY_SIZE - RESERVED_MEMORY_SIZE);

    /**
     * Allocate resources to process
     * 
     * @return true if resources were allocated, false otherwise
     */
    public static boolean allocate(Process process) {
        if (REMAINING_CD_COUNT >= process.getCdCount()
                && REMAINING_MODEM_COUNT >= process.getModemCount()
                && REMAINING_PRINTER_COUNT >= process.getPrinterCount()
                && REMAINING_SCANNER_COUNT >= process.getScannerCount()) {
            process.setId(System.currentTimeMillis());
            REMAINING_CD_COUNT -= process.getCdCount();
            REMAINING_MODEM_COUNT -= process.getModemCount();
            REMAINING_PRINTER_COUNT -= process.getPrinterCount();
            REMAINING_SCANNER_COUNT -= process.getScannerCount();

            // real time process ise memory allocate etmeye gerek yok, reserved memory e sığıyorsa çalışabilir
            if (process.getPriority() == 0) {
                return process.getMemorySize() <= RESERVED_MEMORY_SIZE;
            } else {
                return memory.allocateMemory(process);
            }
        }

        return false;
    }

    public static boolean deallocate(Process process) {
        if (process.getId() == null) {
            return false;
        }
        
        REMAINING_CD_COUNT += process.getCdCount();
        REMAINING_MODEM_COUNT += process.getModemCount();
        REMAINING_PRINTER_COUNT += process.getPrinterCount();
        REMAINING_SCANNER_COUNT += process.getScannerCount();
        memory.deallocateMemory(process.getMemorySize());

        return true;
    }
}
