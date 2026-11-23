package ASSIGNMENT;

public class LightingAppliance extends Appliance {

    public LightingAppliance(String name, double watt, double hours) {
        super(name, watt, hours);
    }

    @Override
    public double calculateImpactScore() {
        return calculateEnergy() * 1.0;
    }

    @Override
    public String getType() { return "Lighting"; }
}