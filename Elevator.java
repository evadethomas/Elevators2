import java.util.Comparator;
import java.util.PriorityQueue;

public class Elevator {
    boolean up;
    int ElevFloor;

    PriorityQueue<Passenger> stopUp;
    PriorityQueue<Passenger> stopDown;

    public Elevator() {
        this.ElevFloor = 0;
        up = true;
        Comparator<Passenger> stopUpComparator = Comparator.comparingInt(Passenger::getEndFloor);
        Comparator<Passenger> stopDownComparator = Comparator.comparingInt(Passenger::getEndFloor).reversed();
        stopUp = new PriorityQueue<Passenger>(stopUpComparator);
        stopDown = new PriorityQueue<Passenger>(stopDownComparator);
    }

    public void loadAndUnload() {
        if (up == true) {
            unloadUp();
            loadUp();
        } else {
            unloadDown();
            loadDown();
        }
    }

    private void loadDown() {
        //See load up, but this if for down
        Floor floor = elevatorSimulation.floorList.get(ElevFloor);
        int passengersToTake = Elevators2.elevatorCapacity;
        if (floor.waitingDown.size() < Elevators2.elevatorCapacity) {
            passengersToTake = floor.waitingDown.size();
            elevatorSimulation.arrivedAtFloorUp(ElevFloor);
        }
        for (int i = 0; i < passengersToTake; i++) {
            Passenger pass = floor.waitingDown.remove(i);
            stopDown.add(pass);
        }
        elevatorSimulation.floorList.set(ElevFloor, floor);
    }

    public void loadUp() {
        //Get the current floor
        Floor floor = elevatorSimulation.floorList.get(ElevFloor);
        //Caluculate how many passengers to grab from the floor
        int passengersToTake = Elevators2.elevatorCapacity;
        if (floor.waitingUp.size() < Elevators2.elevatorCapacity) {
            passengersToTake = floor.waitingUp.size();
            //If it's all of them, unset this floor as a needed/max floor
            elevatorSimulation.arrivedAtFloorUp(ElevFloor);
        }
        //Remove passenger from up array list, then add them to the priority que (on the elevator)
        for (int i = 0; i < passengersToTake; i++) {
            Passenger pass = floor.waitingUp.remove(i);
            stopUp.add(pass);
        }
        //Reset the floor to the updated floor.
        elevatorSimulation.floorList.set(ElevFloor, floor);
    }

    public void unloadUp() {

    }

    public void unloadDown() {

    }

    public void printElevatorUp() {
        System.out.println("Passengers on elevator: \n");
        PriorityQueue<Passenger> temp = new PriorityQueue<>(stopUp);
        while (temp.isEmpty() == false && temp.peek() != null) {
            Passenger curr = temp.poll();
            curr.printPassenger();
        }
        System.out.println("End of passengers on elevator.");
    }

    public void printElevatorDown() {
        System.out.println("Passengers on elevator: \n");
        PriorityQueue<Passenger> temp = new PriorityQueue<>(stopDown);
        while (temp.isEmpty() == false && temp.peek() != null) {
            Passenger curr = temp.poll();
            curr.printPassenger();
        }
        System.out.println("End of passengers on elevator.");
    }
}
