package Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Model.GameConfig;

public class Server {

	public static ArrayList<ClientHandler> clients;
	public static ContestRoomManager contestRoomManager;
	private ExecutorService pool;
	int id = 1;

	static GameConfig config;
	public ServerSocket server;

	public Server() {
		init(5000);
	}

	public Server(int port) {
		init(port);
	}

	private void init(int port) {
		try {
			config = new GameConfig();
			pool = Executors.newCachedThreadPool();
			contestRoomManager = new ContestRoomManager(config);
			clients = new ArrayList<ClientHandler>();
			server = new ServerSocket(port);
			System.out.println("Waiting client connect ....");

			this.running();

			System.out.println("Finish");
			this.close();

		} catch (IOException e) {
			System.out.println("Client off ngu vl");
			e.printStackTrace();
		}
	}

	private void close() throws IOException {
		server.close();
	}

	public void running() throws IOException {
		while (true) {
			Socket client = server.accept();
			ClientHandler clientThread = new ClientHandler(id, client);
			id++;
			clients.add(clientThread);
			pool.submit(clientThread);

			if (pool.isTerminated()) {
				System.out.println("All clients have been killed.");
				break;
			}

		}
		if (pool.isTerminated()) {
			System.out.println("All clients have been killed 2.");
		}
		pool.shutdown();
	}

	public static boolean isOnlinePlayer(String username) {
		boolean isExist = false;
		if (clients == null)
			isExist = false;
		else {
			int n = clients.size();
			if (n < 1) {
				isExist = false;
			} else {
				for (int i = 0; i < n; ++i) {
					if (clients.get(i).isLoggedIn) {
						if (username.equals(clients.get(i).player.getUsername())) {
							isExist = true;
							break;
						}
					}
				}
			}
		}
		return isExist;
	}

	public static void signOutPlayer(int id) {
		clients.remove(getIndexOf(id));
		int n = clients.size();
		System.out.println("Size clients:" + n);
	}

	public static int getIndexOf(int id) {
		int n = Server.clients.size();
		for (int i = 0; i < n; ++i) {
			if (id == Server.clients.get(i).id)
				return i;
		}
		return -1;
	}

	public static int countPlayerOnline() {
		int ans = 0;
		if (clients == null)
			return ans;
		int n = clients.size();
		for (int i = 0; i < n; ++i) {
			if (clients.get(i).isLoggedIn)
				ans++;
		}
		return ans;
	}
	
	public static GameConfig getConfig() {
		return config;
	}

	public static void setConfig(GameConfig configNew) {
		config = configNew;
		contestRoomManager.setConfig(config);
	}

}
