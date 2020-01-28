package edu.lwtech.csd299.samples.elevatorsimulator;

public class MovingDownState implements ElevatorCabState {

    private final ElevatorCab cab;
    
    public MovingDownState(ElevatorCab cab) {
        if (cab == null) throw new IllegalArgumentException("Elevator cab reference cannot be null.");
        this.cab = cab;
    }
    
    @Override
    public void run() {
        if (cab.getDestination() > cab.getCurrentFloor()) {
            cab.setState(cab.movingUpState);
            return;
        }
        
        cab.moveDown();
        
        int curFloor = cab.getCurrentFloor();
        
        if ((curFloor == cab.getDestination())
                || (cab.isCabButtonPressed(curFloor))) {
            cab.setState(cab.openingDoorsState);
            return;
        }
        
        if ((cab.isDownButtonPressed(curFloor)) && cab.getBuilding().noDownCabsOnFloor(curFloor) && rnd(100) < 50) {
            cab.setState(cab.openingDoorsState);
        }
        
    }
    
    @Override
    public String toString() {
        return "MOVING DOWN to " + cab.getDestination();
    }
    
    private int rnd(int n) {
        return ((int)(Math.random()*n));
    }

}
