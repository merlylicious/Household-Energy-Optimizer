package ASSIGNMENT;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {

    //swing n awt declaration
    private JTextField txtName;
    private JTextField txtWatt;
    private JTextField txtHours;
    private JTextField txtTariff;

    private JComboBox<String> cmbType;

    private JTable table;
    private DefaultTableModel model;

    private JLabel lblEnergy;
    private JLabel lblImpact;
    private JLabel lblCost;
    private JLabel lblScore;
    private JLabel lblStatus;
    private JLabel lblRecommendation;

    private EnergyManager manager;


    //main init.
    public MainFrame() {
        manager = new EnergyManager();

        setTitle("Household Energy Optimizer");
        setSize(900, 600); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); setLayout(new BorderLayout(10, 10));

        createInputPanel(); createTablePanel(); createResultPanel();

        setVisible(true);
    }

    //create input section

    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Appliance Input"));

        txtName = new JTextField();
        txtWatt = new JTextField();
        txtHours = new JTextField();
        txtTariff = new JTextField("0.50");

        cmbType = new JComboBox<>(new String[]{"Cooling", "Heating", "Lighting"});

        JButton btnAdd = new JButton("Add Appliance");
        JButton btnRemove = new JButton("Remove Selected");
        JButton btnCalculate = new JButton("Calculate");
        JButton btnReset = new JButton("Reset");

        inputPanel.add(new JLabel("Appliance Name:")); inputPanel.add(txtName);

        inputPanel.add(new JLabel("Appliance Type:")); inputPanel.add(cmbType);

        inputPanel.add(new JLabel("Power Rating (Watt):")); inputPanel.add(txtWatt);

        inputPanel.add(new JLabel("Usage Hours:")); inputPanel.add(txtHours);

        inputPanel.add(new JLabel("Tariff Rate (RM/kWh):")); inputPanel.add(txtTariff);

        inputPanel.add(btnAdd); inputPanel.add(btnRemove);

        JPanel topPanel = new JPanel(new BorderLayout()); topPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10)); buttonPanel.add(btnCalculate); buttonPanel.add(btnReset);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        btnAdd.addActionListener(e -> addAppliance()); btnRemove.addActionListener(e -> removeSelectedAppliance());
        btnCalculate.addActionListener(e -> calculateResult()); btnReset.addActionListener(e -> resetSystem());
    }

    //create list of data section

    private void createTablePanel() {
        model = new DefaultTableModel();
        model.addColumn("Name"); model.addColumn("Type");
        model.addColumn("Watt"); model.addColumn("Hours");
        model.addColumn("Energy (kWh)"); model.addColumn("Impact Score");

        table = new JTable(model); table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table); scrollPane.setBorder(BorderFactory.createTitledBorder("Appliance List"));
        add(scrollPane, BorderLayout.CENTER);
    }

    //create result section

    private void createResultPanel() {
        JPanel resultPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Optimization Result"));

        lblEnergy = new JLabel("Total Energy: 0.00 kWh"); lblImpact = new JLabel("Total Impact Score: 0.00");
        lblCost = new JLabel("Estimated Cost: RM 0.00"); lblScore = new JLabel("Efficiency Score: 0.00");
        lblStatus = new JLabel("Status: -"); lblRecommendation = new JLabel("Recommendation: -");

        resultPanel.add(lblEnergy); resultPanel.add(lblImpact);
        resultPanel.add(lblCost); resultPanel.add(lblScore);
        resultPanel.add(lblStatus); resultPanel.add(lblRecommendation);

        add(resultPanel, BorderLayout.SOUTH);
    }

    //function when user add appliance
    private void addAppliance() {
        try {
            String name = txtName.getText().trim(); String type = cmbType.getSelectedItem().toString();

            if (name.isEmpty()) { throw new IllegalArgumentException("Appliance name cannot be empty."); }

            double watt = Double.parseDouble(txtWatt.getText());
            double hours = Double.parseDouble(txtHours.getText());

            if (watt <= 0 || hours <= 0) { throw new IllegalArgumentException("Watt and hours must be greater than 0."); }

            Appliance appliance;

            if (type.equals("Cooling")) {
                appliance = new CoolingAppliance(name, watt, hours);
            } else if (type.equals("Heating")) {
                appliance = new HeatingAppliance(name, watt, hours);
            } else {
                appliance = new LightingAppliance(name, watt, hours);
            }

            manager.addAppliance(appliance);
            model.addRow(new Object[]{
                    appliance.getName(), appliance.getType(),
                    appliance.getWatt(), appliance.getHours(),
                    String.format("%.2f", appliance.calculateEnergy()),
                    String.format("%.2f", appliance.calculateImpactScore())
            });
            clearInput(); calculateResult();

        } catch (EnergyLimitException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Energy Limit Warning", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for watt and hours.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //function when user delete selected appliance
    private void removeSelectedAppliance() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) { JOptionPane.showMessageDialog(this, "Please select an appliance to remove."); return; }
        manager.removeAppliance(selectedRow); model.removeRow(selectedRow);
        calculateResult();
    }

    //function when user press calculate button.
    private void calculateResult() {
        try {
            double tariff = Double.parseDouble(txtTariff.getText());
            if (tariff <= 0) { throw new IllegalArgumentException("Tariff rate must be greater than 0."); }

            lblEnergy.setText(String.format("Total Energy: %.2f kWh", manager.getTotalEnergy()));
            lblImpact.setText(String.format("Total Impact Score: %.2f", manager.getTotalImpactScore()));
            lblCost.setText(String.format("Estimated Cost: RM %.2f", manager.getTotalCost(tariff)));
            lblScore.setText(String.format("Efficiency Score: %.2f", manager.calculateEfficiencyScore()));
            lblStatus.setText("Status: " + manager.getStatus());
            lblRecommendation.setText("Recommendation: " + manager.getRecommendation());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid tariff rate.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //function when user want to reset the system
    private void resetSystem() {
        manager.clearAll();
        model.setRowCount(0);

        lblEnergy.setText("Total Energy: 0.00 kWh"); lblImpact.setText("Total Impact Score: 0.00");
        lblCost.setText("Estimated Cost: RM 0.00"); lblScore.setText("Efficiency Score: 0.00");
        lblStatus.setText("Status: -"); lblRecommendation.setText("Recommendation: -");
        clearInput();
    }

    private void clearInput() {
        txtName.setText(""); txtWatt.setText(""); txtHours.setText("");
    }
}