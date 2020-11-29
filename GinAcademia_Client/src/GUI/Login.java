package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import Module.MyRegEx;
import Socket.Client;
import Module.ImagePanel;
import java.awt.event.KeyAdapter;


@SuppressWarnings("serial")
public class Login extends JFrame {
	private Client client;
	private JPanel contentPane;
	private ImagePanel panelLamMau;
	private JTextField txtUsername;
	private JLabel lblNewLabel;
	private JLabel label;
	private JPasswordField txtPassword;
	private JLabel lblChaCTi;
	public static JLabel lblRegister;
	public static JButton btnLogin;
	int xx, xy;
	private JLabel errorUsername;
	private JLabel errorPassword;
	private Image bg;

	public Login() {
		this.init("localhost", 5000);
	}
	
	public Login(String host, int port) {
		this.init(host, port);
	}
	
	private void init(String host, int port) {
		System.out.println("run...");

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
				Login.this.setLocation(x - xx, y - xy);
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

		panelLamMau = new ImagePanel(client, this.bg, 365, 400);

		panelLamMau.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				Login.this.setLocation(x - xx, y - xy);
			}
		});
		panelLamMau.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});

		panelLamMau.setBounds(0, 0, 365, 390);
		contentPane.add(panelLamMau);

		txtUsername = new JTextField();
		txtUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loginPlayer();
			}
		});
		txtUsername.setText("user1@gmail.com");
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
		txtPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loginPlayer();
			}
		});
		txtPassword.setText("12345");
		txtPassword.setBounds(391, 186, 280, 36);
		contentPane.add(txtPassword);

		lblChaCTi = new JLabel("Chưa có tài khoản?");
		lblChaCTi.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblChaCTi.setForeground(Color.BLACK);
		lblChaCTi.setBounds(427, 305, 113, 14);
		contentPane.add(lblChaCTi);

		lblRegister = new JLabel("Đăng ký ngay");
		lblRegister.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRegister.setForeground(new Color(8, 87, 40));
		lblRegister.setBounds(535, 305, 94, 14);
		contentPane.add(lblRegister);

		btnLogin = new JButton("Đang tạo kết nối");
		btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				 if (e.getKeyCode()==KeyEvent.VK_ENTER){
			            loginPlayer();
			        }
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loginPlayer();
			}
		});
		btnLogin.setUI(new MetalToggleButtonUI() {
			@Override
			protected Color getSelectColor() {
				return new Color(8, 87, 40).brighter();
			}

			@Override
			protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
				Color fg;
				if (btnLogin.isSelected() || btnLogin.getModel().isArmed()) {
					fg = Color.WHITE;
				}

				else {
					fg = Color.BLACK;
				}

				setForeground(fg);
				super.paintText(g, b, textRect, text);
			}
		});
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnLogin.setBorder(null);
				btnLogin.setBackground(new Color(8, 87, 40).brighter());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnLogin.setBackground(new Color(8, 87, 40));
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(8, 87, 40));
		btnLogin.setBounds(391, 265, 280, 35);
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogin.setFocusable(false);
		btnLogin.setEnabled(false);
		contentPane.add(btnLogin);

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
		lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

		client = new Client(host, port);
		lblRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(client.getSocket() != null) {
					Login.this.dispose();
					Register frame = new Register(client);
					frame.setVisible(true);
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblRegister.setForeground(new Color(8, 87, 40).brighter());
			}

			@Override
			public void mouseExited(MouseEvent e) {

				lblRegister.setForeground(new Color(8, 87, 40));
			}
		});
	}

	private boolean checkData() {
		boolean check = true;
		MyRegEx regex = new MyRegEx();
		if (!this.txtUsername.getText().matches(regex.pattern.get("username").toString())) {
			errorUsername.setText(regex.error.get("username").toString());
			check = false;
		} else
			errorUsername.setText("");
		if (!String.valueOf(this.txtPassword.getPassword()).matches(regex.pattern.get("password").toString())) {
			errorPassword.setText(regex.error.get("password").toString());
			check = false;
		} else
			errorPassword.setText("");
		return check;
	}

	public void loginPlayer() {
		MainFrame frame;
		if (this.client.getSocket() == null) {
			JOptionPane.showMessageDialog(this, "Khổng thể kết nối tới máy chủ");
			return;
		}
		if (this.checkData()) {

			client.connect(this.txtUsername.getText(), String.valueOf(this.txtPassword.getPassword()));

			if (client.isLogin == false) {
				JOptionPane.showMessageDialog(this, client.message, "Alert", JOptionPane.WARNING_MESSAGE);
			} else {
				this.dispose();
				frame = new MainFrame(client);
				frame.setVisible(true);
			}
		}
	}
}
