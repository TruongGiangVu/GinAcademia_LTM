import Socket.Client;

import javax.swing.UIManager;
import GUI.*;

public class Main {
	public Client clientSocket = null;
	
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }	
		Login login = new Login("localhost",5000);
		 
//		Login login = new Login("172.20.10.4",5000);
		login.hashCode();

	}

}