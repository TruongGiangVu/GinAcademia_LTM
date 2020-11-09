package Model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
public class Option implements Serializable {
	@SerializedName("OptionId")
	public int OptionId = 0;
	@SerializedName("Option")
	public String Option ="";
	public Option() {
	}
	public Option(int optionId, String option) {
		super();
		OptionId = optionId;
		Option = option;
	}
	public Option(Option op) {
		super();
		OptionId = op.OptionId;
		Option = op.Option;
	}
	@Override
	public String toString() {
		return "Option [OptionId=" + OptionId + ", Option=" + Option + "]";
	}
	
}