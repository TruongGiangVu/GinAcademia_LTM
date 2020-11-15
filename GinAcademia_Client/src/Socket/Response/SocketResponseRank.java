package Socket.Response;

import java.util.ArrayList;

import Model.Player;

@SuppressWarnings("serial")
public class SocketResponseRank extends SocketResponse {
	ArrayList<Player> arr;
	public SocketResponseRank(ArrayList<Player> arr) {
		// TODO Auto-generated constructor stub
		super(Status.SUCCESS, Action.MESSAGE, "List Player");
		this.arr = arr;
		
	}
	public ArrayList<Player> getList() {
		return arr;
	}
}
