package GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import Module.ImagePanel;
import Module.MenuButton;

public class Home extends ImagePanel implements  ActionListener{
	private JButton btnStart;
	private ImageIcon img;

	/**
	 * Create the panel.
	 */
	public boolean checked = false;
	public Home() {
		Image temp = new ImageIcon("./img/background.jpg").getImage().getScaledInstance(600, 600, java.awt.Image.SCALE_AREA_AVERAGING);
		this.img = new ImageIcon(temp);
		init();
	}
	public void init() {
		this.setSize(600,600);
//		setBackground(null);
	
		btnStart = new JButton("START");
		btnStart.setOpaque(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setBorderPainted(false);
		btnStart.addActionListener(this);
		btnStart.setForeground(Color.WHITE);
		btnStart.setFont(new Font("AdemoW00-Gray", Font.PLAIN, 49));
		btnStart.setBackground(null);
		btnStart.setBorder(null);
		btnStart.setFocusable(false);
		btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));;
		btnStart.setBounds(205, 330, 200, 60);
		add(btnStart);
	}
	public Home(ImageIcon img) {
//		super("./img/background.jpg");
		super(img);
		this.img = img;
		init();
	
//		setLayout(null);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
//		MainFrame parent = (MainFrame)Home.this.getParent().getParent().getParent().getParent().getParent();
		MainFrame parent = (MainFrame)SwingUtilities.getWindowAncestor(this);
		parent.clickStart();
		
	}
	
}
