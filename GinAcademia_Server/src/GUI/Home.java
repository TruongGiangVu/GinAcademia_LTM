package GUI;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.Timer;
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

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class Home extends JPanel implements MouseListener {
	DefaultTableModel tbModelPlayer;
	JTable tbPlayer;
	DefaultTableModel tbModelGameRank;
	DefaultTableModel tbModelWinRank;
	DefaultTableModel tbModelWinSQRank;
	JTable tbGameRank;
	JTable tbWinRank;
	JTable tbWinSQRank;
	private Timer timer;
	public final static int INTERVAL = 5000;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private PlayerBUS bus = new PlayerBUS();
	ArrayList<Player> arr = new ArrayList<Player>();
	
	/**
	 * Create the panel.
	 */
	public Home() {
		
		this.arr = bus.ReadAll();

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		this.setSize(600, 600);
		
		JLabel lblNewLabel = new JLabel("Tổng người chơi: ");
		lblNewLabel.setBounds(30, 30, 105, 30);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(String.valueOf(this.arr.size()));
		lblNewLabel_1.setBounds(145, 30, 30, 30);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Số người chơi đang online: ");
		lblNewLabel_2.setBounds(200, 30, 170, 30);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("0");
		lblNewLabel_1_1.setBounds(380, 30, 30, 30);
		add(lblNewLabel_1_1);
		
		JButton btnNewButton = new JButton("Thiết lập trò chơi");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	
		btnNewButton.setBounds(440, 30, 130, 30);
		btnNewButton.setFocusable(false);
		btnNewButton.addMouseListener(this);
//		btnNewButton.getModel()
		add(btnNewButton);
		
		this.tbModelPlayer = new DefaultTableModel();
		this.tbModelPlayer.setColumnIdentifiers(new Object[] { "Id", "Tên người chơi", "Tên tài khoản", "Trạng thái"});
		this.tbPlayer = new JTable(this.tbModelPlayer);
		this.tbPlayer.setRowHeight(25);
		this.tbPlayer.getColumnModel().getColumn(1).setPreferredWidth(200);
		this.tbPlayer.getColumnModel().getColumn(2).setPreferredWidth(150);

		this.tbPlayer.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		this.tbPlayer.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

//		this.tbPlayer.getColumnModel().getColumn(3).setCellEditor(cellEditor);
		
		this.LoadPlayerOnline();
		scrollPane = new JScrollPane(this.tbPlayer);
		scrollPane.setBounds(30, 80, 540, 150);
		scrollPane.getViewport().setBackground(Color.WHITE);
		add(scrollPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(30, 311, 540, 229);
		add(tabbedPane);
		timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

			   //Refresh the panel
			       LoadPlayerOnline();
			       System.out.println("Refresh table");
//			       if (Server.clients == null || Server.clients.size() == 0) {
//			    	   timer.stop();
//				       System.out.println("Stop refresh");
//
//			       }
			    }    
			});

			timer.start();
		this.tbModelGameRank = new DefaultTableModel();
		this.tbModelGameRank.setColumnIdentifiers(new Object[] { "Id", "Tên người chơi", "Tên tài khoản", "Số trận", "Hạng"});
//		this.tbModelGameRan
		
		this.tbModelWinRank = new DefaultTableModel();
		this.tbModelWinRank.setColumnIdentifiers(new Object[] { "Id", "Tên người chơi", "Tên tài khoản", "Số trận thắng", "Hạng"});
		
		this.tbModelWinSQRank = new DefaultTableModel();
		this.tbModelWinSQRank.setColumnIdentifiers(new Object[] { "Id", "Tên người chơi", "Tên tài khoản", "Chuỗi thắng", "Hạng"});
		
		this.tbGameRank = new JTable(this.tbModelGameRank);
		this.tbGameRank.setRowHeight(25);
		this.tbGameRank.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		this.tbGameRank.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		this.tbGameRank.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

		this.tbGameRank.getColumnModel().getColumn(1).setPreferredWidth(200);
		this.tbGameRank.getColumnModel().getColumn(2).setPreferredWidth(100);

		
		this.tbWinRank = new JTable(this.tbModelWinRank);
		this.tbWinRank.setRowHeight(25);
		this.tbWinRank.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		this.tbWinRank.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		this.tbWinRank.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		this.tbWinRank.getColumnModel().getColumn(1).setPreferredWidth(200);
		this.tbWinRank.getColumnModel().getColumn(2).setPreferredWidth(100);
		
		this.tbWinSQRank = new JTable(this.tbModelWinSQRank);
		this.tbWinSQRank.setRowHeight(25);
		this.tbWinSQRank.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		this.tbWinSQRank.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		this.tbWinSQRank.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		this.tbWinSQRank.getColumnModel().getColumn(1).setPreferredWidth(200);
		this.tbWinSQRank.getColumnModel().getColumn(2).setPreferredWidth(100);
		this.LoadRank();
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
		
	}
	public void LoadPlayerOnline() {
		this.RemoveTableData(this.tbPlayer);
		int n = this.arr.size();
		if(n==0) {
			this.remove(this.scrollPane);
			JPanel pnEmpty = new JPanel();
			pnEmpty.setBounds(30, 80, 540, 150);
			
			JLabel lbEmpty = new JLabel("Không tìm thấy người chơi nào");
			pnEmpty.add(lbEmpty);
			this.add(pnEmpty);
		}
		else {
			for (int i = 0; i < n; i++) {
				String temp = "";
				if(Server.clients == null) {
					temp = "Offline";
					Object[] row = { arr.get(i).getId(), arr.get(i).getName(),arr.get(i).getUsername(), temp };
					this.tbModelPlayer.addRow(row);
				}
				else {
					if(Server.clients.size()==0) {
						temp = "Offline";
						Object[] row = { arr.get(i).getId(), arr.get(i).getName(),arr.get(i).getUsername(), temp };
						this.tbModelPlayer.addRow(row);
						
					}
					else {
						System.out.println("vòng "+i);
						if(Server.isOnlinePlayer(arr.get(i).getUsername())) {
							temp = "Online";
						}
						else {

							temp = "Offline";
						}
						Object[] row = { arr.get(i).getId(), arr.get(i).getName(),arr.get(i).getUsername(), temp };
						this.tbModelPlayer.addRow(row);
					}
					
				}
				
				
			}
			this.tbPlayer.setModel(this.tbModelPlayer);
		}
		
	}
	
	public void LoadRank() {
		this.RemoveTableData(this.tbGameRank);
		this.RemoveTableData(this.tbWinRank);
		this.RemoveTableData(this.tbWinSQRank);
		ArrayList<Player> temp = this.arr;
		temp.sort(Comparator.comparing(Player::getTotalGame).reversed());
		int top = 10;
		if(this.arr.size() < 10 && this.arr.size() > 5) {
			top = 5;
		}
		else {
			if(this.arr.size() < 5) {
				top = 3;
			}
		}
		for (int i = 0; i < top; i++) {
			Object[] row = { arr.get(i).getId(), arr.get(i).getName(),arr.get(i).getUsername(),arr.get(i).getTotalGame(), i+1 };
			this.tbModelGameRank.addRow(row);
		}
		this.tbGameRank.setModel(this.tbModelGameRank);
		
		temp.sort(Comparator.comparing(Player::getWins).reversed());
		for (int i = 0; i < top; i++) {
			Object[] row = { arr.get(i).getId(), arr.get(i).getName(),arr.get(i).getUsername(),arr.get(i).getWins(), i+1 };
			this.tbModelWinRank.addRow(row);
		}
		this.tbWinRank.setModel(this.tbModelWinRank);
		
		temp.sort(Comparator.comparing(Player::getMaxWinSequence).reversed());
		for (int i = 0; i < top; i++) {
			Object[] row = { arr.get(i).getId(), arr.get(i).getName(),arr.get(i).getUsername(),arr.get(i).getMaxWinSequence(), i+1 };
			this.tbModelWinSQRank.addRow(row);
		}
		this.tbWinSQRank.setModel(this.tbModelWinSQRank);
	}
	public void RemoveTableData(JTable jtb) {
		DefaultTableModel tbModel = (DefaultTableModel) jtb.getModel();
		tbModel.setRowCount(0);
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
