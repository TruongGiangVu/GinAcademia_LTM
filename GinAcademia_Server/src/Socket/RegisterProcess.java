package Socket;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


import Socket.Response.SocketResponse;


public class RegisterProcess {

	public String activeCode = "";
	public String playerName = "";
	public ClientHandler client;
	public SendEmail email;

	Timer timer;
	int currentTime = -1;

	boolean gottenCode = false;

	public RegisterProcess(ClientHandler client, String host, String toEmail, String name) {
		this.init(client, host, "vuapha0008@gmail.com", toEmail, name);
	}

	public RegisterProcess(ClientHandler client, String host, String fromEmail, String toEmail, String name) {
		this.init(client, host, fromEmail, toEmail, name);
	}

	public void init(ClientHandler client, String host, String fromEmail, String toEmail, String name) {
		this.email = new SendEmail(host, fromEmail, toEmail);
		this.activeCode = this.createActiveCodeRandom();
		this.playerName = name;
		this.client = client;
	}

	public void getResetAndSendEmail() {
		if(!this.gottenCode) {
			timer.cancel();
			email.send();
			this.waitActive();
		}	
	}
	public void cancelEmail() {
		if(!this.gottenCode) {
			currentTime = -1;
			timer.cancel();
		}
	}
	
	public void sendEmailToClient() {
		if(!this.gottenCode) {
			email.send();
			this.waitActive();
		}
	}

	public boolean getActiveCodeFromClient(String code) {
		if (this.currentTime > 0) {
			if(this.activeCode.equals(code)) {
				this.gottenCode = true;
				this.client.sendResponse(new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.MESSAGE,
						"Đăng ký tài khoản thành công!"), false);
			}else {
				this.client.sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
						"Mã của bạn bị sai"), false);
			}
		}
		else {
			this.client.sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
					"Mã này đã hết hạn"), false);
		}
		return this.gottenCode;
	}

	public void waitActive() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new WaitCode(), 0, 1000);
	}

	class WaitCode extends TimerTask {
		int time = 10 * 60;

		@Override
		public void run() {
			if (gottenCode) {
				currentTime = -1;
				timer.cancel();
			}
			if(time  == 0) {
				currentTime = -1;
				timer.cancel();
			}
			currentTime = --time;
		}

	}

	class SendEmail {
		String host = "localhost";
		String fromEmail = "vuapha0008@gmail.com";
		String toEmail = "vuapha008@gmail.com";

		Properties properties;

		public SendEmail(String host, String fromEmail, String toEmail) {
			this.initComponent(host, fromEmail, toEmail);
		}

		public void initComponent(String host, String fromEmail, String toEmail) {
			this.host = host;
			this.fromEmail = fromEmail;
			this.toEmail = toEmail;

			properties = System.getProperties();
			// Setup mail server
			properties.setProperty("mail.smtp.host", host);
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.debug", "false");
			properties.put("mail.smtp.ssl.enable", "true");
		}

		public void send() {
			// Get the default Session object.
			Session session = Session.getDefaultInstance(properties, new SocialAuth());
			try {
				// Create a default MimeMessage object.
				MimeMessage message = new MimeMessage(session);

				// Set From: header field of the header.
				message.setFrom(new InternetAddress(fromEmail));

				// Set To: header field of the header.
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

				// Set Subject: header field
				message.setSubject("GinAcademia Active Code");

				// Now set the actual message
				 message.setContent("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
				 		+ "<h1>Đăng ký tài khoản GinAcademia</h1>"
				 		+ "<p>Xin chào " + playerName
				 		+ ", chúng tôi gửi bạn email này cùng với mã code để bạn có thể kích hoạt tài khoản và tham gia vào cuộc thi đố vui của GinAcademia. </p>"
				 		+ "<p>Mã code:</p><b>" + activeCode + " </b>", "text/html; charset=utf-8");
				// Send message
				 System.out.println("code: " +activeCode);
				Transport.send(message);
			} catch (MessagingException mex) {
				mex.printStackTrace();
			}
		}

	}

	class SocialAuth extends Authenticator {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("vuapha0008@gmail.com", "Solomen008");
		}
	}

	private String createActiveCodeRandom() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public String getActiveCode() {
		return activeCode;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public SendEmail getEmail() {
		return email;
	}

	public void setEmail(SendEmail email) {
		this.email = email;
	}

}
