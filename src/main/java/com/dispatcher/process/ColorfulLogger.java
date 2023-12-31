package main.java.com.dispatcher.process;

public class ColorfulLogger {

    private ColorfulLogger() {}

    public static void log(Process process, String message) {
        System.out.println(process.color.getValue() + "Process " + process.getId() + " " + message + Color.RESET.getValue());
    }

    private static String createLogText(Process process, String message) {
        return process.color.getValue() + message + Color.RESET.getValue();
    }

}
