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
	
//	private ExecutorService pool;
	public static ContestRoomManager contestRoomManager;
	private ExecutorService pool;
	int numThread = 2;
	int id = 1;

	GameConfig config;

	public ServerSocket server;

	public Server() {
		try {
			config = new GameConfig();
			pool = Executors.newCachedThreadPool();
			contestRoomManager = new ContestRoomManager(this.config);
			clients = new ArrayList<ClientHandler>();
			server = new ServerSocket(5000);
			System.out.println("Waiting client connect ....");

			this.running();

			System.out.println("Finish");
			this.close();

		} catch (IOException e) {
			System.out.println("Client off ngu vl");
//			e.printStackTrace();
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
//			pool.execute(clientThread);

//			if(clients.size() >= numThread) pool.shutdown();

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
		int n = clients.size();
		System.out.println("Username:" + username);
		if (n < 1)
			isExist = false;
		else
			for (int i = 0; i < n; ++i) {
				if(clients.get(i).isLoggedIn) {
					if (username.equals(clients.get(i).player.getUsername())) {
						
						isExist = true;
						break;
					}
				}
			}
		System.out.println("is Exist: " + isExist);
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
}
