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
//		Login login = new Login("localhost",5000);
		Login login = new Login("192.168.110.102",5000);
//		Login login = new Login("tcp://4.tcp.ngrok.io,10092);
		login.hashCode();
	}

}