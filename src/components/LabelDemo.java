import javax.swing.*;

public class LabelDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Label Demo");
        JLabel label = new JLabel("This is a label.", SwingConstants.CENTER);
        frame.add(label);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
} 