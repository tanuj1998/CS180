
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameThread extends Thread {
	private static Random random = new Random();

	private static enum States {
		gameOver, playerJoin, collectSuggestions, collectChoices
	};

	private String gameKey;
	private ClientThread host;
	private ArrayList<ClientThread> clients = new ArrayList<>();
	private ArrayList<String> cookies = new ArrayList<>();
	private States state = States.playerJoin;
	private ArrayList<String> questions = new ArrayList<String>();

	private HashMap<ClientThread, String> suggestions = new HashMap<ClientThread, String>();
	private HashMap<ClientThread, String> choices = new HashMap<ClientThread, String>();
	private String correct;

	public GameThread(String gameKey, ClientThread host, String path) {
		this.gameKey = gameKey;
		this.host = host;

		try {
			File file = new File(path);

			BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			String input = "";
			while ((input = reader.readLine()) != null) {
				questions.add(input);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		newGameWord();

		while (state != States.gameOver) {
			switch (state) {
			case collectSuggestions:
				if (hasAllSuggestions()) {
					sendRoundOptions();
				}
				break;
			case collectChoices:
				if (hasAllChoices()) {
					interpretResults();
					suggestions.clear();
					choices.clear();
					newGameWord();
				}
				break;
			}
		}

	}

	public void interpretResults() {
		HashMap<ClientThread, Integer> plus = new HashMap<>();
		HashMap<ClientThread, Integer> minus = new HashMap<>();

		HashMap<ClientThread, ArrayList<String>> joker = new HashMap<>();
		HashMap<ClientThread, String> victem = new HashMap<>();

		for (ClientThread client : clients) {
			plus.put(client, 0);
			minus.put(client, 0);
			joker.put(client, new ArrayList<String>());
			victem.put(client, null);
		}

		// organize results
		for (ClientThread client : clients) {
			for (ClientThread checker : clients) {
				if (choices.get(client).equals(suggestions.get(checker))) {
					victem.put(client, checker.getPlayerName());
					minus.put(client, minus.get(client) + 1);

					joker.get(checker).add(client.getPlayerName());
					plus.put(checker, plus.get(checker) + 1);
				}
			}
		}

		// update results
		for (ClientThread client : clients) {
			int jokerIncrease = plus.get(client);
			int foolIncrease = minus.get(client);
			int scoreIncrease = jokerIncrease;
			if (foolIncrease == 0) {
				scoreIncrease++;
			}
			client.updatePlayerData(scoreIncrease, jokerIncrease, foolIncrease);
		}
		Server.rewriteUserDataBase();

		// create result string
		String build = "ROUNDRESULT--";
		for (ClientThread client : clients) {
			build += client.getPlayerName() + "--";
			if (victem.get(client) == null) {
				build += "You got it right! ";
				plus.put(client, plus.get(client) + 1);
			} else {
				build += "You were fooled by " + victem.get(client) + ". ";
			}
			ArrayList<String> fooled = joker.get(client);
			if (fooled.size() == 1) {
				build += "You fooled " + fooled.get(0) + ". ";
			} else if (fooled.size() == 2) {
				build += "You fooled " + fooled.get(0) + " and " + fooled.get(1) + ". ";
			} else if (fooled.size() == 3) {
				build += "You fooled ";
				for (int i = 0; i < fooled.size() - 1; i++) {
					build += fooled.get(i) + ", ";
				}
				build += "and " + fooled.get(fooled.size() - 1) + ". ";
			}
			build += "--";
			PlayerData data = Server.getPlayerData(client.getPlayerName());
			build += data.getScore() + "--";
			build += data.getTimesFooledOther() + "--";
			build += data.getTimesWasFooled() + "--";
		}
		
		for (ClientThread client: clients) {
			client.respond(build);
		}

	}

	public void addPlayerChoice(ClientThread thread, String choice) {
		choices.put(thread, choice);
	}

	public void addPlayerSuggestion(ClientThread thread, String suggestion) {
		suggestions.put(thread, suggestion);
	}

	private boolean hasAllChoices() {
		return (choices.size() == clients.size());
	}

	private boolean hasAllSuggestions() {
		return (suggestions.size() == clients.size());
	}

	private void sendRoundOptions() {
		String output = "ROUNDOPTIONS--";
		// add correct option
		ArrayList<String> options = new ArrayList<String>();
		options.add(correct);

		// scramble
		for (String option : suggestions.values()) {
			options.add(random.nextInt(options.size()+1), option);
		}

		for (String option : options) {
			output += option + "--";
		}

		for (ClientThread client : clients) {
			client.respond(output);
		}

		state = States.collectChoices;
	}

	public void newGameWord() {
		String output;
		if (questions.size() == 0) {
			output = "GAMEOVER--";
			state = States.gameOver;
		} else {
			int index = random.nextInt(questions.size());
			String[] selections = questions.get(index).split(":");
			correct = selections[1];
			output = "NEWGAMEWORD--" + selections[0] + "--" + correct;
			questions.remove(index);
			state = States.collectSuggestions;

		}
		for (ClientThread client : clients) {
			client.respond(output);
		}
	}

	public String getHostKey() {
		return gameKey;
	}

	public void addClient(ClientThread client, String cookie) {
		clients.add(client);
		cookies.add(cookie);
	}

	public ArrayList<String> getCookies() {
		return cookies;
	}

	public ClientThread getHost() {
		return host;
	}
	
	public boolean clientsCanJoin() {
		return (state == States.playerJoin);
	}

	public boolean seekingSuggestions() {
		return (state == States.collectSuggestions);
	}
	
	public boolean seekingChoices() {
		return (state == States.collectChoices);
	}
	
	public int getNumClients() {
		return clients.size();
	}
	
	public String getHostCookie() {
		return host.getPlayerData().getCookie();
	}
}
