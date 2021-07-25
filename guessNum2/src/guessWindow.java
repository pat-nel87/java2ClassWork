import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class guessWindow extends JFrame {


    // public members
    public JFrame f;
    public JFrame d;
    public JLabel label1, label2, attNum, clock;
    public JButton b, q1, q2, p;
    public JTextField tf;

    guessWindow(){

        // draw main UI
        f = new JFrame();
        f.setTitle("Guess My Number!");
        f.getContentPane().setBackground(Color.YELLOW);

        //center UI
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        // text field to enter guess
        tf = new JTextField();
        tf.setBounds(50, 50, 150, 20);

       // JFrame to open on correct guess
        d = new JFrame();
        d.getContentPane().setBackground(Color.YELLOW);
        d.setTitle("Correct!");
        d.setLayout(null);
        d.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        d.setSize(250, 250);
        d.setVisible(false);

     // correct guess window labels
        attNum = new JLabel(" ");
        attNum.setBounds(50, 25, 250, 20);
        clock = new JLabel(" ");
        clock.setBounds(50, 50, 150, 20);

     // correct guess window button config
        p = new JButton("Play again");
        p.setBounds(50, 75, 150, 20);

        q2 = new JButton("Quit");
        q2.setBounds(50, 100, 150, 20);

        // main UI Labels
       // JLabel label1, label2;

        label1 = new JLabel("Guess a number");
        label1.setBounds(50, 25, 150, 20);
        label2 = new JLabel("Between 1-1000");
        label2.setBounds(50, 75, 250, 20);

        //main UI button config
        b = new JButton("Submit");
        b.setBounds(75,100,95,30);
        b.setBackground(Color.GREEN);

        q1 = new JButton("Quit");
        q1.setBounds(50, 140, 150, 20);
        q1.setBackground(Color.GREEN);

        f.add(b); f.add(label1); f.add(label2); f.add(tf); f.add(q1);
        f.setSize(250, 250);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        d.add(attNum); d.add(clock); d.add(p); d.add(q2);
    }

}
