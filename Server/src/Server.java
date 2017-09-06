
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server {

	// these should be parallel arrays
	private static ArrayList<Socket> clientSockets = new ArrayList<Socket>();
	private static ArrayList<ClientThread> clientThreads = new ArrayList<ClientThread>();
	// but not these
	private static ArrayList<GameThread> gameThreads = new ArrayList<GameThread>();
	private static ArrayList<PlayerData> players = new ArrayList<PlayerData>();

	private static final int DEFAULTPORTNUMBER = 9999;
	private static final String DATABASEFILE = "src/playerData.txt";
	private static final String WORDDECKFILE = "src/wordDeck.txt";
	private static Random random = new Random();

	public static void main(String[] args) {
		loadPlayerData();
		System.out.println(players.size() + " players in registry");
		int portNumber;

		if (args.length != 1) {
			System.err.println("A port number was not specified, resorting to default of " + DEFAULTPORTNUMBER);
			portNumber = DEFAULTPORTNUMBER;
		} else {
			try {
				portNumber = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.err.println(
						"Please input an integer as the portnumber. resorting to default port of " + DEFAULTPORTNUMBER);
				portNumber = DEFAULTPORTNUMBER;
			}
		}

		try {
			connectClients(portNumber);
		} catch (IOException e) {
			System.err.println("Something horrible happened: " + e.getMessage());
		}

	}

	public static void connectClients(int portNumber) throws IOException {
		ServerSocket serverSocket = null;
		try {
			System.out.println("Attempting to create server socket");
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Server socket created on port " + portNumber);

			while (true) {
				clientSockets.add(serverSocket.accept());
				int lastIndex = clientSockets.size() - 1;
				System.out.println("Recieved request: " + clientSockets.get(lastIndex).getPort());
				clientThreads.add(new ClientThread(clientSockets.get(lastIndex), lastIndex));
				clientThreads.get(lastIndex).start();
				checkOnClients();

			}
		} catch (Exception e) {
			System.err.println(
					"An error occurred while starting up server. Please ensure that you do not already have one running.");
		} finally {
			if (serverSocket != null)
				serverSocket.close();
			for (Socket socket : clientSockets) {
				if (socket != null && !socket.isClosed())
					socket.close();
			}
		}

	}

	private static void checkOnClients() {

		for (int i = 0; i < clientSockets.size(); i++) {
			Socket socket = clientSockets.get(i);

			if (socket.isClosed()) {
				clientSockets.remove(i);
				clientThreads.remove(i);
				i--;
			}
		}
	}

	public synchronized static void appendUserDataBase(PlayerData info) {
		players.add(info);
		String line = info.toString();
		try {
			File file = new File(DATABASEFILE);

			BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
			writer.write(line);
			writer.newLine();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static void rewriteUserDataBase() {
		try {
			File file = new File(DATABASEFILE);

			BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), false));
			for (PlayerData info : players) {
				writer.write(info.toString());
				writer.newLine();
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static void loadPlayerData() {
		players = new ArrayList<PlayerData>();

		try {
			File file = new File(DATABASEFILE);

			BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			String input = "";
			while ((input = reader.readLine()) != null) {
				// System.out.println("parsing: " + input);
				players.add(new PlayerData(input));
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PlayerData getPlayerData(String username) {
		for (PlayerData data : players) {
			if (data.getUsername().equals(username)) {
				return data;
			}
		}
		return null;
	}

	public static PlayerData getCookieData(String cookie) {
		for (PlayerData data : players) {
			if (data.getCookie().equals(cookie)) {
				return data;
			}
		}
		return null;
	}

	public static ClientThread getHost(String hostKey) {
		return getGameThread(hostKey).getHost();
	}

	public static boolean cookieLoggedIn(String cookie) {
		return getCookieData(cookie).isLoggedIn();
	}

	public static boolean userLoggedIn(String username) {
		return getPlayerData(username).isLoggedIn();
	}

	public static void loginUser(String username) {
		getPlayerData(username).login();
	}

	public static void logoutUser(String username) {
		getPlayerData(username).logout();
	}

	public static void setCookie(String username, String cookie) {
		getPlayerData(username).setCookie(cookie);
	}

	public static String generateToken() {
		String str = "";
		for (int i = 0; i < 10; i++) {
			char character;
			if (random.nextBoolean())
				character = (char) ((int) 'a' + random.nextInt(26));
			else
				character = (char) ((int) 'A' + random.nextInt(26));
			str += character;
		}
		// lazy check to ensure unique cookie
		if (getCookieData(str) != null) {
			return generateToken();
		}

		return str;
	}

	public static String generateGameKey() {
		String str = "";
		char a = 'a';
		for (int i = 0; i < 3; i++) {
			char character = (char) ((int) 'a' + random.nextInt(26));
			str += character;
		}

		// lazy check to ensure unique game key
		if (getGameThread(str) != null) {
			return generateGameKey();
		}
		return str;
	}

	public static void startNewGame(String gameKey, ClientThread host) {
		gameThreads.add(new GameThread(gameKey, host, WORDDECKFILE));
	}

	public static GameThread getGameThread(String hostKey) {
		for (GameThread game : gameThreads) {
			if (game.getHostKey().equals(hostKey))
				return game;
		}
		return null;
	}

	public static void linkClientToGame(ClientThread client, String cookie, String hostKey) {
		getGameThread(hostKey).addClient(client, cookie);
	}

	public static boolean cookieInGame(String cookie) {
		for (GameThread game : gameThreads) {
			for (String found : game.getCookies()) {
				if (found.equals(cookie))
					return true;
			}
		}
		return false;
	}

}
