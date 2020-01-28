package edu.lwtech.csd299.samples.elevatorsimulator;

import java.util.*;

public class Building {

    private static final int CAB_CAPACITY = 20;
    
    private final int numFloors;
    private final int numCabs;
    
    private int numPassengersDelivered;
    private long totalTime;
    private long lastTime;
    private long maxTime;
    
    private List<Floor> floors;
    private List<ElevatorCab> cabs;
    
    private int slices;
    
    public Building(int numFloors, int numCabs) {
        if (numFloors < 2) throw new IllegalArgumentException("There must be at least 2 floors.");
        if (numCabs < 1) throw new IllegalArgumentException("There must be at least 1 elevator cab.");

        this.numFloors = numFloors;
        this.numCabs = numCabs;
        
        floors = new ArrayList<>();
        for (int i=0; i < numFloors; i++) {
            floors.add(new Floor(i, this));
        }
        
        cabs = new ArrayList<>();
        for (int i=0; i < numCabs; i++) {
            cabs.add(new ElevatorCab(this, i, CAB_CAPACITY));
        }
        
        slices = 0;
        numPassengersDelivered = 1;     // HACK: Prevents divide by zero
        totalTime = 0L;
        lastTime = 0L;
        maxTime = 0L;
    }
    
    public int getNumFloors() {
        return numFloors;
    }
    
    public int getNumCabs() {
        return numCabs;
    }
    
    public Floor getFloor(int floor) {
        return floors.get(floor);
    }

    public void run() {
        
        slices++;
        
        for (Floor floor : floors) {
            floor.run();
        }

        for (ElevatorCab cab : cabs) {
            cab.run();
        }
        
    }
    
    public int determineNextDestination(ElevatorCab cab) {
//        return rnd(numFloors);

        int curFloor = cab.getCurrentFloor();
        if (curFloor == 0) return numFloors-1;
        if (curFloor == numFloors-1) return 0;
        return cab.getDestination();

    }
    
    public void recordPassengerDelivery(Passenger p) {
        numPassengersDelivered++;
        lastTime = (System.currentTimeMillis() - p.getStartTime());
        totalTime += lastTime;
        if (lastTime > maxTime)
            maxTime = lastTime;
    }
    
    public boolean noDownCabsOnFloor(int floor) {
        int count = 0;
        for (ElevatorCab cab : cabs) {
            if ((cab.getCurrentFloor() == floor) && (!cab.isGoingUp()))
                    count++;
        }
        return (count < 2);
    }
    
    public boolean noUpCabsOnFloor(int floor) {
        int count = 0;
        for (ElevatorCab cab : cabs) {
            if ((cab.getCurrentFloor() == floor) && (cab.isGoingUp()))
                    count++;
        }
        return (count < 2);
    }
    
    @Override
    public String toString() {
        String s = "\n";
        
        s += "Slice: " + slices + "\n";

        for (int i=numFloors-1; i >= 0; i--) {
            s += floors.get(i).toString();
            s += "| ";
            
            for (ElevatorCab cab : cabs) {
                if (cab.getCurrentFloor() == i)
                    s += cab.toString() + "\t|";
                else
                    s += "\t\t\t\t|";
            }
            s += "\n";
        }
        
        double avgTime = totalTime / numPassengersDelivered;
        avgTime = avgTime / 1000.0;
        
        s += "\n";
        s += "Total passengers delivered: " + numPassengersDelivered + "\t\t\t";
        s += "Total time taken: " + totalTime + "ms\n";
        s += "Last delivery time: " + lastTime + "ms\t\t\t";
        s += "Max delivery time: " + maxTime + "ms\n";
        s += "Average delivery time: " + avgTime + " seconds\n";
        
        return s;
    }
        
}
