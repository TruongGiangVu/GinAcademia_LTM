package Module;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JToggleButton;
import javax.swing.plaf.metal.MetalToggleButtonUI;

@SuppressWarnings("serial")
public class MenuButton extends JToggleButton{
	
	public MenuButton() {
		this.init();
	}
	public MenuButton(String text) {
		this.setText(text);
		this.init();
	}
	protected void init() {
//		this.setContentAreaFilled(false);
		
		this.setUI(new MetalToggleButtonUI() {
		    @Override
		    protected Color getSelectColor() {
		        return new Color(8, 87, 40).brighter();
		    }
		    
		    @Override
		    protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
		    	Color fg;
		    	if(isSelected() || getModel().isArmed() || getModel().isRollover()) {
		    		fg = Color.WHITE;
		    	}
		    		
		    	else {
		    		fg = Color.BLACK;
		    	}
		    	
		    	setForeground(fg);
		    	super.paintText(g, b, textRect, text);
		    }
		});
//		this.setForeground(Color.black);
		this.setFont(new Font("SansSerif", Font.BOLD, 13));
		this.setBackground(Color.white);
		this.setBorder(null);
		this.setFocusable(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
//		this.setVerticalAlignment();
		
	}
	
}
