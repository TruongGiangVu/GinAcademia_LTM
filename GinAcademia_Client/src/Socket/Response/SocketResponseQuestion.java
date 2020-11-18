package Socket.Response;

import Model.Question;

public class SocketResponseQuestion extends SocketResponse{
	private static final long serialVersionUID = 1L;
	Question question;
	public SocketResponseQuestion(Question question) {
		super(Status.SUCCESS, Action.CONTEST, "question");
		this.question = question;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
}
