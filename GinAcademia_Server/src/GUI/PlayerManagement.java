package GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class PlayerManagement extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public PlayerManagement() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Danh sách người chơi");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(30, 15, 365, 40);
		add(lblNewLabel);
		
		JButton btnAdd = new JButton("+");
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAdd.setBackground(new Color(0, 153, 0));
		btnAdd.setBounds(30, 70, 50, 30);
		add(btnAdd);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(30, 115, 265, 25);
		add(textField);
		
		JButton btnSearch = new JButton("Tìm kiếm");
		btnSearch.setBounds(305, 115, 90, 25);
		add(btnSearch);
		
		JButton btnI = new JButton("i");
		btnI.setForeground(Color.BLACK);
		btnI.setFont(new Font(".VnAristote", Font.BOLD, 20));
		btnI.setBorder(null);
		btnI.setBackground(null);
		btnI.setBounds(520, 115, 50, 25);
		add(btnI);
		
		JPanel panelTable = new JPanel();
		panelTable.setBounds(30, 155, 540, 400);
		add(panelTable);
		panelTable.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane((Component) null);
		panelTable.add(scrollPane);

	}
}
