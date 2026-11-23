package ASSIGNMENT;

public class HeatingAppliance extends Appliance {

    public HeatingAppliance(String name, double watt, double hours) {
        super(name, watt, hours);
    }

    @Override
    public double calculateImpactScore() { return calculateEnergy() * 2.0; }

    @Override
    public String getType() { return "Heating"; }
}