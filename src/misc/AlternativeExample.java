import javax.swing.*;
import java.awt.*;

public class AlternativeExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Addition Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 180);
    JPanel panel=new JPanel();
        
        JPanel panel1 = new JPanel(new GridLayout(3, 2)); 

        JLabel label1 = new JLabel("Enter first number:");
        JTextField number1 = new JTextField(8);

        JLabel label2 = new JLabel("Enter second number:");
        JTextField number2 = new JTextField(8);

        JButton addButton = new JButton("Add");
        JLabel resultLabel = new JLabel("Result: ");

        addButton.addActionListener(e -> {
            String text1 = number1.getText();
            String text2 = number2.getText();
            if (!text1.isEmpty() && !text2.isEmpty()) {
                try {
                    double num1 = Double.parseDouble(text1);
                    double num2 = Double.parseDouble(text2);
                    resultLabel.setText("Result: " + (num1 + num2));
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Please enter valid numbers.");
                }
            } else {
                resultLabel.setText("Please enter both numbers.");
            }
        });

        panel1.add(label1);
        panel1.add(number1);
        panel1.add(label2);
        panel1.add(number2);
        panel1.add(addButton);
        panel1.add(resultLabel);
        panel.add(panel1);
        frame.add(panel);
        frame.setVisible(true);
    }
}