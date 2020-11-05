package Socket.Request;

import Model.Player;
public class SocketRequestPlayer extends SocketRequest {
	public Player player;
	public SocketRequestPlayer(SocketRequest.Action action,Player player) {
		// TODO Auto-generated constructor stub
		 super(action, "Update profile");
		 this.player = player;
	}

}