import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import java.util.Random;

public class swingTut {

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
	
        static void result(int compareValue, JFrame f, JButton b, JTextField tf) {

                if (compareValue == 1) {
                        f.getContentPane().setBackground(Color.GREEN);
                        b.setBackground(Color.RED);
                	tf.setEditable(false);
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
	


	public static void main(String[] args) {

		JFrame f = new JFrame("Patrick's Java Experiments!");
		f.getContentPane().setBackground(Color.YELLOW);

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
				
				String randomValue = String.valueOf(genRandom());
				String myGuess = tf.getText();
				
				int randInt = Integer.parseInt(randomValue);
				int myInt = Integer.parseInt(myGuess);
						
				sndLabel.setText(myGuess);		
				tf.setText(randomValue);
					
				result(hotOrCold(randInt, myInt), f, b, tf);
				
			}
		
		});

		f.add(b); f.add(tf); f.add(firstLabel); f.add(sndLabel); //adding button in JFrame

		f.setSize(400,500); // w, h
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}	

}
