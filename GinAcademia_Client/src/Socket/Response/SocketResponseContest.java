package Socket.Response;

import java.util.ArrayList;

import Model.Question;

@SuppressWarnings("serial")
public class SocketResponseContest extends SocketResponse {
	ArrayList<Question> arr = new ArrayList<Question>();
	public SocketResponseContest(ArrayList<Question> arr) {
		// TODO Auto-generated constructor stub
		super(Status.SUCCESS, Action.CONTEST, "Contest question");
		this.arr = arr;
		
	}
	public ArrayList<Question> getQuestionList() {
		return arr;
	}

}
