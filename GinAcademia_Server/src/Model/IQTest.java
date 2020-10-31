package Model;

import java.util.ArrayList;

public class IQTest {
	private String IQTestId = "";
	private String ImageQuestion = "";
	private ArrayList<Option> Options = new ArrayList<Option>();
	private int Answer = 0;
	private int status = 0;
	public IQTest() {
		// TO DO Auto-generated constructor stub
	}
	public IQTest(String iQTestId, String imageQuestion, ArrayList<Option> options, int answer, int status) {
		super();
		IQTestId = iQTestId;
		ImageQuestion = imageQuestion;
		Options = options;
		Answer = answer;
		this.status = status;
	}
	public String getId() {
		return IQTestId;
	}
	public void setId(String iQTestId) {
		IQTestId = iQTestId;
	}
	public String getImageQuestion() {
		return ImageQuestion;
	}
	public void setImageQuestion(String imageQuestion) {
		ImageQuestion = imageQuestion;
	}
	public ArrayList<Option> getOptions() {
		return Options;
	}
	public void setOptions(ArrayList<Option> options) {
		Options = options;
	}
	public int getAnswer() {
		return Answer;
	}
	public void setAnswer(int answer) {
		Answer = answer;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "IQTest [IQTestId=" + IQTestId + ", ImageQuestion=" + ImageQuestion + ", Options=" + Options
				+ ", Answer=" + Answer + ", status=" + status + "]";
	}
	
	
	

}
