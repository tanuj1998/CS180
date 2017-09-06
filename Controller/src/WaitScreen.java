
import javax.swing.JLabel;
import javax.swing.JPanel;


public class WaitScreen extends JPanel{

	private Controller controller;
	
	public WaitScreen(Controller controller) {
		add(new JLabel("Waiting for leader."));

	}
}
