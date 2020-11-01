package Module;

import javax.swing.JPanel;

import Socket.Client;

public class MyPanel extends JPanel {
	public Client client;
	public void setClientSocket(Client client) {
		this.client = client;
	}
}
