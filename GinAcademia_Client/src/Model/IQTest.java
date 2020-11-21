package Model;

import java.util.ArrayList;

public class IQTest {
	private String IQTestId = "";
	private String IQQuestion = "";
	private String ImageQuestion = "";
	private ArrayList<OptionIQ> Options = new ArrayList<OptionIQ>();
	private String Answer = "";
	private boolean isImage = false;

	public IQTest() {
	}

	public IQTest(String iQTestId, String iQQuestion, String imageQuestion, ArrayList<OptionIQ> options, String answer) {
		IQTestId = iQTestId;
		IQQuestion = iQQuestion;
		ImageQuestion = imageQuestion;
		Options = options;
		Answer = answer;
		if(!ImageQuestion.trim().equals("")) 
			isImage = true;
	}

	public String getIQTestId() {
		return IQTestId;
	}

	public void setIQTestId(String iQTestId) {
		IQTestId = iQTestId;
	}

	public String getIQQuestion() {
		return IQQuestion;
	}

	public void setIQQuestion(String iQQuestion) {
		IQQuestion = iQQuestion;
	}

	public String getImageQuestion() {
		return ImageQuestion;
	}

	public void setImageQuestion(String imageQuestion) {
		ImageQuestion = imageQuestion;
	}

	public ArrayList<OptionIQ> getOptions() {
		return Options;
	}

	public void setOptions(ArrayList<OptionIQ> options) {
		Options = options;
	}

	public String getAnswer() {
		return Answer;
	}

	public void setAnswer(String answer) {
		Answer = answer;
	}
	
	public boolean isImageQuestion() {
		return this.isImage;
	}

	@Override
	public String toString() {
		return "IQTest [IQTestId=" + IQTestId + ", IQQuestion=" + IQQuestion + ", ImageQuestion=" + ImageQuestion
				+ ", Options=" + Options + ", Answer=" + Answer + "]";
	}
	

}
