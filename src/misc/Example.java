import javax.swing.*;
import java.awt.*;

public class Example {
    public static void main(String[] args) {

        JFrame frame = new JFrame("GridBagLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

      
        JTextArea label1 = new JTextArea("Enter first number:");
        label1.setEditable(false);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(label1, gbc);


        JTextArea label2 = new JTextArea("Enter second number:");
        label2.setEditable(false); 

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(label2, gbc);


        JTextField number1 = new JTextField(10); 

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(number1, gbc);

        JTextField number2 = new JTextField(10); // JTextField is used to take number input from the user

        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(number2, gbc);

        JLabel resultLabel = new JLabel();
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(resultLabel, gbc);



        JButton button2 = new JButton("Add");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(button2, gbc);

        button2.addActionListener(e->{
            try {
                double num1 = Double.parseDouble(number1.getText());
                double num2 = Double.parseDouble(number2.getText());
               double Result = num1 + num2;
                resultLabel.setText("Result: " + Result);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers.");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
