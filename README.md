# Household Energy Optimizer

A Java Swing desktop application that estimates household electricity consumption, calculates energy costs, and provides basic energy-efficiency feedback. Developed as a group Object-Oriented Programming (OOP) project.

## Features

- Add cooling, heating, and lighting appliances with their wattage and usage hours.
- View per-appliance electricity usage and impact score in a table.
- Calculate total energy consumption (kWh), estimated electricity cost (RM), and an efficiency score.
- Receive an efficiency status and a recommendation based on the highest-impact appliance.
- Remove selected appliances or reset the list.
- Validate input and prevent adding an appliance if total calculated consumption would exceed **20 kWh**.

## Technologies

- Java
- Java Swing / AWT
- Object-Oriented Programming (OOP)

## OOP Concepts Demonstrated

- **Abstraction:** `Appliance` is an abstract base class.
- **Inheritance:** `CoolingAppliance`, `HeatingAppliance`, and `LightingAppliance` extend `Appliance`.
- **Polymorphism:** each appliance class overrides `calculateImpactScore()` and `getType()`.
- **Encapsulation:** `EnergyManager` maintains the appliance collection and exposes methods to work with it.
- **Method overloading:** `calculateCost()` and `calculateCost(double tariffRate)`.
- **Exception handling:** `EnergyLimitException` and input validation.

## Project Structure

```text
Household-Energy-Optimizer/
├── ASSIGNMENT/
│   ├── Appliance.java
│   ├── CoolingAppliance.java
│   ├── HeatingAppliance.java
│   ├── LightingAppliance.java
│   ├── EnergyManager.java
│   ├── EnergyLimitException.java
│   ├── MainFrame.java
│   └── MainSystem.java
├── .gitignore
└── README.md
```

## Getting Started

Requires a Java Development Kit (JDK) with support for Swing.

From the project root:

```bash
javac ASSIGNMENT/*.java
java ASSIGNMENT.MainSystem
```

On Windows Command Prompt, use `javac ASSIGNMENT\*.java` followed by `java ASSIGNMENT.MainSystem`.

The program opens a desktop GUI; no external Java libraries or databases are required.

## How It Works

1. Enter an appliance name, category, wattage, and hours of use.
2. Select **Add Appliance** to add it to the table.
3. Enter the electricity tariff (RM/kWh); the default is **0.50**.
4. Select **Calculate** to display the totals, efficiency score, status, and recommendation.
5. Use **Remove Selected** or **Reset** to manage the list.

### Calculations

```text
Energy (kWh) = Power (W) × Usage Hours / 1000
Estimated Cost (RM) = Total Energy (kWh) × Tariff (RM/kWh)

Impact weights:
  Lighting: 1.0 × Energy
  Cooling:  1.5 × Energy
  Heating:  2.0 × Energy

Efficiency Score = clamp(100 − (Total Impact Score × 2), 0, 100)
```

Efficiency statuses: **Excellent** (80–100), **Good** (60–below 80), **Average** (40–below 60), and **Poor** (below 40).

> **Note:** The impact weights and efficiency score are project-defined heuristics, not official electrical-energy efficiency standards. Calculated consumption applies to the entered hours, not automatically to a billing month. The 20 kWh limit is a program rule.

## Credits

Developed by **Amir Syahmi @ merlylicious**.

## License

No open-source license is included. All rights remain with the respective copyright holders unless a license is added with the contributors' agreement.
