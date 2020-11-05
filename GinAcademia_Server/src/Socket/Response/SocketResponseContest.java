package Socket.Response;

import java.util.ArrayList;

import Model.Question;

public class SocketResponseContest extends SocketResponse {
	ArrayList<Question> arr;
	public SocketResponseContest(ArrayList<Question> arr) {
		// TODO Auto-generated constructor stub
		super(Status.SUCCESS, Action.CONTEST, "Contest question");
		this.arr= arr;
		
	}
	public ArrayList<Question> getQuestionList() {
		return arr;
	}

}
