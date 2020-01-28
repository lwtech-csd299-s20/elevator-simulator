package edu.lwtech.csd299.samples.elevatorsimulator;

import java.util.*;

public class ElevatorCab extends Thread {

    protected final ElevatorCabState movingUpState;
    protected final ElevatorCabState movingDownState;
    protected final ElevatorCabState openingDoorsState;
    protected final ElevatorCabState unloadingState;
    protected final ElevatorCabState loadingState;
    protected final ElevatorCabState closingDoorsState;
    protected final ElevatorCabState waitingState;
    
    private final Building building;
    private final int cabNumber;
    private final int capacity;
    
    private int floor;
    private int destination;
    private boolean goingUp;
    private boolean doorsOpen;
    private ElevatorCabState state;
    private List<Passenger> passengers;
    private boolean[] buttons;
    
    public ElevatorCab(Building building, int cabNumber, int capacity) {
        if (building == null) throw new IllegalArgumentException("Building cannot be null.");
        if (cabNumber < 0) throw new IllegalArgumentException("Negative cab numbers are not allowed.");
        if (capacity < 0) throw new IllegalArgumentException("Negative capacity is not allowed.");

        this.building = building;
        this.cabNumber = cabNumber;
        this.capacity = capacity;
        
        movingUpState = new MovingUpState(this);
        movingDownState = new MovingDownState(this);
        openingDoorsState = new OpeningDoorsState(this);
        unloadingState = new UnloadingState(this);
        loadingState = new LoadingState(this);
        closingDoorsState = new ClosingDoorsState(this);
        waitingState = new WaitingState(this);

        floor = rnd(building.getNumFloors()-1);
        goingUp = true;
        doorsOpen = false;
        
        passengers = new LinkedList<Passenger>();
        
        buttons = new boolean[building.getNumFloors()];
        for (int i=0; i < building.getNumFloors(); i++) {
            buttons[i] = false;
        }
        
        state = waitingState;
    }
    
    public Building getBuilding() {
        return building;
    }
    
    public int getCabNumber() {
        return cabNumber;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public int getCurrentFloor() {
        return floor;
    }
    
    public boolean isGoingUp() {
        return goingUp;
    }

    public boolean isCabButtonPressed(int floor) {
        return buttons[floor];
    }
    
    public void pressCabButton(int i) {
        buttons[i] = true;
    }
    
    public void clearCabButton(int i) {
        buttons[i] = false;
    }
    
    public boolean isUpButtonPressed(int floor) {
        return building.getFloor(floor).isUpButtonPressed();
    }
    
    public void clearUpButton(int floor) {
        building.getFloor(floor).clearUpButton();
    }
    
    public boolean isDownButtonPressed(int floor) {
        return building.getFloor(floor).isDownButtonPressed();
    }
    
    public void clearDownButton(int floor) {
        building.getFloor(floor).clearDownButton();
    }
    
    public int numPassengers() {
        return passengers.size();
    }
    
    public void moveUp() {
        if (floor < building.getNumFloors())
            floor++;
    }
    
    public void moveDown() {
        if (floor > 0)
            floor--;
    }
    
    public int getDestination() {
        return destination;
    }
    
    public void setDestination(int destination) {
        if ((destination < 0) || (destination > building.getNumFloors()))
            throw new IllegalArgumentException("Trying to set invalid destination.");
        
        this.destination = destination;
        this.goingUp = (this.destination > this.floor);
    }
    
    public boolean areDoorsOpen() {
        return doorsOpen;
    }
    
    public void openDoors() {
        this.doorsOpen = true;
    }
    
    public void closeDoors() {
        this.doorsOpen = false;
    }
    
    public void loadPassenger(Passenger p) {
        if (p == null) throw new IllegalArgumentException("Cannot add null passenger to elevator cab.");
        passengers.add(p);
        pressCabButton(p.getDestination());
    }
    
    public void unloadPassengerForCurrentFloor() {
        Passenger exiter = null;
        for (Passenger p : passengers) {
            if (p.getDestination() == floor)
                exiter = p;
        }
        
        if (exiter != null) {
            building.recordPassengerDelivery(exiter);
            passengers.remove(exiter);
        }
        
    }
    
    public void unloadPassengers() {
        List<Passenger> gettingOut = new ArrayList<>();
        
        for (Passenger p : passengers) {
            if (p.getDestination() == floor)
                gettingOut.add(p);
        }
        
        for (Passenger p : gettingOut) {
            building.recordPassengerDelivery(p);
            passengers.remove(p);
        }
        
        clearCabButton(floor);
    }
    
    public int numPassengersForCurrentFloor() {
        int count = 0;
        for (Passenger p : passengers) {
            if (p.getDestination() == floor)
                count++;
        }
        return count;
    }
    
    public void setState(ElevatorCabState state) {
        if (state == null) throw new IllegalArgumentException("Cannot set elevator cab state to null.");
        this.state = state;
    }
    
    public void run() {
        state.run();
    }
    
    @Override
    public String toString() {
        String s = "#" + cabNumber + ":";
        
        s += goingUp ? "A" : "V";
        s += doorsOpen ? "[  ]" : " ][ ";
        s += "(" + passengers.size() + ") ";
        s += state.toString();
        
        return s;
    }
    
    private int rnd(int n) {
        return ((int)(Math.random()*n));
    }

}
