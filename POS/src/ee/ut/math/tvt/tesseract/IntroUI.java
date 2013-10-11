package ee.ut.math.tvt.tesseract;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class IntroUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame raam = new JFrame("Intro");
		raam.setSize(500, 250);
		raam.setLocation(100, 100);
		Container sisu = raam.getContentPane();
		sisu.setLayout(new GridLayout(0,1));
		JButton nupp = new JButton();
		
		ImageIcon icon = new ImageIcon("logo.PNG");
		JLabel t1 = new JLabel();
		t1.setText("Team name: TESSERACT");
		sisu.add(t1);
		

		JLabel t2 = new JLabel();
		t2.setText("Team leader: K�lver Kilvits");
		sisu.add(t2);
		
		JLabel t3 = new JLabel();
		t3.setText("Team leader's email: kalverk@gmail.com");
		sisu.add(t3);
		
		JLabel t4 = new JLabel();
		t4.setText("Team members: K�lver Kilvits, Karin Klooster, Markus K�ngsepp, Kadi Laidoja");
		sisu.add(t4);
		

		JLabel t5 = new JLabel();
		t5.setText("Team logo: ");
		sisu.add(t5);
		
		JLabel t6 = new JLabel();
		t6.setText("Version number: 0.0.0");
		sisu.add(t6);

		raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		raam.setVisible(true);
	}

}
