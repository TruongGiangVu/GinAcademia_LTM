package Socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import Model.Player;

public class Client {
	
	String host = "127.0.0.1";
	int port = 5000;
	
	Socket socket;
	DataInputStream receiver;
	DataOutputStream sender;
	BufferedReader stdIn = null; 
	
	public Client() {
		// TODO Auto-generated constructor stub
		try {
			this.socket = new Socket(host,port);
			this.receiver = new DataInputStream(this.socket.getInputStream());
			this.sender = new DataOutputStream(this.socket.getOutputStream());
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Connect successfully:");
			String response = "";
			while(true) {
				System.out.print("Input: ");
				String request = stdIn.readLine();
				sender.writeUTF(request);
				sender.flush();
				if(request.equals("exit")) {
					System.out.println("Stop connection:");
					break;
				}
				else {
					response = this.receiver.readUTF();
					String[] split = request.split(" ");
					switch(split[0]) {
					case "player":
//						Player p = gson.fromJson(response, Player.class);
						Player p = new Player().ToObject(response);
						response = p.toString();
						break;
					default: response = request;
					}
				}
				System.out.println("GET: "+ response);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}