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
	ObjectInputStream receiver = null;
	ObjectOutputStream sender = null;
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
				// check if client off super idiot
				if(!this.socket.isConnected() || this.socket.isClosed()) {
					break;
				}
				// check request
				if (requestRaw.getAction().equals(SocketRequest.Action.LOGIN)) { // if client request login
					this.loginPlayerProcess(requestRaw);
				} else if (requestRaw.getAction().equals(SocketRequest.Action.DISCONNECT)) { // disconnect close socket
					this.disconnectProcess();
					break;
				} else if (requestRaw.getAction().equals(SocketRequest.Action.CONTEST)) {
					// send to contest
					this.contest(requestRaw);
				} else {
					// if not login or disconnect, create new Thread and solve it, then delete this
					new RequestProcess(this, requestRaw).init();
				}
			}
			this.close();

		} catch (Exception e) {
			e.printStackTrace();
			this.close();
			System.out.println("Client thoat ngu vl");
		}
	}

	private void close() {
		try {
			this.sender.close();
			this.receiver.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void sendResponse(SocketResponse response) {
		try {
			sender.writeObject(response);
		} catch (IOException e) {
			this.close();
		}
	}

	protected SocketRequest receiveRequest() {
		SocketRequest request = null;
		try {
			request = (SocketRequest) receiver.readObject();
			if (request == null) {
				sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE, "Deo !"));
			}
		} catch (ClassNotFoundException | IOException e) {
			this.close();
		}

		return request;
	}
	private void loginPlayerProcess(SocketRequest requestRaw) {
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
	}
	private void disconnectProcess() {
		isLoggedIn = false;
		// remove player
		if(this.contestRoom != null) {
			this.contestRoom.removePlayer(player);
			if(this.contestRoom.amountOfPlayerInGame() == 0) {
				Server.contestRoomManager.finishRoom(this.contestRoom.RoomId);
			}
			this.contestRoom = null;
		}
		// sign out off server by id of clientHandler
		Server.signOutPlayer(this.id); 
		this.close();
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

}
