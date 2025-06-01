import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ActionEventListener {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JPanel panel = new JPanel();
       
        panel.add(button1);
        panel.add(button2);
        frame.add(panel);
       
        button1.setText("Click Me");
        button1.addActionListener(e -> {
            System.out.println("Button clicked!");
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.println("Button clicked!");
            }
        
        });


        
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("ActionEventListener Example");
    }
}
