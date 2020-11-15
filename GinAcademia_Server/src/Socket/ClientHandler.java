package Socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

import Model.Player;
import Socket.Request.*;
import Socket.Response.SocketResponse;
import Socket.Response.*;
import BUS.PlayerBUS;

// a client Thread for connect to just 1 player
public class ClientHandler implements Runnable {
	public int id = 0;
	Socket socket;
	ContestRoom contestRoom = null;
	ObjectInputStream receiver;
	ObjectOutputStream sender;
	PlayerBUS bus = new PlayerBUS();
	boolean isLoggedIn = false;
	Player player = null;

	public ClientHandler(int id, Socket socket) {
		try {
			this.id = id;
			this.socket = socket;
			this.sender = new ObjectOutputStream(this.socket.getOutputStream());
			this.receiver = new ObjectInputStream(this.socket.getInputStream());
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
					if (this.isValidateClient(requestRaw)) { // check data, and get this.player
						if (this.player.getStatus() == 1) { // if client is blocked
							sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
									"Tài khoản này đã bị khóa!"));
						} else if (this.player.getStatus() == 0) {
							if (Server.isOnlinePlayer(this.player.getUsername())) { // if player is online
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
					// remove player
					Server.clients.remove(Server.getIndexOf(this.id));
					this.close();
					break;
				} else if (requestRaw.getAction().equals(SocketRequest.Action.CONTEST)) {
//					new RequestProcess(this, requestRaw).init();
//					System.out.println("contest request");
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

	private boolean isValidateClient(SocketRequest requestRaw) { // check data
		boolean isValidated = false;
		SocketRequestLogin request = (SocketRequestLogin) requestRaw;
		if (bus.loginCheck(request.username, request.password)) {
			player = bus.loginCheckPlayer(request.username, request.password); // get player
			isValidated = true;
		}
		return isValidated;
	}

	private void contest(SocketRequest requestRaw) {
		if (this.isLoggedIn == false) { // check off player
			this.contestRoom.removePlayer(player);
			this.contestRoom = null;
			return;
		}
		if (this.contestRoom == null) { // doesn't have room -> join room
			this.contestRoom = Server.contestRoomManager.findingAvailableRoom();
			this.contestRoom.joinGame(player, this);
		} else if (requestRaw.getMessage().equals("answer")) { // has room, player answer Question
			this.contestRoom.getAnswer(requestRaw);
		}

		if (this.contestRoom.isEndContest) { // finish room, stop game
			Server.contestRoomManager.finishRoom(this.contestRoom.RoomId); // delete this room
			this.contestRoom = null;
		}
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
