
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
	private Socket socket;
	private int ID;
	private boolean done = false;
	private PrintWriter outToClient;
	private BufferedReader inFromClient;
	private GameThread game;
	private PlayerData player;

	public ClientThread(Socket socket, int ID) {
		this.socket = socket;
		this.ID = ID; 
		try {
			outToClient = new PrintWriter(socket.getOutputStream(), true);
			inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		try {
			interact();
		} catch (IOException e) {
			System.out.println("IO exception on client " + ID);
			if (player != null) {
				Server.logoutUser(player.getUsername());
				System.out.println("Logging user out");
			}
		}
	}

	private void interact() throws IOException {
		String request;

		while (!done) {
			request = inFromClient.readLine();
			// System.out.println("Received message on client " + ID + ":\n" +
			// request);
			react(request);
		}

	}

	private void react(String request) {
		String[] values = request.split("--");
		switch (values[0]) {
		case "CREATENEWUSER":
			createNewUser(values);
			break;
		case "LOGIN":
			login(values);
			break;
		case "STARTNEWGAME":
			startNewGame(values);
			break;
		case "JOINGAME":
			joinGame(values);
			break;
		case "ALLPARTICIPANTSHAVEJOINED":
			allPartipantsHaveJoined(values);
			break;
		case "PLAYERSUGGESTION":
			playerSuggestion(values);
			break;
		case "PLAYERCHOICE":
			playerChoice(values);
			break;
		case "LOGOUT":
			logout();
			break;
		default:
			System.err.println("Recieved an unhandled first parameter");
		}

	}

	private void logout() {
		if (player != null)
			Server.logoutUser(player.getUsername());
	}

	private void playerChoice(String[] values) {
		String base = "RESPONSE--PLAYERCHOICE--";
		String userToken = values[1];
		String gameToken = values[2];
		String choice = values[3];

		if (values.length != 4) {
			respond(base + "INVALIDMESSAGEFORMAT--");
			return;
		}
		if (!Server.cookieLoggedIn(userToken)) {
			respond(base + "USERNOTLOGGEDIN--");
			return;
		}
		if (Server.getGameThread(gameToken) == null) {
			respond(base + "INVALIDGAMETOKEN--");
			return;
		}
		if (!game.seekingChoices()) {
			respond(base + "UNEXPECTEDMESSAGETYPE--");
			return;
		}
		game.addPlayerChoice(this, choice);

	}

	private void playerSuggestion(String[] values) {
		String base = "RESPONSE--PLAYERSUGGESTION--";
		String userToken = values[1];
		String gameToken = values[2];
		String suggestion = values[3];
		if (values.length != 4) {
			respond(base + "INVALIDMESSAGEFORMAT--");
			return;
		}
		if (!Server.cookieLoggedIn(userToken)) {
			respond(base + "USERNOTLOGGENIN--");
			return;
		}
		if (Server.getGameThread(gameToken) == null) {
			respond(base + "INVALIDGAMETOKEN--");
			return;
		}
		if (!game.seekingSuggestions()) {
			respond(base + "UNEXPECTEDMESSAGETYPE--");
			return;
		}
		game.addPlayerSuggestion(this, suggestion);
	}

	private void allPartipantsHaveJoined(String[] values) {
		String base = "RESPONSE--ALLPARTICIPANTSHAVEJOINED--";
		String userToken = values[1];
		String gameToken = values[2];
		if (!Server.cookieLoggedIn(userToken)) {
			respond(base + "USERNOTLOGGEDIN--");
			return;
		}
		GameThread hostgame = Server.getGameThread(gameToken);
		if (hostgame == null) {
			respond(base + "INVALIDGAMETOKEN--");
			return;
		}
		if (Server.cookieInGame(userToken)) {
			respond(base + "USERNOTGAMELEADER--");
			return;
		}

		Server.getGameThread(gameToken).addClient(this, userToken);
		game = hostgame;
		hostgame.start();

	}

	private void joinGame(String[] values) {
		String base = "RESPONSE--JOINGAME--";
		if (values.length != 3) {
			// not the right number of values
		}

		String cookie = values[1];
		String gameToken = values[2];

		if (!Server.cookieLoggedIn(cookie)) {
			respond(base + "USERNOTLOGGEDIN--" + gameToken);
			return;
		}

		game = Server.getGameThread(gameToken);
		if (game == null) {
			respond(base + "GAMEKEYNOYFOUND--" + gameToken);
			return;
		}
		if (!game.clientsCanJoin()) {
			respond(base + "GAMEKEYNOTFOUND--"); // your too slow
			return;
		}

		if (Server.cookieInGame(cookie)) {
			respond(base + "FAILURE");
			return;
		}

		int score = player.getScore();
		Server.linkClientToGame(this, cookie, gameToken);
		Server.getHost(gameToken).respond("NEWPARTICIPANT--" + player.getUsername() + "--" + score + "--");
		respond(base + "SUCCESS");
	}

	private void startNewGame(String[] values) {
		String base = "RESPONSE--STARTNEWGAME--";

		if (values.length != 2) {
			respond(base + "FAILURE--");
			return;
		}

		String token = values[1];
		if (!Server.cookieLoggedIn(token)) {
			respond(base + "USERNOTLOGGEDIN--");
			return;
		}

		String hostKey = Server.generateGameKey();
		respond(base + "SUCCESS--" + hostKey);
		Server.startNewGame(hostKey, this);
	}

	public void createNewUser(String[] values) {
		if (values.length != 3) {
			respond("RESPONSE--CREATENEWUSER--INVALIDMESSAGEFORMAT--");
			return;
		}

		String username = values[1];
		if (username.equals("")) {
			respond("RESPONSE--CREATENEWUSER--INVALIDUSERNAME--");
			return;
		}
		if (Server.getPlayerData(username) != null) {
			respond("RESPONSE--CREATENEWUSER--USERALREADYEXISTS--");
			return;
		}

		String password = values[2];
		if (password.equals("")) {
			respond("RESPONSE--CREATENEWUSER--INVALIDUSERPASSWORD--");
			return;
		}

		Server.appendUserDataBase(PlayerData.buildNewPlayer(username, password));
		respond("RESPONSE--CREATENEWUSER--SUCCESS--");
	}

	private void login(String[] values) {
		String base = "RESPONSE--LOGIN--";
		if (values.length != 3) {
			respond(base + "INVALIDMESSAGEFORMAT--");
			return;
		}

		String username = values[1];
		String password = values[2];

		PlayerData data = Server.getPlayerData(username);
		if (data == null) {
			respond(base + "UNKNOWNUSER--");
			return;
		}
		if (!data.getPassword().equals(password)) {
			respond(base + "INVALIDPASSWORD--");
			return;
		}
		if (Server.userLoggedIn(username)) {
			respond(base + "USERALREADYLOGGEDIN");
			return;
		}

		Server.loginUser(username);
		String cookie = Server.generateToken();
		respond(base + "SUCCESS--" + cookie + "--");
		Server.setCookie(username, cookie);
		player = Server.getPlayerData(username);
	}

	public void respond(String response) {
		outToClient.println(response);
	}

	public String getPlayerName() {
		return player.getUsername();
	}

	public PlayerData getPlayerData() {
		return player;
	}

	public void updatePlayerData(int points, int joker, int fool) {
		player.update(points, joker, fool);
	}

}
