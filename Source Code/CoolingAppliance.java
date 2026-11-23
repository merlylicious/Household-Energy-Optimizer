package ASSIGNMENT;

public class CoolingAppliance extends Appliance {

    public CoolingAppliance(String name, double watt, double hours) {
        super(name, watt, hours);
    }

    @Override
    public double calculateImpactScore() {
        return calculateEnergy() * 1.5;
    }

    @Override
    public String getType() { return "Cooling"; }
}