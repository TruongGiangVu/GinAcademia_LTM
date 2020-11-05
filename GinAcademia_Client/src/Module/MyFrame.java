package Module;

import javax.swing.JFrame;

import Socket.Client;
import Socket.Request.SocketRequest;

public class MyFrame extends JFrame{
	public Client client;
	public void setClientSocket(Client client) {
		this.client = client;
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//		        if (JOptionPane.showConfirmDialog(this, 
//		            "Are you sure you want to close this window?", "Close Window?", 
//		            JOptionPane.YES_NO_OPTION,
//		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
//		            System.exit(0);
//		        }
		    	client.sendRequest(new SocketRequest(SocketRequest.Action.DISCONNECT,"Logout"));
				client.close();
				System.exit(0);   
		    }
		});
	}
}
