package Module;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

@SuppressWarnings("serial")
public class MyLabel extends JLabel{
	public int theme = -1;
	public MyLabel() {
		super();
		setFont(new Font("SansSerif", Font.BOLD, 14));
		this.setBorder(new EmptyBorder(10,10,10,10));
		
		this.setBackground(Color.WHITE);
		this.setOpaque(true);

		this.setForeground(Color.BLACK);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		UIManager.put("Label[Disabled].textForeground",Color.WHITE);
		this.repaint();
//		this.setUI(new );
	}
	public MyLabel(String text) {
		super(text);
	}
	

}
