package Socket;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import Socket.Request.SocketRequest;
import Socket.Response.SocketResponse;

import javax.activation.*;

public class RegisterProcess {

	public String activeCode = "";
	public String playerName = "";
	public ClientHandler client;
	public SendEmail email;

	Timer timer;

	boolean getCode = false;

	public RegisterProcess(ClientHandler client, String host, String toEmail, String name) {
		this.init(client, host, "vuapha0008@gmail.com", toEmail, name);
	}

	public RegisterProcess(ClientHandler client, String host, String fromEmail, String toEmail, String name) {
		this.init(client, host, fromEmail, toEmail, name);
	}

	public void init(ClientHandler client, String host, String fromEmail, String toEmail, String name) {
		email = new SendEmail(host, fromEmail, toEmail);
		this.activeCode = this.createActiveCodeRandom();
		this.playerName = name;
		this.client = client;
	}

	public void getReset(SocketRequest requestRaw) {
		email.send();
		timer.cancel();
		this.waitActive();
		this.getCode = false;
	}

	public void getActiveCodeFromClient(String code) {
		if (this.activeCode.equals(code)) {
			this.getCode = true;
			this.client.sendResponse(new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.MESSAGE,
					"Đăng ký tài khoản thành công!"), false);
		}
	}

	public void waitActive() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new WaitCode(), 0, 1000);
	}

	class WaitCode extends TimerTask {
		int time = 10 * 60;

		@Override
		public void run() {
			time--;
			if (getCode) {
				timer.cancel();
			}
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
				message.setText("<h1> Đăng ký tài khoản GinAcademia </h1>\r\n" + "<p>Xin chào " + playerName
						+ ", chúng tôi gửi bạn email này cùng với mã code để bạn có thể kích hoạt tài khoản và tham gia vào cuộc thi đố vui của GinAcademia. </p>\r\n"
						+ "<p>Mã code:</p> <b>" + activeCode + " </b>\r\n"
						+ "<p>Xin hãy nhập mã trên vào khung yêu cầu trên ứng dụng của bạn. Nếu không thấy email hoặc bạn có thể nhấn gửi lại.</p>\r\n"
						+ "<p>Cảm ơn vì đã quan tâm đến dịch vụ của chúng tôi. </p>\r\n" + "<p>from GinAcademia</p>");

				// Send message
				Transport.send(message);
				System.out.println("Sent message successfully....");
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
