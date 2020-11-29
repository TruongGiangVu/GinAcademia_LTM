package Socket.Response;

import java.util.ArrayList;

import Model.Player;

public class SocketResponseRank extends SocketResponse {
	private static final long serialVersionUID = 1L;
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
