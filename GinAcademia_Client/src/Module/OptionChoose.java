package Module;

import java.awt.Color;
// color for game when player choose options
public class OptionChoose {
	private Color WrongChoose = new Color(210, 70, 69);
	private Color RightChoose = new Color(143, 203, 43);
	private Color NotChoose = new Color(93, 98, 189);
	public OptionChoose() {
		// TO DO Auto-generated constructor stub
	}
	public Color getColor(String status) {
		Color ans = null;
		status = this.cleanString(status);
		switch(status) {
			case "wrong": ans = this.WrongChoose; 
			case "right": ans = this.RightChoose; 
			case "not": ans = this.NotChoose; 
			default: ans = new Color(0,0,0);
		}
		return ans;
	}
	private String cleanString(String str) {
		str = str.toLowerCase();
		str = str.trim();
		str = str.replaceAll("//s+", " ");
		return str;
	}
}
