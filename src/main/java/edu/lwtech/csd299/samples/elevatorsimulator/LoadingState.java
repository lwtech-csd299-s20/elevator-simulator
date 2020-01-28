package edu.lwtech.csd299.samples.elevatorsimulator;

public class LoadingState implements ElevatorCabState {

    private final ElevatorCab cab;
    
    public LoadingState(ElevatorCab cab) {
        this.cab = cab;
    }
    
    @Override
    public void run() {
        
        int curFloor = cab.getCurrentFloor();
        Floor floor = cab.getBuilding().getFloor(curFloor);
        
        if (curFloor == cab.getBuilding().getNumFloors()-1)
            cab.setDestination(0);
        if (curFloor == 0)
            cab.setDestination(cab.getBuilding().getNumFloors()-1);
        
        Passenger newPassenger = null;
        int remainingPassengers = 0;
        if (cab.isGoingUp()) {
            remainingPassengers = floor.numUpPassengers();
            if (remainingPassengers > 0) {
                newPassenger = floor.getUpPassenger();
                remainingPassengers--;
                if (remainingPassengers == 0)
                    cab.clearUpButton(curFloor);
            }
        } else {
            remainingPassengers = floor.numDownPassengers();
            if (remainingPassengers > 0) {
                newPassenger = floor.getDownPassenger();
                remainingPassengers--;
                if (remainingPassengers == 0)
                    cab.clearDownButton(curFloor);
            }
        }
        
        if (newPassenger != null)
            cab.loadPassenger(newPassenger);
        
        if ((remainingPassengers == 0) || (cab.numPassengers() == cab.getCapacity())) {
            cab.setDestination(cab.getBuilding().determineNextDestination(cab));
            cab.setState(cab.closingDoorsState);
        }
                
    }

    @Override
    public String toString() {
        return "LOADING     ";
    }

}
