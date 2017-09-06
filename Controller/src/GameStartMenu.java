
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 
 * The second pane to show
 * retrieves if the user will host or join a game
 * leads to HostingMenu or JoiningMenu
 * 
 */
public class GameStartMenu extends JPanel implements ActionListener{

	private Controller controller;
	private JButton startGame;
	private JButton joinGame;
	
	public GameStartMenu(Controller controller) {
		
		this.controller = controller;
		JPanel buttonPanel = new JPanel();
		startGame = new JButton("Start New Game");
		startGame.addActionListener(this);
		joinGame = new JButton("Join a Game");
		joinGame.addActionListener(this);
		buttonPanel.add(startGame);
		buttonPanel.add(joinGame);
		
		add(buttonPanel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			if (button == startGame) {
				controller.startNewGameStart();
			} else if (button == joinGame) {
				controller.joinPrepare();
			}
		}
	}

}
