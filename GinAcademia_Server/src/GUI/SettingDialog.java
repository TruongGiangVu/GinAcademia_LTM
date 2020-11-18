package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Option;
import Model.Question;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SettingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private boolean confirm = false;
	private String type= "add";
	
	private JButton okButton;
	private JButton cancelButton;
	private JButton updateButton;
	
	public Question question = new Question();

	public static void main(String[] args) {
		try {
			SettingDialog dialog = new SettingDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SettingDialog() {
		init();
	}
	public SettingDialog(Question q, String type) {
		this.question = q;
		this.type = type;
		init();
	}
	public void init() {
		setTitle("Người chơi");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				String str = "";
				if(type.equals("add")) str = "Thêm";
				else {
					str = "Cập nhật";
					this.updateButton = new JButton("Chỉnh sửa");
					
					buttonPane.add(updateButton);
					getRootPane().setDefaultButton(updateButton);
				}
				okButton = new JButton("Xác nhận");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						confirm = true;
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Hủy");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						confirm = false;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
			
	}

}
