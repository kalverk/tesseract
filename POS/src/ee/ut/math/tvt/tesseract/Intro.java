package ee.ut.math.tvt.tesseract;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Intro {
	private static final Logger log = Logger.getLogger(Intro.class);
	public static void main(String[] args) {
		BasicConfigurator.configure();

	
		IntroUI.run();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		log.info("Intro window is opened  "+dateFormat.format(date));

	}
	

}
