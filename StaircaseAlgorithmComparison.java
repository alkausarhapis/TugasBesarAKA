import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaircaseAlgorithmComparison {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Staircase Algorithm Comparison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel labelInput = new JLabel("Enter number of steps (N):");
        JTextField textFieldInput = new JTextField(10);
        JButton buttonCompare = new JButton("Compare");

        inputPanel.add(labelInput);
        inputPanel.add(textFieldInput);
        inputPanel.add(buttonCompare);

        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel iterativePanel = new JPanel();
        iterativePanel.setBorder(BorderFactory.createTitledBorder("Iterative Algorithm"));
        iterativePanel.setLayout(new BoxLayout(iterativePanel, BoxLayout.Y_AXIS));

        JLabel labelIterativeResult = new JLabel("Result: -");
        JLabel labelIterativeTime = new JLabel("Time: - ms");

        iterativePanel.add(labelIterativeResult);
        iterativePanel.add(Box.createVerticalStrut(5)); 
        iterativePanel.add(labelIterativeTime);

        frame.add(iterativePanel, BorderLayout.CENTER);

        JPanel recursivePanel = new JPanel();
        recursivePanel.setBorder(BorderFactory.createTitledBorder("Recursive Algorithm"));
        recursivePanel.setLayout(new BoxLayout(recursivePanel, BoxLayout.Y_AXIS));

        JLabel labelRecursiveResult = new JLabel("Result: -");
        JLabel labelRecursiveTime = new JLabel("Time: - ms");

        recursivePanel.add(labelRecursiveResult);
        recursivePanel.add(Box.createVerticalStrut(5)); 
        recursivePanel.add(labelRecursiveTime);

        frame.add(recursivePanel, BorderLayout.SOUTH);

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
                    int iterativeResult = countWaysIterative(n);
                    long endIterative = System.nanoTime();
                    double iterativeTime = (endIterative - startIterative) / 1_000_000.0; 

                    labelIterativeResult.setText("Result: " + iterativeResult);
                    labelIterativeTime.setText(String.format("Time: %.3f ms", iterativeTime));

                    long startRecursive = System.nanoTime();
                    int recursiveResult = countWaysRecursive(n);
                    long endRecursive = System.nanoTime();
                    double recursiveTime = (endRecursive - startRecursive) / 1_000_000.0; 

                    labelRecursiveResult.setText("Result: " + recursiveResult);
                    labelRecursiveTime.setText(String.format("Time: %.3f ms", recursiveTime));
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
