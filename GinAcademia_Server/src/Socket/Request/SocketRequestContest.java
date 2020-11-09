package Socket.Request;

import Model.Player;

@SuppressWarnings("serial")
public class SocketRequestContest extends SocketRequest {
	public Player player;
	public int ans = 0;
	public int time = 10000;
	public SocketRequestContest(Player player, int ans, int time ) {
		 super(SocketRequest.Action.CONTEST, "answer");
		 this.player = player;
		 this.ans = ans;
		 this.time = time;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public int getAns() {
		return ans;
	}
	public void setAns(int ans) {
		this.ans = ans;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	

}