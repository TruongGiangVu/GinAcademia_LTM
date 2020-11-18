package Socket.Response;

import java.util.ArrayList;

import Model.Question;

public class SocketResponseContestTest extends SocketResponse {
	private static final long serialVersionUID = 1L;
	ArrayList<Question> arr = new ArrayList<Question>();
	public SocketResponseContestTest(ArrayList<Question> arr) {
		// TODO Auto-generated constructor stub
		super(Status.SUCCESS, Action.CONTEST, "Contest question");
		this.arr = arr;
		
	}
	public ArrayList<Question> getQuestionList() {
		return arr;
	}

}
