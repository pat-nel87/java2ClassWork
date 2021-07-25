// Patrick Nelson
// Computer Science 2 HW - Assignment one
// Main class for Guess Number Game

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main {

    public static int compVal; // computer player's number
    public static int playerGuess;
    public static int attempts;
    public static long startTime;

    public static guessWindow gWin;
    public static scoreBoard currentGame;

    static void reset(guessWindow gWin) {

        Main.attempts = 0;
        Main.gWin.f.dispose();
        Main.gWin.d.dispose();
        run();

    }

    static long time() {

        long seconds = System.currentTimeMillis() / 1000;

        return seconds;
    }

    static void result(int compVal, int playerGuess) {

        //handles game logic
        //increments attempts and compares compVal to player guess
        Main.attempts++;

        long attemptTime = time() - Main.startTime;

        String totalTry = "Attempts this round: " + String.valueOf(Main.attempts);
        String roundTime = "Total Time: " + String.valueOf(attemptTime) + "s";

        if (playerGuess == compVal) {

            Main.currentGame.setWinNum(Main.attempts);
            Main.currentGame.setWinTim(attemptTime);

            Main.gWin.f.getContentPane().setBackground(Color.GREEN);
            Main.gWin.b.setBackground(Color.RED);

            Main.gWin.label1.setForeground(Color.BLACK);
            Main.gWin.label2.setForeground(Color.BLACK);

            Main.gWin.label2.setText("Just Right!");

            Main.gWin.tf.setEditable(false);
            Main.gWin.attNum.setText(totalTry);
            Main.gWin.clock.setText(roundTime);
            Main.gWin.d.setVisible(true);

        } else if (playerGuess < compVal) {

            Main.gWin.label1.setForeground(Color.WHITE);
            Main.gWin.label2.setForeground(Color.WHITE);
            Main.gWin.label2.setText("Too Cold!");
            Main.gWin.f.getContentPane().setBackground(Color.BLUE);
            Main.gWin.b.setBackground(Color.YELLOW);
            Main.gWin.q1.setBackground(Color.YELLOW);
        } else {

            Main.gWin.label2.setText("Too Hot!");
            Main.gWin.f.getContentPane().setBackground(Color.RED);
            Main.gWin.b.setBackground(Color.GREEN);
            Main.gWin.q1.setBackground(Color.GREEN);
        }

    }

    public static void run() {
            // sets time, computer's number, creates guessWindow UI object
            // and add event listeners to UI buttons

            Main.startTime = time();
            Random ranNum = new Random();
            compVal = ranNum.nextInt(1000);

            //instance of guessWindow gWin
            //guessWindow gWin;
            Main.gWin = new guessWindow();

            //event listeners added in main as to work within local scope
            gWin.b.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    // submit button on main UI
                    // handles exception of blank submission
                    try
                    {
                        playerGuess = Integer.parseInt(gWin.tf.getText());
                        result(compVal, playerGuess);
                    }
                    catch (NumberFormatException z) {
                        gWin.tf.setText("Invalid, try again!");
                    }
                }


            });

            gWin.q1.addActionListener(new ActionListener() {
                // quit button on main UI
                public void actionPerformed(ActionEvent e) {

                  //  currentGame.wins.setText(currentGame.tallyUp());
                    currentGame.tallyUp();
                    currentGame.board.setVisible(true);
                    //System.exit(0);
                }

            });

            gWin.p.addActionListener(new ActionListener() {
                // play again button on correct answer UI box
                public void actionPerformed(ActionEvent e) {
                    reset(gWin);
                    gWin.d.dispose();
                }
            });

            gWin.q2.addActionListener(new ActionListener() {
                // quit button on correct answer UI box
                public void actionPerformed(ActionEvent e) {

                    gWin.d.dispose();
                    gWin.f.dispose();
                    currentGame.tallyUp();
                    currentGame.board.setVisible(true);
                }
            });

    }

    public static void main(String[] args) {
         {
                //creates instance of scoreboard, executes run function.
            Main.currentGame = new scoreBoard();
            run();

         }


    }
}
