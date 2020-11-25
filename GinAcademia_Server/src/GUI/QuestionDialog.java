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
public class QuestionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtQuestion;
	private JLabel lblNewLabel_1;
	private JTextField txtOption1;
	private JLabel lblNewLabel_2;
	private JTextField txtOption2;
	private JLabel lblNewLabel_3;
	private JTextField txtOption3;
	private JLabel lblNewLabel_4;
	private JTextField txtOption4;
	private JLabel lblNewLabel_5;
	private boolean confirm = false;
	private String type= "add";
	
	private JButton okButton;
	private JButton cancelButton;
	private JButton updateButton;
	
	public Question question = new Question();
	private JComboBox<String> txtAnswer;

	public static void main(String[] args) {
		try {
			QuestionDialog dialog = new QuestionDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public QuestionDialog() {
		init();
	}
	public QuestionDialog(Question q, String type) {
		this.question = q;
		this.type = type;
		init();
		this.loadData();
	}
	public void init() {
		setTitle("Câu hỏi");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Câu hỏi:");
			lblNewLabel.setBounds(10, 10, 50, 25);
			contentPanel.add(lblNewLabel);
		}
		
		txtQuestion = new JTextField();
		txtQuestion.setBounds(63, 10, 360, 25);
		contentPanel.add(txtQuestion);
		txtQuestion.setColumns(10);
		
		lblNewLabel_1 = new JLabel("A");
		lblNewLabel_1.setBounds(10, 60, 30, 25);
		contentPanel.add(lblNewLabel_1);
		
		txtOption1 = new JTextField();
		txtOption1.setBounds(45, 60, 150, 25);
		contentPanel.add(txtOption1);
		txtOption1.setColumns(10);
		
		lblNewLabel_2 = new JLabel("B");
		lblNewLabel_2.setBounds(220, 60, 30, 25);
		contentPanel.add(lblNewLabel_2);
		
		txtOption2 = new JTextField();
		txtOption2.setColumns(10);
		txtOption2.setBounds(250, 60, 150, 25);
		contentPanel.add(txtOption2);
		
		lblNewLabel_3 = new JLabel("C");
		lblNewLabel_3.setBounds(10, 110, 30, 25);
		contentPanel.add(lblNewLabel_3);
		
		txtOption3 = new JTextField();
		txtOption3.setColumns(10);
		txtOption3.setBounds(45, 110, 150, 25);
		contentPanel.add(txtOption3);
		
		lblNewLabel_4 = new JLabel("D");
		lblNewLabel_4.setBounds(220, 110, 30, 25);
		contentPanel.add(lblNewLabel_4);
		
		txtOption4 = new JTextField();
		txtOption4.setColumns(10);
		txtOption4.setBounds(250, 110, 150, 25);
		contentPanel.add(txtOption4);
		
		lblNewLabel_5 = new JLabel("Đáp án:");
		lblNewLabel_5.setBounds(30, 168, 45, 25);
		contentPanel.add(lblNewLabel_5);
		
		txtAnswer = new JComboBox<String>();
		txtAnswer.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtAnswer.setModel(new DefaultComboBoxModel<String>(new String[] {"A", "B", "C", "D"}));
		txtAnswer.setBounds(85, 170, 61, 23);
		contentPanel.add(txtAnswer);
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
					updateButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							viewData(true);
							 enableData(true);
							
						}
					});
					updateButton.setActionCommand("Update");
					buttonPane.add(updateButton);
					getRootPane().setDefaultButton(updateButton);
				}
				okButton = new JButton(str);
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
		if(type.equals("view")) {
			this.viewData(false);
			 enableData(false);
		}	
	}
	private Question getData() {
		Question p = new Question();
		p.setId(this.question.getId());
		p.setQuestion(this.txtQuestion.getText());
		ArrayList<Option> options = new ArrayList<Option>();
		
		options.add(new Option(1,this.txtOption1.getText() ));
		options.add(new Option(2,this.txtOption2.getText() ));
		options.add(new Option(3,this.txtOption3.getText() ));
		options.add(new Option(4,this.txtOption4.getText() ));
		p.setOptions(options);
		p.setAnswer( this.txtAnswer.getSelectedIndex() + 1 );
		return p;
	}
	private void loadData() {
		this.txtQuestion.setText(this.question.getQuestion());
		this.txtOption1.setText(this.question.getOptions().get(0).Option);
		this.txtOption2.setText(this.question.getOptions().get(1).Option);
		this.txtOption3.setText(this.question.getOptions().get(2).Option);
		this.txtOption4.setText(this.question.getOptions().get(3).Option);
		this.txtAnswer.setSelectedIndex(this.question.getAnswer()-1);
	}
	public Question getConfirm() {
		if(this.confirm == true) {
			return this.getData();
		}
		else return null;
	}
	public void enableData(boolean isUpdate) {
		this.txtQuestion.setEnabled(isUpdate);
		this.txtOption1.setEnabled(isUpdate);
		this.txtOption2.setEnabled(isUpdate);
		this.txtOption3.setEnabled(isUpdate);
		this.txtOption4.setEnabled(isUpdate);
		this.txtAnswer.setEnabled(isUpdate);
	}
	public void viewData(boolean isUpdate) {
		this.updateButton.setVisible(!isUpdate);
		
		this.cancelButton.setVisible(isUpdate);
		this.okButton.setVisible(isUpdate);
		
	}
}
