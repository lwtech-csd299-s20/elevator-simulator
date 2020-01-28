package edu.lwtech.csd299.samples.elevatorsimulator;

public class ClosingDoorsState implements ElevatorCabState {

    private final ElevatorCab cab;
    
    public ClosingDoorsState(ElevatorCab cab) {
        if (cab == null) throw new IllegalArgumentException("Elevator cab reference cannot be null.");
        this.cab = cab;
    }
    
    @Override
    public void run() {
        
        cab.closeDoors();

        int curFloor = cab.getCurrentFloor();
        
        if (cab.isGoingUp()) {
            cab.clearUpButton(curFloor);
            cab.setState(cab.movingUpState);
        } else {
            cab.clearDownButton(curFloor);
            cab.setState(cab.movingDownState);
        }
        
    }

    @Override
    public String toString() {
        return "CLOSING DOORS";
    }

}
