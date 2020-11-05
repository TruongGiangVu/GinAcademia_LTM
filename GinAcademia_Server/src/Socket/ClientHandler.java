package Socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import Model.Player;
import Socket.Request.*;
import Socket.Response.SocketResponse;
import Socket.Response.*;
import BUS.PlayerBUS;

public class ClientHandler implements Runnable {
	public int id = 0;
	Socket socket;
	ArrayList<ClientHandler> clients;
	ObjectInputStream receiver;
	ObjectOutputStream sender;
	PlayerBUS bus = new PlayerBUS();
	boolean isLoggedIn = false;
	Player player = null;

	public ClientHandler(int id,Socket socket, ArrayList<ClientHandler> clients) {
		// TODO Auto-generated constructor stub
		try {
			this.id = id;
			this.clients = new ArrayList<ClientHandler>();
			this.socket = socket;
			this.sender = new ObjectOutputStream(this.socket.getOutputStream());
			this.receiver = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
//		this.runCommand();
		try {
			System.out.println(socket.getInetAddress() + " " + socket.getPort() + " accept");
			while (true) {
				SocketRequest requestRaw = receiveRequest();
				if (requestRaw.getAction().equals(SocketRequest.Action.LOGIN)) {
					if (this.performValidateClient(requestRaw)) {
						if(this.player.getStatus() == 1) {
							sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
									"Tài khoản này đã bị khóa!"));
						}
						else if(this.player.getStatus() == 0){
							isLoggedIn = true;
							sendResponse(new SocketResponsePlayer(this.player));
						}
					} else {
						sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
								"Tài khoản hoặc mật khẩu không đúng."));
					}
				}
				else if(requestRaw.getAction().equals(SocketRequest.Action.REGISTER)) {
					
				}
				else if (requestRaw.getAction().equals(SocketRequest.Action.DISCONNECT)) {
					this.close();
					break;
				} else {
					// code here
					System.out.println("update");
					new RequestProcess(this,requestRaw).init();
				}
			}
			this.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Client thoat ngu vl");
		}
	}

	private void close() throws IOException {
		this.sender.close();
		this.receiver.close();
		this.socket.close();
	}

	private void sendResponse(String request) {
		try {
			sender.writeUTF(request);
			sender.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void sendResponse(SocketResponse response) {
		try {
			sender.writeObject(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected SocketRequest receiveRequest() throws IOException, ClassNotFoundException {
		SocketRequest request = null;
		
		request = (SocketRequest) receiver.readObject();
		return request;
	}

	private boolean performValidateClient(SocketRequest requestRaw) {
		boolean isValidated = false;
		SocketRequestLogin request = (SocketRequestLogin) requestRaw;
		if (bus.loginCheck(request.username, request.password)) {
			isValidated = true;
			player = bus.loginCheckPlayer(request.username, request.password);
		}
		return isValidated;
	}

	public void runCommand() {
		try {
			System.out.println(socket.getInetAddress() + " " + socket.getPort() + " accept");
			String response = "";
			while (true) {
				// code
				String request = receiver.readUTF();
				System.out.println(socket.getInetAddress() + " " + socket.getPort() + ": " + request);
				if (request.equals("exit")) {
					System.out.println(socket.getInetAddress() + " " + socket.getPort() + ": has died.");
					break;
				} else {
					String[] split = request.split(" ");
					switch (split[0]) {
					case "player":
						Player p = bus.getPlayerById(split[1]);
						response = p.toJson();
						break;
					default:
						response = request;
					}
				}
				this.sendResponse(response);

			}
			this.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
