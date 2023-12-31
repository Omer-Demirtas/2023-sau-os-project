package main.java.com.dispatcher.resource;

import main.java.com.dispatcher.process.ColorfulLogger;
import main.java.com.dispatcher.process.Process;

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
        if (process.getId() != null) return true;
        if (REMAINING_CD_COUNT >= process.getCdCount()
                && REMAINING_MODEM_COUNT >= process.getModemCount()
                && REMAINING_PRINTER_COUNT >= process.getPrinterCount()
                && REMAINING_SCANNER_COUNT >= process.getScannerCount()) {
            REMAINING_CD_COUNT -= process.getCdCount();
            REMAINING_MODEM_COUNT -= process.getModemCount();
            REMAINING_PRINTER_COUNT -= process.getPrinterCount();
            REMAINING_SCANNER_COUNT -= process.getScannerCount();

            // real time process ise memory allocate etmeye gerek yok, reserved memory e sığıyorsa çalışabilir
            if (process.isRealTime()) {
                return process.getMemorySize() <= RESERVED_MEMORY_SIZE;
            } else {
                return memory.allocateMemory(process);
            }
        } else {
            // İlgili hata mesajını oluştur ve ekrana bas
            String errorMessage = createErrorMessage(process);
            ColorfulLogger.logError(process, errorMessage);

            // Prosesi sil, işlemi gerçekleştirme
            return false;
        }
    }

    private static String createErrorMessage(Process process) {
        // Hata mesajını oluştur ve geri döndür
        if (process.getCdCount() > REMAINING_CD_COUNT) {
            return "Proses çok sayıda CD sürücü talep ediyor - proses silindi";
        } else if (process.getModemCount() > REMAINING_MODEM_COUNT) {
            return "Proses çok sayıda modem talep ediyor - proses silindi";
        } else if (process.getPrinterCount() > REMAINING_PRINTER_COUNT) {
            return "Proses çok sayıda yazıcı talep ediyor - proses silindi";
        } else if (process.getScannerCount() > REMAINING_SCANNER_COUNT) {
            return "Proses çok sayıda tarayıcı talep ediyor - proses silindi";
        } else if (process.getMemorySize() > TOTAL_MEMORY_SIZE - RESERVED_MEMORY_SIZE) {
            return "Proses çok fazla bellek talep ediyor - proses silindi";
        }

        return "Bilinmeyen bir hata oluştu - proses silindi";
    }

    public static boolean deallocate(Process process) {
        if (process.getId() == null) {
            return false;
        }
        
        REMAINING_CD_COUNT += process.getCdCount();
        REMAINING_MODEM_COUNT += process.getModemCount();
        REMAINING_PRINTER_COUNT += process.getPrinterCount();
        REMAINING_SCANNER_COUNT += process.getScannerCount();

        if (process.getPriority() > 0) {
            memory.deallocateMemory(process.getMemoryStartAddr());
        }

        return true;
    }
}
