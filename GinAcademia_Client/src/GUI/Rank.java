package GUI;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Comparator;

import Model.Player;
import Module.MyPanel;
import Socket.Client;
import Socket.Request.SocketRequest;
import Socket.Response.SocketResponseRank;
import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Rank extends MyPanel implements ActionListener{

	private JTable table;
	private DefaultTableCellRenderer cellRenderer;
	public int orderRank = 0;
	private JLabel lblNewLabel;
	private JLabel lblYourRank;
	private JLabel lblNewLabel_2;
	private JComboBox<String> cbbRank;
	private Player player;

	private ArrayList<Player> listPlayer = new ArrayList<Player>();
	private ArrayList<Player> playerActive= new ArrayList<Player>();
	private DefaultTableModel tableModel;
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
		this.loadPlayerActive(response);
		
		
		
		tableModel = new DefaultTableModel();
//		tableModel.setColumnIdentifiers(new Object[] { "Hạng", "Tên người chơi","Số trận tham gia", "Số trận thắng","Chuỗi thắng","Tỉ lệ thắng"});
		setLayout(null);
		table = new JTable(tableModel){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		cbbRank = new JComboBox<String>();
		cbbRank.setFont(new Font("Monospace", Font.PLAIN, 12));
		cbbRank.setBackground(Color.WHITE);
		cbbRank.setModel(new DefaultComboBoxModel<String>(new String[] { "Số trận thắng", "Tỉ lệ thắng","Chuỗi thắng" }));
		cbbRank.setBounds(85, 80, 101, 30);
		add(cbbRank);

		lblYourRank = new JLabel("0");
		lblYourRank.setFont(new Font("Monospace", Font.PLAIN, 14));
		lblYourRank.setBounds(145, 28, 38, 20);
		add(lblYourRank);
		this.loadRank(this.playerActive);
		

		
		table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(30, 134, 540, 400);
		scrollPane.setBackground(Color.WHITE);

		add(scrollPane);

		lblNewLabel = new JLabel("Hạng của bạn:");
		lblNewLabel.setFont(new Font("Monospace", Font.BOLD, 14));
		lblNewLabel.setBounds(30, 26, 105, 24);
		add(lblNewLabel);

		

		lblNewLabel_2 = new JLabel("Hạng:");
		lblNewLabel_2.setFont(new Font("Monospace", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(30, 80, 45, 30);
		add(lblNewLabel_2);

		
		JLabel lblSearch = new JLabel("Tìm kiếm tên: ");
		lblSearch.setFont(new Font("Monospace", Font.PLAIN, 13));
		lblSearch.setBounds(227, 80, 83, 30);
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
		
		btnNewButton.addActionListener(this);
		txtSearch.addActionListener(this);
		add(btnNewButton);
	}
	public void loadPlayerActive(SocketResponseRank rs) {
		this.listPlayer.clear();
		this.playerActive.clear();
		this.listPlayer = rs.getList();
		for(Player p:this.listPlayer) {
			if(p.getStatus()==0) {
				this.playerActive.add(p);
			}
		}
		
	}
	public void loadRank(ArrayList<Player> data) {
		String val = (String)cbbRank.getSelectedItem();
		int n = data.size()-1;
		int ind = 0;
		this.RemoveTableData(this.table);
		if(val.equals("Số trận thắng")) {
			this.tableModel.setColumnIdentifiers(new Object[] { "Hạng", "Tên người chơi","Tên tài khoản","Số trận thắng"});
			data.sort(Comparator.comparing(Player::getWins).reversed());
			for(int i=0;i<n;i++) {
				if(data.get(i).getId().equals(this.player.getId())) ind = i+1;
				Object[] row = {i+1, data.get(i).getName(),data.get(i).getUsername(),data.get(i).getWins() };
				this.tableModel.addRow(row);
			}
			this.table.setModel(this.tableModel);
			lblYourRank.setText(ind+"");
		}
		else
		{
			if(val.equals("Số trận tham gia")) {
				data.sort(Comparator.comparing(Player::getTotalGame).reversed());
				this.tableModel.setColumnIdentifiers(new Object[] { "Hạng", "Tên người chơi","Giới tính","Số trận tham gia"});
				for(int i=0;i<n;i++) {
					if(data.get(i).getId().equals(this.player.getId())) ind = i;
					Object[] row = {i+1, data.get(i).getName(),data.get(i).getUsername(),data.get(i).getTotalGame() };
					this.tableModel.addRow(row);
				}
				this.table.setModel(this.tableModel);
				lblYourRank.setText(ind+"");
			}
			else
			{
				if(val.equals("Chuỗi thắng")) {
					data.sort(Comparator.comparing(Player::getMaxWinSequence).reversed());
					this.tableModel.setColumnIdentifiers(new Object[] { "Hạng", "Tên người chơi","Giới tính","Chuỗi thắng"});
					for(int i=0;i<n;i++) {
						if(data.get(i).getId().equals(this.player.getId())) ind = i;
						Object[] row = {i+1, data.get(i).getName(),data.get(i).getUsername(),data.get(i).getMaxWinSequence() };
						this.tableModel.addRow(row);
					}
					this.table.setModel(this.tableModel);
					lblYourRank.setText(ind+"");
				}
			}
		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
	}
	private String normalizedString(String val) {
		return val.toLowerCase().trim().replaceAll("\\s+", " ");
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object widget = arg0.getSource();
		if(widget == btnNewButton || widget == txtSearch) {
			String txt = txtSearch.getText();
			this.SearchRank(txt);
		}
	}
	
	public void SearchRank(String searchQuerry) {
		this.RemoveTableData(this.table);
		ArrayList<Player> temp = new ArrayList<Player>();
		if(searchQuerry.isEmpty() || searchQuerry.trim().equals("") || searchQuerry == null) {
//			this.loadData(arr);
		}
		else {
			
		}
		for(Player p:this.playerActive) {
			if(p.getName().contains(searchQuerry)) {
				temp.add(p);
			}
		}
	}
	public void RemoveTableData(JTable jtb) {
		DefaultTableModel tbModel = (DefaultTableModel) jtb.getModel();
		tbModel.setRowCount(0);
	}

}
