
// % java -jar FoilMaker.student.jar <port number> 

// using port 5 atm

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Controller {
	private View view;
	private Model model;
	private String serverIP = "localhost";
	private int serverPort = 4200;
	private PrintWriter out;
	private InputStreamReader isr;
	private BufferedReader in;
	private HostingMenu hostMenu;
	private Socket socket;
	private ResponseThread responseThread;

	public static void main(String[] args) {
		Controller game = new Controller();
	}

	public Controller() {
		view = new View(this);
		model = new Model(this);
		print("Server port is currently set to: " + serverPort);

		try {
			socket = new Socket(serverIP, serverPort);
			out = new PrintWriter(socket.getOutputStream(), true);
			isr = new InputStreamReader(socket.getInputStream());
			in = new BufferedReader(isr);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server port is currently set to : " + serverPort);
		}

		responseThread = new ResponseThread(this);
	}

	// create new user
	public void createNewUser(String username, String password) {
		String request = "CREATENEWUSER--" + username + "--" + password;
		out.println(request);
	}

	// start new game
	public void startNewGameStart() {
		String request = "STARTNEWGAME--" + model.getCookie();
		out.println(request);
	}
	
	public void startNewGameFinish() {
		hostMenu = new HostingMenu(this, model.getHostKey());
		view.setPanel(hostMenu);
	}
	
	// login
	public void loginStart(String username, String password) {
		model.setPlayerName(username);
		view.setPlayerName(username);
		String request = "LOGIN--" + username + "--" + password;
		out.println(request);
	}
	public void loginFinish() {
		view.setPanel(new GameStartMenu(this));
	}
	
	// join
	public void joinPrepare() {
		view.setPanel(new JoiningMenu(this));
	}
	
	public void joinStart(String hostKey) {
		model.setHostKey(hostKey);
		String request = "JOINGAME--" + model.getCookie() + "--" + hostKey; 
		out.println(request);
	}
	
	public void joinFinish() {
		view.setPanel(new WaitScreen(this));
	}
	
	// set game word screen
	public void newGameWordStart(String question, String correct) {
		view.setPanel(new SuggestionScreen(this, question));
	}
	
	// player suggestion
	public void playerSuggestionStart(String suggestion) {
		String request = "PLAYERSUGGESTION--" + model.getCookie() + "--" + model.getHostKey() + "--" + suggestion;
		out.println(request);
	}
	
	// round options
	public void roundOptionsStart(String[] options) {
		view.setPanel(new ChoiceScreen(options, this));
	}
	
	// player choice
	public void submitPlayerChoice(String choice) {
		String request = "PLAYERCHOICE--" + model.getCookie() + "--" + model.getHostKey() + "--" + choice;
		out.println(request);
	}

	// next round
	public void nextRound(String stored) {
		notice(stored);
	}
	
	
	public void hostReady() {
		String request = "ALLPARTICIPANTSHAVEJOINED--" + model.getCookie() + "--" + model.getHostKey();
		out.println(request);
	}

	public void startRound(String input) {
		view.setPanel(new SuggestionScreen(this, input));
	}

	public void submitSuggestion(String suggestion) {
		String request = "PLAYERSUGGESTION--" + model.getCookie() + "--" + model.getHostKey() + "--" + suggestion;
		out.println(request);
	}


	public void joinGameResponse() {
		/*
		 * if (response.contains("SUCCESS")) { view.setPanel(new
		 * WaitScreen(this)); }
		 * 
		 */
	}

	public void notice(String notice) {
		model.handleNotice(notice);
	}

	public void setPlayerName(String name) {
		model.setPlayerName(name);
	}

	public String getPlayerName() {
		return model.getPlayerName();
	}

	public void addToHostingMenu(String name) {
		hostMenu.addPlayer(name);
	}

	public String getServerIP() {
		return serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public Socket getSocket() {
		return socket;
	}

	public BufferedReader getBufferedReader() {
		return in;
	}

	public void roundResultStart(String result, String stored) {
		view.setPanel(new ResultWindow(this, view.getName(), result, stored));
	}
	

	public void print(String message) {
		view.print(message);
	}
}
