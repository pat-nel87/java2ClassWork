import javax.swing.*;
import java.awt.*;

public class guessGameUI extends gameWindow {

    public JFrame f, d;
    public JLabel label1, label2, attNum, clock;
    public JButton submit, playAgain, quit1, quit2;
    public JTextField tf;

    public void addElements() {
        /*
         Had to remove the f.add(element) from the addElementType methods because it
        was running really glitchy and elements would randomly disappear

         */
        this.f.add(tf); this.f.add(label1); this.f.add(label2); this.f.add(submit); this.f.add(quit1);
        this.d.add(attNum); this.d.add(clock); this.d.add(playAgain); this.d.add(quit2);

        this.f.setVisible(true);
    }

    guessGameUI() {

            //sets up main game window, frame f
            this.f = newFrame("Guess my Number", 250, 250, Color.YELLOW, true, false);

            this.tf = addTextField(50, 50, 150, 20);

            this.label1 = addLabel("Guess a number", 50, 25, 150, 20);
            this.label2 = addLabel("Between 1-1000", 50, 75, 250, 20);

            this.submit = addButton("Submit", 75, 100, 95, 30, Color.GREEN);
            this.quit1 = addButton("Quit", 50, 140, 150, 20, Color.GREEN);

            // sets up main game window, frame d
            this.d = newFrame("Correct!", 250, 250, Color.YELLOW, true, false);

            this.attNum = addLabel(" ", 50, 25, 250, 20);
            this.clock = addLabel(" ", 50, 50, 150, 20);

            this.playAgain = addButton("Play again", 50, 75, 150, 20, Color.RED);
            this.quit2 = addButton("Quit", 50, 140, 150, 20, Color.RED);
            this.addElements();
    }

}
