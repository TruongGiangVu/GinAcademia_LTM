package GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BUS.PlayerBUS;
import Model.Player;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Image;
import java.util.ArrayList;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class PlayerPanel extends JPanel implements ActionListener {
	DefaultTableModel tbModelPlayer;
	JTable tbPlayer;
	ArrayList<Player> arr = new ArrayList<Player>();
	ArrayList<Player> searchArr;
	PlayerBUS bus = new PlayerBUS();
	private JButton btnSearch;
	private JButton btnBlock;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public PlayerPanel() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
			@Override
            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) {
                Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
                c.setFont(new Font("Sans Serif", Font.BOLD, 12));
                c.setForeground(((String) value) == "Tốt" ? Color.GREEN : Color.RED);
                return c;
            }
		};
		customRenderer.setHorizontalAlignment(JLabel.CENTER);
		setBackground(Color.WHITE);
		setLayout(null);
		this.arr = bus.ReadAll();
		JLabel lblNewLabel = new JLabel("Danh sách người chơi");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(30, 15, 365, 40);
		add(lblNewLabel);
		
		this.tbModelPlayer = new DefaultTableModel();
	
		this.tbModelPlayer.setColumnIdentifiers(new Object[] { "Id", "Tên người chơi", "Tên tài khoản","Email", "Trạng thái"});
		this.tbPlayer = new JTable(this.tbModelPlayer);
		this.tbPlayer.setRowHeight(25);
		this.tbPlayer.getColumnModel().getColumn(1).setPreferredWidth(200);
		this.tbPlayer.getColumnModel().getColumn(2).setPreferredWidth(100);
		this.tbPlayer.getColumnModel().getColumn(3).setPreferredWidth(150);

		this.tbPlayer.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		this.tbPlayer.getColumnModel().getColumn(4).setCellRenderer(customRenderer);
		this.loadData(arr);
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String txt = textField.getText();
				searchData(txt); 
			}
		});
		textField.setColumns(10);
		textField.setBounds(30, 65, 265, 25);
//		textField.setFocusable(false);
		add(textField);
		Image scaled = new ImageIcon("./img/search_icon.png").getImage().getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);

		btnSearch = new JButton();
		
		btnSearch.setBounds(305, 65, 40, 25);
		btnSearch.setBorder(null);
		btnSearch.setOpaque(false);
		btnSearch.setBackground(null);
		btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSearch.setIcon(new ImageIcon(scaled));
		

		add(btnSearch);
		Image block_icon = new ImageIcon("./img/block-icon.jpg").getImage().getScaledInstance(25,25, java.awt.Image.SCALE_SMOOTH);

		btnBlock = new JButton("Khóa");
		btnBlock.setBackground(Color.WHITE);
		btnBlock.setHorizontalAlignment(SwingConstants.RIGHT);
		btnBlock.setFocusable(false);
		btnBlock.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBlock.setBounds(475, 65, 95, 25);
		btnBlock.setIcon(new ImageIcon(block_icon));
		add(btnBlock);
		
		btnBlock.addActionListener(this);
		btnSearch.addActionListener(this);
		JScrollPane scrollPane = new JScrollPane(this.tbPlayer);
		scrollPane.setBounds(30, 120, 540, 400);
		add(scrollPane);

	}
	public void searchData(String searchQuerry) {
		if(searchQuerry.isEmpty() || searchQuerry.trim().equals("") || searchQuerry == null) {
			this.loadData(arr);
		}
		else {
			searchArr = new ArrayList<Player>();
			for (Player p : arr) {
				if (p.getUsername().contains(searchQuerry) || p.getEmail().contains(searchQuerry) || p.getName().contains(searchQuerry) || "Tốt".contains(searchQuerry) || "Khóa".contains(searchQuerry)) {
					searchArr.add(p);
				}
			}
			this.loadData(searchArr);
		}
		
	}
	public void loadData(ArrayList<Player> data) {
		this.RemoveTableData();
		int n = data.size();
		if(n > 0) {
			for(int i=0;i<n;i++) {
				String temp = "";
				if(data.get(i).getStatus() == 0) {
					temp = "Tốt";
				}
				else {
					temp = "Khóa";
				}
				Object[] row = { data.get(i).getId(), data.get(i).getName(),data.get(i).getUsername(),data.get(i).getEmail(),temp};
				tbModelPlayer.addRow(row);
			}
			this.tbPlayer.setModel(tbModelPlayer);
		}
		else {
			System.out.println("False");
		}
	}
	public void UpdateData(int i, String status) {
		Object[] row = {arr.get(i).getId(), arr.get(i).getName(),arr.get(i).getUsername(),arr.get(i).getEmail(),status};
		for(int j = 0; j < this.tbModelPlayer.getColumnCount();j++) {
			this.tbModelPlayer.setValueAt(row[j], i, j);
		}
		this.tbModelPlayer.fireTableDataChanged();
	}
	public void RemoveTableData() {
		this.tbModelPlayer = (DefaultTableModel) this.tbPlayer.getModel();
		this.tbModelPlayer.setRowCount(0);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if (source == this.btnBlock) {
			if(this.tbPlayer.getSelectionModel().isSelectionEmpty()) {
			}
			else {
				this.blockPlayer();
			}
			
		} else if (source == this.btnSearch)
		{
			String txt = textField.getText();
			this.searchData(txt);
		}
	}
	public void blockPlayer() {
		int ind = this.tbPlayer.getSelectedRow();
		Player p = bus.getPlayerById(this.tbPlayer.getValueAt(ind,0).toString());
		
		String stt = "Khóa";
		String mess = "";
		int status = 0;
		if(p != null) {
			if(p.getStatus() == 1) {
				stt = "Tốt";
				status = 0;
				mess = "Bạn muốn mở khóa tài khoản" + p.getUsername() + " ?";
			}
			else {
				stt = "Khóa";
				status = 1;
				mess = "Bạn muốn khóa tài khoản " + p.getUsername() + " ?";
			}
			int result = JOptionPane.showConfirmDialog(this, (Object) mess);
			if(result == 0) {
				p.setStatus(status);
				bus.update(p);
				arr.set(ind,p);
				this.UpdateData(ind, stt);
			}

		}
				
	}
	
}
