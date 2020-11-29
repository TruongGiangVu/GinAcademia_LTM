package GUI;



import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Player;
import Socket.Client;
import Socket.Request.SocketRequest;
import Socket.Request.SocketRequestPlayer;
import Socket.Response.SocketResponse;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class ActiveCodeDialog extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCode;
	private JButton btnSend;
	private JButton btnCancel;
	private JButton btnReset;
	Client client;
	Player player;
	boolean isOk = false;
	private JLabel lblNewLabel;

	public ActiveCodeDialog(Client client, Player player) {
		this.client = client;
		this.player = player;
		setTitle("Mã code");
		setBounds(100, 100, 400, 225);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 384, 140);
//		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		txtCode = new JTextField();
		txtCode.setBounds(85, 50, 230, 30);
		contentPanel.add(txtCode);
		txtCode.setColumns(10);

		JLabel lblMCodeKch = new JLabel("Mã kích hoạt");
		lblMCodeKch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMCodeKch.setHorizontalAlignment(SwingConstants.CENTER);
		lblMCodeKch.setBounds(140, 10, 120, 30);
		contentPanel.add(lblMCodeKch);
		{
			btnReset = new JButton("Gửi lại email");
			btnReset.setBounds(140, 105, 120, 30);
			contentPanel.add(btnReset);
			{
				lblNewLabel = new JLabel("Mã kích hoạt chỉ tồn tài trong 10 phút");
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setBounds(85, 85, 224, 14);
				contentPanel.add(lblNewLabel);
			}
			btnReset.addActionListener(this);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(new Rectangle(0, 140, 384, 46));
			getContentPane().add(buttonPane);
			{
				btnSend = new JButton("Kích hoạt");
				btnSend.setBounds(209, 8, 100, 30);
				btnSend.setActionCommand("OK");
				btnSend.addActionListener(this);
				buttonPane.setLayout(null);
				buttonPane.add(btnSend);
				getRootPane().setDefaultButton(btnSend);
			}
			{
				btnCancel = new JButton("Hủy");
				btnCancel.setBounds(319, 8, 55, 30);
				btnCancel.setActionCommand("Cancel");
				btnCancel.addActionListener(this);
				buttonPane.add(btnCancel);
			}
		}

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				client.sendRequest(new SocketRequest(SocketRequest.Action.REGISTER, "cancel Email"));
				dispose();
			}
		});
	}

	public boolean getConfirm() {
		return isOk;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton source = (JButton) e.getSource();
		if (source == btnSend) {
			String str = "code " + this.txtCode.getText().trim();
			client.sendRequest(new SocketRequestPlayer(SocketRequest.Action.REGISTER, str, player));
			SocketResponse response = client.getResponse();
			if (response.getStatus().equals(SocketResponse.Status.FAILED)) {
				JOptionPane.showMessageDialog(this, response.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				if(response.getMessage().equals("Mã này đã hết hạn")) {
					this.isOk = false;
					dispose();
				}	
			}
				
			else {
				JOptionPane.showMessageDialog(this, response.getMessage(), "", JOptionPane.INFORMATION_MESSAGE);
				this.isOk = true;
				dispose();
			}
		} else if (source == btnReset) {
			client.sendRequest(new SocketRequest(SocketRequest.Action.REGISTER, "reset Email"));
		} else if (source == btnCancel) {
			this.isOk = false;
			client.sendRequest(new SocketRequest(SocketRequest.Action.REGISTER, "cancel Email"));
			dispose();
		}
	}
}
