package Model;

import java.util.concurrent.atomic.AtomicInteger;

public class Task implements Comparable{  //clientul
    private int arrivalTime;
    private int processingTime;
    private int ID;
    private int before;
    public Task() {
    }
    public Task(int arrivalTime, int processingTime, int ID) {
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.ID = ID;
        this.before=0;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getProcessingTime() {
        return processingTime;
    }
    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    @Override
    public int compareTo(Object o) {
        int at=((Task)o).getArrivalTime();
        return this.getArrivalTime()-at;
    }
    public int getBefore() {
        return before;
    }
    public void setBefore(int before) {
        this.before = before;
    }
}
