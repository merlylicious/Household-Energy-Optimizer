package ASSIGNMENT;

public abstract class Appliance {
    //variable declaration.
    protected String name;
    protected double watt;
    protected double hours;

    public Appliance(String name, double watt, double hours) {
        this.name = name; this.watt = watt; this.hours = hours;
    }

    //get data
    public String getName() { return name; }

    public double getWatt() { return watt; }

    public double getHours() { return hours; }

    //calculation
    public double calculateEnergy() { return (watt * hours) / 1000.0; }

    // method overloading 1
    public double calculateCost() {
        return calculateEnergy() * 0.50;
    }

    // method overloading 2
    public double calculateCost(double tariffRate) { return calculateEnergy() * tariffRate; }

    public abstract double calculateImpactScore();

    public abstract String getType();
}