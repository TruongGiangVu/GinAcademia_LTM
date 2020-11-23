package Model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
public class OptionIQ implements Serializable {
	@SerializedName("OptionId")
	public String OptionId = "";
	@SerializedName("Option")
	public String Option ="";
	public OptionIQ() {
	}
	
	public OptionIQ(String optionId, String option) {
		super();
		OptionId = optionId;
		Option = option;
	}

	public String getOptionId() {
		return OptionId;
	}
	public void setOptionId(String optionId) {
		OptionId = optionId;
	}
	public String getOption() {
		return Option;
	}
	public void setOption(String option) {
		Option = option;
	}
	@Override
	public String toString() {
		return "OptionIQ [OptionId=" + OptionId + ", Option=" + Option + "]";
	}
	
	
}