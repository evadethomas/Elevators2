import java.io.*;
import java.util.Properties;
public class Elevators2 {
    /* Class Elevators2 contains the main method, gets the properties of a given file and reads the file built-in to the
    * project. It creates an instance of and calls run simulation. */
    static String structures;
    static int floorNumber;
    static double passengers;
    static int elevatorNumber;
    static int elevatorCapacity;
    static int duration;

    public static void main(String[] args) throws Exception {
        // get all the system properties
        Properties prop = new Properties();
        if (args.length < 1) {
            FileReader propFile = new FileReader("db.properties");
            prop.load(propFile);
        } else {
            try {
                FileReader propFile = new FileReader(args[0]);
                prop.load(propFile);
            } catch (Exception e) {
                System.out.println("Error reading file");
            }
        }

        structures = prop.getProperty("structures");
        floorNumber = Integer.parseInt(prop.getProperty("floors"));
        elevatorNumber = Integer.valueOf(prop.getProperty("elevators"));
        passengers = Double.valueOf(prop.getProperty("passengers"));
        elevatorCapacity = Integer.valueOf(prop.getProperty("elevatorCapacity"));
        duration = Integer.valueOf(prop.getProperty("duration"));

        /*
        Commented out prints for testing:
        System.out.println(structures+ " " + floorNumber+ " " + elevatorNumber+ " " + passengers+ " " + elevatorCapacity+ " " + duration);
        */

        elevatorSimulation simulation = new elevatorSimulation();
        simulation.runSimulation();

    }
}