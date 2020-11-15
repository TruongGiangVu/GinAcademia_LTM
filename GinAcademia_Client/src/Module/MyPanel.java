package Module;

import javax.swing.JPanel;

import Socket.Client;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {
	public Client client;
	public MyPanel(Client client) {
		this.client = client;
		// TODO Auto-generated constructor stub
	}
	public void setClientSocket(Client client) {
		this.client = client;
	}
}
