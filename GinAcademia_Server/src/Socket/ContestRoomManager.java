package Socket;

import java.util.ArrayList;
import Model.GameConfig;

public class ContestRoomManager {
	public ArrayList<ContestRoom> rooms;
	public static int roomId = 0;
	public GameConfig  config;

	public ContestRoomManager(GameConfig config) {
		rooms = new ArrayList<ContestRoom>();
		this.config = config;
	}
	
	public ContestRoom getRoom(int roomId) {
		ContestRoom room = null;
		int n = rooms.size();
		for (int i = 0; i < n; ++i) {
			if(rooms.get(i).RoomId == roomId) {
				room = rooms.get(i);
				break;
			}
		}
		return room;
	}

	public ContestRoom createRoom() {
		return this.createRoom(this.config);
	}

	public ContestRoom createRoom(GameConfig config) {
		ContestRoom room = new ContestRoom(roomId++, config);
		rooms.add(room);
		return room;
	}

	public void finishRoom(int roomId) {
		int n = rooms.size();
		for (int i = 0; i < n; ++i) {
			if (rooms.get(i).RoomId == roomId) {
				rooms.remove(i);
				break;
			}
		}
		System.out.print("RoomId kill: "+ roomId );
		System.out.print("Num Room: "+ rooms.size() );
	}

	public ContestRoom findingAvailableRoom() {
		ContestRoom room = null;
		int n = rooms.size();
		if(n == 0) {
			room = this.createRoom();
		}
		else {
			for (int i = 0; i < n; ++i) {
				if(rooms.get(i).isRoomAvailable()) {
					room = rooms.get(i);
					break;
				}
			}
			if(room == null) {
				room = this.createRoom();
			}
		}
		return room;
	}

}
