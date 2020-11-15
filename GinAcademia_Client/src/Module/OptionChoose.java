package Module;

import java.awt.Color;
// color for game when player choose options

public class OptionChoose {
	private Color WrongChoose = new Color(210, 70, 69);
	private Color RightChoose = new Color(143, 203, 43);
	private Color NotChoose = new Color(93, 98, 189);
	private Color EnemyChoose = new Color(210, 110, 18);

	public OptionChoose() {
	}

	public Color getColor(String status) {
		Color ans = null;
		status = this.cleanString(status);
		if (status.equals("wrong")) {
			ans = this.WrongChoose;
		} else if (status.equals("right")) {
			ans = this.RightChoose;
		} else if (status.equals("not")) {
			ans = this.NotChoose;
		} else if (status.equals("enemy")) {
			ans = this.EnemyChoose;
		} else {
			ans = new Color(0, 0, 0);
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
