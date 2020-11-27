package Module;

import javax.swing.JFrame;

import Socket.Client;
import Socket.Request.SocketRequest;

@SuppressWarnings("serial")
public class MyFrame extends JFrame {
	public Client client;

	public MyFrame(Client client) {
		this.client = client;

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				
				client.sendRequest(new SocketRequest(SocketRequest.Action.DISCONNECT, "Logout"));
				
				

				System.exit(0);
			}
		});
	}

	public void setClientSocket(Client client) {
		this.client = client;
	}
}
