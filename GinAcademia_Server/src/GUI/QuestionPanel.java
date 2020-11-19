package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

import Model.Question;
import BUS.QuestionBUS;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;

@SuppressWarnings("serial")
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
	private JLabel lblNewLabel;

	public QuestionPanel() {
		setBackground(Color.WHITE);
		this.setSize(600, 600);
		setLayout(null);
		this.arr = bus.ReadAll();
		arr = bus.sort(arr, false);

		panelTable = new JPanel();
		panelTable.setBounds(30, 155, 540, 400);
		add(panelTable);

		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(new Object[] { "Id", "Câu hỏi", "Câu A", "Câu B", "Câu C", "Câu D", "Đáp án" });
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(25);

		this.RefreshTableData();

		JScrollPane scrollPane = new JScrollPane(table);
		panelTable.setLayout(new GridLayout(0, 1, 0, 0));
		panelTable.add(scrollPane);

		btnAdd = new JButton("+");
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAdd.setBackground(new Color(0, 153, 0));
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBounds(30, 70, 50, 30);
		add(btnAdd);

		btnAdd.addActionListener(this);

		btnView = new JButton("i");
		btnView.setFont(new Font(".VnAristote", Font.BOLD, 20));
		btnView.setForeground(Color.BLACK);
		btnView.setBorder(null);
		btnView.setBackground(null);
		btnView.addActionListener(this);
		btnView.setBounds(520, 115, 50, 25);
		add(btnView);

		txtSearch = new JTextField();
		txtSearch.setBounds(30, 115, 265, 25);
		add(txtSearch);
		txtSearch.setColumns(10);

		btnSearch = new JButton("Tìm kiếm");
		btnSearch.setBounds(305, 115, 90, 25);
		btnSearch.addActionListener(this);
		add(btnSearch);
		
		lblNewLabel = new JLabel("Danh sách câu hỏi");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(30, 15, 365, 40);
		add(lblNewLabel);
	}

	public void RefreshTableData() {
		this.RemoveTableData();
//		tableModel.removeRow();
//		tableModel.remove
		int n = arr.size();
//	    Object[][] rows = new Object[n][8];
		for (int i = 0; i < n; i++) {
			Object[] row = { arr.get(i).getId(), arr.get(i).getQuestion(), arr.get(i).getOptions().get(0).Option,
					arr.get(i).getOptions().get(1).Option, arr.get(i).getOptions().get(2).Option,
					arr.get(i).getOptions().get(3).Option, arr.get(i).getAnswerString() };
			this.tableModel.addRow(row);
		}
		this.SetTableData();
	}
	public void AddData() {
		int n = arr.size() - 1;
		Object[] row = {arr.get(n).getId(), arr.get(n).getQuestion(), arr.get(n).getOptions().get(0).Option,
				arr.get(n).getOptions().get(1).Option, arr.get(n).getOptions().get(2).Option,
				arr.get(n).getOptions().get(3).Option, arr.get(n).getAnswerString() };
		this.tableModel.addRow(row);
		this.tableModel.fireTableDataChanged();
	}
	public void UpdateData(int i, Question p) {
		Object[] row = {arr.get(i).getId(), arr.get(i).getQuestion(), arr.get(i).getOptions().get(0).Option,
				arr.get(i).getOptions().get(1).Option, arr.get(i).getOptions().get(2).Option,
				arr.get(i).getOptions().get(3).Option, arr.get(i).getAnswerString() };
		for(int j = 0; j < this.tableModel.getColumnCount();j++) {
			this.tableModel.setValueAt(row[j], i, j);
		}
		this.tableModel.fireTableDataChanged();
	}
	public void RefreshTableData(ArrayList<Question> arrl) {
		this.RemoveTableData();
		int n = arrl.size();
		for (int i = 0; i < n; i++) {
			Object[] row = { arrl.get(i).getId(), arrl.get(i).getQuestion(), arrl.get(i).getOptions().get(0).Option,
					arrl.get(i).getOptions().get(1).Option, arrl.get(i).getOptions().get(2).Option,
					arrl.get(i).getOptions().get(3).Option, arrl.get(i).getAnswerString() };
			tableModel.addRow(row);
		}
		this.SetTableData();
	}
	public void RemoveTableData() {
		this.tableModel = (DefaultTableModel) this.table.getModel();
		this.tableModel.setRowCount(0);
	}
	public void SetTableData() {
		
		this.table.setModel(this.tableModel);
		this.table.getColumnModel().getColumn(0).setPreferredWidth(20);
		this.table.getColumnModel().getColumn(1).setPreferredWidth(150);
		this.table.getColumnModel().getColumn(2).setPreferredWidth(30);
		this.table.getColumnModel().getColumn(3).setPreferredWidth(30);
		this.table.getColumnModel().getColumn(4).setPreferredWidth(30);
		this.table.getColumnModel().getColumn(5).setPreferredWidth(30);
		this.table.getColumnModel().getColumn(6).setPreferredWidth(20);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if (source == this.btnAdd) {
			this.InsertButton();
		} else if (source == this.btnView)
			this.UpdateButton();
		else if (source == this.btnSearch)
			this.SearchButton();
	}

	public void InsertButton() {
		QuestionDialog dialog = new QuestionDialog();
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		Question p = dialog.getConfirm();
		if (p != null) {
			bus.insert(p);
			arr.add(p);
			this.AddData();
//			this.RefreshTableData();
		}
	}

	public void UpdateButton() {
		int i = table.getSelectedRow();
		if (i >= 0) {
			Question p = bus.getQuestionById(table.getValueAt(i, 0).toString());
			QuestionDialog dialog = new QuestionDialog(p, "view");
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
			p = dialog.getConfirm();
			if (p != null) {
				bus.update(p);
				arr.set(i, p);
				this.UpdateData(i, p);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Xin hãy chọn câu hỏi bạn muốn xem!");
		}

	}

	public void SearchButton() {
		String search = txtSearch.getText();
		if (search == null || search.trim() == "") {
			arr = bus.ReadAll();
		} else {
			arr = bus.searchQuestion(arr, search);
		}
		RefreshTableData(arr);
	}
}
