package main.java.com.dispatcher.process;

public class ColorfulLogger {

    private ColorfulLogger() {}

    
    public static void logError(Process process, String message) {
        System.out.println(
            process.color.getValue() + 
            "ERROR " +
            process.getId() + ": " +
            message + 
            " " +
            message + Color.RESET.getValue());
    }

    public static void log(Process process, String message) {
        System.out.println(
            process.color.getValue() + 
            process + ": " + message + 
            " " +
            message + Color.RESET.getValue()
        );
    }

    private static String createLogText(Process process, String message) {
        return process.color.getValue() + message + Color.RESET.getValue();
    }
}
