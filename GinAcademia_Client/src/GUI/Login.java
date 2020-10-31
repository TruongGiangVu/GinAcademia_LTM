package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Module.MyRegEx;
import Model.Player;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Panel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Server.BUS.PlayerBUS;

public class Login extends JFrame {

	private JPanel contentPane;
	private Panel panel;
	private JTextField txtUsername;
	private JLabel lblNewLabel;
	private JLabel label;
	private JPasswordField txtPassword;
	private JLabel lblChaCTi;
	private JLabel label_1;
	private JButton btnNewButton;
	private PlayerBUS bus;
	int xx,xy;
	private JLabel errorUsername;
	private JLabel errorPassword;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		bus = new PlayerBUS();
		
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
		
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 420);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new Panel();
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
		panel.setBackground(new Color(31, 22, 127));
		panel.setBounds(-15, 0, 350, 453);
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
		txtPassword.setText("12345");
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
				Register frame = new Register();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				label_1.setForeground(new Color(0, 51, 204));
			}
			@Override
			public void mouseExited(MouseEvent e) {

				label_1.setForeground(Color.BLACK);
			}
		});
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setForeground(Color.BLACK);
		label_1.setBounds(540, 327, 94, 14);
		contentPane.add(label_1);
		
		btnNewButton = new JButton("Đăng nhập");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login.this.loginPlayer();
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnNewButton.setBorder(null);
				btnNewButton.setBackground(new Color(1, 143, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBackground(new Color(0, 51, 204));
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 51, 204));
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
		if(!this.txtPassword.getText().matches(regex.pattern.get("password").toString())) {
			errorPassword.setText(regex.error.get("password").toString()); 
			check = false;
		} else errorPassword.setText("");
		return check;
	}
	public void loginPlayer() {
		// socket
		if(this.checkData()) {
			Player p = bus.loginCheckPlayer(this.txtUsername.getText(), this.txtPassword.getText());
			MainFrame frame;
			if(p == null)
				JOptionPane.showMessageDialog(this,"Tài khoản hoặc Mật khẩu không đúng!","Alert",JOptionPane.WARNING_MESSAGE);
			else 
				frame = new MainFrame(p);
		}		
	}
}
