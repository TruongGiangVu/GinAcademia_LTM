
import javax.swing.UIManager;

import Socket.*;
import GUI.*;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
		Server server = new Server();
		server.hashCode();
		
	}

}
