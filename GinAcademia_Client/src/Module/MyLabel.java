package Module;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MyLabel extends JLabel{
	public int theme = -1;
	public MyLabel() {
		super();
		this.setBorder(new EmptyBorder(10,10,10,10));
		
		this.setBackground(Color.WHITE);
		this.setOpaque(true);

		this.setForeground(Color.BLACK);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		UIManager.put("Label.disabledForeground",Color.WHITE);

//		this.setUI(new );
	}
	public MyLabel(String text) {
		super(text);
	}
	

}
