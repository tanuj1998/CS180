
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ResponseThread extends Thread {
	private Controller controller;
	private Socket socket;
	private boolean done = false;
	private BufferedReader in;

	public ResponseThread(Controller controller) {
		this.controller = controller;
		socket = controller.getSocket();
		in = controller.getBufferedReader();
		start();
	}

	@Override
	public void run() {
		try {
			socket.setSoTimeout(1);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		while (!done) {
			String output = null;
			try {
				output = in.readLine();
			} catch (IOException e) {
				// no news
			}

			if (output != null) {
				controller.notice(output);
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
