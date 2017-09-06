import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


/**
 * 
 * The first pane to show Retrieves the user's name, password, and allows the
 * user to login Leads to GameStartMenu
 *
 */
public class LoginWindow extends JPanel implements ActionListener {
	Controller controller;
	JTextField name;
	JTextField pass;
	JButton login;
	JButton register;

	public LoginWindow(Controller controller) {
		this.controller = controller;
		JPanel namePanel = new JPanel();
		namePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		namePanel.add(new JLabel("Input name:"));
		name = new JTextField(12);
		namePanel.add(name);
		this.add(namePanel);

		JPanel passPanel = new JPanel();
		passPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		passPanel.add(new JLabel("Input password:"));
		pass = new JPasswordField(12);

		passPanel.add(pass);
		this.add(passPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		login = new JButton("Login");
		login.addActionListener(this);

		register = new JButton("Register");
		register.addActionListener(this);

		buttonPanel.add(login);
		buttonPanel.add(register);
		this.add(buttonPanel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			String username = name.getText();
			String password = pass.getText();

			if (button == login) {
				controller.loginStart(username, password);
			} else if (button == register) {
				controller.createNewUser(username, password);
			}
		}
	}

}
