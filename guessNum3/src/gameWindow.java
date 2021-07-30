import javax.swing.*;
import java.awt.*;

public class gameWindow extends JFrame {
    /* Base Class, General extension of JFrame
    that can be repurposed for different UI elements
     */
    //public JFrame f;

    public void centerScreen(JFrame f) {
        // method to manually center a JFrame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    public JFrame newFrame(String title, int width, int height, Color frameColor, boolean center, boolean show) {
        // method to build frame to specified parameters
        JFrame x = new JFrame();
        x.setTitle(title);
        x.setLayout(null);
        x.setSize(width,height);
        if (center == true) {
            centerScreen(x);
        }
        x.getContentPane().setBackground(frameColor);
        x.setVisible(show);
        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return x;
    }

    public JButton addButton(String text, int x, int y, int width, int height, Color btnColor) {
        //add button

        JButton b = new JButton(text);
        b.setBounds(x, y, width, height);
        b.setBackground(btnColor);

        return b;
    }

    public JLabel addLabel(String text, int x, int y, int width, int height) {
        //add label

        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);


        return label;
    }

    public JTextField addTextField(int x, int y, int width, int height) {
        // add a textfield

        JTextField tf = new JTextField();
        tf.setBounds(x, y, width, height);


        return tf;
    }
    gameWindow() {

    }

}
