/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package shape;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 *
 * @author Home
 */
public class Shape {
      private JFrame frame;
    private JComboBox<String> shapeSelector;
    private JPanel inputPanel;
    private JLabel resultLabel;
    private JButton calculateButton;
    
    public Shape(OWLOntology ontology) {
 
        frame = new JFrame("Math ITS - Area Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        // Main container with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Shape selection dropdown (NORTH)
        String[] shapes = {"Triangle", "Rectangle", "Circle"};
        shapeSelector = new JComboBox<>(shapes);
        shapeSelector.addActionListener(e -> updateInputFields());
        mainPanel.add(shapeSelector, BorderLayout.NORTH);

        // Input panel (CENTER)
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2, 5, 5)); // 2 columns
        updateInputFields();
        mainPanel.add(new JScrollPane(inputPanel), BorderLayout.CENTER);

        // Button panel (SOUTH)
        JPanel buttonPanel = new JPanel();
        calculateButton = new JButton("Calculate Area");
        calculateButton.addActionListener(e -> calculateArea());
        buttonPanel.add(calculateButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Result label (PAGE_END)
        resultLabel = new JLabel(" ", SwingConstants.CENTER);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPanel.add(resultLabel, BorderLayout.PAGE_END);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void updateInputFields() {
        inputPanel.removeAll();
        String selectedShape = (String) shapeSelector.getSelectedItem();

        switch (selectedShape) {
            case "Triangle":
                inputPanel.add(new JLabel("Base:"));
                inputPanel.add(new JTextField(10));
                inputPanel.add(new JLabel("Height:"));
                inputPanel.add(new JTextField(10));
                break;
                
            case "Rectangle":
                inputPanel.add(new JLabel("Length:"));
                inputPanel.add(new JTextField(10));
                inputPanel.add(new JLabel("Width:"));
                inputPanel.add(new JTextField(10));
                break;
                
            case "Circle":
                inputPanel.add(new JLabel("Radius:"));
                inputPanel.add(new JTextField(10));
                break;
        }

        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void calculateArea() {
        String selectedShape = (String) shapeSelector.getSelectedItem();
        double area = 0;
        
        try {
            Component[] components = inputPanel.getComponents();
            switch (selectedShape) {
                case "Triangle":
                    double base = Double.parseDouble(((JTextField)components[1]).getText());
                    double height = Double.parseDouble(((JTextField)components[3]).getText());
                    area = 0.5 * base * height;
                    break;
                    
                case "Rectangle":
                    double length = Double.parseDouble(((JTextField)components[1]).getText());
                    double width = Double.parseDouble(((JTextField)components[3]).getText());
                    area = length * width;
                    break;
                    
                case "Circle":
                    double radius = Double.parseDouble(((JTextField)components[1]).getText());
                    area = Math.PI * radius * radius;
                    break;
            }
            
            resultLabel.setText(String.format("Area = %.2f", area));
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input! Please enter numbers.");
        }
    }


        
        
    public static void main(String[] args) {

        try {
            // 1. Specify and validate OWL file path
            File owlFile = new File("C:\\area.owl");
            
            if (!owlFile.exists()) {
                JOptionPane.showMessageDialog(null, 
                    "OWL file not found at: " + owlFile.getAbsolutePath(),
                    "File Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Load ontology with proper URI conversion
            String ontologyPath = owlFile.toURI().toString();
            OntologyLoader loader = new OntologyLoader(ontologyPath);
            OWLOntology ontology = loader.getOntology();

            // 3. Launch GUI on Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                try {
                    new Shape(ontology); // Pass ontology if needed
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                        "GUI initialization failed: " + e.getMessage(),
                        "Critical Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });

        } catch (OWLOntologyCreationException e) {
            JOptionPane.showMessageDialog(null, 
                "Ontology loading failed:\n" + e.getMessage(), 
                "Ontology Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Unexpected error:\n" + e.getMessage(),
                "Runtime Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
