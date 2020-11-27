package Socket.Request;

import Model.Player;

public class SocketRequestPlayer extends SocketRequest {
	private static final long serialVersionUID = 1L;
	public Player player;
	public SocketRequestPlayer(SocketRequest.Action action,Player player) {
		// TODO Auto-generated constructor stub
		 super(action, "Update profile");
		 this.player = player;
	}
	public SocketRequestPlayer(SocketRequest.Action action,String message,Player player) {
		// TODO Auto-generated constructor stub
		 super(action, message);
		 this.player = player;
	}

}