
import javax.swing.*;

public class ComponentDemo {
    public static void main(String[] args) {
        JTextArea jTextArea = new JTextArea(5, 20);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        JButton jButton = new JButton("Click Me");

        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.GridLayout(3, 1)); // 3 rows, 1 column
        panel.add(jScrollPane);
        panel.add(jButton);

        JPanel jPanel1 = new JPanel();
        JRadioButton jRadioButton1 = new JRadioButton("Option 1");
        JRadioButton jRadioButton2 = new JRadioButton("Option 2");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(jRadioButton1);
        buttonGroup.add(jRadioButton2);
        jPanel1.add(jRadioButton1);
        jPanel1.add(jRadioButton2);

        String[] itemList={ "Item 1", "Item 2", "Item 3"};
        JList <String> jList= new JList<>(itemList);

        String[] comboBoxList = {"Column 1", "Column 2"};
        JComboBox<String> jComboBox =new JComboBox<>(comboBoxList);

        JSlider jSlider=new JSlider(0,100,40);


        JMenuBar jMenuBar = new JMenuBar();
        JMenu filMenu = new JMenu("File");
        JMenuItem newItem= new JMenuItem("New");
        JMenuItem openItem= new JMenuItem("Open");


        filMenu.add(newItem);
        filMenu.add(openItem);
        jMenuBar.add(filMenu);
       
JButton jButton1 = new JButton("Button 1");
jButton1.setToolTipText("This is Button 1");
JButton jButton2 = new JButton("Button 2");
jButton2.setToolTipText("This is Button 2");


//add action listener to buttons
jButton1.addActionListener(e -> System.out.println("Button 1 clicked"));
jButton2.addActionListener(e -> System.out.println("Button 2 clicked"));


JToolBar jToolBar=new JToolBar();
jToolBar.add(jButton1);
jToolBar.add(jButton2);



        jMenuBar.add(jToolBar);
        jMenuBar.add(new JMenu("Edit"));
        jMenuBar.add(new JMenu("View"));
        jMenuBar.add(new JMenu("Help"));

        // Adding a JTabbedPane to the JMenuBar



JTabbedPane jTabbedPane =new JTabbedPane();
jTabbedPane.addTab("Tab 1", new JLabel("label1"));
        jTabbedPane.addTab("Tab 2", new JLabel("label2"));
        jTabbedPane.addTab("Tab 3", new JLabel("label3"));
        jMenuBar.add(jTabbedPane);




        JFrame jFrame = new JFrame("Menu Example");
        jFrame.add(jToolBar);
        JPanel jpanel3=new JPanel();
        jpanel3.add(jMenuBar);
        JPanel jPanel2 = new JPanel();
        jPanel2.add(jList);
        jPanel2.add(jComboBox);
        JFrame frame = new JFrame("Radio Button Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.GridLayout(3, 3)); // 3 rows, 3 column
         frame.add(jMenuBar);
        frame.add(jPanel1);
        frame.add(panel);
        frame.add(jPanel2);
        frame.add(jSlider);
       
        frame.setSize(900, 600);
        frame.setVisible(true);

    }
}
