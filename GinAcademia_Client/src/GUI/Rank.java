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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	public int orderRank = 0;
	private JLabel lblNewLabel;
	private JLabel lblYourRank;
	private JLabel lblNewLabel_2;
	private JComboBox<String> cbbRank;
	private Player player;
	private JScrollPane scrollPane;
	private ArrayList<Player> playerActive= new ArrayList<Player>();
	private ArrayList<Player> totalGame;
	private ArrayList<Player> totalWin;
	private ArrayList<Player> winStreak;
	private String totalGameRank;
	private String totalWinRank;
	private String winStreakRank;
	private JPanel pnEmpty;
	private JLabel lblEmpty;
	private DefaultTableModel tableModel;
	private JTextField txtSearch;
	private JButton btnNewButton;
	public static boolean flag = true;

	public Rank(Client client, int status) {
		super(client);
		this.player = client.player;
		this.setSize(600, 600);
		this.setBackground(Color.WHITE);
		if (status == 0)
			return;
		
		SocketRequest request = new SocketRequest(SocketRequest.Action.RANK, "Rank view");
		
		
		tableModel = new DefaultTableModel();
		setLayout(null);
		table = new JTable(){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		table.setFillsViewportHeight(true);
		table.setBackground(Color.WHITE);
		cbbRank = new JComboBox<String>();
		
		cbbRank.setFont(new Font("Monospace", Font.PLAIN, 12));
		cbbRank.setBackground(Color.WHITE);
		cbbRank.setModel(new DefaultComboBoxModel<String>(new String[] { "Số trận thắng", "Số trận tham gia","Chuỗi thắng" }));
		cbbRank.setBounds(85, 80, 120, 30);
		add(cbbRank);

		lblYourRank = new JLabel("0");
		lblYourRank.setFont(new Font("Monospace", Font.PLAIN, 14));
		lblYourRank.setBounds(145, 28, 38, 20);
		add(lblYourRank);
		
		pnEmpty = new JPanel();
		pnEmpty.setBounds(40, 30, 460, 150);
		pnEmpty.setBackground(Color.WHITE);
		
		lblEmpty = new JLabel("Không tìm thấy kết quả");
		lblEmpty.setFont(new Font("Monospace",Font.BOLD,20));
		pnEmpty.add(lblEmpty);
		scrollPane = new JScrollPane(table);
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
		String option = (String) cbbRank.getSelectedItem();
		if(client.checkSend) {
			client.sendRequest(request);
			if(client.checkSend) {
				SocketResponseRank response = (SocketResponseRank) client.getResponse();

				this.loadPlayerRankTable(response);
				lblYourRank.setText(this.totalWinRank);
				this.loadRank(this.totalWin,option);
				cbbRank.addActionListener(this);
			}
			
			
		}
		
		
		
//		
	}
	public void loadPlayerRankTable(SocketResponseRank rs) {
		this.playerActive.clear();
		this.totalGame = new ArrayList<Player>();
		this.totalWin = new ArrayList<Player>();
		this.winStreak= new ArrayList<Player>();

		
		this.playerActive = rs.getList();
		
		this.totalGame.addAll(this.playerActive);
		this.totalWin.addAll(this.playerActive);
		this.winStreak.addAll(this.playerActive);
		
		this.totalWin.sort(Comparator.comparing(Player::getWins).reversed());
		this.totalGame.sort(Comparator.comparing(Player::getTotalGame).reversed());
		this.winStreak.sort(Comparator.comparing(Player::getMaxWinSequence).reversed());
		
		this.totalGameRank = this.loadPlayerRank(this.totalGame);
		this.totalWinRank = this.loadPlayerRank(this.totalWin);
		this.winStreakRank = this.loadPlayerRank(this.winStreak);
		
		
		
	}
	public void loadRank(ArrayList<Player> data, String opt) {
		
		if(data!= null && data.size() > 0) {
			
			scrollPane.revalidate();
			scrollPane.repaint();
			int n = data.size() > 1? data.size() : 1;
			this.RemoveTableData(this.table);
			this.tableModel.setRowCount(0);
			if(opt.equals("Số trận thắng")) {
				this.tableModel.setColumnIdentifiers(new Object[] { "Hạng", "Tên người chơi","Tên tài khoản","Số trận thắng"});
				for(int i=0;i<n;i++) {
					Object[] row = {i+1, data.get(i).getName(),data.get(i).getUsername(),data.get(i).getWins() };
					this.tableModel.addRow(row);
				}
				lblYourRank.setText(this.totalWinRank);
			}
			else
			{
				if(opt.equals("Số trận tham gia")) {
					this.tableModel.setColumnIdentifiers(new Object[] { "Hạng", "Tên người chơi","Tên tài khoản","Số trận tham gia"});
					for(int i=0;i<n;i++) {
						Object[] row = {i+1, data.get(i).getName(),data.get(i).getUsername(),data.get(i).getTotalGame() };
						this.tableModel.addRow(row);
					}
					lblYourRank.setText(this.totalGameRank);
				}
				else
				{
					if(opt.equals("Chuỗi thắng")) {
						this.tableModel.setColumnIdentifiers(new Object[] { "Hạng", "Tên người chơi","Tên tài khoản","Chuỗi thắng"});
						for(int i=0;i<n;i++) {
							Object[] row = {i+1, data.get(i).getName(),data.get(i).getUsername(),data.get(i).getMaxWinSequence() };
							this.tableModel.addRow(row);
						}
						lblYourRank.setText(this.winStreakRank);

					}
				}
			}
			this.table.setModel(this.tableModel);
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment( JLabel.CENTER );
			table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
			table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
			table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
		}
		else {
			JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả");
			
		}
		
	}
	public String loadPlayerRank(ArrayList<Player> data) {
		int ind = -1;
		String rs = "";
		boolean flag = true;
		int n = data.size();
		int i = 0;
		while(flag && i<n ) {
			if(data.get(i).getId().equals(this.player.getId())) {
				ind = i + 1;
				flag = false;
				i++;
			}
			else {
				i++;
			}
		}
		if(ind == -1) {
			rs = "Chưa vào bảng xếp hạng";
		}
		else {
			rs = ind + "";
		}
		return rs;
	}
	private String normalizedString(String val) {
		return val.toLowerCase().trim().replaceAll("\\s+", " ");
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object widget = arg0.getSource();
		if(widget == btnNewButton || widget == txtSearch) {
			String txt = txtSearch.getText();
			String option = (String) cbbRank.getSelectedItem();
			this.SearchRank(txt,option);
		}
		else
		{
			if(widget == cbbRank) {
				String option = (String) cbbRank.getSelectedItem();
				if(option == "Số trận thắng") {
					this.loadRank(this.totalWin, option);
				}
				else
				{
					if(option == "Số trận tham gia") {
						this.loadRank(this.totalGame, option);
					}
					else {
						this.loadRank(this.winStreak, option);
					}
				}
			}
		}
	}
	
	public void SearchRank(String searchQuerry,String opt) {
		this.RemoveTableData(this.table);
		
		ArrayList<Player> temp = new ArrayList<Player>();
		if(searchQuerry.isEmpty() || searchQuerry.trim().equals("") || searchQuerry == null) {
			this.loadRank(this.playerActive,opt);
		}
		else {
			searchQuerry = this.normalizedString(searchQuerry);
			if(opt.equals("Số trận thắng")) {
				for(Player p:this.totalWin) {
					if(this.normalizedString(p.getName()).contains(searchQuerry)) {
						temp.add(p);
					}
				}
				
				lblYourRank.setText(this.totalWinRank);
			}
			else {
				if(opt.equals("Số trận tham gia")) {
					for(Player p:this.totalGame) {
						if(this.normalizedString(p.getName()).contains(searchQuerry)) {
							temp.add(p);
						}
					}
					lblYourRank.setText(this.totalGameRank);

				}
				else {
					if(opt.equals("Chuỗi thắng")) {
						for(Player p:this.winStreak) {
							if(this.normalizedString(p.getName()).contains(searchQuerry)) {
								temp.add(p);
							}
						}
						lblYourRank.setText(this.winStreakRank);
					}
				}
			}
			
			this.loadRank(temp,opt);
			
		}
		
	}
	public void RemoveTableData(JTable jtb) {
		DefaultTableModel tbModel = (DefaultTableModel) jtb.getModel();
		tbModel.setRowCount(0);
	}

}
