import javax.swing.*;
import java.awt.event.*;

public class KeyEventListener extends JFrame implements KeyListener {
    private JLabel label;
    public KeyEventListener() {
        super("Key Event Listener Demo");
        label = new JLabel("Press any key", SwingConstants.CENTER);
        add(label);
        addKeyListener(this);
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();
    }
    public void keyTyped(KeyEvent e) {
        label.setText("Key Typed: " + e.getKeyChar());
    }
    public void keyPressed(KeyEvent e) {
        label.setText("Key Pressed: " + e.getKeyChar());
    }
    public void keyReleased(KeyEvent e) {
        label.setText("Key Released: " + e.getKeyChar());
    }
    public static void main(String[] args) {
        new KeyEventListener();
    }
} 