package edu.lwtech.csd299.samples.elevatorsimulator;

public class Passenger {
    
    private final String name;
    private final int destination;
    private final long startTime;
    
    public Passenger(int curFloor, int maxFloors) {
        name = "P" + (rnd(900) + 100);
        
        int dest;
        do {
            dest = rnd(maxFloors);
        } while (dest == curFloor);
        destination = dest;
        
        startTime = System.currentTimeMillis();
    }
    
    public int getDestination() {
        return destination;
    }
    
    public long getStartTime() {
        return startTime;
    }
    
    public String toString() {
        return name + "(" + destination + ")";
    }

    private int rnd(int n) {
        return ((int)(Math.random()*n));
    }
}
