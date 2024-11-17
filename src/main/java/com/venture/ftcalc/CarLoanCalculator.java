package com.venture.ftcalc;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class CarLoanCalculator {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Car Loan Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(null);

        frame.setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(CarLoanCalculator.class.getResource("/icon.png")));
        frame.setIconImage(icon.getImage());

        JLabel loanLabel = new JLabel("Amount Owed on Loan:");
        loanLabel.setBounds(20, 20, 180, 25);
        JTextField loanField = new JTextField();
        loanField.setBounds(200, 20, 150, 25);

        JLabel tradeInLabel = new JLabel("Trade-In Value:");
        tradeInLabel.setBounds(20, 60, 180, 25);
        JTextField tradeInField = new JTextField();
        tradeInField.setBounds(200, 60, 150, 25);

        JLabel newCarLabel = new JLabel("New Car Price:");
        newCarLabel.setBounds(20, 100, 180, 25);
        JTextField newCarField = new JTextField();
        newCarField.setBounds(200, 100, 150, 25);

        JLabel interestLabel = new JLabel("Interest Rate (Annual %):");
        interestLabel.setBounds(20, 140, 180, 25);
        JTextField interestField = new JTextField();
        interestField.setBounds(200, 140, 150, 25);

        JLabel loanTermLabel = new JLabel("Loan Term (Months):");
        loanTermLabel.setBounds(20, 180, 180, 25);
        JTextField loanTermField = new JTextField();
        loanTermField.setBounds(200, 180, 150, 25);

        JLabel resultLabel = new JLabel("Monthly Payment: $0.00");
        resultLabel.setBounds(20, 220, 350, 25);

        JLabel equityLabel = new JLabel("Equity Status: NONE");
        equityLabel.setBounds(20, 260, 180, 25);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(150, 300, 100, 30);

        JButton importButton = new JButton("Import");
        importButton.setBounds(150, 340, 100, 30);

        JButton generateButton = new JButton("Generate");
        generateButton.setBounds(150, 380, 100, 30);

        JLabel infoLabel = new JLabel();
        ImageIcon infoIcon = new ImageIcon(Objects.requireNonNull(CarLoanCalculator.class.getResource("/info.png")));
        Image scaledImage = infoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        infoLabel.setIcon(new ImageIcon(scaledImage));
        infoLabel.setBounds(260, 340, 35, 35);

        // Footer Label
        JLabel footerLabel = new JLabel("Developed with <3 by Venture");
        footerLabel.setBounds(100, 420, 200, 25);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 10));

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double loanAmount = Double.parseDouble(loanField.getText().replaceAll("[^\\d.]", ""));
                    double tradeInValue = Double.parseDouble(tradeInField.getText().replaceAll("[^\\d.]", ""));
                    double newCarPrice = Double.parseDouble(newCarField.getText().replaceAll("[^\\d.]", ""));
                    double annualInterestRate = Double.parseDouble(interestField.getText()) / 100;
                    int loanTerm = Integer.parseInt(loanTermField.getText());

                    double negativeEquity = loanAmount - tradeInValue;
                    double newLoanAmount = newCarPrice + Math.max(negativeEquity, 0);

                    double monthlyInterestRate = annualInterestRate / 12;

                    double monthlyPayment = newLoanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTerm))
                            / (Math.pow(1 + monthlyInterestRate, loanTerm) - 1);

                    resultLabel.setText(String.format("Monthly Payment: $%.2f", monthlyPayment));

                    double equity = loanAmount - tradeInValue;
                    if (equity > 0) {
                        equityLabel.setText(String.format("Equity Status: +$%.2f", equity));
                        equityLabel.setForeground(Color.GREEN);
                    } else if (equity < 0) {
                        equityLabel.setText(String.format("Equity Status: -$%.2f", -equity));
                        equityLabel.setForeground(Color.RED);
                    } else {
                        equityLabel.setText("Equity Status: $0.00");
                        equityLabel.setForeground(Color.BLACK);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numeric values.");
                }
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clipboardText = getClipboardText();
                if (clipboardText != null) {
                    parseAndPopulateFields(clipboardText, loanField, tradeInField, newCarField, interestField, loanTermField);
                }
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rand = new Random();

                // Generate realistic values
                double loanAmount = 15000 + (50000 - 15000) * rand.nextDouble(); // Loan amount between $15,000 and $50,000
                double tradeInValue = 5000 + (20000 - 5000) * rand.nextDouble(); // Trade-in value between $5,000 and $20,000
                double newCarPrice = 20000 + (50000 - 20000) * rand.nextDouble(); // New car price between $20,000 and $50,000
                double interestRate = 2.5 + (10 - 2.5) * rand.nextDouble(); // Interest rate between 2.5% and 10%
                int loanTerm = 36 + rand.nextInt(61); // Loan term between 36 and 96 months

                // Set generated values in the fields
                loanField.setText(String.format("%.2f", loanAmount));
                tradeInField.setText(String.format("%.2f", tradeInValue));
                newCarField.setText(String.format("%.2f", newCarPrice));
                interestField.setText(String.format("%.2f", interestRate));
                loanTermField.setText(String.valueOf(loanTerm));

                // Trigger calculation with generated values
                calculateButton.doClick();
            }
        });

        infoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String message = "Write your numbers in this format to quickly import it into this calculator:\n\n" +
                        "owed: 0.00\n" +
                        "tradein: 0.00\n" +
                        "newcar: 0.00\n" +
                        "interest: 0.00\n" +
                        "term: 0";
                JOptionPane.showMessageDialog(frame, message, "Import Format", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.add(loanLabel);
        frame.add(loanField);
        frame.add(tradeInLabel);
        frame.add(tradeInField);
        frame.add(newCarLabel);
        frame.add(newCarField);
        frame.add(interestLabel);
        frame.add(interestField);
        frame.add(loanTermLabel);
        frame.add(loanTermField);
        frame.add(calculateButton);
        frame.add(importButton);
        frame.add(generateButton);  // Add the Generate button to the frame
        frame.add(resultLabel);
        frame.add(equityLabel);
        frame.add(infoLabel);
        frame.add(footerLabel);  // Add the footer to the frame

        frame.setLocationRelativeTo(null);  // Center the window
        frame.setVisible(true);
    }

    private static String getClipboardText() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);
            if (contents != null) {
                DataFlavor[] flavors = contents.getTransferDataFlavors();
                for (DataFlavor flavor : flavors) {
                    if (flavor.equals(DataFlavor.stringFlavor)) {
                        return (String) contents.getTransferData(DataFlavor.stringFlavor);
                    }
                }
            }
        } catch (UnsupportedFlavorException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static void parseAndPopulateFields(String clipboardText, JTextField loanField, JTextField tradeInField, JTextField newCarField, JTextField interestField, JTextField loanTermField) {
        String[] lines = clipboardText.split("\n");
        if (lines.length == 5) {
            try {
                for (String line : lines) {
                    if (line.startsWith("owed:")) {
                        loanField.setText(line.replace("owed:", "").trim());
                    } else if (line.startsWith("tradein:")) {
                        tradeInField.setText(line.replace("tradein:", "").trim());
                    } else if (line.startsWith("newcar:")) {
                        newCarField.setText(line.replace("newcar:", "").trim());
                    } else if (line.startsWith("interest:")) {
                        interestField.setText(line.replace("interest:", "").trim());
                    } else if (line.startsWith("term:")) {
                        loanTermField.setText(line.replace("term:", "").trim());
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed to parse clipboard data.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Clipboard format is incorrect.");
        }
    }
}
