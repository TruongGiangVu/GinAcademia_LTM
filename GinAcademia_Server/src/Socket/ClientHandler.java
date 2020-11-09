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

// a client Thread for connect to just 1 player
public class ClientHandler implements Runnable {
	public int id = 0;
	Socket socket;
	ArrayList<ClientHandler> clients;
	ContestRoomManager contestRoomManager;
	ContestRoom contestRoom = null;
	ObjectInputStream receiver;
	ObjectOutputStream sender;
	PlayerBUS bus = new PlayerBUS();
	boolean isLoggedIn = false;
	Player player = null;

	public ClientHandler(int id, Socket socket, ArrayList<ClientHandler> clients,
			ContestRoomManager contestRoomManager) {
		try {
			this.id = id;
			this.clients = new ArrayList<ClientHandler>();
			this.socket = socket;
			this.sender = new ObjectOutputStream(this.socket.getOutputStream());
			this.receiver = new ObjectInputStream(this.socket.getInputStream());
			this.contestRoomManager = contestRoomManager;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			System.out.println(socket.getInetAddress() + " " + socket.getPort() + " accept");
			while (true) {
				// get request from client
				SocketRequest requestRaw = receiveRequest();

				// check request
				if (requestRaw.getAction().equals(SocketRequest.Action.LOGIN)) { // if client request login
					if (this.performValidateClient(requestRaw)) { // check data, and get this.player
						if (this.player.getStatus() == 1) { // if client is blocked
							sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
									"Tài khoản này đã bị khóa!"));
						} else if (this.player.getStatus() == 0) {
							if (this.player == null) { // if player is online
								sendResponse(
										new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
												"Tài khoản này hiện đang online ở một thiết bị khác!"));
							} else { // login OK
								isLoggedIn = true;
								sendResponse(new SocketResponsePlayer(this.player));
							}
						}
					} else { // data wrong
						sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
								"Tài khoản hoặc mật khẩu không đúng."));
					}
				} else if (requestRaw.getAction().equals(SocketRequest.Action.DISCONNECT)) { // disconnect close socket
					isLoggedIn = false;
					this.close();
					break;
				} else if (requestRaw.getAction().equals(SocketRequest.Action.CONTEST)) {
					this.contest(requestRaw);
				} else {
					// if not login or disconnect, create new Thread and solve it, then delete this
					new RequestProcess(this, requestRaw).init();
				}
			}
			this.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
		if (request == null) {
			sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE, "Deo !"));
		}
		return request;
	}

	private boolean performValidateClient(SocketRequest requestRaw) { // check data
		boolean isValidated = false;
		SocketRequestLogin request = (SocketRequestLogin) requestRaw;
		if (bus.loginCheck(request.username, request.password)) {
			isValidated = true;
			if (this.isOnlinePlayer(request.username)) {
				player = null;
			} else {
				player = bus.loginCheckPlayer(request.username, request.password); // get player
			}
		}
		return isValidated;
	}

	private boolean isOnlinePlayer(String username) {
		boolean isExist = false;
		int n = clients.size();
		for (int i = 0; i < n; ++i) {
			if (bus.comparePlayer(username, clients.get(i).player)) {
				isExist = true;
			}
		}
		return isExist;
	}

	private void contest(SocketRequest requestRaw) {
		if (this.isLoggedIn == false) { // check out player
			this.contestRoom.removePlayer(player);
			this.contestRoom = null;
			return;
		}
		if (this.contestRoom == null) { // join room
			this.contestRoom = this.contestRoomManager.findingAvailableRoom();
			this.contestRoom.joinGame(player, this);
		} else if (requestRaw.getMessage().equals("answer")) { // player answer Question
			this.contestRoom.getAnswer(requestRaw);
		}
		
		// finish room
//		this.contestRoomManager.finishRoom(this.contestRoom.RoomId);
	}

	public void runCommand() {// write for fun, just to test
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
