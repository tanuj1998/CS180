import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class SuggestionScreen extends JPanel implements ActionListener {
	private Controller controller;
	private JTextField suggestion;
	private JButton submit;

	public SuggestionScreen(Controller controller, String input) {
		this.controller = controller;

		JTextArea phrase = new JTextArea(12, 24);
		phrase.setText(input);
		phrase.setBackground(new Color(255, 227, 190));
		phrase.setEnabled(false);
		phrase.setDisabledTextColor(Color.BLACK);

		JPanel suggestArea = new JPanel();
		suggestArea.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Your Suggestion"));
		suggestion = new JTextField(12);
		suggestArea.add(suggestion);

		submit = new JButton("Submit Suggestion");
		submit.addActionListener(this);

		add(new JLabel("What is the word for: "));
		add(phrase);
		add(suggestArea);
		add(submit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.submitSuggestion(suggestion.getText());
		submit.setEnabled(false);
	}
}
