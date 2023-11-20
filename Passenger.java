import java.util.Random;

public class Passenger extends Elevators2{
    int StartFloor;
    int EndFloor;
    int startTime;
    int endTime;

    public Passenger(int i, int currentTick) {
        setFloors(i);
        setStartTime(currentTick);
    }


    public void setStartTime(int tick) {
        this.startTime = tick;
    }
    public void setFloors(int i) {
        Random rand = new Random();
        this.StartFloor = i;
        this.EndFloor = rand.nextInt(floorNumber);
        while (this.StartFloor == this.EndFloor) {
            this.EndFloor = rand.nextInt(floorNumber);
        }
        elevatorSimulation.requestFloor(this.EndFloor);
    }

    public void setEndTime(int i) {
        this.endTime = i;
    }

    public void printPassenger() {
        System.out.println();
        System.out.println("Passenger:\nStart Floor: " + StartFloor + "\nEnd Floor: " + EndFloor);
        System.out.println("Start Time: " + startTime + "\nEndTime: " + endTime + "\n");
    }

    public int getEndFloor() {
        return EndFloor;
    }
}
