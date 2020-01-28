package edu.lwtech.csd299.samples.elevatorsimulator;

public class UnloadingState implements ElevatorCabState {

    private final ElevatorCab cab;
    
    public UnloadingState(ElevatorCab cab) {
        if (cab == null) throw new IllegalArgumentException("Elevator cab reference cannot be null.");
        this.cab = cab;
    }
    
    @Override
    public void run() {

        cab.unloadPassengerForCurrentFloor();
        
        if (cab.numPassengersForCurrentFloor() == 0) {
            cab.clearCabButton(cab.getCurrentFloor());
            cab.setState(cab.loadingState);
        }
        
    }

    @Override
    public String toString() {
        return "UNLOADING    ";
    }

}
