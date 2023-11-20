import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class elevatorSimulation extends Elevators2 {
    //Initializing variables. Note Elevators and Floors are both ArrayLists for the "Array" argument.
    int currentTick;
    static ArrayList<Elevator> elevatorList;
    static ArrayList<Floor> floorList;

    static HashMap<Integer, Integer> requests;

    static int maxFloor;
    static int minFloor;


    public elevatorSimulation() {
        //Constructing new arrays for floors and elevators

        this.elevatorList = new ArrayList<Elevator>();

        for (int i = 0; i < elevatorNumber; i++) {
            Elevator newElevator = new Elevator();
            this.elevatorList.add(newElevator);
        }

        this.floorList = new ArrayList<Floor>();

        for (int i = 0; i < floorNumber; i++) {
            Floor newFloor = new Floor();
            this.floorList.add(newFloor);
        }

        requests = new HashMap<Integer, Integer>();
        for (int i = 0; i <= floorNumber; i++) {
            requests.put(i, 0);
        }
        //Initializing to values that will always be lower and higher than the floors given so they can be reset.
        minFloor = floorNumber;
        maxFloor = -1;
    }
    public void runSimulation() {
        //Iterating through requested number of ticks from prop file
        for (currentTick = 0; currentTick < duration; currentTick++) {
            //Calling generate passenger for every tick
            generatePassengers();
            for (int i = 0; i < elevatorNumber; i++) {
                elevatorList.get(i).loadAndUnload();
            }
            elevatorList.get(0).printElevatorUp();
            System.out.println("Current floor " + elevatorList.get(0).ElevFloor);
        }


    }

    public void generatePassengers() {
        //Iterate through each floor
        for (int i = 0; i < floorNumber; i++) {
            //Check prob for whether a passenger will be generated or not
            double passengerCheck = Math.random();
            //If correct prob, add new passenger to current floor.
            if (passengerCheck <= passengers) {
                Floor toAddTo = floorList.get(i);
                //Passenger constructor
                Passenger pass = new Passenger(i, currentTick);
                toAddTo.addPass(pass);
            }
        }
        printWaitingPassengers();
    }

    public static void printWaitingPassengers() {
        //Implimented for testing
        //For each floor, iterate through and print out passengers currently on the floor waiting to be picked up.
        for (int i = 0; i < floorNumber; i++) {
            Floor currFloor = floorList.get(i);
            System.out.println();
            System.out.println("Passengers on floor " + i + " going up:\n");

            for (int j = 0; j < currFloor.waitingUp.size(); j++) {
                currFloor.waitingUp.get(j).printPassenger();
            }

            System.out.println("Passengers on floor " + i + " going down:\n");

            for (int j = 0; j < currFloor.waitingDown.size(); j++) {
                currFloor.waitingDown.get(j).printPassenger();
            }

        }
    }

    public static void requestFloor(int i) {
        requests.put(i, 1);
        if (i < minFloor) {
            minFloor = i;
        }
        if (i > maxFloor) {
            maxFloor = i;
        }
    }

    public static void arrivedAtFloorUp(int j) {
        //Switches floor that's been arrived at back to 0 when all passengers have been unloaded from floor
        requests.put(j, 0);
        //Resets the min
        for (int i = 0; i < floorNumber; i++) {
            if (requests.get(i) == 1) {
                minFloor = i;
                break;
            }
        }
        //Resets the max

    }
    public static void arrivedAtFloorDown(int j) {
        //Switches floor that's been arrived at back to 0 when all passengers have been unloaded from floor
        requests.put(j, 0);
        //Resets the max
        for (int k = floorNumber - 1; k >= 0; k--) {
            if (requests.get(k) == 1) {
                maxFloor = k;
                break;
            }
        }
    }





}
