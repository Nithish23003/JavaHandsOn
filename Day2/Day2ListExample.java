package Day2;

import java.util.*;

abstract class Vehicle{
    protected String brand;
    protected int speed;

    public Vehicle(String brand, int speed){
        this.brand = brand;
        this.speed = speed;
    }

    public abstract void displayInfo();
}

class Car extends Vehicle{
    private int numberOfDoors;

    public Car(String brand, int speed, int numberOfDoors){
        super(brand, speed);
        this.numberOfDoors = numberOfDoors;
    }

    @Override

    public void displayInfo(){
        System.out.println("Brand: "+brand+ ", " +"Speed: "+ speed +", "+ "Number of Doors: "+ numberOfDoors );
    }
}

class Bike extends Vehicle{
    private boolean hasGear;

    public Bike(String brand, int speed, boolean hasGear){
        super(brand, speed);
        this.hasGear = hasGear;
    }

    @Override

    public void displayInfo(){
        System.out.println("Brand: "+brand+ ", " +"Speed: "+ speed +", "+ "Gear: "+ hasGear );
    }
}

public class Day2ListExample {
    public static void main (String[] args){
        List <Vehicle> vehicles = new ArrayList<>();
        
        vehicles.add(new Car("Tesla", 120, 4));
        vehicles.add(new Car("Ferari", 320, 2));
        vehicles.add(new Bike("Kwasiki", 300, true));
        vehicles.add(new Bike("Hero", 90, false));

        vehicles.forEach(Vehicle::displayInfo);
    }
    
}

