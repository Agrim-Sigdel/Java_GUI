import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;


public class Flowlayoutex {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setLayout(new java.awt.FlowLayout());
       
        jFrame.add(new JButton("hello"));
        jFrame.add(new JButton("world"));
        jFrame.add(new JButton("My"));
        jFrame.add(new JButton("Name"));
        jFrame.add(new JButton("Is"));
        jFrame.add(new JButton("Agrim"));
        jFrame.add(new JTextArea("Sigdel"));
    jFrame.setResizable(false);
 jFrame.setSize(600, 400);
 JFrame.setDefaultLookAndFeelDecorated(false);
 jFrame.setVisible(true);
    }
    
}
