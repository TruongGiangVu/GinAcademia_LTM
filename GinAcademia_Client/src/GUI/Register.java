package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.util.ArrayList;
import java.util.Properties;

import org.jdatepicker.impl.*;

import Module.DateLabelFormatter;
import Module.MyRegEx;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Server.BUS.PlayerBUS;
import Model.Player;

public class Register extends JFrame {

	private JPanel contentPane;
	private Panel panel;
	private JTextField txtName;
	private JLabel lblNewLabel;
	private JLabel lblTnTiKhon;
	private JPasswordField txtPassword;
	private JButton btnNewButton;
	int xx,xy;
	private JTextField txtUsername;
	private JLabel lblMtKhu;
	private JLabel lblNhpLiMt;
	private JPasswordField txtRePassword;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblNgySinh;
	private JLabel lblGiiTnh;
	private JComboBox txtGender;
	private JDatePickerImpl datePicker;
	private JLabel lblCTi;
	private JLabel lblngNhpNgay;
	private ArrayList<JTextField> arrTF = new ArrayList<JTextField>();
	private ArrayList<JLabel> arrError = new ArrayList<JLabel>();
	private JLabel errorName;
	private JLabel errorUsername;
	private JLabel errorPassword;
	private JLabel errorRePassword;
	private JLabel errorEmail;
	private JLabel errorBirthdate;
	
	private PlayerBUS bus = new PlayerBUS();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Register() {
		

		
		setBackground(new Color(31, 22, 127));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 10, 730, 715);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
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
				Register.this.setLocation(x-xx,y-xy);
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
		panel.setBounds(-15, -47, 350, 818);
		contentPane.add(panel);
		
		txtName = new JTextField();
		txtName.setText("Vu Tr");
		txtName.setToolTipText("name");
		txtName.setBounds(391, 60, 280, 35);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		lblNewLabel = new JLabel("HỌ VÀ TÊN");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(391, 25, 159, 25);
		contentPane.add(lblNewLabel);
		
		lblTnTiKhon = new JLabel("TÊN TÀI KHOẢN");
		lblTnTiKhon.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTnTiKhon.setForeground(Color.BLACK);
		lblTnTiKhon.setBounds(391, 105, 159, 25);
		contentPane.add(lblTnTiKhon);
		
		txtPassword = new JPasswordField("12345");
		txtPassword.setToolTipText("password");
		txtPassword.setBounds(391, 220, 280, 35);
		contentPane.add(txtPassword);
		
		btnNewButton = new JButton("Đăng ký");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Register.this.registerPlayer();
			}
		});
		btnNewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
		btnNewButton.setBounds(450, 605, 205, 35);
		btnNewButton.setFocusable(false);
		contentPane.add(btnNewButton);
		
		txtUsername = new JTextField();
		txtUsername.setText("user3");
		txtUsername.setToolTipText("username");
		txtUsername.setColumns(10);
		txtUsername.setBounds(391, 140, 280, 35);
		contentPane.add(txtUsername);
		
		lblMtKhu = new JLabel("MẬT KHẨU");
		lblMtKhu.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMtKhu.setForeground(Color.BLACK);
		lblMtKhu.setBounds(391, 185, 159, 25);
		contentPane.add(lblMtKhu);
		
		lblNhpLiMt = new JLabel("NHẬP LẠI MẬT KHẨU");
		lblNhpLiMt.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNhpLiMt.setForeground(Color.BLACK);
		lblNhpLiMt.setBounds(391, 265, 181, 25);
		contentPane.add(lblNhpLiMt);
		
		txtRePassword = new JPasswordField("123456");
		txtRePassword.setToolTipText("password");
		txtRePassword.setBounds(391, 300, 280, 35);
		contentPane.add(txtRePassword);
		
		lblEmail = new JLabel("EMAIL");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setForeground(Color.BLACK);
		lblEmail.setBounds(391, 345, 159, 25);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setText("abc@gmail.com");
		txtEmail.setToolTipText("email");
		txtEmail.setColumns(10);
		txtEmail.setBounds(391, 380, 280, 35);
		contentPane.add(txtEmail);
		
		lblNgySinh = new JLabel("NGÀY SINH");
		lblNgySinh.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNgySinh.setForeground(Color.BLACK);
		lblNgySinh.setBounds(391, 425, 159, 25);
		contentPane.add(lblNgySinh);
		
		UtilDateModel model = new UtilDateModel();
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		datePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
		datePicker.setSize(202, 23);
		datePicker.setLocation(391, 460);
		contentPane.add(datePicker);
		lblGiiTnh = new JLabel("GIỚI TÍNH");
		lblGiiTnh.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGiiTnh.setForeground(Color.BLACK);
		lblGiiTnh.setBounds(391, 505, 103, 25);
		contentPane.add(lblGiiTnh);
		
		txtGender = new JComboBox();
		txtGender.setModel(new DefaultComboBoxModel(new String[] {"Nam", "Nữ"}));
		txtGender.setBounds(391, 540, 72, 35);
		contentPane.add(txtGender);
		
		lblCTi = new JLabel("Đã có tài khoản?");
		lblCTi.setForeground(Color.BLACK);
		lblCTi.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCTi.setBounds(450, 645, 103, 25);
		contentPane.add(lblCTi);
		
		lblngNhpNgay = new JLabel("Đăng nhập ngay");
		lblngNhpNgay.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		lblngNhpNgay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// return login
				Register.this.dispose();
				Login frame = new Login();
				frame.setVisible(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblngNhpNgay.setForeground(new Color(0, 51, 204));
			}
			@Override
			public void mouseExited(MouseEvent e) {

				lblngNhpNgay.setForeground(Color.BLACK);
			}
		});
		lblngNhpNgay.setForeground(Color.BLACK);
		lblngNhpNgay.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblngNhpNgay.setBounds(548, 645, 159, 25);
		contentPane.add(lblngNhpNgay);
		
		errorName = new JLabel("");
		errorName.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorName.setForeground(Color.RED);
		errorName.setBounds(391, 95, 280, 14);
		contentPane.add(errorName);
		
		errorUsername = new JLabel("");
		errorUsername.setForeground(Color.RED);
		errorUsername.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorUsername.setBounds(391, 175, 280, 14);
		contentPane.add(errorUsername);
		
		errorPassword = new JLabel("");
		errorPassword.setForeground(Color.RED);
		errorPassword.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorPassword.setBounds(391, 255, 280, 14);
		contentPane.add(errorPassword);
		
		errorRePassword = new JLabel("");
		errorRePassword.setForeground(Color.RED);
		errorRePassword.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorRePassword.setBounds(391, 335, 280, 14);
		contentPane.add(errorRePassword);
		
		errorEmail = new JLabel("");
		errorEmail.setForeground(Color.RED);
		errorEmail.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorEmail.setBounds(391, 415, 280, 14);
		contentPane.add(errorEmail);
		
		errorBirthdate = new JLabel("");
		errorBirthdate.setForeground(Color.RED);
		errorBirthdate.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorBirthdate.setBounds(391, 483, 280, 14);
		contentPane.add(errorBirthdate);
		
		arrTF.add(txtName);
		arrTF.add(txtUsername);
		arrTF.add(txtPassword);
		arrTF.add(txtRePassword);
		arrTF.add(txtEmail);
		
		arrError.add(errorName);
		arrError.add(errorUsername);
		arrError.add(errorPassword);
		arrError.add(errorRePassword);
		arrError.add(errorEmail);
		
//		this.setUndecorated(true);
		setResizable(false);
		this.setVisible(true);
		
	}
	private Player getData() {
		String name = this.txtName.getText();
		String user = this.txtUsername.getText();
		String pass = this.txtPassword.getText();
		String email = this.txtEmail.getText();
		String birthdate = this.datePicker.getJFormattedTextField().getText();
		boolean gender = this.txtGender.getSelectedItem().toString().equals("Nam") ? true: false ;
		Player p = new Player(name, user, pass, email, birthdate, gender);
		return p;
	}
	private boolean checkData() {
		boolean check = true;
		MyRegEx regex = new MyRegEx();
		int num = 0;
		for(JTextField a : arrTF) {
			String theme = a.getToolTipText();
			if(!a.getText().trim().matches(regex.pattern.get(theme).toString())) {
				this.arrError.get(num).setText(regex.error.get(theme).toString());
				check = false;
			}
			else this.arrError.get(num).setText("");
			num++;
		}
		if(!this.txtPassword.getText().trim().equals(txtRePassword.getText().trim())) {
			this.errorRePassword.setText("Mật khẩu nhập lại phải khớp với mật khẩu trên.");
			check = false;
		}
		String birthdate = this.datePicker.getJFormattedTextField().getText();
		if(birthdate.trim().equals("")) {
			errorBirthdate.setText("Không để ngày sinh rỗng.");
			check = false;
		}
		else errorBirthdate.setText("");
		return check;
	}
	public void registerPlayer() {
		// socket
		if(this.checkData()) {
			Player p = getData();
			if(bus.checkExistPlayer(p) == true) {
				JOptionPane.showMessageDialog(this,"Username này đã tồn tại, Xin hãy đổi tên khác!","Alert",JOptionPane.WARNING_MESSAGE);
			}		
			else {
				JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công.");
				bus.insert(p);
				this.dispose();
				MainFrame frame = new MainFrame(p);
				frame.setVisible(true);
			}
		}
	}
}
