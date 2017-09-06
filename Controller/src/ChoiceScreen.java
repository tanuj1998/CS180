

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class ChoiceScreen extends JPanel implements ActionListener {
	private Controller controller;
	private ArrayList<JRadioButton> buttons;
	private JButton submit;

	public ChoiceScreen(String[] options, Controller controller) {
		this.controller = controller;

		ButtonGroup group = new ButtonGroup();
		buttons = new ArrayList<>();

		for (String option : options) {
			JRadioButton button = new JRadioButton(option);
			button.addActionListener(this);

			buttons.add(button);
			group.add(button);
			add(button);
		}

		submit = new JButton("Submit choice.");
		submit.addActionListener(this);
		add(submit);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			if ((JButton) e.getSource() == submit) {
				String choice = null;
				for (JRadioButton button : buttons) {
					if (button.isSelected())
						choice = button.getText();
				}
				System.out.println(choice + " was chosen");
				submit.setEnabled(false);
				controller.submitPlayerChoice(choice);
			}
		}
	}
}
