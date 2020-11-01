package GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.GridLayout;

import java.util.ArrayList;

import Model.Player;
import Module.RankTableModel;
import Module.MyPanel;
import Server.BUS.PlayerBUS;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class Rank extends MyPanel {
	
	private PlayerBUS bus = new PlayerBUS();
	private JTable table;
	private JPanel panelTable;
	private DefaultTableCellRenderer cellRenderer;
	public int orderRank = 0;
	private JLabel lblNewLabel;
	private JLabel lblYourRank;
	private JLabel lblNewLabel_2;
	private JComboBox comboBox;
	private Player player;

	/**
	 * Create the panel.
	 */
	public Rank(Player p) {
		this.player = p;
		
		this.setSize(600,600);
		//socket
		ArrayList<Player> listplayer = bus.Read();
		
        RankTableModel tableModel = new RankTableModel(listplayer);
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
        
//        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
//        sorter.toggleSortOrder(0);
//        sorter.addRowSorterListener(new RowSorterListener() {
//
//			@Override
//			public void sorterChanged(RowSorterEvent e) {
//				// TODO Auto-generated method stub
//				int indexOfNoColumn = 0;
//				if(Rank.this.orderRank == 0) {
//					for (int i = 0; i < table.getRowCount(); i++) {
//			            table.setValueAt(i + 1, i, indexOfNoColumn);
//			        }
//				}
//				else
//				{
//					int n = table.getRowCount();
//					for (int i = 0; i < n; i++) {
//			            table.setValueAt(n-i, i, indexOfNoColumn);
//			        }
//				}	
//				Rank.this.orderRank = 1-Rank.this.orderRank;
//			}
//        	
//        });
//        table.setRowSorter(sorter);
        

        panelTable = new JPanel();
        panelTable.setBounds(15, 150, 570, 400);
        panelTable.setLayout(new GridLayout(0, 1, 0, 0));
        JScrollPane scrollPane = new JScrollPane(table);
        panelTable.add(scrollPane);
        
        
        add(panelTable);
        
        lblNewLabel = new JLabel("Hạng của bạn:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(27, 26, 101, 24);
        add(lblNewLabel);
        
        lblYourRank = new JLabel("0");
        lblYourRank.setFont(new Font("Tahoma", Font.ITALIC, 14));
        lblYourRank.setBounds(136, 33, 38, 14);
        lblYourRank.setText(tableModel.getRankById(this.player.getId())+"");
        add(lblYourRank);
        
        lblNewLabel_2 = new JLabel("Hạng:");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_2.setBounds(27, 104, 63, 24);
        add(lblNewLabel_2);
        
        comboBox = new JComboBox();
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Tăng dần", "Giảm dần"}));
        comboBox.setBounds(100, 101, 101, 30);
        add(comboBox);
        


	}
}
