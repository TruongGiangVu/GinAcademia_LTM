package Module;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
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
		this.setHorizontalAlignment(SwingConstants.LEFT);
//		this.setContentAreaFilled(false);
		
		this.setUI(new MetalToggleButtonUI() {
		    @Override
		    protected Color getSelectColor() {
		        return new Color(8, 87, 40).brighter();
		    }
		    
		    @Override
		    protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
		    	Color fg;
		    	if(isSelected() || getModel().isArmed()) {
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
		
	}
//	@Override
//    public void paintComponent(Graphics g)
//    {
//        
//        Color fg;
//        Color bg;
//        if (isSelected()){
//            bg = new Color(8, 87, 40,200);
//            fg = Color.WHITE;
//        } else {
//        	bg = Color.white;
//        	fg = Color.BLACK;
//        	
//        }
//        
//        setForeground(fg);
//        setBackground(bg);
//        super.paintComponent(g);
//    }
	
}
