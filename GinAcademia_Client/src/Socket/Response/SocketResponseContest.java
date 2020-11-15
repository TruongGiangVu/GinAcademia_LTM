package Socket.Response;

import java.util.ArrayList;

import Model.Player;

@SuppressWarnings("serial")
public class SocketResponseContest extends SocketResponse {
	public ArrayList<Player> players;
	public ArrayList<Integer> points;
	public SocketResponseContest(ArrayList<Player> players, ArrayList<Integer> points) {
		super(Status.SUCCESS, Action.CONTEST, "contest");
		this.players = players;
		this.points = points;
	}
}
