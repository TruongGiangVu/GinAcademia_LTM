package GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class PlayerPanel extends JPanel {
	DefaultTableModel tbModelPlayer;
	JTable tbPlayer;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public PlayerPanel() {
		setBackground(Color.WHITE);
		setLayout(null);
		 
		JLabel lblNewLabel = new JLabel("Danh sách người chơi");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(30, 15, 365, 40);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(30, 65, 265, 25);
		add(textField);
		
		JButton btnSearch = new JButton("Tìm kiếm");
		btnSearch.setBounds(305, 65, 90, 25);
		add(btnSearch);
		
		JButton btnI = new JButton("X");
		btnI.setForeground(new Color(255, 0, 0));
		btnI.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnI.setBorder(null);
		btnI.setBackground(null);
		btnI.setBounds(520, 65, 50, 25);
		add(btnI);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 120, 540, 400);
		add(scrollPane);

	}
}
