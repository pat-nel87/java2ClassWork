import java.awt.event.*;
import javax.swing.*;

public class swingTut {

	public static void main(String[] args) {

		JFrame f = new JFrame("Patrick's Java Experiments!");
		final JTextField tf=new JTextField();
		tf.setBounds(50,50,150,20);

		JButton b = new JButton("click here");
		b.setBounds(75, 100, 95, 30); //x,y,w,h
		b.addActionListener(new ActionListener(){
		
			public void actionPerformed(ActionEvent e){
				tf.setText("Spark up the doja!");

			}
		
		});

		f.add(b); f.add(tf); //adding button in JFrame

		f.setSize(400,500); // w, h
		f.setLayout(null);
		f.setVisible(true);

	}
}
