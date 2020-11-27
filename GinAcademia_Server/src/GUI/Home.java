package GUI;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BUS.PlayerBUS;
import Model.Player;
import Socket.Server;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
//import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class Home extends JPanel implements MouseListener {
	static DefaultTableModel tbModelPlayer;
	JTable tbPlayer;
	DefaultTableModel tbModelGameRank;
	DefaultTableModel tbModelWinRank;
	DefaultTableModel tbModelWinSQRank;
	JTable tbGameRank;
	JTable tbWinRank;
	JTable tbWinSQRank;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private PlayerBUS bus = new PlayerBUS();
	private static JLabel lblNumOnline;
	ArrayList<Player> arr = new ArrayList<Player>();
	static ArrayList<Player> PlayerActive =  new ArrayList<Player>();
	
	/**
	 * Create the panel.
	 */
	public Home() {
		
		this.arr = bus.ReadAll();
		this.loadActive(this.arr);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
			@Override
            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) {
                Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
                c.setForeground(((String) value) == "Online" ? Color.GREEN : Color.RED);
                return c;
            }
		};
		customRenderer.setHorizontalAlignment(JLabel.CENTER);
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		this.setSize(600, 600);
		
		JLabel lblNewLabel = new JLabel("Tổng người chơi: ");
		lblNewLabel.setBounds(30, 30, 105, 30);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(String.valueOf(this.arr.size()));
		lblNewLabel_1.setBounds(145, 30, 30, 30);
		add(lblNewLabel_1);
		
		JLabel lblOnline = new JLabel("Số người chơi đang online: ");
		lblOnline.setBounds(200, 30, 170, 30);
		add(lblOnline);
		
		lblNumOnline = new JLabel(Server.countPlayerOnline()+ ""); 
		lblNumOnline.setBounds(380, 30, 30, 30);
		add(lblNumOnline);
		
		JButton btnNewButton = new JButton("Thiết lập trò chơi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SettingDialog dlg = new SettingDialog();
				dlg.setLocationRelativeTo(null);
				dlg.setModal(true);
				dlg.setResizable(false);
				dlg.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	
		btnNewButton.setBounds(440, 30, 130, 30);
		btnNewButton.setFocusable(false);
		btnNewButton.addMouseListener(this);
//		btnNewButton.getModel()
		add(btnNewButton);
		
		tbModelPlayer = new DefaultTableModel();
		tbModelPlayer.setColumnIdentifiers(new Object[] { "Id", "Tên người chơi", "Tên tài khoản", "Trạng thái"});
		this.tbPlayer = new JTable(tbModelPlayer){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		this.tbPlayer.setRowHeight(25);
		this.tbPlayer.getColumnModel().getColumn(1).setPreferredWidth(200);
		this.tbPlayer.getColumnModel().getColumn(2).setPreferredWidth(150);

		this.tbPlayer.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		this.tbPlayer.getColumnModel().getColumn(3).setCellRenderer(customRenderer);
		
		this.LoadPlayerOnline();
		
		
		scrollPane = new JScrollPane(this.tbPlayer);
		scrollPane.setBounds(30, 80, 540, 165);
		scrollPane.getViewport().setBackground(Color.WHITE);
		add(scrollPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(30, 311, 540, 229);
		add(tabbedPane);

		this.tbModelGameRank = new DefaultTableModel();
		this.tbModelGameRank.setColumnIdentifiers(new Object[] { "Hạng", "Tên người chơi", "Tên tài khoản", "Số trận"});
		
		this.tbModelWinRank = new DefaultTableModel();
		this.tbModelWinRank.setColumnIdentifiers(new Object[] { "Hạng", "Tên người chơi", "Tên tài khoản", "Số trận thắng"});
		
		this.tbModelWinSQRank = new DefaultTableModel();
		this.tbModelWinSQRank.setColumnIdentifiers(new Object[] { "Hạng", "Tên người chơi", "Tên tài khoản", "Chuỗi thắng"});
		
		this.tbGameRank = new JTable(this.tbModelGameRank){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		this.tbGameRank.setRowHeight(25);
		this.tbGameRank.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		this.tbGameRank.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		this.tbGameRank.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

		this.tbGameRank.getColumnModel().getColumn(1).setPreferredWidth(200);
		this.tbGameRank.getColumnModel().getColumn(2).setPreferredWidth(100);

		
		this.tbWinRank = new JTable(this.tbModelWinRank){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		this.tbWinRank.setRowHeight(25);
		this.tbWinRank.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		this.tbWinRank.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		this.tbWinRank.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		this.tbWinRank.getColumnModel().getColumn(1).setPreferredWidth(200);
		this.tbWinRank.getColumnModel().getColumn(2).setPreferredWidth(100);
		
		this.tbWinSQRank = new JTable(this.tbModelWinSQRank){
			private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		this.tbWinSQRank.setRowHeight(25);
		this.tbWinSQRank.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		this.tbWinSQRank.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		this.tbWinSQRank.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		this.tbWinSQRank.getColumnModel().getColumn(1).setPreferredWidth(200);
		this.tbWinSQRank.getColumnModel().getColumn(2).setPreferredWidth(100);
		this.loadRank(arr);
		scrollPane_1 = new JScrollPane(this.tbGameRank);
		scrollPane_1.getViewport().setBackground(Color.WHITE);
		tabbedPane.addTab("Tham gia", null, scrollPane_1, null);
		
		scrollPane_2 = new JScrollPane(this.tbWinRank);
		scrollPane_2.getViewport().setBackground(Color.WHITE);
		tabbedPane.addTab("Thắng", null, scrollPane_2, null);
		
		scrollPane_3 = new JScrollPane(this.tbWinSQRank);
		scrollPane_3.getViewport().setBackground(Color.WHITE);
		tabbedPane.addTab("Chuỗi thắng", null, scrollPane_3, null);
		JLabel lblNewLabel_3 = new JLabel("Bảng xếp hạng");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(30, 270, 130, 30);
		add(lblNewLabel_3);
		
		Image refresh_icon = new ImageIcon("./img/refresh.png").getImage().getScaledInstance(31,35, java.awt.Image.SCALE_SMOOTH);
		JButton btnRefresh = new JButton();
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				arr = bus.ReadAll();
				loadRank(arr);
			}
		});
		btnRefresh.setBounds(540, 270, 30, 30);
		btnRefresh.setBorder(null);
//		btnRefresh.setOpaque(false);
		btnRefresh.setBackground(null);
		btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnRefresh.setIcon(new ImageIcon(refresh_icon));
		add(btnRefresh);
		
	}
	public void LoadPlayerOnline() {
		this.RemoveTableData(this.tbPlayer);
		if(PlayerActive == null || PlayerActive.size() == 0) {
			this.remove(this.scrollPane);
			JPanel pnEmpty = new JPanel();
			pnEmpty.setBounds(30, 80, 540, 150);
			
			JLabel lbEmpty = new JLabel("Không tìm thấy người chơi nào");
			pnEmpty.add(lbEmpty);
			this.add(pnEmpty);
		}
		else {
			int n = PlayerActive.size();
			String status = "Offline";
			if(Server.clients == null ||Server.clients.size() == 0) {
				status = "Offline";
			}
			for (int i = 0; i < n; i++) {
				status = "Offline";
				if(Server.isOnlinePlayer(PlayerActive.get(i).getUsername())) {
					status = "Online";
				}
				Object[] row = { PlayerActive.get(i).getId(), PlayerActive.get(i).getName(),PlayerActive.get(i).getUsername(), status };
				tbModelPlayer.addRow(row);				
			}
			this.tbPlayer.setModel(tbModelPlayer);
		}
		
	}
	public void loadActive(ArrayList<Player> data) {
		PlayerActive.clear();
		for(Player p: data) {
			if(p.getStatus()==0) {
				PlayerActive.add(p);
			}
		}
	}
	public void loadRank(ArrayList<Player> data) {
		
		this.RemoveTableData(this.tbGameRank);
		this.RemoveTableData(this.tbWinRank);
		this.RemoveTableData(this.tbWinSQRank);
		
		data.sort(Comparator.comparing(Player::getTotalGame).reversed());
		int top = 10;
//		set top: 3,5,10
		if(data.size() < 10 && data.size() > 5) {
			top = 5;
		}
		else {
			if(data.size() <= 5) {
				top = 3;
			}
		}
//		load number game join
		for (int i = 0; i < top; i++) {
			Object[] row = {i+1, data.get(i).getName(),data.get(i).getUsername(),data.get(i).getTotalGame() };
			this.tbModelGameRank.addRow(row);
		}
		this.tbGameRank.setModel(this.tbModelGameRank);
		
//		load win game
		data.sort(Comparator.comparing(Player::getWins).reversed());
		for (int i = 0; i < top; i++) {
			Object[] row = {i+1, data.get(i).getName(),data.get(i).getUsername(),data.get(i).getWins() };
			this.tbModelWinRank.addRow(row);
		}
		this.tbWinRank.setModel(this.tbModelWinRank);
//		load win sequence game
		data.sort(Comparator.comparing(Player::getMaxWinSequence).reversed());
		for (int i = 0; i < top; i++) {
			Object[] row = { i+1, data.get(i).getName(),data.get(i).getUsername(),data.get(i).getMaxWinSequence()};
			this.tbModelWinSQRank.addRow(row);
		}
		this.tbWinSQRank.setModel(this.tbModelWinSQRank);
		
	}
	public void RemoveTableData(JTable jtb) {
		DefaultTableModel tbModel = (DefaultTableModel) jtb.getModel();
		tbModel.setRowCount(0);
	}
	public static void updatePlayerOnline(Player p) {
		String status = "Offline";
		int i = 0;
		boolean flag = true;
		if(p == null) return ; 
		if(Server.isOnlinePlayer(p.getUsername())) {
			status = "Online";
		}
		Object[] row = { p.getId(), p.getName(),p.getUsername(), status };
		int n = PlayerActive.size() - 1;
		while(flag) {
			if(i > n) {
				flag = false;
			}
			else {
				if(PlayerActive.get(i).getId().equals(p.getId())) {
					flag = false;
				}
				else {
					i++;
				}
			}	
		}
		for(int j = 0; j < tbModelPlayer.getColumnCount();j++) {
			tbModelPlayer.setValueAt(row[j], i, j);
		}
		
		tbModelPlayer.fireTableDataChanged();
		
		lblNumOnline.setText(Server.countPlayerOnline()+"");
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		JButton a=(JButton) arg0.getSource();
	
		a.setForeground(Color.WHITE);
		a.setBackground(new Color(8, 87, 40).brighter());
		
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		JButton a=(JButton) e.getSource();
		a.setBackground(Color.white);
		a.setForeground(Color.black);
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
//		JButton a=(JButton) arg0.getSource();		
//		a.setForeground(Color.WHITE);
//		a.setBackground(new Color(8, 87, 40).brighter());
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		JButton a=(JButton) arg0.getSource();
		a.setBackground(new Color(8, 87, 40).brighter());

		a.setForeground(Color.WHITE);
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
			}
	
	
}
