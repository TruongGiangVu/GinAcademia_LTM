
import javax.swing.UIManager;

import Socket.*;
import GUI.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
//		MainFrame frame = new MainFrame();
//		frame.setVisible(true);
		Server server = new Server();
		server.hashCode();
		
	}// a con ve giao din khi dang contest thif t van chua sua, tu tu test cai t moi lam nay,

}
