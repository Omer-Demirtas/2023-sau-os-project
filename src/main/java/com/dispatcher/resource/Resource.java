package main.java.com.dispatcher.resource;

import main.java.com.dispatcher.Process;

public class Resource {
    public static Integer REMAINING_CD_COUNT = 2;
    public static Integer REMAINING_MODEM_COUNT = 1;
    public static Integer REMAINING_PRINTER_COUNT = 2;
    public static Integer REMAINING_SCANNER_COUNT = 1;
    public static Integer REMAINING_MEMORY_SIZE = 1024;
    public static Memory memory = new Memory(960);

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

            // real time process ise memory allocate etmeye gerek yok
            if (process.getPriority() == 0) {
                return true;
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
