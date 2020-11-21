package Socket.Response;

import java.util.ArrayList;

import Model.GameConfig;
import Model.Player;

public class SocketResponseContest extends SocketResponse {
	private static final long serialVersionUID = 1L;
	public ArrayList<Player> players;
	public ArrayList<Integer> points;
	public GameConfig config;
	public SocketResponseContest(ArrayList<Player> players, ArrayList<Integer> points, GameConfig config) {
		super(Status.SUCCESS, Action.CONTEST, "contest");
		this.players = players;
		this.points = points;
		this.config = config;
	}
}
