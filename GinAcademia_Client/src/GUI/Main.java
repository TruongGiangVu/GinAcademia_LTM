package GUI;

import Server.BUS.*;
import Socket.Client;
import Model.*;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerBUS bus = new PlayerBUS();
		Player a = bus.getPlayerById("00001");
		MainFrame mainFrame = new MainFrame(a); mainFrame.setVisible(true);
//		Login c = new Login();
//		System.out.print(System.getProperty("user.dir"));
//		Register b = new Register();
//		b.setVisible(true);
//		System.out.println("Stop application");
//		Client client = new Client();
//		client.hashCode();
		
	}
}