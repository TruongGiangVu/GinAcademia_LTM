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
import java.awt.Color;
import javax.swing.JScrollBar;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class AvatarDialog extends JDialog implements ActionListener {

	
	Client client;
	Player player;

	public AvatarDialog(Client client, Player player) {
		getContentPane().setBackground(Color.WHITE);
		this.client = client;
		this.player = player;
		getContentPane().setLayout(null);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBackground(Color.WHITE);
		scrollBar.setForeground(Color.BLACK);
		scrollBar.setBounds(0, 0, 434, 261);
		getContentPane().add(scrollBar);
		
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
