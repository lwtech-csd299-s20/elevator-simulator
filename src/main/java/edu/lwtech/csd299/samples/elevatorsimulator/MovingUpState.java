package edu.lwtech.csd299.samples.elevatorsimulator;

public class MovingUpState implements ElevatorCabState {
    
    private final ElevatorCab cab;
    
    public MovingUpState(ElevatorCab cab) {
        if (cab == null) throw new IllegalArgumentException("Elevator cab reference cannot be null.");
        this.cab = cab;
    }
    
    @Override
    public void run() {
        if (cab.getDestination() < cab.getCurrentFloor()) {
            cab.setState(cab.movingDownState);
            return;
        }

        cab.moveUp();
        
        int curFloor = cab.getCurrentFloor();
        
        if ((curFloor == cab.getDestination())
                || (cab.isCabButtonPressed(curFloor))) {
            cab.setState(cab.openingDoorsState);
            return;
        }
        
        if ((cab.isUpButtonPressed(curFloor)) && cab.getBuilding().noUpCabsOnFloor(curFloor) && rnd(100) > 50) {
            cab.setState(cab.openingDoorsState);
        }
                
    }

    @Override
    public String toString() {
        return "MOVING UP to " + cab.getDestination();
    }
    
    private int rnd(int n) {
        return ((int)(Math.random()*n));
    }

}
