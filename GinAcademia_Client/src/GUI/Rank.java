package GUI;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

@SuppressWarnings("serial")
public class Rank extends MyPanel {

	private JTable table;
	private JPanel panelTable;
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

	public Rank(Client client, int status) {
		super(client);
		this.player = client.player;
		this.setSize(600, 600);
		this.setBackground(Color.WHITE);
		if (status == 1) {
			this.initRank();
			this.initTable();

			panelTable = new JPanel();
			panelTable.setBounds(30, 134, 540, 435);
			panelTable.setLayout(new GridLayout(0, 1, 0, 0));
			panelTable.setBackground(Color.WHITE);
			JScrollPane scrollPane = new JScrollPane(table);
			panelTable.add(scrollPane);

			add(panelTable);

			this.initComponent();
		}
	}

	private void initRank() {
		SocketRequest request = new SocketRequest(SocketRequest.Action.RANK, "Rank view");
		client.sendRequest(request);
		SocketResponseRank response = (SocketResponseRank) client.getResponse();
		listplayer = response.getList();
	}

	private void initTable() {
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
	}

	private void initComponent() {
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
		lblSearch.setBounds(276, 80, 70, 30);
		add(lblSearch);

		txtSearch = new JTextField();
		txtSearch.setBounds(356, 80, 214, 30);
		add(txtSearch);
		txtSearch.setColumns(10);
	}
}
