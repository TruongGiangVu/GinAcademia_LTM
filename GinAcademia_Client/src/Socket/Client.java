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
import javax.swing.JOptionPane;

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
	public ObjectInputStream receiver = null;
	public ObjectOutputStream sender = null;

	public boolean isLogin = false;
	public Player player = null;
	public String message = "";
	public boolean checkSend = false;
	public boolean checkRequest = false;
	public String request = "";
	private Cipher cipher = null;
	private Cipher dcipher = null;

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
			this.socket = new Socket(host, port);
			this.sender = new ObjectOutputStream(this.socket.getOutputStream());
			this.receiver = new ObjectInputStream(this.socket.getInputStream());
			Login.btnLogin.setText("Đăng nhập");
			Login.btnLogin.setEnabled(true);
			
		} catch (IOException e) {
			System.out.println("lỗi");
			Login.btnLogin.setText("Lỗi kết nối");
			Login.lblRegister.setCursor(null);
//			Login.lblRegister.remove
			if(e.getMessage().contains("timed out")) {
				JOptionPane.showMessageDialog(null,(Object) "Kết nối hết hạn", "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
				
			}
			else {
				if(e.getMessage().contains("refused")) {
					JOptionPane.showMessageDialog(null,(Object) "Lỗi kết nối đến máy chủ","Lỗi kết nối",JOptionPane.ERROR_MESSAGE);
				}
			}
//			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
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
			if(this.sender != null && this.checkSend == true)
				this.sender.close();
			if(this.receiver != null && this.checkRequest == true)
				this.receiver.close();
			if(this.socket != null)
				this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendRequest(SocketRequest request) {
		try {
			
			SealedObject so = new SealedObject(request, cipher); // ma hoa
			sender.writeObject(so);
			sender.flush();
			checkSend = true;
			
		} catch (IOException e) {
			checkSend = false;
			if(!request.getAction().equals(SocketRequest.Action.DISCONNECT)) {
				if(e.getMessage().contains("reset")) {
					JOptionPane.showMessageDialog(null, (Object) "Không thể kết nối tới server","Lỗi kết nối",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
	}

	public Player getPlayer() {
		return player;
	}

	public SocketResponse getResponse() {
		SocketResponse response = null;
		try {
//			response = (SocketResponse) this.receiver.readObject();
			SealedObject s = (SealedObject) this.receiver.readObject();
			response = (SocketResponse) s.getObject(dcipher);
			this.message = response.getMessage();
			checkRequest = true;


		} catch (IOException | ClassNotFoundException e) {
			if(e.getMessage() !=null) {
				if(e.getMessage().contains("reset")) {
					JOptionPane.showMessageDialog(null, (Object) "Không nhận được thông tin phản hồi từ server","Lỗi nhận phản hồi từ server",JOptionPane.ERROR_MESSAGE);
				}
				checkRequest = false;
			}
			
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return response;
	}

	public Socket getSocket() {
		return socket;
	}
}