package Socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

import Model.Player;
import Socket.Request.SocketRequest;
import Socket.Request.*;
import Socket.Response.SocketResponse;
import Socket.Response.*;

public class Client {

	String host = "127.0.0.1";
	int port = 5000;

	Socket socket;
	public ObjectInputStream receiver;
	public ObjectOutputStream sender;

	public boolean isLogin = false;
	public Player player = null;
	public String message = "";

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
			this.sender = new ObjectOutputStream(this.socket.getOutputStream());
			this.receiver = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
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
			this.message = authenticationResponse.getMessage();
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
			e.printStackTrace();
		}
	}

	public void sendRequest(SocketRequest request) {
		try {
			sender.writeObject(request);
			sender.flush();
		} catch (IOException e) {
			System.out.println("loi");
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
//			Object object = this.receiver.readObject();
//			response = (SocketResponse)object;
			this.message = response.getMessage();
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return response;
	}

	public Socket getSocket() {
		return socket;
	}
}