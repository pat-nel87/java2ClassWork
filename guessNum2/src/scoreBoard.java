// Patrick Nelson
// Computer Science 2 HW - Assignment one
/* Scoreboard class to keep track of the users attempts and times per round */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class scoreBoard extends JFrame {

    public static JFrame board;
    public static JLabel header, wins, winTimes;
    public JButton ok;
    public static ArrayList<String> winNum, winTim;

    public static void setWinNum(int value) {

        winNum.add(String.valueOf(value));

    }

    public static void setWinTim(long value) {

        winTim.add(String.valueOf(value));

    }

    public static void tallyUp() {

        String tally;
        int h = 40;

        for (int i = 0; i < winTim.size(); i++) {

        JLabel x = new JLabel("\n" + winNum.get(i) + "                       " + winTim.get(i) + " sec");
        x.setBounds(50, h, 150, 20);
        scoreBoard.board.add(x);
        h = h + 10;

        System.out.println("\n" + winNum.get(i) + " " + winTim.get(i) + " ");
        }

    }

    scoreBoard(){

        winNum = new ArrayList<String>();
        winTim = new ArrayList<String>();

        board = new JFrame();
        board.setTitle("Statistics");
        board.getContentPane().setBackground(Color.YELLOW);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        board.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        ok = new JButton("Quit"); // ok button loses text for some reason, can't figure out why.
        ok.setBounds(100, 300, 50, 25);
        ok.setBackground(Color.GREEN);
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }

        });

        header = new JLabel(" # of Attempts       Time per round ");
        header.setBounds(50, 10, 300, 30);

        board.add(header); board.add(ok);
        board.setSize(400, 400);
        board.setLayout(null);
        board.setVisible(false);
        board.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

}
