package Socket.Request;

import java.util.ArrayList;

import Model.Player;

public class SocketRequestRank extends SocketRequest {
	ArrayList<Player> listPlayer;
	public SocketRequestRank(ArrayList<Player> listPlayer) {
		 super(SocketRequest.Action.RANK, "Rank request.");
		// TODO Auto-generated constructor stub
		 this.listPlayer = listPlayer;
	}
}
