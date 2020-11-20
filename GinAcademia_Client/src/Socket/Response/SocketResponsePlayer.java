package Socket.Response;

import Model.Player;

public class SocketResponsePlayer extends SocketResponse {
	private static final long serialVersionUID = 1L;
	Player player= null;
	public SocketResponsePlayer(Player player) {
		// TODO Auto-generated constructor stub
		super(Status.SUCCESS, Action.PLAYER, "Login success");
		this.player = player;
		
	}
	public SocketResponsePlayer(Player player, SocketResponse.Action action,String message) {
		// TODO Auto-generated constructor stub
		super(Status.SUCCESS, action, message);
		this.player = player;
		
	}
	public Player getPlayer() {
		return player;
	}
}
