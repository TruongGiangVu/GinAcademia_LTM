package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalToggleButtonUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Panel;
import java.awt.Rectangle;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Module.MyRegEx;
import Socket.Client;
import Module.ImagePanel;
import Module.MyFrame;

@SuppressWarnings("serial")
public class Login extends MyFrame {

	private JPanel contentPane;
	private ImagePanel panel;
	private JTextField txtUsername;
	private JLabel lblNewLabel;
	private JLabel label;
	private JPasswordField txtPassword;
	private JLabel lblChaCTi;
	private JLabel label_1;
	private JButton btnNewButton;
	int xx,xy;
	private JLabel errorUsername;
	private JLabel errorPassword;
	private Image bg;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Login frame = new Login(client);
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 */
	public Login(Client client) {
		super(client);
//		bus = new PlayerBUS();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
				int y = arg0.getYOnScreen();
				Login.this.setLocation(x-xx,y-xy);
			}
		});
		Image temp = new ImageIcon("./img/background.jpg").getImage();
		this.bg = temp;
		this.setTitle("GinAcademia - Đăng nhập");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 420);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new ImagePanel(client,this.bg,365,400);
	
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				Login.this.setLocation(x-xx,y-xy);
			}
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
//		panel.setBackground(new Color(31, 22, 127));
		panel.setBounds(0, 0, 365, 390);
		contentPane.add(panel);
		
		txtUsername = new JTextField();
		txtUsername.setText("user1");
		txtUsername.setBounds(391, 106, 280, 36);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		lblNewLabel = new JLabel("TÀI KHOẢN");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(391, 71, 129, 24);
		contentPane.add(lblNewLabel);
		
		label = new JLabel("MẬT KHẨU");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setForeground(Color.BLACK);
		label.setBounds(391, 153, 60, 24);
		contentPane.add(label);
		
		txtPassword = new JPasswordField();
//		txtPassword.setText("12345");
		txtPassword.setBounds(391, 186, 280, 36);
		contentPane.add(txtPassword);
		
		lblChaCTi = new JLabel("Chưa có tài khoản?");
		lblChaCTi.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblChaCTi.setForeground(Color.BLACK);
		lblChaCTi.setBounds(428, 327, 113, 14);
		contentPane.add(lblChaCTi);
		
		label_1 = new JLabel("Đăng ký ngay");
		label_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// return login
				Login.this.dispose();
				Register frame = new Register(client);
				frame.hashCode();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				label_1.setForeground(new Color(8,87,40).brighter());
			}
			@Override
			public void mouseExited(MouseEvent e) {

				label_1.setForeground(new Color(8,87,40));
			}
		});
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setForeground(new Color(8,87,40));
		label_1.setBounds(540, 327, 94, 14);
		contentPane.add(label_1);
		
		btnNewButton = new JButton("Đăng nhập");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login.this.loginPlayer();
			}
		});
		btnNewButton.setUI(new  MetalToggleButtonUI() {
			@Override
		    protected Color getSelectColor() {
		        return new Color(8, 87, 40).brighter();
		    }
		    
		    @Override
		    protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
		    	Color fg;
		    	if(btnNewButton.isSelected() || btnNewButton.getModel().isArmed()) {
		    		fg = Color.WHITE;
		    	}
		    		
		    	else {
		    		fg = Color.BLACK;
		    	}
		    	
		    	setForeground(fg);
		    	super.paintText(g, b, textRect, text);
		    }
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnNewButton.setBorder(null);
				btnNewButton.setBackground(new Color(8, 87, 40).brighter());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBackground(new Color(8, 87, 40));
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(8, 87, 40));
		btnNewButton.setBounds(428, 287, 205, 35);
		btnNewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnNewButton.setFocusable(false);
		contentPane.add(btnNewButton);
		
		errorUsername = new JLabel("");
		errorUsername.setForeground(Color.RED);
		errorUsername.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorUsername.setBounds(391, 140, 280, 14);
		contentPane.add(errorUsername);
		
		errorPassword = new JLabel("");
		errorPassword.setForeground(Color.RED);
		errorPassword.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorPassword.setBounds(391, 219, 280, 14);
		contentPane.add(errorPassword);
		
//		this.setUndecorated(true);
		setResizable(false);
		this.setVisible(true);
	}
	private boolean checkData() {
		boolean check = true;
		MyRegEx regex = new MyRegEx();
		if(!this.txtUsername.getText().matches(regex.pattern.get("username").toString())) {
			errorUsername.setText(regex.error.get("username").toString());
			check = false;
		} else errorUsername.setText("");
		if(!String.valueOf(this.txtPassword.getPassword()).matches(regex.pattern.get("password").toString())) {
			errorPassword.setText(regex.error.get("password").toString()); 
			check = false;
		} else errorPassword.setText("");
		return check;
	}
	public void loginPlayer() {
		if(this.checkData()) {
			client.connect(this.txtUsername.getText(), String.valueOf(this.txtPassword.getPassword()));
			
			if(client.isLogin == false) {
				JOptionPane.showMessageDialog(this,client.message,"Alert",JOptionPane.WARNING_MESSAGE);
			}else {
				this.dispose();
				MainFrame frame = new MainFrame(client);
				frame.hashCode(); // just for not warming
			}
		}		
	}
}
