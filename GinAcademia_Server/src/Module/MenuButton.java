package Module;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.plaf.metal.MetalToggleButtonUI;

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
		});
//		this.setForeground(Color.black);
		this.setFont(new Font("SansSerif", Font.BOLD, 13));
		this.setBackground(Color.white);
		this.setBorder(null);
		this.setFocusable(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	}
	@Override
    public void paintComponent(Graphics g)
    {
        
        Color fg;
        if (isSelected()){
            
            fg = Color.WHITE;
        } else {
            fg = Color.BLACK;
        }
        setForeground(fg);
        super.paintComponent(g);
    }
	
}
