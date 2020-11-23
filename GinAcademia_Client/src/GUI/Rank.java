package GUI;

import java.awt.Image;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import java.util.ArrayList;

import Model.Player;
import Module.RankTableModel;
import Module.MyPanel;
import Socket.Client;
import Socket.Request.SocketRequest;
import Socket.Response.SocketResponseRank;
import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Rank extends MyPanel {

	private JTable table;
	private DefaultTableCellRenderer cellRenderer;
	public int orderRank = 0;
	private JLabel lblNewLabel;
	private JLabel lblYourRank;
	private JLabel lblNewLabel_2;
	private JComboBox<String> comboBox;
	private Player player;

	private ArrayList<Player> listplayer;
	private RankTableModel tableModel;
	private JTextField txtSearch;
	private JButton btnNewButton;

	public Rank(Client client, int status) {
		super(client);
		this.player = client.player;
		this.setSize(600, 600);
		this.setBackground(Color.WHITE);
		if (status == 0)
			return;
		
		SocketRequest request = new SocketRequest(SocketRequest.Action.RANK, "Rank view");
		client.sendRequest(request);
		SocketResponseRank response = (SocketResponseRank) client.getResponse();
		listplayer = response.getList();

		tableModel = new RankTableModel(listplayer);
		setLayout(null);
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);

		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getColumnModel().getColumn(6).setPreferredWidth(40);

		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(30, 134, 540, 400);
		scrollPane.setBackground(Color.WHITE);

		add(scrollPane);

		lblNewLabel = new JLabel("Hạng của bạn:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(30, 26, 105, 24);
		add(lblNewLabel);

		lblYourRank = new JLabel("0");
		lblYourRank.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblYourRank.setBounds(145, 28, 38, 20);
		lblYourRank.setText(tableModel.getRankById(this.player.getId()) + "");
		add(lblYourRank);

		lblNewLabel_2 = new JLabel("Hạng:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(30, 80, 45, 30);
		add(lblNewLabel_2);

		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBox.setBackground(Color.WHITE);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Tăng dần", "Giảm dần" }));
		comboBox.setBounds(85, 80, 101, 30);
		add(comboBox);

		JLabel lblSearch = new JLabel("Tìm kiếm: ");
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSearch.setBounds(240, 80, 70, 30);
		add(lblSearch);

		txtSearch = new JTextField();
		txtSearch.setBounds(315, 80, 214, 30);
		add(txtSearch);
		txtSearch.setColumns(10);

		Image scaled = new ImageIcon("./img/search_icon.png").getImage().getScaledInstance(30, 30,
				java.awt.Image.SCALE_SMOOTH);
//		Icon icon = new ImageIcon("./img/search_icon.png");

		btnNewButton = new JButton();
		btnNewButton.setBounds(539, 80, 31, 30);
		btnNewButton.setBorder(null);
		btnNewButton.setOpaque(false);
		btnNewButton.setBackground(null);
		btnNewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnNewButton.setIcon(new ImageIcon(scaled));
		add(btnNewButton);
	}



}
