package Socket;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import GUI.Login;
import Model.Player;
import Socket.Request.SocketRequest;
import Socket.Request.*;
import Socket.Response.SocketResponse;
import Socket.Response.*;

public class Client {

	String host = "127.0.0.1";
	int port = 5000;

	Socket socket;
	ObjectInputStream receiver;
	ObjectOutputStream sender;
	BufferedReader stdIn = null;

	public boolean isLogin = false;
	public Player player = null;

	public Client() {
		init();
	}

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		init();
	}

	public void init() {
		try {
			this.socket = new Socket(host, port);
			System.out.println("Socket client");
			this.sender = new ObjectOutputStream(this.socket.getOutputStream());
			this.receiver = new ObjectInputStream(this.socket.getInputStream());
			System.out.println("Socket client");
//			stdIn = new BufferedReader(new InputStreamReader(System.in));

//			this.runCommand();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connect(String username, String password) {
		if (this.isLogin == true)
			return;
		SocketRequestLogin authenticationRequest = new SocketRequestLogin(username, password);
		sendRequest(authenticationRequest);
		SocketResponse authenticationResponse = getResponse();
		switch (authenticationResponse.getStatus()) {
		case SUCCESS:
			SocketResponsePlayer player = (SocketResponsePlayer) authenticationResponse;
			this.player = player.getPlayer();
			this.isLogin = true;
			this.player.setPlayerStatus(1);
			System.out.println(this.player.getId() + " login success");
			break;
		case FAILED:
			System.out.println("Login failed");
			break;
		}
	}

	public void close() {
		try {
			this.isLogin = false;
			this.sender.close();
			this.receiver.close();
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendRequest(String str) {
		try {
			sender.writeUTF(str);
			sender.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendRequest(SocketRequest request) {
		try {
			sender.writeObject(request);
			sender.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Player getPlayer() {
		return player;
	}

	public SocketResponse getResponse() {
		SocketResponse response = null;
		try {
			response = (SocketResponse) this.receiver.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return response;
	}

	// test
	private void runCommand() {
		try {
			System.out.println("Connect successfully:");
			String response = "";
			while (true) {
				System.out.print("Input: ");
				String request = stdIn.readLine();
				sender.writeUTF(request);
				sender.flush();
				if (request.equals("exit")) {
					System.out.println("Stop connection:");
					break;
				} else {
					response = this.receiver.readUTF();
					String[] split = request.split(" ");
					switch (split[0]) {
					case "player":
//						Player p = gson.fromJson(response, Player.class);
						Player p = new Player().ToObject(response);
						response = p.toString();
						break;
					default:
						response = request;
					}
				}
				System.out.println("GET: " + response);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}