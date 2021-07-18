/* package start;
*/
import javax.swing.*;
import java.util.Random;

public class HelloWorldSwing {
	
	static int randomNumber() {
		Random rand = new Random();
		int int_random = rand.nextInt(1000);	
		return int_random;
	}

	private static void createAndShowGUI() {
		
		String myNumber = String.valueOf(randomNumber());
		
		JFrame frame = new JFrame("HelloWorldSwing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel label = new JLabel(myNumber);
		frame.getContentPane().add(label);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
