package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.util.ArrayList;
import java.util.Properties;

import org.jdatepicker.impl.*;

import Module.DateLabelFormatter;
import Module.MyRegEx;
import Module.MyFrame;
import Model.Player;
import Socket.Client;
import Socket.Request.SocketRequest;
import Socket.Request.SocketRequestPlayer;
import Socket.Response.SocketResponse;

@SuppressWarnings("serial")
public class Register extends MyFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JLabel lblNewLabel;
	private JPasswordField txtPassword;
	private JButton btnNewButton;
	int xx, xy;
	private JLabel lblMtKhu;
	private JLabel lblNhpLiMt;
	private JPasswordField txtRePassword;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblNgySinh;
	private JLabel lblGiiTnh;
	private JComboBox<String> txtGender;
	private JDatePickerImpl datePicker;
	private JLabel lblCTi;
	private JLabel lblngNhpNgay;
	private ArrayList<JTextField> arrTF = new ArrayList<JTextField>();
	private ArrayList<JLabel> arrError = new ArrayList<JLabel>();
	private JLabel errorName;
	private JLabel errorPassword;
	private JLabel errorRePassword;
	private JLabel errorEmail;
	private JLabel errorBirthdate;

	public Register(Client client) {
		super(client);

		setBackground(new Color(31, 22, 127));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(450, 20, 350, 610);
		this.setTitle("GinAcademia - Đăng ký");
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtName = new JTextField();
		txtName.setText("Vu Tr");
		txtName.setToolTipText("name");
		txtName.setBounds(30, 60, 280, 35);
		contentPane.add(txtName);
		txtName.setColumns(10);

		lblNewLabel = new JLabel("HỌ VÀ TÊN");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(30, 25, 159, 25);
		contentPane.add(lblNewLabel);

		txtPassword = new JPasswordField("12345");
		txtPassword.setToolTipText("password");
		txtPassword.setBounds(30, 330, 280, 35);
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
				btnNewButton.setBackground(new Color(3, 87, 40).brighter());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBackground(new Color(8, 87, 40));
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(8, 87, 40));
		btnNewButton.setBounds(30, 495, 280, 35);
		btnNewButton.setFocusable(false);
		contentPane.add(btnNewButton);

		lblMtKhu = new JLabel("MẬT KHẨU");
		lblMtKhu.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMtKhu.setForeground(Color.BLACK);
		lblMtKhu.setBounds(30, 295, 159, 25);
		contentPane.add(lblMtKhu);

		lblNhpLiMt = new JLabel("NHẬP LẠI MẬT KHẨU");
		lblNhpLiMt.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNhpLiMt.setForeground(Color.BLACK);
		lblNhpLiMt.setBounds(30, 390, 181, 25);
		contentPane.add(lblNhpLiMt);

		txtRePassword = new JPasswordField("123456");
		txtRePassword.setToolTipText("password");
		txtRePassword.setBounds(30, 425, 280, 35);
		contentPane.add(txtRePassword);

		lblEmail = new JLabel("EMAIL");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setForeground(Color.BLACK);
		lblEmail.setBounds(30, 200, 159, 25);
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setText("abc@gmail.com");
		txtEmail.setToolTipText("email");
		txtEmail.setColumns(10);
		txtEmail.setBounds(30, 235, 280, 35);
		contentPane.add(txtEmail);

		lblNgySinh = new JLabel("NGÀY SINH");
		lblNgySinh.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNgySinh.setForeground(Color.BLACK);
		lblNgySinh.setBounds(30, 115, 159, 25);
		contentPane.add(lblNgySinh);

		UtilDateModel model = new UtilDateModel();

		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		model.setDate(1999, 01, 01);
		model.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.getJFormattedTextField().setBackground(Color.WHITE);
		datePicker.setSize(173, 30);
		datePicker.setLocation(30, 150);

		contentPane.add(datePicker);
		lblGiiTnh = new JLabel("GIỚI TÍNH");
		lblGiiTnh.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGiiTnh.setForeground(Color.BLACK);
		lblGiiTnh.setBounds(219, 115, 103, 28);
		contentPane.add(lblGiiTnh);

		txtGender = new JComboBox<String>();
		txtGender.setBackground(Color.WHITE);
		txtGender.setModel(new DefaultComboBoxModel<String>(new String[] { "Nam", "Nữ" }));
		txtGender.setBounds(219, 150, 72, 30);
		txtGender.getEditor().getEditorComponent().setBackground(Color.WHITE);
		((JTextField) txtGender.getEditor().getEditorComponent()).setBackground(Color.WHITE);
		contentPane.add(txtGender);

		lblCTi = new JLabel("Đã có tài khoản?");
		lblCTi.setForeground(Color.BLACK);
		lblCTi.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCTi.setBounds(72, 535, 103, 25);
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
				lblngNhpNgay.setForeground(new Color(3, 87, 40).brighter());
			}

			@Override
			public void mouseExited(MouseEvent e) {

				lblngNhpNgay.setForeground(new Color(8, 87, 40));
			}
		});
		lblngNhpNgay.setForeground(new Color(8, 87, 40));
		lblngNhpNgay.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblngNhpNgay.setBounds(167, 535, 159, 25);
		contentPane.add(lblngNhpNgay);

		errorName = new JLabel("");
		errorName.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorName.setForeground(Color.RED);
		errorName.setBounds(30, 95, 280, 15);
		contentPane.add(errorName);

		errorPassword = new JLabel("");
		errorPassword.setForeground(Color.RED);
		errorPassword.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorPassword.setBounds(30, 365, 280, 15);
		contentPane.add(errorPassword);

		errorRePassword = new JLabel("");
		errorRePassword.setForeground(Color.RED);
		errorRePassword.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorRePassword.setBounds(30, 460, 280, 15);
		contentPane.add(errorRePassword);

		errorEmail = new JLabel("");
		errorEmail.setForeground(Color.RED);
		errorEmail.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorEmail.setBounds(30, 270, 280, 15);
		contentPane.add(errorEmail);

		errorBirthdate = new JLabel("");
		errorBirthdate.setForeground(Color.RED);
		errorBirthdate.setFont(new Font("SansSerif", Font.ITALIC, 11));
		errorBirthdate.setBounds(30, 180, 280, 15);
		contentPane.add(errorBirthdate);

		arrTF.add(txtName);
		arrTF.add(txtPassword);
		arrTF.add(txtRePassword);
		arrTF.add(txtEmail);

		arrError.add(errorName);
		arrError.add(errorPassword);
		arrError.add(errorRePassword);
		arrError.add(errorEmail);

//		this.setUndecorated(true);
		setResizable(false);
		this.setVisible(true);

	}

	private Player getData() { // take data
		String name = this.txtName.getText();
		String user = this.txtEmail.getText();
		String pass = String.valueOf(this.txtPassword.getPassword());
		String birthdate = this.datePicker.getJFormattedTextField().getText();
		boolean gender = this.txtGender.getSelectedItem().toString().equals("Nam") ? true : false;
		Player p = new Player(name, user, pass, "", birthdate, gender);
		return p;
	}

	private boolean checkData() { // check data, return true if data is OK
		boolean check = true;
		MyRegEx regex = new MyRegEx();
		int num = 0;
		for (JTextField a : arrTF) {
			String theme = a.getToolTipText();
			if (!a.getText().trim().matches(regex.pattern.get(theme).toString())) {
				this.arrError.get(num).setText(regex.error.get(theme).toString());
				check = false;
			} else
				this.arrError.get(num).setText("");
			num++;
		}
		String pass = String.valueOf(this.txtPassword.getPassword()).trim();
		String rePass = String.valueOf(txtRePassword.getPassword()).trim();
		if (!pass.equals(rePass)) {
			this.errorRePassword.setText("Mật khẩu nhập lại phải khớp với mật khẩu trên.");
			check = false;
		}
		String birthdate = this.datePicker.getJFormattedTextField().getText();
		if (birthdate.trim().equals("")) {
			errorBirthdate.setText("Không để ngày sinh rỗng.");
			check = false;
		} else
			errorBirthdate.setText("");
		return check;
	}

	public void registerPlayer() {
		if (this.checkData()) {
			// send request to server
			Player p = getData();
			client.sendRequest(new SocketRequestPlayer(SocketRequest.Action.REGISTER, p));
			SocketResponse response = client.getResponse();
			if (response.getStatus().equals(SocketResponse.Status.SUCCESS)) {
				ActiveCodeDialog dialog = new ActiveCodeDialog(client, p);
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				dialog.setVisible(true);
				boolean check = dialog.getConfirm();
				if (check == true) {
					client.connect(p.getUsername(), p.getPassword()); // connect Player
					this.dispose();
					MainFrame frame = new MainFrame(client); // open main frame
					frame.setVisible(true);
				} 
			} else {
				JOptionPane.showMessageDialog(this, response.getMessage(), "Alert", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
