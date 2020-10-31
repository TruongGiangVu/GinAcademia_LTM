package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import Module.RankTableModel;
import Model.Question;
import BUS.QuestionBUS;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class QuestionPanel extends JPanel implements ActionListener {
	DefaultTableModel tableModel;
	JTable table;
	private JPanel panelTable;
	private QuestionBUS bus = new QuestionBUS();
	ArrayList<Question> arr = new ArrayList<Question>();
	private JButton btnAdd;
	private JButton btnView;
	private JTextField txtSearch;
	private JButton btnSearch;

	/**
	 * Create the panel.
	 */
	public QuestionPanel() {
		this.setSize(600, 600);
		setLayout(null);
		this.arr = bus.ReadAll();
		arr = bus.sort(arr, false);
		
		panelTable = new JPanel();
		panelTable.setBounds(20, 130, 560, 400);
		add(panelTable);

		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(new Object[] { "Id", "Câu hỏi", "Câu A", "Câu B", "Câu C", "Câu D", "Đáp án"});
	    table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(25);
		
		this.RefreshTableData();
		
		JScrollPane scrollPane = new JScrollPane(table);
		panelTable.setLayout(new GridLayout(0, 1, 0, 0));
		panelTable.add(scrollPane);

		btnAdd = new JButton("Thêm mới");
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdd.setBackground(Color.GREEN);
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBounds(20, 41, 101, 30);
		add(btnAdd);
		
		btnAdd.addActionListener(this);
		
		btnView = new JButton("Chi tiết");
		btnView.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnView.setForeground(Color.WHITE);
		btnView.setBackground(Color.BLUE);
		btnView.addActionListener(this);
		btnView.setBounds(280, 90, 100, 25);
		add(btnView);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(20, 91, 150, 25);
		add(txtSearch);
		txtSearch.setColumns(10);
		
		btnSearch = new JButton("Tìm kiếm");
		btnSearch.setBounds(181, 92, 89, 23);
		btnSearch.addActionListener(this);
		add(btnSearch);
	}
	public void RefreshTableData() {
		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(new Object[] { "Id", "Câu hỏi", "Câu A", "Câu B", "Câu C", "Câu D", "Đáp án"});
	    int n = arr.size();
//	    Object[][] rows = new Object[n][8];
	    for (int i = 0; i < n; i++) {
	    	Object[] row = { arr.get(i).getId(), arr.get(i).getQuestion(), arr.get(i).getOptions().get(0).Option,
					arr.get(i).getOptions().get(1).Option, arr.get(i).getOptions().get(2).Option,
					arr.get(i).getOptions().get(3).Option, arr.get(i).getAnswerString()};
	       tableModel.addRow(row);
	    }
	    table.setModel(tableModel);
	    table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
		table.getColumnModel().getColumn(6).setPreferredWidth(20);
	}
	public void RefreshTableData(ArrayList<Question> arrl) {
		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(new Object[] { "Id", "Câu hỏi", "Câu A", "Câu B", "Câu C", "Câu D", "Đáp án"});
	    int n = arrl.size();
	    for (int i = 0; i < n; i++) {
	    	Object[] row = { arrl.get(i).getId(), arrl.get(i).getQuestion(), arrl.get(i).getOptions().get(0).Option,
					arrl.get(i).getOptions().get(1).Option, arrl.get(i).getOptions().get(2).Option,
					arrl.get(i).getOptions().get(3).Option, arrl.get(i).getAnswerString()};
	       tableModel.addRow(row);
	    }
	    table.setModel(tableModel);
	    table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
		table.getColumnModel().getColumn(6).setPreferredWidth(20);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton source = (JButton) e.getSource();
		if(source == this.btnAdd) {
			this.InsertButton();
		}
		else if(source == this.btnView)
			this.UpdateButton();
		else if(source == this.btnSearch)
			this.SearchButton();
	}
	public void InsertButton() {
		QuestionDialog dialog = new QuestionDialog();
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		Question p = dialog.getConfirm();
		if(p != null) {
			bus.insert(p);
			arr.add(p);
			this.RefreshTableData();
		}
	}
	public void UpdateButton() {
		int i = table.getSelectedRow();
		if(i >= 0) {
			Question p = bus.getQuestionById(table.getValueAt(i, 0).toString());
			QuestionDialog dialog = new QuestionDialog(p,"view");
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
			p = dialog.getConfirm();
			if(p != null) {
				bus.update(p);
				arr.set(i, p);
				this.RefreshTableData();
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Xin hãy chọn câu hỏi bạn muốn xem!");
		}
		
	}
	public void SearchButton() {
		String search =txtSearch.getText();
		if(search == null || search.trim() == "") {
			arr = bus.ReadAll();
		}
		else {
			arr = bus.searchQuestion(arr, search );
		}
		RefreshTableData(arr);
	}
}
