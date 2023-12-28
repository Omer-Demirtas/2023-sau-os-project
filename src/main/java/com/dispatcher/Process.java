package main.java.com.dispatcher;

public class Process {
    private Long id;
    private Integer time;

    public void run() { 
        System.out.println("Process running...");
    }

    public Process(Integer id) {
        this.time = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
