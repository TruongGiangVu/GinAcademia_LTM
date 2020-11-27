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
import java.util.Properties;

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
	private JLabel lblGiiTnh;
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

		lblNewLabel = new JLabel("Họ và tên");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(20, 20, 65, 25);
		panelInfo.add(lblNewLabel);

		txtName = new JTextField();
		txtName.setText("df");
		txtName.setBounds(100, 20, 170, 25);
		txtName.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panelInfo.add(txtName);
		txtName.setColumns(10);

		lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(280, 20, 50, 25);
		panelInfo.add(lblNewLabel_1);

		txtGender = new JComboBox<String>();
		txtGender.setModel(new DefaultComboBoxModel<String>(new String[] { "Nam", "Nữ" }));
		txtGender.setBounds(345, 70, 72, 25);
		txtGender.setBackground(Color.WHITE);
		panelInfo.add(txtGender);

		lblNewLabel_2 = new JLabel("Giới tính");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(280, 70, 60, 25);
		panelInfo.add(lblNewLabel_2);

		lblNgySinh = new JLabel("Ngày sinh");
		lblNgySinh.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNgySinh.setBounds(20, 70, 65, 25);
		panelInfo.add(lblNgySinh);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.getJFormattedTextField().setBackground(Color.WHITE);
		datePicker.setSize(170, 35);
		datePicker.setLocation(100, 70);
		datePicker.setVisible(false);
		datePicker.setBackground(Color.WHITE);
//		datePicker.set
		panelInfo.add(datePicker);

		lblGiiTnh = new JLabel("GIỚI TÍNH");
		lblGiiTnh.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGiiTnh.setForeground(Color.BLACK);
		lblGiiTnh.setBounds(391, 505, 103, 25);
		panelInfo.add(lblGiiTnh);

		lblBirth = new JLabel(this.player.getBirthdateString());
		lblBirth.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBirth.setBounds(100, 70, 170, 25);
		panelInfo.add(lblBirth);

		btnEdit = new JButton("Chỉnh sửa");
		btnEdit.setBounds(395, 125, 120, 23);
		panelInfo.add(btnEdit);

		btnCancel = new JButton("Hủy");
		btnCancel.setBounds(460, 125, 55, 23);
		panelInfo.add(btnCancel);

		btnUpdate = new JButton("Cập nhật");
		btnUpdate.setBounds(325, 125, 120, 23);
		panelInfo.add(btnUpdate);
		
		lblGender = new JLabel("New label");
		lblGender.setBounds(345, 72, 75, 25);
		panelInfo.add(lblGender);
		
		lblEmail = new JLabel("");
		lblEmail.setBounds(345, 20, 170, 25);
		panelInfo.add(lblEmail);
		this.btnUpdate.setVisible(false);
		btnUpdate.addActionListener(this);

		this.btnCancel.setVisible(false);
		btnCancel.addActionListener(this);

		btnEdit.addActionListener(this);

		panelGame = new JPanel();
		panelGame.setBackground(Color.WHITE);
		panelGame.setBounds(30, 241, 545, 180);
		panelGame.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Game", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		btnLogout.setBounds(455, 451, 120, 25);
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

	public void loadInfo() { // load player profile information, can update
		this.txtName.setText(this.player.getName());
		this.datePicker.getJFormattedTextField().setText(this.player.getBirthdateString());
		this.txtGender.setSelectedIndex(this.player.getGenderInt());
		this.lblBirth.setText(this.player.getBirthdateString());
		this.lblGender.setText(this.player.getGenderString());
		this.lblEmail.setText(this.player.getUsername());
	}

	public void updateData() { // update info on GUI
		this.player.setName(this.txtName.getText());
		this.player.setBirthdate(this.datePicker.getJFormattedTextField().getText());
		this.player.setGender(this.txtGender.getSelectedItem().toString());
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton source = (JButton) arg0.getSource();
		if (source == btnEdit) { // show button update
			this.viewUpdateButton(true);
			this.activeText(true);
		} else if (source == btnCancel) { // off buttons of update
			this.activeText(false);
			this.loadInfo();
			this.viewUpdateButton(false);
		} else if (source == btnUpdate) {
			// update player
			this.updateData();
			client.sendRequest(new SocketRequestPlayer(SocketRequest.Action.UPDATEPROFILE, this.player));
			SocketResponse response = client.getResponse();
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
		} else if (source == btnLogout) { // logout account
			this.client.sendRequest(new SocketRequest(SocketRequest.Action.DISCONNECT, "Logout")); // send request to
																									// disconnect
			this.client.close(); // disconnect to server
			// open login frame
			Login login = new Login(); // create new socket for new login
			login.hashCode();
			// close this main frame
			MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(this);
			parent.dispose();
		}
	}
}
