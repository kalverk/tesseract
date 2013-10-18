package ee.ut.math.tvt.tesseract;

import org.apache.log4j.Logger;

import java.awt.Container;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.PropertyConfigurator;

public class IntroUI {
	private static final Logger log = Logger.getLogger(Intro.class);
	
	public static JFrame raam;

	public static void run() {
		Properties prop = new Properties();
		Properties ver = new Properties();
		Properties props = new Properties();

		try {
			prop.load(new FileInputStream("etc/application.properties"));
			ver.load(new FileInputStream("etc/version.properties"));
			props.load(new FileInputStream("etc/log4j.properties"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		PropertyConfigurator.configure(props);
		log.info("Intro window is opened  ");

		raam = new JFrame("Intro");
		raam.setSize(500, 300);
		raam.setLocation(100, 100);
		Container sisu = raam.getContentPane();
		sisu.setLayout(new GridLayout(0, 1));

		ImageIcon icon = new ImageIcon(prop.getProperty("logo"));
		JLabel t5 = new JLabel();
		t5.setIcon(icon);
		sisu.add(t5);

		JLabel t1 = new JLabel();
		t1.setText("Team name: " + prop.getProperty("teamname"));
		sisu.add(t1);

		JLabel t2 = new JLabel();
		t2.setText("Team leader: " + prop.getProperty("teamleader"));
		sisu.add(t2);

		JLabel t3 = new JLabel();
		t3.setText("Team leader's email: " + prop.getProperty("leadermail"));
		sisu.add(t3);

		JLabel t4 = new JLabel();
		t4.setText("Team members: " + prop.getProperty("member1") + ", "
				+ prop.getProperty("member2") + ", "
				+ prop.getProperty("member3"));
		sisu.add(t4);

		JLabel t6 = new JLabel();
		t6.setText("Version number: " + ver.getProperty("build.major.number")
				+ "." + ver.getProperty("build.minor.number") + "."
				+ ver.getProperty("build.revision.number"));
		sisu.add(t6);

		raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		raam.setVisible(true);
	}

}
