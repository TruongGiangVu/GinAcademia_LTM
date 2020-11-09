package Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Model.GameConfig;

public class Server {
	static ArrayList<ClientHandler> clients;
//	private ExecutorService pool;
	private ContestRoomManager contestRoomManager;
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
			// TODO Auto-generated catch block
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
			ClientHandler clientThread = new ClientHandler(id, client, clients, contestRoomManager);
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
}
