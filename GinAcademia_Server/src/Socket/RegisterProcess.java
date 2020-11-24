package Socket;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class RegisterProcess {
	public RegisterProcess() {
		SendEmail send = new SendEmail();
		send.init();
	}

	public RegisterProcess(String host, String fromEmail, String toEmail) {
		SendEmail send = new SendEmail(host, fromEmail, toEmail);
		send.init();
	}

	class SendEmail {
		String host = "localhost";
		String fromEmail = "vuapha0008@gmail.com";
		String toEmail = "vuapha008@gmail.com";

		public SendEmail() {
		}

		public SendEmail(String host, String fromEmail, String toEmail) {
			this.host = host;
			this.fromEmail = fromEmail;
			this.toEmail = toEmail;
		}

		public void init() {
			Properties properties = System.getProperties();

			// Setup mail server
			properties.setProperty("mail.smtp.host", host);
			properties.put("mail.smtp.host","smtp.gmail.com");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.debug", "false");
			properties.put("mail.smtp.ssl.enable", "true");

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
				message.setSubject("This is the Subject Line!");

				// Now set the actual message
				message.setText("This is actual message");

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
}
