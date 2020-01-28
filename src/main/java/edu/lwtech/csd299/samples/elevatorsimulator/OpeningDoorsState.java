package edu.lwtech.csd299.samples.elevatorsimulator;

public class OpeningDoorsState implements ElevatorCabState {

    private final ElevatorCab cab;
    
    public OpeningDoorsState(ElevatorCab cab) {
        if (cab == null) throw new IllegalArgumentException("Elevator cab reference cannot be null.");
        this.cab = cab;
    }
    
    @Override
    public void run() {
        
        cab.openDoors();
        
        int curFloor = cab.getCurrentFloor();
        if ((curFloor == 0) || (curFloor == cab.getBuilding().getNumFloors()))
            cab.setDestination(cab.getBuilding().determineNextDestination(cab));
        
        if (cab.isCabButtonPressed(curFloor)) {
            cab.setState(cab.unloadingState);
        } else {
            cab.setState(cab.loadingState);
        }
    }

    @Override
    public String toString() {
        return "OPENING DOORS";
    }

}
