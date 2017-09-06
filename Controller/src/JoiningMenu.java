import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * 
 * allows the user to join an existing game leads to @TODO
 * 
 */
public class JoiningMenu extends JPanel implements ActionListener {
	private Controller controller;
	private JButton joinGame;
	private JTextField key;

	public JoiningMenu(Controller controller) {
		this.controller = controller;

		add(new JLabel("Enter the game key to join a game"));
		key = new JTextField(3);
		add(key);

		joinGame = new JButton("Join Game");
		joinGame.addActionListener(this);
		add(joinGame);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			if (button == joinGame) {
				controller.joinStart(key.getText());
			}
		}
	}

}
