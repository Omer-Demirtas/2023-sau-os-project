package main.java.com.dispatcher;

public class Resource {
    public static Integer REMAINING_CD_COUNT = 2;
    public static Integer REMAINING_MODEM_COUNT = 1;
    public static Integer REMAINING_PRINTER_COUNT = 2;
    public static Integer REMAINING_SCANNER_COUNT = 1;
    public static Integer REMAINING_MEMORY_SIZE = 1024;

    public static final int PROCESS_TIMEOUT_SECONDS = 20; // Örnek bir değer, gerçek zaman aşımı süresini belirtir
    /**
     * Allocate resources to process
     * 
     * @return true if resources were allocated, false otherwise
     */
    public static boolean allocate(Process process) {
        if (REMAINING_CD_COUNT >= process.getCdCount()
                && REMAINING_MODEM_COUNT >= process.getModemCount()
                && REMAINING_PRINTER_COUNT >= process.getPrinterCount()
                && REMAINING_SCANNER_COUNT >= process.getScannerCount()
                && REMAINING_MEMORY_SIZE >= process.getMemorySize()) {
            REMAINING_CD_COUNT -= process.getCdCount();
            REMAINING_MODEM_COUNT -= process.getModemCount();
            REMAINING_PRINTER_COUNT -= process.getPrinterCount();
            REMAINING_SCANNER_COUNT -= process.getScannerCount();
            REMAINING_MEMORY_SIZE -= process.getMemorySize();

            process.setId(System.currentTimeMillis());
            return true;
        } else {
            // Hata durumu kontrolü
            if (process.getCdCount() > REMAINING_CD_COUNT
                    || process.getModemCount() > REMAINING_MODEM_COUNT
                    || process.getPrinterCount() > REMAINING_PRINTER_COUNT
                    || process.getScannerCount() > REMAINING_SCANNER_COUNT
                    || process.getMemorySize() > REMAINING_MEMORY_SIZE) {
                // İlgili hata mesajını oluştur ve ekrana bas
                String errorMessage = createErrorMessage(process);
                System.out.println(errorMessage);

                // Prosesi sil, işlemi gerçekleştirme
                return false;
            } else {
                // İlgili hata mesajını oluştur ve ekrana bas
                String errorMessage = createTimeoutErrorMessage(process);
                System.out.println(errorMessage);

                // Prosesi sil, işlemi gerçekleştirme
                return false;
            }
        }

    }

    private static String createErrorMessage(Process process) {
        // Hata mesajını oluştur ve geri döndür
        if (process.getCdCount() > REMAINING_CD_COUNT) {
            return "HATA - Proses çok sayıda CD sürücü talep ediyor - proses silindi";
        } else if (process.getModemCount() > REMAINING_MODEM_COUNT) {
            return "HATA - Proses çok sayıda modem talep ediyor - proses silindi";
        } else if (process.getPrinterCount() > REMAINING_PRINTER_COUNT) {
            return "HATA - Proses çok sayıda yazıcı talep ediyor - proses silindi";
        } else if (process.getScannerCount() > REMAINING_SCANNER_COUNT) {
            return "HATA - Proses çok sayıda tarayıcı talep ediyor - proses silindi";
        } else if (process.getMemorySize() > REMAINING_MEMORY_SIZE) {
            return "HATA - Proses çok fazla bellek talep ediyor - proses silindi";
        }

        return "HATA - Bilinmeyen bir hata oluştu - proses silindi";
    }

    private static String createTimeoutErrorMessage(Process process) {
        // Proses zaman aşımına uğradığı durumda oluşturulan hata mesajını döndür
        return String.format("HATA - Proses zaman aşımı (%d sn de tamamlanamadı)", PROCESS_TIMEOUT_SECONDS);
    }


    public static boolean deallocate(Process process) {
        if (process.getId() == null) {
            return false;
        }
        
        REMAINING_CD_COUNT += process.getCdCount();
        REMAINING_MODEM_COUNT += process.getModemCount();
        REMAINING_PRINTER_COUNT += process.getPrinterCount();
        REMAINING_SCANNER_COUNT += process.getScannerCount();
        REMAINING_MEMORY_SIZE += process.getMemorySize();

        return true;
    }
}
