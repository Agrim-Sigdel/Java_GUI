import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class NoLayout {
    public static void main(String[] args) {
        JFrame frame = new JFrame("No Layout Example");
        frame.setSize(400, 200);

        JLabel label1 = new JLabel("Label 1");
        label1.setBounds(50, 50, 100, 30); // x, y, width, height


JButton fileButton = new JButton("File");
fileButton.setBounds(50, 100, 100, 30); // x, y, width, height
JButton colorButton = new JButton("Color");
colorButton.setBounds(0, 0, 100, 30); // x, y, width, height
frame.add(fileButton);
frame.add(colorButton);
        JLabel label2 = new JLabel("Label 2");          
        label2.setBounds(200, 50, 100, 30); // x, y, width, height
       

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // No layout manager
        frame.add(label1);
        frame.setVisible(true);
       

    }
}
