
package main.java.com.dispatcher;

import main.java.com.dispatcher.process.Color;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Color.GREEN.getValue() + "All processes are terminated." + Color.RESET.getValue());
        Dispatacher dispatacher = new Dispatacher();
        dispatacher.readFile("giris.txt");
        dispatacher.start();

    }
}