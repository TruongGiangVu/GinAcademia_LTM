package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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

	public ActiveCodeDialog(Client client, Player player) {
		this.client = client;
		this.player = player;
		setTitle("Mã code");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtCode = new JTextField();
		txtCode.setBounds(100, 120, 230, 30);
		contentPanel.add(txtCode);
		txtCode.setColumns(10);
		{
			btnReset = new JButton("Gửi lại email");
			btnReset.addActionListener(this);
			btnReset.setBounds(180, 170, 90, 25);
			contentPanel.add(btnReset);
		}

		JLabel lblMCodeKch = new JLabel("Mã kích hoạt");
		lblMCodeKch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMCodeKch.setHorizontalAlignment(SwingConstants.CENTER);
		lblMCodeKch.setBounds(165, 72, 120, 37);
		contentPanel.add(lblMCodeKch);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnSend = new JButton("Gửi mã");
				btnSend.setActionCommand("OK");
				btnSend.addActionListener(this);
				buttonPane.add(btnSend);
				getRootPane().setDefaultButton(btnSend);
			}
			{
				btnCancel = new JButton("Hủy");
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
				if(response.getMessage().equals("Mã này đã hết giờ")) {
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
