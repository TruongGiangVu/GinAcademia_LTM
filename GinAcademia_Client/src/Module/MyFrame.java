package Module;

import javax.swing.JFrame;

import Socket.Client;

public class MyFrame extends JFrame{
	public Client client;
	public void setClientSocket(Client client) {
		this.client = client;
	}
}
