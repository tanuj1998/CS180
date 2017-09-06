import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class View extends JFrame {
	private Controller controller;
	private JLabel name;
	private JLabel text;

	public View(Controller controller) {
		this.controller = controller;
		setUpGUI();

		name = new JLabel("Unregistered guest");
		name.setHorizontalAlignment(SwingConstants.CENTER);
		text = new JLabel("");

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		this.setVisible(true);

		setPanel(new LoginWindow(controller));
	}

	private void setUpGUI() {
		setSize(500, 500);
		setMinimumSize(new Dimension(300, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public void setPanel(JPanel panel) {
		Container contentPane = getContentPane();

		contentPane.removeAll();
		contentPane.add(name, BorderLayout.NORTH);
		contentPane.add(panel, BorderLayout.CENTER);
		contentPane.add(text, BorderLayout.SOUTH);

		revalidate();
		repaint();

	}

	public void setPlayerName(String name) {
		this.name.setText(name);
	}
	
	public void print(String message) {
		text.setText(message);
	}
}
