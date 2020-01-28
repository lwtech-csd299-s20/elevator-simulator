package edu.lwtech.csd299.samples.elevatorsimulator;

public class WaitingState implements ElevatorCabState {
    
    private final ElevatorCab cab;
    
    public WaitingState(ElevatorCab cab) {
        if (cab == null) throw new IllegalArgumentException("Elevator cab reference cannot be null.");
        this.cab = cab;
    }
    
    @Override
    public void run() {
        int currentFloor = cab.getCurrentFloor();
        int nextDestination = cab.getBuilding().determineNextDestination(cab);
        if (nextDestination != currentFloor) {
            cab.setDestination(nextDestination);
            if (nextDestination > currentFloor) {
                cab.setState(cab.movingUpState);
            } else {
                cab.setState(cab.movingDownState);
            }
        }
    }
    
    @Override
    public String toString() {
        return "WAITING    ";
    }

}
