package edu.lwtech.csd299.samples.elevatorsimulator;

import java.util.*;

public class Floor {
    
    private static final int NEW_PASSENGER_CHANCE = 2;
    
    private final int number;
    private final Building building;
    
    private Queue<Passenger> upQ;
    private Queue<Passenger> downQ;
    private boolean upButton;
    private boolean downButton;
    
    public Floor(int number, Building building) {
        if (number < 0) throw new IllegalArgumentException("Floor number cannot be negative.");
        if (building == null) throw new IllegalArgumentException("Building reference cannot be null.");

        this.number = number;
        this.building = building;
        
        upQ = new LinkedList<Passenger>();
        downQ = new LinkedList<Passenger>();
        
        upButton = false;
        downButton = false;
    }
    
    public Passenger getUpPassenger() {
        Passenger p = upQ.remove();
        return p;
    }
        
    public Passenger getDownPassenger() {
        Passenger p = downQ.remove();
        return p;
    }
    
    public int numUpPassengers() {
        return upQ.size();
    }
    
    public int numDownPassengers() {
        return downQ.size();
    }
    
    public List<Passenger> getUpPassengers(int max) {
        List<Passenger> boarders = new ArrayList<>();
        for (int i=0; i < max; i++) {
            if (!upQ.isEmpty()) {
                Passenger p = upQ.remove();
                boarders.add(p);
            }
        }
        return boarders;
    }
    
    public List<Passenger> getDownPassengers(int max) {
        List<Passenger> boarders = new ArrayList<>();
        for (int i=0; i < max; i++) {
            if (!downQ.isEmpty()) {
                Passenger p = downQ.remove();
                boarders.add(p);
            }
        }
        return boarders;
    }
    
    public boolean isUpButtonPressed() {
        return upButton;
    }
    
    public void pressUpButton() {
        upButton = true;
    }
    
    public void clearUpButton() {
        upButton = false;
    }
    
    public boolean isDownButtonPressed() {
        return downButton;
    }
    
    public void pressDownButton() {
        downButton = true;
    }
    
    public void clearDownButton() {
        downButton = false;
    }
    
    public void run() {
        if (rnd(100) < NEW_PASSENGER_CHANCE) {
            Passenger p = new Passenger(number, building.getNumFloors());
            if (p.getDestination() > number) {
                upQ.add(p);
                pressUpButton();
            } else {
                downQ.add(p);
                pressDownButton();
            }
        }        
    }
    
    @Override
    public String toString() {
        String s;
        
        s = number < 10 ? "0" : "";
        s += number + ":";
        s += "[" + (upButton ? "A" : " ") + "]";
        s += "[" + (downButton ? "V" : " ") + "]";
        s += "(" + upQ.size() + "," + downQ.size() + ")";
        return s;
    }

    private int rnd(int n) {
        return ((int)(Math.random()*n));
    }

}
