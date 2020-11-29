package Socket.Response;

import java.util.ArrayList;

import Model.Player;

public class SocketResponseAnswer extends SocketResponse {
	private static final long serialVersionUID = 1L;
	public ArrayList<Player> players;
	public ArrayList<Integer> answers;
	public int rightAnswer=0;
	public SocketResponseAnswer (ArrayList<Player> players, ArrayList<Integer> answers,int right) {
		super(Status.SUCCESS, Action.CONTEST, "contest");
		this.players = players;
		this.answers = answers;
		this.rightAnswer = right;
	}
}
