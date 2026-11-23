package ASSIGNMENT;

import java.util.ArrayList;

public class EnergyManager {
    //array list declaration
    private ArrayList<Appliance> applianceList;

    //constant list declaration
    private final double ENERGY_LIMIT = 20.0;

    public EnergyManager() {
        applianceList = new ArrayList<>();
    }

    public void addAppliance(Appliance appliance) throws EnergyLimitException {
        applianceList.add(appliance);

        //warning if the energy above the energy limit
        if (getTotalEnergy() > ENERGY_LIMIT) { applianceList.remove(appliance); throw new EnergyLimitException("Energy limit exceeded. Please reduce the appliance usage hours or watt value."); }
    }

    public void removeAppliance(int index) {
        //index value checking before remove.
        if (index >= 0 && index < applianceList.size()) { applianceList.remove(index); }
    }

    public void clearAll() {
        applianceList.clear();
    }

    public ArrayList<Appliance> getApplianceList() {
        return applianceList;
    }

    //get data
    public double getTotalEnergy() {
        double total = 0;

        for (Appliance appliance : applianceList) { total += appliance.calculateEnergy(); }
        return total;
    }

    public double getTotalImpactScore() {
        double total = 0;

        for (Appliance appliance : applianceList) { total += appliance.calculateImpactScore(); }
        return total;
    }

    public double getTotalCost(double tariffRate) {
        double total = 0;

        for (Appliance appliance : applianceList) { total += appliance.calculateCost(tariffRate); }
        return total;
    }

    //calculate efficiency score.

    public double calculateEfficiencyScore() {
        double score = 100 - (getTotalImpactScore() * 2);

        if (score < 0) {
            score = 0;
        }
        if (score > 100) {
            score = 100;
        }
        return score;
    }


    //get data
    public String getStatus() {
        double score = calculateEfficiencyScore();

        if (score >= 80) {
            return "Excellent";
        } else if (score >= 60) {
            return "Good";
        } else if (score >= 40) {
            return "Average";
        } else {
            return "Poor";
        }
    }

    public String getRecommendation() {
        if (applianceList.isEmpty()) { return "No appliance data available."; }

        String highestName = applianceList.get(0).getName();
        double highestImpact = applianceList.get(0).calculateImpactScore();

        for (Appliance appliance : applianceList) {
            if (appliance.calculateImpactScore() > highestImpact) {
                highestImpact = appliance.calculateImpactScore(); highestName = appliance.getName();
            }
        }

        double score = calculateEfficiencyScore();

        if (score >= 80) {
            return "Energy usage is well optimized.";
        } else if (score >= 60) {
            return "Try reducing usage hours for " + highestName + ".";
        } else {
            return "Reduce usage of " + highestName + " because it has the highest energy impact.";
        }
    }
}