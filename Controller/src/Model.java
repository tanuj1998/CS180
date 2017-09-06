import java.util.ArrayList;
import java.util.Random;

public class Model {
	private Controller controller;
	private String cookie;
	private String playerName;
	private String hostKey;
	private static Random random = new Random();
	private int joinSlotsRemaining = 1;
	private boolean resultMenuCatcher = false;
	private String savedResultMenu;

	private boolean isHost = false;
	// for host
	private ArrayList<Player> players;

	public Model(Controller controller) {
		this.controller = controller;

	}

	public void setHost() {
		this.isHost = true;
		players = new ArrayList<Player>();
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String str) {
		playerName = str;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String str) {
		cookie = str;
	}

	public String getHostKey() {
		return hostKey;
	}

	public void setHostKey(String str) {
		hostKey = str;
	}

	public int getJoinSlotsRemaining() {
		return joinSlotsRemaining;
	}

	public void addPlayer(Player player) {
		if (isHost) {
			players.add(player);
		} else {
			print("You need to be a host to add players to roster");
		}
	}

	public void handleNotice(String notice) {
		String[] param = notice.split("--");
		if (resultMenuCatcher) {
			roundResult(savedResultMenu, notice);
			resultMenuCatcher = false;
		}

		if (!param[0].equals("RESPONSE")) {
			String name = "";
			String score = "";
			switch (param[0]) {
			case "NEWPARTICIPANT":
				name = param[1];
				score = param[2];
				newParticipant(name, score);
				break;
			case "NEWGAMEWORD":
				String question = param[1];
				String correct = param[2];
				newGameWord(question, correct);
				break;
			case "ROUNDRESULT":
				resultMenuCatcher = true;
				savedResultMenu = notice;
				break;
			case "ROUNDOPTIONS":
				String[] options = new String[param.length - 1];
				for (int i = 1; i < param.length; i++) {
					options[i - 1] = param[i];
				}
				roundOptions(options);
				break;
			}
		} else {
			String status = param[2];
			boolean success = status.equals("SUCCESS");
			switch (param[1]) {
			case "CREATENEWUSER":
				createNewUser(status);
				break;
			case "LOGIN":
				String cookie = null;
				if (success)
					cookie = param[3];
				login(status, cookie);
				break;
			case "STARTNEWGAME":
				String hostKey = null;
				if (success)
					hostKey = param[3];
				startNewGame(status, hostKey);
				break;
			case "JOINGAME":
				if (success) {
					joinGame();
				}
				break;
			case "ALLPARTICIPANTSHAVEJOINED":
				allParticipantsHaveJoined(status);
				break;
			case "PLAYERSUGGESTION":
				playerSuggestion(status);

				break;
			case "PLAYERCHOICE":
				playerChoice(status);
				break;
			case "LOGOUT":
				logout(status);
			}
		}
	}

	private void roundResult(String resultMessage, String caughtMessage) {
		controller.roundResultStart(resultMessage, caughtMessage);
	}

	public void newParticipant(String name, String score) {
		// TODO
		int points = Integer.parseInt(score);
		Player player = new Player(name, points);
		addPlayer(player);
		controller.addToHostingMenu(name);
	}

	public void newGameWord(String question, String correct) {
		controller.newGameWordStart(question, correct);
	}

	public void createNewUser(String status) {
		// TODO
	}

	public void login(String status, String cookie) {
		if (cookie != null)
			this.cookie = cookie;

		switch (status) {
		case "SUCCESS":
			controller.loginFinish();
		}
	}

	private void startNewGame(String status, String hostKey) {

		if (hostKey != null)
			this.hostKey = hostKey;
		switch (status) {
		case "SUCCESS":
			controller.startNewGameFinish();
			setHost();
			break;
		}
	}

	private void joinGame() {
		controller.joinFinish();
	}

	private void allParticipantsHaveJoined(String status) {
		// TODO

	}

	private void playerSuggestion(String status) {
		// TODO
	}

	private void roundOptions(String[] options) {
		controller.roundOptionsStart(options);
	}

	private void playerChoice(String status) {
		// TODO
	}

	private void logout(String status) {
		// TODO
	}

	public void print(String message) {
		controller.print(message);
	}
}
