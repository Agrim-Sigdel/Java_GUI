import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MouseEventListener {

    public static void main(String[] args) {
        JFrame frame = new JFrame("MouseEventListener Example");
        JLabel label = new JLabel("Click anywhere in the frame to see mouse coordinates");

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                label.setText("Mouse clicked at: X=" + x + ", Y=" + y);
            }
        });
frame.add(label);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        ;
    }
}


//first box ma label 
//enter first number
//enter second number
//add
//add button1
