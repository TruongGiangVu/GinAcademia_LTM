package GUI;



import javax.swing.JButton;
import javax.swing.JDialog;

import Model.GameConfig;
import Socket.Server;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import javax.swing.event.ChangeListener;

import BUS.QuestionBUS;

import javax.swing.event.ChangeEvent;


@SuppressWarnings("serial")
public class SettingDialog extends JDialog {

	
	private JSpinner.DefaultEditor editor;
	private QuestionBUS quesBus;
	private boolean questionFlag = true;
	private boolean timeFlag = true;
	private boolean pointFlag = true;

	public GameConfig conf = new GameConfig();

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
	public void init() {
		setTitle("Thiết lập trò chơi");
		setBounds(100, 100, 320, 250);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Số lượng câu hỏi: ");
		lblNewLabel.setBounds(20, 20, 120, 30);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Thời gian trả lời: ");
		lblNewLabel_1.setBounds(20, 70, 100, 30);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Điểm mỗi câu: ");
		lblNewLabel_2.setBounds(20, 120, 100, 30);
		getContentPane().add(lblNewLabel_2);
		
		
		
		JLabel lblNumQuesErr = new JLabel();
//		
		lblNumQuesErr.setVisible(false);
		lblNumQuesErr.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblNumQuesErr.setForeground(Color.RED);
		lblNumQuesErr.setBounds(140, 50, 100, 14);
		getContentPane().add(lblNumQuesErr);
		
		JLabel lblTimeErr = new JLabel();
//		"Tối thiếu 10 giây"
		lblTimeErr.setVisible(false);
		lblTimeErr.setForeground(Color.RED);
		lblTimeErr.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblTimeErr.setBounds(140, 100, 100, 14);
		getContentPane().add(lblTimeErr);
		
		JLabel lblPointErr = new JLabel();
//		"Tối thiếu 100 điểm/câu"
		lblPointErr.setVisible(false);
		lblPointErr.setForeground(Color.RED);
		lblPointErr.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblPointErr.setBounds(140, 150, 116, 14);
		getContentPane().add(lblPointErr);
		
		JSpinner spnQuestion = new JSpinner();
		GameConfig conf = Server.getConfig(); 
		spnQuestion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spnQuestion.setModel(new SpinnerNumberModel(conf.getNumQuestion(), null, null, 1));
		spnQuestion.setBounds(140, 22, 60, 25);
		spnQuestion.setFocusable(false);
		
		editor = ( JSpinner.DefaultEditor ) spnQuestion.getEditor();
//		editor.setFocusable(false);
		editor.getTextField().setFocusable(false);
		editor.getTextField().setEnabled(false);
		editor.getTextField().setDisabledTextColor(Color.BLACK);
		getContentPane().add(spnQuestion);
		spnQuestion.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				
				quesBus = new QuestionBUS();
				int num = quesBus.ReadAll().size();
				int n = (int)spnQuestion.getValue() ;

				if(n < 5) {
					lblNumQuesErr.setText("Tối thiếu 5 câu");
					lblNumQuesErr.setVisible(true);
					questionFlag = false;
				}
				else {
					if(n>num) {
						lblNumQuesErr.setText("Số lượng câu hỏi vượt quá số câu hỏi có hiện tại");
						lblNumQuesErr.setVisible(true);
						questionFlag = true;
					}
					if(n > 30) {
						lblNumQuesErr.setText("Tối đa 30 câu");
						lblNumQuesErr.setVisible(true);
						questionFlag = false;
					}
					else {
						lblNumQuesErr.setVisible(false);
						questionFlag = true;
					}
				}
			}
		});
		JSpinner spnTime = new JSpinner();
		spnTime.setModel(new SpinnerNumberModel(conf.getTime(), null, null, 1));
		spnTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spnTime.setBounds(140, 72, 60, 25);
		((JSpinner.DefaultEditor) spnTime.getEditor()).getTextField().setEnabled(false);
		((JSpinner.DefaultEditor) spnTime.getEditor()).getTextField().setDisabledTextColor(Color.BLACK);
		
		spnTime.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int n = (int)spnTime.getValue() ;
				if(n < 10) {
					lblTimeErr.setText("Tối thiếu 10 giây");
					lblTimeErr.setVisible(true);
					timeFlag = false;
				}
				else {
					if(n > 30) {
						lblTimeErr.setText("Tối đa 30 giây");
						lblTimeErr.setVisible(true);
						timeFlag = false;
					}
					else {
						lblTimeErr.setVisible(false);
						timeFlag = true;
					}
				}
			}
		});
		getContentPane().add(spnTime);
		
		JLabel lblNewLabel_3 = new JLabel("Câu");
		lblNewLabel_3.setBounds(210, 20, 46, 30);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Giây");
		lblNewLabel_3_1.setBounds(210, 70, 46, 30);
		getContentPane().add(lblNewLabel_3_1);
		
		JSpinner spnPoint = new JSpinner();
		spnPoint.setModel(new SpinnerNumberModel(conf.getPoint(), null, null, 100));
		spnPoint.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spnPoint.setBounds(140, 122, 60, 25);
		
		((JSpinner.DefaultEditor) spnPoint.getEditor()).getTextField().setEnabled(false);
		((JSpinner.DefaultEditor) spnPoint.getEditor()).getTextField().setDisabledTextColor(Color.BLACK);;
		spnPoint.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int n = (int)spnPoint.getValue() ;
				System.out.println(n);
				if(n < 100) {
					lblPointErr.setText("Tối thiếu 100 điểm/câu");
					lblPointErr.setVisible(true);
					pointFlag = false;
				}
				else {
					if(n> 1000) {
						lblPointErr.setText("Tối đa 1000 điểm/câu");
						lblPointErr.setVisible(true);
						pointFlag = false;
					}
					else {
						lblPointErr.setVisible(false);
						pointFlag  =true;
					}
				}
			}
		});
		getContentPane().add(spnPoint);
		
		JButton btnNewButton = new JButton("Thiết lập");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(questionFlag && timeFlag && pointFlag) {
					GameConfig conf = new GameConfig((int)spnQuestion.getValue(),(int)spnPoint.getValue(),(int)spnTime.getValue(),2);
					Server.setConfig(conf);
					dispose();
				}
				else {
					String message = "Các thông số chưa đúng";
					JOptionPane.showMessageDialog(getContentPane(), (Object) message, "Lỗi thiết lập", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(56, 170, 100, 23);
		getContentPane().add(btnNewButton);
		JLabel lblNewLabel_3_1_1 = new JLabel("Điểm/câu");
		lblNewLabel_3_1_1.setBounds(210, 120, 60, 30);
		getContentPane().add(lblNewLabel_3_1_1);
		
		JButton btnNewButton_1 = new JButton("Hủy");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(166, 170, 90, 23);
		getContentPane().add(btnNewButton_1);
		
			
	}
}
