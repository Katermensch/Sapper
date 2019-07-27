package main.java.controller;

import main.java.model.Difficult;
import main.java.model.Model;

import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.NANOSECONDS;


public class Controller{

    private Difficult difficult = Difficult.MEDIUM;
    private Model model;
    private long startTime = 0;
    private long stopTime = 0;
    private boolean isStart = false;
    private Thread timer;


    public Controller(Model model){this.model = model;}

    public void run() {
        model.run(difficult);
    }

    public void setDifficult(Difficult difficult) {
        this.difficult = difficult;
        model.run(difficult);
    }

    public void leftClick(int line, int column) {
        if (!isStart) {
            startTimer();
        }
        model.openCell(line, column);
    }

    public void rightClick(int line, int column) {
        model.markCell(line, column);
    }

    public void restartGame() {
        if (isStart) {
            stopTimer();
        }
        model.restart();
    }

    public void setCustomLevel(int line, int column, int mine) {
        model.runCustomGame(line, column, mine);
    }

    private void startTimer() {
        this.startTime = System.nanoTime();
        this.isStart = true;
        timer = new Thread(() -> {
            while (!timer.isInterrupted()) {
                try {
                    sleep(100);
                    model.setTime(getTime());
                } catch (InterruptedException e) {
                    System.out.println("stopTimer");
                    stopTimer();
                }
            }
        });
        timer.start();
    }

    public void stopTimer() {
        this.stopTime = System.nanoTime();
        this.isStart = false;
        if (!timer.isInterrupted()) {
            timer.interrupt();
        }
    }

    private long getTime() {
        long time;
        if (isStart) {
            time = (System.nanoTime() - startTime);
        } else {
            time = (stopTime - startTime);
        }
        return NANOSECONDS.toSeconds(time);
    }

}
