package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import Model.Player;
import BUS.PlayerBUS;


public class ClientHandler implements Runnable {
	Socket socket;
	ArrayList<ClientHandler> clients;
	DataInputStream receiver;
	DataOutputStream sender;
	PlayerBUS bus = new PlayerBUS();
	
	public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
		// TODO Auto-generated constructor stub
		try {
			this.clients = new ArrayList<ClientHandler>();
			this.socket = socket;
			this.receiver = new DataInputStream(this.socket.getInputStream());
			this.sender = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println(socket.getInetAddress() + " " + socket.getPort() + " accept");
			String response = "";
			while(true) {
				// code
				String request = receiver.readUTF();
				System.out.println(socket.getInetAddress() + " " + socket.getPort() +": " + request);
				if(request.equals("exit")){
					System.out.println(socket.getInetAddress() + " " + socket.getPort() +": has died.");
					break;
				}
				else {
					String[] split = request.split(" ");
					switch(split[0]) {
					case "player":
						Player p = bus.getPlayerById(split[1]);
//						response = gson.toJson(p);
						response = p.toJson();
						break;
					default: response = request;
					}
				}
				this.sendResponse(response);
				
			}
			this.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void close() throws IOException {
		this.sender.close();
		this.receiver.close();
		this.socket.close();
	}
	private void sendResponse(String request) {
		try {
			sender.writeUTF(request);
			sender.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
