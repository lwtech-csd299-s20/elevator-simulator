package edu.lwtech.csd299.samples.elevatorsimulator;

public class ElevatorSimulatorApp {
    
    public static final int TIME_SLICE = 250;
    
    public static final int FLOORS = 25;
    public static final int CABS = 4;
    
    public static void main(String[] args) {
        
        println("Elevator Simulator 2016");
        println("=================================\n");
        println("Creating a building with " + FLOORS + " floors and " + CABS + " elevators...");
        
        Building building = new Building(FLOORS, CABS);
        
        println("Done!\n");
        
        println("Starting simulation...");
        
        while (true) {
            
            building.run();
            
            println(building.toString());
            
            try { Thread.sleep(TIME_SLICE); } catch (InterruptedException e) {}
            
        }
        
    }
    
    private static void println(String s) {
        System.out.println(s);
    }

}
