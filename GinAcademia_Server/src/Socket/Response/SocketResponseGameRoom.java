package Socket.Response;

import java.util.ArrayList;

import Model.Player;
import Model.Question;

@SuppressWarnings("serial")
public class SocketResponseGameRoom extends SocketResponse{
	public ArrayList<Player> players;
	public ArrayList<Integer> points;
	public ArrayList<Integer> answers;
	public int rightAnswer=0;
	public Question question;
	public SocketResponseGameRoom(ArrayList<Player> players,ArrayList<Integer> points,ArrayList<Integer> answers,int rightAnswer,Question question ) {
		super(Status.SUCCESS, Action.CONTEST, "contest");
		this.players = players;
		this.points = points;
		this.answers = answers;
		this.rightAnswer = rightAnswer;
		this.question = question;
		
	}

}
