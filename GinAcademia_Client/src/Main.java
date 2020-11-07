
import Socket.Client;

import javax.swing.UIManager;

import Model.*;
import GUI.*;

public class Main {
	public Client clientSocket = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
//		PlayerBUS bus = new PlayerBUS();
//		Player a = bus.getPlayerById("00001");
//		MainFrame mainFrame = new MainFrame(a); mainFrame.setVisible(true);
//		Login c = new Login();
//		System.out.print(System.getProperty("user.dir"));
//		Register b = new Register();
//		b.setVisible(true);
//		System.out.println("Stop application");
//		Client client = new Client();
//		client.hashCode();
		
		Client clientSocket = null;
		System.out.println("run...");
		clientSocket = new Client("localhost",5000);
		System.out.println("client");
		Login login = new Login(clientSocket);
		System.out.println("over");
//		clientSocketConnect.
		
	}

}