/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trial1;

/**
 *
 * @author sumat
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator {

    public static void main(String[] args) {

        // Create a frame
        JFrame frame = new JFrame("Simple Calculator");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 2, 10, 10));

        // Create components
        JLabel label1 = new JLabel("Enter Number 1:");
        JTextField text1 = new JTextField();

        JLabel label2 = new JLabel("Enter Number 2:");
        JTextField text2 = new JTextField();

        JLabel resultLabel = new JLabel("Result:");
        JTextField resultField = new JTextField();
        resultField.setEditable(false);

        JButton add = new JButton("Add");
        JButton sub = new JButton("Subtract");
        JButton mul = new JButton("Multiply");
        JButton div = new JButton("Divide");

        // Add components to frame
        frame.add(label1);
        frame.add(text1);
        frame.add(label2);
        frame.add(text2);
        frame.add(add);
        frame.add(sub);
        frame.add(mul);
        frame.add(div);
        frame.add(resultLabel);
        frame.add(resultField);

        // Add action listeners
        ActionListener action = e -> {
            try {
                double num1 = Double.parseDouble(text1.getText());
                double num2 = Double.parseDouble(text2.getText());
                double result = 0;

                if (e.getSource() == add) {
                    result = num1 + num2;
                } else if (e.getSource() == sub) {
                    result = num1 - num2;
                } else if (e.getSource() == mul) {
                    result = num1 * num2;
                } else if (e.getSource() == div) {
                    if (num2 != 0)
                        result = num1 / num2;
                    else
                        resultField.setText("Cannot divide by zero");
                }

                if (!(e.getSource() == div && num2 == 0))
                    resultField.setText(String.valueOf(result));

            } catch (NumberFormatException ex) {
                resultField.setText("Invalid Input");
            }
        };

        add.addActionListener(action);
        sub.addActionListener(action);
        mul.addActionListener(action);
        div.addActionListener(action);

        // Show frame
        frame.setVisible(true);
    }
}

