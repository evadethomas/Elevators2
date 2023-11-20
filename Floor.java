import java.util.ArrayList;

public class Floor {

    ArrayList<Passenger> waitingUp;
    ArrayList<Passenger> waitingDown;

    public Floor() {
        ArrayList<Passenger> queU = new ArrayList<Passenger>();
        ArrayList<Passenger> queD = new ArrayList<Passenger>();

        this.waitingUp = queU;
        this.waitingDown = queD;
    }
    public void addPass(Passenger pass) {
        if (pass.StartFloor < pass.EndFloor) {
            this.waitingUp.add(pass);
        } else {
            this.waitingDown.add(pass);
        }
    }

}
