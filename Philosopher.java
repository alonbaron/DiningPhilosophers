import jdk.jshell.execution.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Philosopher {
    public static final int THINKING = 1;
    public static final int WAITING_FOR_FORK_1 = 2;
    public static final int WAITING_FOR_FORK_2 = 3;
    public static final int EATING = 4;

    private String name;
    private int status;
    private int eatingCount;
    private Fork fork1;
    private Fork fork2;
    private Fork firstFork;
    private Fork secondFork;
    private volatile boolean isRunning = true;

    public void stop() {
        isRunning = false;
    }

    public Philosopher (String name, Fork fork1, Fork fork2) {
        this.name = name;
        this.status = THINKING;
        this.eatingCount = 0;
        this.fork1 = fork1;
        this.fork2 = fork2;
        if (fork1.getNumber() < fork2.getNumber()) {
            this.firstFork  = fork1;
            this.secondFork = fork2;
        } else {
            this.firstFork  = fork2;
            this.secondFork = fork1;
        }
        this.start();
    }


    private void start() {
        new Thread(() -> {
            Random rnd = new Random();
            while (isRunning) {
                status = THINKING;
                Utils.sleep(rnd.nextInt(5000));
                status = WAITING_FOR_FORK_1;
                while (isRunning && firstFork.getHeldBy() != null) {
                    Utils.sleep(100);
                }
                if (!isRunning) break;
                firstFork.setHeldBy(this);
                Utils.sleep(rnd.nextInt(2000));
                status = WAITING_FOR_FORK_2;
                while (isRunning && secondFork.getHeldBy() != null) {
                    Utils.sleep(100);
                }
                if (!isRunning) {
                    firstFork.setHeldBy(null);
                    break;
                }
                secondFork.setHeldBy(this);
                status = EATING;
                Utils.sleep(rnd.nextInt(5000));
                firstFork.setHeldBy(null);
                secondFork.setHeldBy(null);
                eatingCount++;
            }
        }).start();

    }

    public String getName () {
        return this.name;
    }

    public int getEatingCount() {return this.eatingCount;}
    public boolean isStopped() {
        return !isRunning;
    }

    public String toString () {

        return new SimpleDateFormat("HH:mm:ss").format(new Date()) + " Philosopher " + this.name + " is currently " + getStatusText()
                + " (total times he ate: " + this.eatingCount + ")";

    }
    public String getStatusText(){
        String statusText = switch (this.status) {
            case THINKING ->  "thinking ";
            case WAITING_FOR_FORK_1 ->  "waiting for fork " + this.fork1.getNumber();
            case WAITING_FOR_FORK_2 -> "waiting for fork " + this.fork2.getNumber();
            case EATING -> "eating ";
            default -> "";
        };
        return statusText;
    }

    public int getStatus() {
        return status;
    }
}
