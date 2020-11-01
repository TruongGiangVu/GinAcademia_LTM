package GUI;

import javax.swing.JPanel;

import Model.Player;
import Module.DateLabelFormatter;
import Module.MyPanel;
import Server.BUS.PlayerBUS;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class Profile extends MyPanel implements ActionListener{
	private JLabel lblNewLabel;
	private JPanel panelInfo;
	private JTextField txtName;
	private JLabel lblNewLabel_1;
	private JTextField txtEmail;
	private JComboBox txtGender;
	private JLabel lblNewLabel_2;
	private JLabel lblNgySinh;
	private JLabel lblGiiTnh;
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
	private JPanel panelButton;
	private JButton btnEdit;
	private JButton btnCancel;
	private JButton btnUpdate;
	private JButton btnLogout;
	
//	private PlayerBUS bus = new PlayerBUS();

	/**
	 * Create the panel.
	 */
	public Profile(Player player) {
		this.player = player;
		this.setSize(600,600);
		setLayout(null);
		
		panelInfo = new JPanel();
		panelInfo.setBounds(30, 15, 540, 220);
		panelInfo.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Thông tin cá nhân",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panelInfo);
		panelInfo.setLayout(null);
		
		lblNewLabel = new JLabel("Họ và tên");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(20, 20, 65, 20);
		panelInfo.add(lblNewLabel);
		
		txtName = new JTextField();
		txtName.setText("df");
		txtName.setBounds(90, 20, 172, 25);
		panelInfo.add(txtName);
		txtName.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(280, 20, 50, 20);
		panelInfo.add(lblNewLabel_1);
		
		txtEmail = new JTextField();
		txtEmail.setText("df");
		txtEmail.setColumns(10);
		txtEmail.setBounds(345, 20, 157, 25);
		panelInfo.add(txtEmail);
		
		txtGender = new JComboBox();
		txtGender.setModel(new DefaultComboBoxModel(new String[] {"Nam", "Nữ"}));
		txtGender.setBounds(345, 70, 72, 25);
		panelInfo.add(txtGender);
		
		lblNewLabel_2 = new JLabel("Giới tính");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(280, 70, 60, 20);
		panelInfo.add(lblNewLabel_2);
		
		lblNgySinh = new JLabel("Ngày sinh");
		lblNgySinh.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNgySinh.setBounds(20, 70, 65, 20);
		panelInfo.add(lblNgySinh);
		
		lblGiiTnh = new JLabel();
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		datePanel.setEnabled(false);
		datePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
		datePicker.setButtonFocusable(false);
		datePicker.setSize(172, 23);
		datePicker.setLocation(90, 70);
		panelInfo.add(datePicker);
		
		lblGiiTnh = new JLabel("GIỚI TÍNH");
		lblGiiTnh.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGiiTnh.setForeground(Color.BLACK);
		lblGiiTnh.setBounds(391, 505, 103, 25);
		panelInfo.add(lblGiiTnh);
		
		panelButton = new JPanel();
		panelButton.setBounds(297, 164, 215, 33);
		panelInfo.add(panelButton);
		panelButton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnEdit = new JButton("Chỉnh sửa");
		panelButton.add(btnEdit);
		
		btnCancel = new JButton("Hủy");
		panelButton.add(btnCancel);
		
		btnUpdate = new JButton("Cập nhật");
		panelButton.add(btnUpdate);
		
		this.btnCancel.setVisible(false);
		this.btnUpdate.setVisible(false);
		
		panelGame = new JPanel();
		panelGame.setBounds(30, 260, 540, 180);
		panelGame.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Game",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(panelGame);
		panelGame.setLayout(null);
	
		lblSTrnThng = new JLabel("Thắng:");
		lblSTrnThng.setBounds(30, 30, 80, 20);
		lblSTrnThng.setFont(new Font("Tahoma", Font.BOLD, 13));
		panelGame.add(lblSTrnThng);
		
		lblThua = new JLabel("Thua:");
		lblThua.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblThua.setBounds(210, 30, 80, 20);
		panelGame.add(lblThua);
		
		lblLinThng = new JLabel("Liên thắng:");
		lblLinThng.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLinThng.setBounds(31, 70, 80, 20);
		panelGame.add(lblLinThng);
		
		lblLinThua = new JLabel("Liên thua:");
		lblLinThua.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLinThua.setBounds(210, 70, 80, 20);
		panelGame.add(lblLinThua);
		
		lblIq = new JLabel("IQ:");
		lblIq.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIq.setBounds(31, 110, 80, 20);
		panelGame.add(lblIq);
		
		lblWin = new JLabel("0");
		lblWin.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblWin.setBounds(115, 30, 50, 20);
		panelGame.add(lblWin);
		
		lblLose = new JLabel("0");
		lblLose.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblLose.setBounds(295, 30, 50, 20);
		panelGame.add(lblLose);
		
		lblWinSe = new JLabel("0");
		lblWinSe.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblWinSe.setBounds(115, 70, 50, 20);
		panelGame.add(lblWinSe);
		
		lblLoseSe = new JLabel("0");
		lblLoseSe.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblLoseSe.setBounds(295, 70, 50, 20);
		panelGame.add(lblLoseSe);
		
		lblIQ = new JLabel("0");
		lblIQ.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblIQ.setBounds(115, 110, 50, 20);
		panelGame.add(lblIQ);
		
		btnLogout = new JButton("Đăng xuất");
		btnLogout.setBounds(400, 450, 120, 25);
		add(btnLogout);
		
		btnEdit.addActionListener(this);
		btnCancel.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnLogout.addActionListener(this);
		
		this.loadInfo();
		this.loadGame();
		this.activeText(false);

	}
	public void activeText(boolean active) {
		this.txtName.setEnabled(active);
		this.txtGender.setEnabled(active);
		this.txtEmail.setEnabled(active);
		this.datePicker.setEnabled(false);
		
	}
	public void loadInfo() {
		this.txtName.setText(this.player.getName());
		this.txtEmail.setText(this.player.getEmail());
		this.datePicker.getJFormattedTextField().setText(this.player.getBirthdateString());
		this.txtGender.setSelectedIndex(this.player.getGenderInt());
	}
	public void updateData() {
		this.player.setName(this.txtName.getText());
		this.player.setEmail(this.txtEmail.getText());
		this.player.setBirthdate(this.datePicker.getJFormattedTextField().getText());
		this.player.setGender(this.txtGender.getSelectedItem().toString());
	}
	public void loadGame() {
		this.lblWin.setText(this.player.getWins()+"");
		this.lblLose.setText(this.player.getLoses()+"");
		this.lblWinSe.setText(this.player.getMaxWinSequence()+"");
		this.lblLoseSe.setText(this.player.getMaxLoseSequence()+"");
		this.lblIQ.setText(this.player.getIQPoint()+"");
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JButton source = (JButton) arg0.getSource();
		if(source == btnEdit) {
			this.btnEdit.setVisible(false);
			this.btnCancel.setVisible(true);
			this.btnUpdate.setVisible(true);
			
			this.activeText(true);
		}else if(source == btnCancel) {
			this.activeText(false);
			this.loadInfo();
			this.btnEdit.setVisible(true);
			this.btnCancel.setVisible(false);
			this.btnUpdate.setVisible(false);
		}else if(source == btnUpdate) {
			this.updateData();
			//socket
//			bus.update(this.player);
			
			
			this.loadInfo();
			this.activeText(false);
			this.btnEdit.setVisible(true);
			this.btnCancel.setVisible(false);
			this.btnUpdate.setVisible(false);
			// update on MainFrame
			MainFrame parent = (MainFrame)SwingUtilities.getWindowAncestor(this);
			parent.lblName.setText(this.player.getName());
		}else if(source == btnLogout) {
			// socket
			Login login = new Login();
			login.hashCode();
			MainFrame parent = (MainFrame)SwingUtilities.getWindowAncestor(this);
			parent.dispose();
		}
	}
}
