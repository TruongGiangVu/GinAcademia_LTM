
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

		Login login = new Login();
		login.hashCode();
//		clientSocketConnect.
		
	}

}