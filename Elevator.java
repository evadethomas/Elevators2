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

    public void travel() {
        int nextDestUp = ElevFloor + 5;
        if (nextDestUp > Elevators2.floorNumber - 1) {
            nextDestUp = Elevators2.floorNumber;
        }
        int nextDestDown = ElevFloor -5;
        boolean noUp = true;
        if (up == true) {
            for (int i = ElevFloor + 1; i < nextDestUp; i++) {
                if (elevatorSimulation.requests.get(i) == 1) {
                    nextDestUp = i;
                    noUp = false;
                    break;
                }
                if (i == Elevators2.floorNumber - 1) {
                    break;
                }
            }
            if (!stopUp.isEmpty()) {
                if (stopUp.peek().EndFloor < nextDestUp) {
                    nextDestUp = stopUp.peek().EndFloor;
                    noUp = false;
                }
            }
        }
        if (elevatorSimulation.maxFloor > Elevators2.floorNumber) {
            noUp = false;
        }

        if (noUp != true && up == true) {
            ElevFloor = nextDestUp;
        }
        /*
        if (up == false || noUp == true) {
            for (int i = ElevFloor - 1; i > ElevFloor - 5; i--) {
                if (i == 0) {
                    break;
                }
                if (elevatorSimulation.requests.get(i) == 1) {
                    nextDestDown = i;
                    break;
                }
            }
        }
        */
    }

    public void loadAndUnload() {
        if (up == true) {
            unloadUp();
            loadUp();
        } else {
            unloadDown();
            loadDown();
        }
        travel();
    }

    private void loadDown() {
        //See load up, but this if for down
        Floor floor = elevatorSimulation.floorList.get(ElevFloor);
        int passengersToTake = Elevators2.elevatorCapacity;
        if (floor.waitingDown.size() < Elevators2.elevatorCapacity) {
            passengersToTake = floor.waitingDown.size();
            elevatorSimulation.arrivedAtFloorDown(ElevFloor);
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
        //Let the passengers off that are going up with "poll" for priority queue
        if (!stopUp.isEmpty()) {
            while (!stopUp.isEmpty() && stopUp.peek().EndFloor == ElevFloor) {
                stopUp.poll();
            }
        }
    }

    public void unloadDown() {
        //Let the passengers off that are going down with "poll" for priority queue
        if (!stopDown.isEmpty()) {
            while (stopDown.peek().EndFloor == ElevFloor) {
                stopDown.poll();
            }
        }
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
