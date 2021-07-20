import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import java.util.Random;

public class guessNumber {

	static int genRandom() {
		
		Random ranNum = new Random(); //instance of class random
		int int_random = ranNum.nextInt(1000);
		
		return int_random;
	}

	static int hotOrCold(int computer, int player) {
		
		
		if (player == computer) {
		return 1;
		}
		
		else if (player < computer) {
		return 2;
		}
		
		else if (player > computer) {
		return 3;
		}

		return 0;
	}
	
	static long timer() {
	       
	        long start = System.currentTimeMillis();
	        long seconds = start / 1000;
			       
	        return seconds;
	}

	
        static void result(int compareValue, JFrame f, JButton b, JTextField tf, JDialog d) {

                if (compareValue == 1) {
                        f.getContentPane().setBackground(Color.GREEN);
                        b.setBackground(Color.RED);
                	tf.setEditable(false);
			d.setVisible(true);
		}
                else if (compareValue == 2) {
                        f.getContentPane().setBackground(Color.BLUE);
                        b.setBackground(Color.YELLOW);
                }
                else if (compareValue == 3) {
                        f.getContentPane().setBackground(Color.RED);
                        b.setBackground(Color.GREEN);
                }

        }
	
	static void setCount(int attempts) {
		
		attempts = attempts + 1;
	
	}	

	
	public static void main(String[] args) {
		
		int compNumber = genRandom();
		int attempts = 0;
		long startTime = timer();        
		

		JFrame f = new JFrame("Guess my number!");
		f.getContentPane().setBackground(Color.YELLOW);
		JDialog d;
		d = new JDialog(f, "You've won!", true);
		d.setLayout(new FlowLayout());
		d.add(new JLabel(String.valueOf(attempts)));
		d.setVisible(false);

		final JTextField tf=new JTextField();
		tf.setBounds(50,50,150,20);
				
		JLabel firstLabel;
		JLabel sndLabel;
	


		firstLabel=new JLabel("Guess my Number!");
		firstLabel.setBounds(50,25,200,20);
				
	        sndLabel =new JLabel(" ");
                sndLabel.setBounds(50,75,200,20);

		JButton b = new JButton("click here");
		b.setBounds(75, 100, 95, 30); //x,y,w,h
		b.setBackground(Color.GREEN);
		b.addActionListener(new ActionListener(){
		
			public void actionPerformed(ActionEvent e){
				
				setCount(attempts);
				String randomValue = String.valueOf(compNumber);
				String myGuess = tf.getText();
				
				int randInt = Integer.parseInt(randomValue);
				int myInt = Integer.parseInt(myGuess);
						
				sndLabel.setText(myGuess);		
				tf.setText(randomValue);
					
				result(hotOrCold(randInt, myInt), f, b, tf, d);
				
			}
		
		});

		f.add(b); f.add(tf); f.add(firstLabel); f.add(sndLabel); //adding button in JFrame

		f.setSize(250,250); // w, h
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}	

}
