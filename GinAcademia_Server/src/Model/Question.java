package Model;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
public class Question implements Serializable{
	@SerializedName("QuestionId")
	private String QuestionId = "";
	@SerializedName("Question")
	private String Question = "";
	@SerializedName("Options")
	private ArrayList<Option> Options = new ArrayList<Option>();
	@SerializedName("Answer")
	private int Answer = 0;
	@SerializedName("Status")
	private int Status = 0;
	
	private static Gson gson = new Gson();
			
	public Question() {
		// TO DO Auto-generated constructor stub
	}
	public Question(String questionId, String question, ArrayList<Option> options, int answer) {
		super();
		QuestionId = questionId;
		Question = question;
		Answer = answer;
		Options = options;
	}

	public Question(String questionId, String question, ArrayList<Option> options, int answer, int status) {
		this(questionId,  question, options,  answer);
		Status = status;
	}
	public Question(Question p) {
		this.copy(p);
	}
	private void copy(Question p) {
		QuestionId = p.QuestionId;
		Question = p.Question;
		Answer = p.Answer;
		Options = p.Options;
		Status = p.Status;
	}
	public String getId() {
		return QuestionId;
	}
	public void setId(String questionId) {
		QuestionId = questionId;
	}
	public String getQuestion() {
		return Question;
	}
	public void setQuestion(String question) {
		Question = question;
	}
	public int getAnswer() {
		return Answer;
	}
	public String getAnswerString() {
		String ap = "";
		switch(Answer) {
			case 1: ap ="A"; break;
			case 2: ap ="B"; break;
			case 3: ap ="C"; break;
			case 4: ap ="D"; break;
			default: ap =""; break;
		}
		return ap;
	}
	public void setAnswer(int answer) {
		Answer = answer;
	}
	public ArrayList<Option> getOptions() {
		return Options;
	}
	public void setOptions(ArrayList<Option> options) {
		Options = options;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	@Override
	public String toString() {
		return "Question [QuestionId=" + QuestionId + ", Question=" + Question + ", Options=" + Options + ", Answer="
				+ Answer + ", Status=" + Status + "]";
	}
	public String toJson() {
		return gson.toJson(this);
	}
	public Question ToObject(String json) {
		Question p = gson.fromJson(json, Question.class);
		copy(p);
		return p;
	}
	
	
		
	
}

