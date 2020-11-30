package GUI;

import javax.swing.JPanel;

import Model.Player;
import Module.DateLabelFormatter;
import Module.MyPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import Socket.Client;
import Socket.Request.SocketRequest;
import Socket.Request.SocketRequestPlayer;
import Socket.Response.SocketResponse;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class Profile extends MyPanel implements ActionListener {
	private JLabel lblNewLabel;
	private JPanel panelInfo;
	private JTextField txtName;
	private JLabel lblNewLabel_1;
	private JComboBox<String> txtGender;
	private JLabel lblGender;
	private JLabel lblNewLabel_2;
	private JLabel lblNgySinh;
	private JLabel lblBirth;
	private JLabel lblEmail;
	private JDatePickerImpl datePicker;
	private Player player;
	private JPanel panelGame;
	private JLabel lblSTrnThng;
	private JLabel lblThua;
	private JLabel lblLinThng;
	private JLabel lblLinThua;
	private JLabel lblIq;
	private JLabel lblWin;
	private JLabel lblLose;
	private JLabel lblWinSe;
	private JLabel lblLoseSe;
	private JLabel lblIQ;
	private JButton btnEdit;
	private JButton btnCancel;
	private JButton btnUpdate;
	private JButton btnLogout;
	private JLabel lblNameErr;
	private JLabel lblMailErr;
	private JLabel lblBirthErr;
	private boolean flag = true;
	public Profile(Client client) {
		super(client);
		this.player = client.player;
		this.setSize(600, 600);
		setLayout(null);
		this.setBackground(Color.WHITE);
		panelInfo = new JPanel();
		panelInfo.setBackground(Color.WHITE);
		panelInfo.setBounds(30, 15, 545, 188);
		panelInfo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Thông tin cá nhân",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panelInfo);
		panelInfo.setLayout(null);

		lblNewLabel = new JLabel("Họ và tên:");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblNewLabel.setBounds(20, 20, 70, 25);
		panelInfo.add(lblNewLabel);

		txtName = new JTextField();
		txtName.setFont(new Font("SansSerif", Font.PLAIN, 13));
		txtName.setBackground(Color.WHITE);
		txtName.setText("df");
		txtName.setBounds(100, 20, 170, 25);
		txtName.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		txtName.setDisabledTextColor(Color.black);
		panelInfo.add(txtName);
		txtName.setColumns(10);

		lblNewLabel_1 = new JLabel("Email:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblNewLabel_1.setBounds(280, 20, 50, 25);
		panelInfo.add(lblNewLabel_1);

		txtGender = new JComboBox<String>();
		txtGender.setFont(new Font("SansSerif", Font.PLAIN, 13));
		txtGender.setModel(new DefaultComboBoxModel<String>(new String[] { "Nam", "Nữ" }));
		txtGender.setBounds(345, 70, 72, 25);
		txtGender.setBackground(Color.WHITE);
		panelInfo.add(txtGender);

		lblNewLabel_2 = new JLabel("Giới tính:");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblNewLabel_2.setBounds(280, 70, 60, 25);
		panelInfo.add(lblNewLabel_2);

		lblNgySinh = new JLabel("Ngày sinh:");
		lblNgySinh.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblNgySinh.setBounds(20, 70, 70, 25);
		panelInfo.add(lblNgySinh);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		SpringLayout springLayout = (SpringLayout) datePicker.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH, datePicker.getJFormattedTextField(), 0, SpringLayout.SOUTH, datePicker);
		datePicker.getJFormattedTextField().setFont(new Font("SansSerif", Font.PLAIN, 13));
		datePicker.getJFormattedTextField().setBackground(Color.WHITE);
		datePicker.setSize(170, 35);
		datePicker.setLocation(100, 70);
		datePicker.setVisible(false);
		datePicker.setBackground(Color.WHITE);
//		datePicker.set
		panelInfo.add(datePicker);

		

		lblBirth = new JLabel(this.player.getBirthdateString());
		lblBirth.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblBirth.setBounds(100, 70, 170, 25);
		panelInfo.add(lblBirth);

		btnEdit = new JButton("Chỉnh sửa");
		btnEdit.setBackground(Color.WHITE);
		btnEdit.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnEdit.setBounds(395, 125, 120, 23);
		panelInfo.add(btnEdit);

		btnCancel = new JButton("Hủy");
		btnCancel.setBackground(Color.WHITE);
		btnCancel.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnCancel.setBounds(450, 125, 65, 23);
		panelInfo.add(btnCancel);

		btnUpdate = new JButton("Cập nhật");
		btnUpdate.setBackground(Color.WHITE);
		btnUpdate.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnUpdate.setBounds(315, 125, 120, 23);
		panelInfo.add(btnUpdate);
		
		lblGender = new JLabel("New label");
		lblGender.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblGender.setBounds(345, 70, 75, 25);
		panelInfo.add(lblGender);
		
		lblEmail = new JLabel("");
		lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblEmail.setBounds(345, 20, 170, 25);
		panelInfo.add(lblEmail);
		
		lblNameErr = new JLabel("Họ và tên không được rỗng");
		lblNameErr.setForeground(Color.RED);
		lblNameErr.setFont(new Font("SansSerif", Font.ITALIC, 12));
		lblNameErr.setBounds(100, 45, 170, 25);
		lblNameErr.setVisible(false);
		panelInfo.add(lblNameErr);
		
		lblMailErr = new JLabel("Họ và tên không được rỗng");
		lblMailErr.setForeground(Color.RED);
		lblMailErr.setFont(new Font("SansSerif", Font.ITALIC, 12));
		lblMailErr.setBounds(345, 45, 170, 14);
		lblMailErr.setVisible(false);
		panelInfo.add(lblMailErr);
		
		lblBirthErr = new JLabel("Họ và tên không được rỗng");
		lblBirthErr.setForeground(Color.RED);
		lblBirthErr.setFont(new Font("SansSerif", Font.ITALIC, 12));
		lblBirthErr.setBounds(100, 106, 170, 14);
		lblBirthErr.setVisible(false);
		panelInfo.add(lblBirthErr);
		this.btnUpdate.setVisible(false);
		btnUpdate.addActionListener(this);

		this.btnCancel.setVisible(false);
		btnCancel.addActionListener(this);

		btnEdit.addActionListener(this);

		panelGame = new JPanel();
		panelGame.setBackground(Color.WHITE);
		panelGame.setBounds(30, 241, 545, 180);
		panelGame.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Th\u00F4ng tin ch\u1EC9 s\u1ED1 tr\u00F2 ch\u01A1i", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panelGame);
		panelGame.setLayout(null);

		lblSTrnThng = new JLabel("Thắng:");
		lblSTrnThng.setBounds(30, 30, 80, 20);
		lblSTrnThng.setFont(new Font("SansSerif", Font.BOLD, 13));
		panelGame.add(lblSTrnThng);

		lblThua = new JLabel("Thua:");
		lblThua.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblThua.setBounds(210, 30, 80, 20);
		panelGame.add(lblThua);

		lblLinThng = new JLabel("Liên thắng:");
		lblLinThng.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblLinThng.setBounds(31, 70, 80, 20);
		panelGame.add(lblLinThng);

		lblLinThua = new JLabel("Liên thua:");
		lblLinThua.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblLinThua.setBounds(210, 70, 80, 20);
		panelGame.add(lblLinThua);

		lblIq = new JLabel("IQ:");
		lblIq.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblIq.setBounds(31, 110, 80, 20);
		panelGame.add(lblIq);

		lblWin = new JLabel("0");
		lblWin.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblWin.setBounds(115, 30, 50, 20);
		panelGame.add(lblWin);

		lblLose = new JLabel("0");
		lblLose.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblLose.setBounds(295, 30, 50, 20);
		panelGame.add(lblLose);

		lblWinSe = new JLabel("0");
		lblWinSe.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblWinSe.setBounds(115, 70, 50, 20);
		panelGame.add(lblWinSe);

		lblLoseSe = new JLabel("0");
		lblLoseSe.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblLoseSe.setBounds(295, 70, 50, 20);
		panelGame.add(lblLoseSe);

		lblIQ = new JLabel("0");
		lblIQ.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblIQ.setBounds(115, 110, 50, 20);
		panelGame.add(lblIQ);

		btnLogout = new JButton("Đăng xuất");
		btnLogout.setBackground(Color.WHITE);
		btnLogout.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnLogout.setBounds(425, 451, 120, 25);
		add(btnLogout);
		btnLogout.addActionListener(this);
		this.loadInfo();
		this.loadGame();
		this.activeText(false);
	}

	public void activeText(boolean active) { // allow edit text
		this.txtName.setEditable(active);
		
		this.lblGender.setVisible(!active);
		this.txtGender.setVisible(active);
		
		this.lblBirth.setVisible(!active);
		this.datePicker.setVisible(active);
	}
	public boolean isFullname(String str) {
		str = removeAccent(str);
	    String expression = "^[a-zA-Z\\s]+"; 
	    return str.matches(expression);        
	}
	public void loadInfo() { // load player profile information, can update
		this.txtName.setText(this.player.getName());
		this.datePicker.getJFormattedTextField().setText(this.player.getBirthdateString());
		this.txtGender.setSelectedIndex(this.player.getGenderInt());
		this.lblBirth.setText(this.player.getBirthdateString());
		this.lblGender.setText(this.player.getGenderString());
		this.lblEmail.setText(this.player.getUsername());
	}

	public void updateData() { // update info player
		String name = this.txtName.getText();
		String birth = this.datePicker.getJFormattedTextField().getText();
		boolean nameFlag = true;
		boolean birthFlag= true;
		if(!isFullname(name)) {
			lblNameErr.setText("Tên không được rỗng và có số");
			lblNameErr.setVisible(true);
			nameFlag = false;
		}
		else
		{
			lblNameErr.setVisible(false);
			nameFlag= true;
		}
		if(birth == null || birth.equals("")) {
			lblBirthErr.setText("Ngày sinh không được rỗng");
			lblBirthErr.setVisible(true);
			birthFlag = false;
		}
		else
		{
			lblBirthErr.setVisible(false);
			birthFlag = true;
		}
		if(nameFlag && birthFlag) {
			flag= true;
			this.player.setName(this.txtName.getText());
			this.player.setBirthdate(this.datePicker.getJFormattedTextField().getText());
			this.player.setGender(this.txtGender.getSelectedItem().toString());
		}
		else {
			flag = false;
		}
		
	}

	public void loadGame() { // load player game information, it can not change
		this.lblWin.setText(this.player.getWins() + "");
		this.lblLose.setText(this.player.getLoses() + "");
		this.lblWinSe.setText(this.player.getMaxWinSequence() + "");
		this.lblLoseSe.setText(this.player.getMaxLoseSequence() + "");
		this.lblIQ.setText(this.player.getIQPoint() + "");
	}

	private void viewUpdateButton(boolean isUpdate) { // change view button for update info
		this.btnEdit.setVisible(!isUpdate);
		this.btnCancel.setVisible(isUpdate);
		this.btnUpdate.setVisible(isUpdate);
	}
	
	public String removeAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton source = (JButton) arg0.getSource();
		if (source == btnEdit) { // show button update
			this.viewUpdateButton(true);
			this.activeText(true);
		} else if (source == btnCancel) { // off buttons of update
			lblNameErr.setVisible(false);
			lblBirthErr.setVisible(false);
			this.activeText(false);
			this.loadInfo();
			this.viewUpdateButton(false);
		} else if (source == btnUpdate) {
			// update player
			if(client.checkSend) {
				this.updateData();
				if(flag) {
					client.sendRequest(new SocketRequestPlayer(SocketRequest.Action.UPDATEPROFILE, this.player));
					if(client.checkSend) {
						SocketResponse response = client.getResponse();
						if(client.checkRequest) {
							if (response.getStatus().equals(SocketResponse.Status.SUCCESS)) {
								// update on GUI
								this.loadInfo();
								// enable view
								this.activeText(false);
								this.viewUpdateButton(false);
								// update name on MainFrame
								MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(this);
								parent.lblName.setText(this.player.getName());
								// update client info
								client.player = this.player;
								// show info
								JOptionPane.showMessageDialog(this, response.getMessage());
							}
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(this,(Object)"Thông tin cập nhật chưa đúng","Lỗi cập nhật",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
			
		} else if (source == btnLogout) { // logout account
			this.client.sendRequest(new SocketRequest(SocketRequest.Action.DISCONNECT, "Logout")); // send request to
																									// disconnect
//			this.client.close(); // disconnect to server
			// open login frame
			Login login = new Login(); // create new socket for new login
			login.hashCode();
			// close this main frame
			MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(this);
			parent.dispose();
		}
	}
}
