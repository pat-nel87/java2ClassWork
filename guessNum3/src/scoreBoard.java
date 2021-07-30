import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class scoreBoard extends gameWindow {

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
        /* method to add new label corresponding to
        each game won and its elapsed time.
        * */
        String tally;
        int h = 40;
        for (int i = 0; i < winTim.size(); i++) {

            JLabel x = new JLabel("\n" + winNum.get(i) + "                       " + winTim.get(i) + " sec");
            x.setBounds(50, h, 150, 20);
            scoreBoard.board.add(x);
            h = h + 10;

        }

    }

    public void addElements() {

        this.board.add(header); this.board.add(ok);

    }

    scoreBoard() {

        this.winNum = new ArrayList<String>();
        this.winTim = new ArrayList<String>();

        this.board = newFrame("Statistics", 400, 400, Color.YELLOW, true, false);

        this.ok = addButton("Ok", 150, 300, 75, 50, Color.GREEN);
             ok.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent actionEvent) {
                     //quits game
                     System.exit(0);

                 }
             });

        this.header = addLabel(" # of Attempts       Time per round ", 50, 10, 300, 30);

        this.addElements();


    }



}
