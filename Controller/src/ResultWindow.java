import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ResultWindow extends JPanel implements ActionListener {
	private Controller controller;
	private JTextArea roundResult;
	private JTextArea overallResult;
	private JButton nextRound;
	private String stored;

	public ResultWindow(Controller controller, String name, String result, String stored) {
		this.controller = controller;
		this.stored = stored;

		String[] trans = result.split("--");
		int players = trans.length / 5;
		String[] names = new String[players];
		String[] message = new String[players];
		String[] score = new String[players];
		String[] fooled = new String[players];
		String[] fails = new String[players];
		int myIndex = 1;
		int tempIndex = 0;

		for (int index = 0; index < players; index++) {
			int mult = index * 5;
			names[index] = trans[mult + 1];
			message[index] = trans[mult + 2];
			score[index] = trans[mult + 3];
			fooled[index] = trans[mult + 4];
			fails[index] = trans[mult + 5];
			if (names[index].equals(controller.getPlayerName()))
				myIndex = index;
		}

		JPanel roundPanel = new JPanel();
		roundPanel.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Round Result"));
		roundResult = new JTextArea(18, 10);
		roundResult.setText(message[myIndex]);
		roundResult.setEnabled(false);
		roundResult.setDisabledTextColor(Color.BLACK);
		roundResult.setWrapStyleWord(true);
		roundPanel.add(roundResult);

		JPanel overPanel = new JPanel();
		overPanel.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Overall Results"));
		overallResult = new JTextArea(18, 10);

		String overall = names[myIndex] + " => Score: " + score[myIndex] + ", Fooled " + fooled[myIndex]
				+ " players, Fooled by " + fails[myIndex] + " players";
		overallResult.setText(overall);
		overallResult.setEnabled(false);
		overallResult.setDisabledTextColor(Color.BLACK);
		overallResult.setWrapStyleWord(true);
		overPanel.add(overallResult);

		nextRound = new JButton("Next Round");
		nextRound.addActionListener(this);

		if (stored.equals("GAMEOVER--"))
			nextRound.setEnabled(false);

		add(roundPanel);
		add(overPanel);
		add(nextRound);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			controller.nextRound(stored);
		}
	}
}
