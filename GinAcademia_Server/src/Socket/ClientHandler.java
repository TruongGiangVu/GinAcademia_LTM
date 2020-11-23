package Socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import Model.Player;
import Socket.Request.*;
import Socket.Response.SocketResponse;
import Socket.Response.*;
import BUS.PlayerBUS;

// a client Thread for connect to just 1 player
public class ClientHandler implements Runnable { 
	public int id = 0;
	Socket socket;
	public ContestRoom contestRoom = null;
	ObjectInputStream receiver = null;
	ObjectOutputStream sender = null;
	PlayerBUS bus = new PlayerBUS();
	boolean isLoggedIn = false;
	Player player = null;

	public boolean isInGame = false;
	private Cipher cipher;
	private Cipher dcipher;

	public ClientHandler(int id, Socket socket) {
		try {
			final char[] password = "secret_password".toCharArray();
			final byte[] salt = "random_salt".getBytes();
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(password, salt, 1024, 128);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			dcipher = Cipher.getInstance("AES");
			dcipher.init(Cipher.DECRYPT_MODE, secret);
			this.id = id;
			this.socket = socket;
			this.sender = new ObjectOutputStream(this.socket.getOutputStream());
			this.receiver = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
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
				if (!this.socket.isConnected() || this.socket.isClosed()) {
					break;
				}
				// check request
				if (requestRaw.getAction().equals(SocketRequest.Action.LOGIN)) { // if client request login
					this.loginPlayerProcess(requestRaw);
					
				} else if (requestRaw.getAction().equals(SocketRequest.Action.DISCONNECT)) { // disconnect close socket
					this.disconnectProcess();
					break;
				} else if (requestRaw.getAction().equals(SocketRequest.Action.CONTEST)) { // if player join game
					this.contest(requestRaw); // send to contest, game room
				} else if (requestRaw.getAction().equals(SocketRequest.Action.CANCELCONTEST)) { // player cancel
					this.cancelContest();
				} else {
					// if not login or disconnect, create new Thread and solve it, then delete this
					new RequestProcess(this, requestRaw).init();
				}
			}
			this.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Client thoat ngu vl");
		} finally {
			this.close();
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

	protected void sendResponse(SocketResponse response, boolean flag_reset) {
		try {
			SealedObject so = new SealedObject(response, cipher);
			sender.writeObject(so);
//			sender.writeObject(response);
			if(flag_reset) {
				sender.reset();
			}
		} catch (IOException e) {
			this.close();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
	}

	protected SocketRequest receiveRequest() {
		SocketRequest request = null;
		try {
			SealedObject s = (SealedObject) this.receiver.readObject();
			request = (SocketRequest) s.getObject(dcipher);
//			request = (SocketRequest) receiver.readObject();
			if (request == null) {
				sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE, "Deo !"),false);
			}
		} catch (ClassNotFoundException | IOException e) {
			this.close();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return request;
	}

	private void loginPlayerProcess(SocketRequest requestRaw) { // for login
		if (this.isValidateClient(requestRaw)) { // check data, and get this.player
			if (this.player.getStatus() == 1) { // if client is blocked
				sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
						"Tài khoản này đã bị khóa!"),false);
			} else if (this.player.getStatus() == 0) {
				if (Server.isOnlinePlayer(this.player.getUsername())) { // if player is online
					sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
							"Tài khoản này hiện đang online ở một thiết bị khác!"),false);
				} else { // login OK
					isLoggedIn = true;
					sendResponse(new SocketResponsePlayer(this.player),false);
				}
			}
		} else { // data wrong
			sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
					"Tài khoản hoặc mật khẩu không đúng."),false); 
		}
	}
 
	private void disconnectProcess() {
		isLoggedIn = false;
		// remove player, if player is in Game
		if (this.contestRoom != null) {
			this.contestRoom.removePlayer(this);
			if (this.contestRoom.amountOfPlayerInGame() == 0) {
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
			this.contestRoom.removePlayer(this);
			this.contestRoom = null;
			return;
		}
		if (this.contestRoom == null && requestRaw.getMessage().equals("join")) { // doesn't have room -> join room
			this.contestRoom = Server.contestRoomManager.findingAvailableRoom();
			this.contestRoom.joinGame(this);
		}
		if (this.isInGame == true && requestRaw.getMessage().equals("answer")) { // player answer Question
			this.contestRoom.getAnswer(requestRaw);
		}

		if (this.contestRoom.isEndContest) { // finish room, stop game
			this.isInGame = false;
			Server.contestRoomManager.finishRoom(this.contestRoom.RoomId); // delete this room
			this.contestRoom = null;
		}
	}
	private void cancelContest() {
		this.isInGame = false;
		this.contestRoom.leaveRoom(this);
		if (this.contestRoom.amountOfPlayerInGame() == 0) { // delete room if no one is in room
			Server.contestRoomManager.finishRoom(this.contestRoom.RoomId);
		}
		this.contestRoom = null;
		sendResponse(new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.MESSAGE,
				"cancelGame"),false);
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}
}
