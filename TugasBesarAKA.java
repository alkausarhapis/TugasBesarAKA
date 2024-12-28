package com.aka.tugasbesaraka;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TugasBesarAKA {

    private static DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private static DefaultTableModel tableModel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Staircase Algorithm Comparison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel labelInput = new JLabel("Enter number of steps (N):");
        JTextField textFieldInput = new JTextField(10);
        JButton buttonCompare = new JButton("Run");

        inputPanel.add(labelInput);
        inputPanel.add(textFieldInput);
        inputPanel.add(buttonCompare);

        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new GridLayout(1, 2, 10, 10));

        JPanel iterativePanel = new JPanel(new GridLayout(2, 1));
        JLabel iterativeLabel = new JLabel("Iterative Output:");
        JLabel iterativeResult = new JLabel("Steps: - | Time: - ms");
        iterativePanel.add(iterativeLabel);
        iterativePanel.add(iterativeResult);

        JPanel recursivePanel = new JPanel(new GridLayout(2, 1));
        JLabel recursiveLabel = new JLabel("Recursive Output:");
        JLabel recursiveResult = new JLabel("Steps: - | Time: - ms");
        recursivePanel.add(recursiveLabel);
        recursivePanel.add(recursiveResult);

        outputPanel.add(iterativePanel);
        outputPanel.add(recursivePanel);

        frame.add(outputPanel, BorderLayout.CENTER);

        JFreeChart chart = ChartFactory.createLineChart(
                "Running Time Comparison", "Input Size (N)", "Time (ms)", dataset
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 400));

        String[] columns = {"Input Size (N)", "Iterative Time (ms)", "Recursive Time (ms)"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable resultTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(resultTable);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.add(tableScrollPane);
        bottomPanel.add(chartPanel);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        buttonCompare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(textFieldInput.getText());
                    if (n < 0) {
                        JOptionPane.showMessageDialog(frame, "Number of steps must be non-negative.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    long startIterative = System.nanoTime();
                    int iterativeSteps = countWaysIterative(n);
                    long endIterative = System.nanoTime();
                    double iterativeTime = (endIterative - startIterative) / 1_000_000.0;

                    long startRecursive = System.nanoTime();
                    int recursiveSteps = countWaysRecursive(n);
                    long endRecursive = System.nanoTime();
                    double recursiveTime = (endRecursive - startRecursive) / 1_000_000.0;

                    iterativeResult.setText(String.format("Steps: %d | Time: %.3f ms", iterativeSteps, iterativeTime));
                    recursiveResult.setText(String.format("Steps: %d | Time: %.3f ms", recursiveSteps, recursiveTime));

                    dataset.addValue(iterativeTime, "Iterative", String.valueOf(n));
                    dataset.addValue(recursiveTime, "Recursive", String.valueOf(n));

                    tableModel.addRow(new Object[]{n, String.format("%.3f", iterativeTime), String.format("%.3f", recursiveTime)});

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }

    public static int countWaysRecursive(int n) {
        if (n == 0) return 1;
        if (n < 0) return 0;
        return countWaysRecursive(n - 1) + countWaysRecursive(n - 2);
    }

    public static int countWaysIterative(int n) {
        if (n == 0) return 1;
        if (n == 1) return 1;

        int prev1 = 1, prev2 = 1, current = 0;
        for (int i = 2; i <= n; i++) {
            current = prev1 + prev2;
            prev1 = prev2;
            prev2 = current;
        }
        return current;
    }
}