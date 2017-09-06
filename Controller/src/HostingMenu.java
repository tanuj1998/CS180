import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * 
 * allows the user to set up a game to be hosted leads to @TODO
 * 
 */
public class HostingMenu extends JPanel implements ActionListener {
	private Controller controller;
	private JTextArea participants;
	private JButton startGame;

	public HostingMenu(Controller controller, String hostKey) {
		this.controller = controller;
		add(new JLabel(controller.getPlayerName()));
		add(new JLabel("Others should use this key to join your game"));

		JTextField f = new JTextField(hostKey, 4);
		f.setDisabledTextColor(Color.BLACK);
		f.setEnabled(false);
		add(f);

		JPanel partPanel = new JPanel();
		partPanel.setName("Participants");
		partPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		participants = new JTextArea(12, 24);
		participants.setBackground(new Color(255, 227, 190));
		participants.setEnabled(false);
		participants.setDisabledTextColor(Color.BLACK);
		partPanel.add(participants);
		add(partPanel);


		startGame = new JButton("Start Game");
		startGame.addActionListener(this);
		add(startGame);
		
		System.out.println("hosting menu created");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			if (button == startGame) {
				controller.hostReady();
			}
		}
	}

	public void addPlayer(String name) {
		participants.setText(participants.getText() + "\n" + name);
	}

}
