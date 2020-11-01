package Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	static ArrayList<ClientHandler> clients;
	private ExecutorService pool;
//	private ExecutorService pool = Executors.newCachedThreadPool();
	int numThread = 2;
	
	public ServerSocket server;
	public Server() {
		try {
			pool = Executors.newFixedThreadPool(numThread);
			clients = new ArrayList<ClientHandler>();
			server = new ServerSocket(5000);
			System.out.println("Waiting client connect ...." );
			
			this.running();
			System.out.println("Finish" );
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
		while(true) {
			Socket client = server.accept();
			ClientHandler clientThread = new ClientHandler(client,clients);
			clients.add(clientThread);
			pool.execute(clientThread);
			if(clients.size() >= numThread)
				pool.shutdown();
			if(pool.isTerminated()) {
				System.out.println("All clients have been killed." );
				break;
			}
			
		}
		
		if(pool.isTerminated()) {
			System.out.println("All clients have been killed 2." );
		}
	}
}
