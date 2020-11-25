import javax.swing.UIManager;
import Socket.*;
import GUI.*;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		RegisterProcess send = new RegisterProcess();
		
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
		Server server = new Server(5000);
		server.hashCode();
		
		
		
//		Server server = null;
//		MainFrame main = null;
//		boolean isServerRun = false;
//		boolean isMainRun = false;
//		
//		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//		try {
//			server = new Server(5000);
//			server.hashCode();
//			isServerRun = true;
//			String symbol = "$>";
//			while (true) {
//				System.out.print(symbol);
//				String command = stdIn.readLine();
//				if (command.equals("exit")) {
//					break;
//				} else if (command.equals("main run")) {
//					if (isMainRun == false) {
//						main = new MainFrame();
//						main.setVisible(true);
//						isMainRun = true;
//					}
//				} else if (command.equals("main stop")) {
//					if (isMainRun) {
//						main.dispose();
//						isMainRun = false;
//					}
//				}
//			}
//			System.out.println("Program end");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}

}
