package GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Model.IQTest;
import Module.MyPanel;
import Socket.Client;
import Socket.Request.SocketRequestIQTest;
import Socket.Response.SocketResponsePlayer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class IQTestPanel extends MyPanel {

	ArrayList<IQTest> arr = new ArrayList<IQTest>();
	int numRight = 0;
	Timer timer;
	int currentQ = 0;
	int numQ;
	private ArrayList<JLabel> arrTxt = new ArrayList<JLabel>();

	private JTextField txtAnswer;
	private JLabel lblTime;
	private JLabel lblQuestion;
	private JButton btnEnter;
	private JLabel lblA;
	private JLabel lblB;
	private JLabel lblC;
	private JLabel lblD;
	private JPanel panelOption;
	private JLabel lblImage;

	public IQTestPanel(Client client) {
		super(client);
		this.setBackground(Color.WHITE);
		this.setSize(600, 600);
		setLayout(null);

		lblTime = new JLabel("2000");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblTime.setBounds(240, 11, 80, 25);
		add(lblTime);

		lblQuestion = new JLabel("question");
		lblQuestion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQuestion.setBorder(new EmptyBorder(0, 6, 0, 0));
		lblQuestion.setBounds(25, 40, 550, 90);
//		lblQuestion.
		add(lblQuestion);

		txtAnswer = new JTextField();
		txtAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getAnswer();
			}
		});
		txtAnswer.setBounds(221, 481, 83, 20);
		add(txtAnswer);
		txtAnswer.setColumns(10);

		btnEnter = new JButton("Chọn");
		btnEnter.setBackground(Color.WHITE);
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getAnswer();
			}
		});
		btnEnter.setBounds(314, 480, 70, 23);
		add(btnEnter);

		lblImage = new JLabel("");
		lblImage.setBackground(Color.WHITE);
		lblImage.setBounds(25, 140, 550, 300);
		add(lblImage);

		panelOption = new JPanel();
		panelOption.setBorder(new EmptyBorder(0, 6, 0, 0));
		panelOption.setBackground(Color.WHITE);
		panelOption.setBounds(25, 140, 550, 300);
		add(panelOption);
		panelOption.setLayout(new GridLayout(0, 1, 0, 0));

		lblA = new JLabel("A");
		panelOption.add(lblA);

		lblB = new JLabel("B");
		panelOption.add(lblB);

		lblC = new JLabel("C");
		panelOption.add(lblC);

		lblD = new JLabel("D");
		panelOption.add(lblD);

		arrTxt.add(lblA);
		arrTxt.add(lblB);
		arrTxt.add(lblC);
		arrTxt.add(lblD);

		JLabel lblNewLabel = new JLabel("Nhập đáp án vào ô trống dưới đây");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(200, 450, 200, 20);
		add(lblNewLabel);
		
		JLabel lblCuTrLi = new JLabel("Câu trả lời chỉ gồm các chữ cái A,B,C,D,E, ... , không phân biệt hoa thường");
		lblCuTrLi.setHorizontalAlignment(SwingConstants.CENTER);
		lblCuTrLi.setBounds(90, 530, 420, 20);
		add(lblCuTrLi);

		this.seedData();
		this.startIQTest();
		this.loadQuestion(this.arr.get(this.currentQ));
	}

	private void seedData() {
		arr.add(new IQTest("00001", "Điền hình còn thiếu vào chỗ trống.", "./img/IQTest/00001.png", null, "F"));
		arr.add(new IQTest("00002", "Điền hình còn thiếu vào chỗ trống.", "./img/IQTest/00002.png", null, "A"));
		arr.add(new IQTest("00003", "Hình tiếp theo sẽ là hình nào?", "./img/IQTest/00003.png", null, "A"));
		arr.add(new IQTest("00004", "Điền hình còn thiếu vào chỗ trống.", "./img/IQTest/00004.png", null, "C"));
		arr.add(new IQTest("00005", "Chọn đáp án đúng.", "./img/IQTest/00005.png", null, "C"));
		arr.add(new IQTest("00006", "Tìm hình còn thiếu trong hình bát giác trên.", "./img/IQTest/00006.png", null,
				"D"));
		arr.add(new IQTest("00007", "Gấp miếng bìa trên ta được hình hộp nào phía dưới?", "./img/IQTest/00007.png",
				null, "D"));
		arr.add(new IQTest("00008", "Hình nào khác với những hình còn lại?", "./img/IQTest/00008.png", null, "B"));
		arr.add(new IQTest("00009", "Tìm hình thích hợp thay cho hình có dấu ?", "./img/IQTest/00009.png", null, "A"));
		arr.add(new IQTest("00010", "Hình nào còn thiếu trong dấu hỏi chấm?", "./img/IQTest/00010.png", null, "C"));
		arr.add(new IQTest("00011", "Hình nào còn thiếu trong dấu hỏi chấm?", "./img/IQTest/00011.png", null, "A"));
		arr.add(new IQTest("00012", "Hình tiếp theo trong dãy hình trên là hình nào trong 4 đáp án A, B, C, D?",
				"./img/IQTest/00012.png", null, "C"));
		arr.add(new IQTest("00013", "Hình tiếp theo trong dãy hình trên là hình nào trong 4 đáp án A, B, C, D?",
				"./img/IQTest/00013.png", null, "C"));
		arr.add(new IQTest("00014", "Hình nào còn thiếu trong dấu hỏi chấm?", "./img/IQTest/00014.png", null, "C"));

		this.numQ = this.arr.size();
	}

	public void startIQTest() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new IQTask(), 0, 1000);
	}

	class IQTask extends TimerTask {
		int time = 20 * 60;

		@Override
		public void run() {
			// set label
			String timeStr = (time / 60) + " : " + (time % 60);
			lblTime.setText(timeStr);
			if (time == 0) {
				// end
			}
			time--;
		}

	}

	public void visuablePanel(boolean isImage) {
		this.lblImage.setVisible(isImage);
		this.panelOption.setVisible(!isImage);
	}

	public void loadQuestion(IQTest q) {
		// set text
		String title = "<html>" + "Câu " + (this.currentQ + 1) + " : " + q.getIQQuestion() + "</html>";
		lblQuestion.setText(title);
		if (q.isImageQuestion()) { // image question
			this.visuablePanel(true);
			this.loadImage(q.getImageQuestion());
		} else { // select question
			this.visuablePanel(false);
			ArrayList<String> symbol = new ArrayList<String>();
			symbol.add("A.");
			symbol.add("B.");
			symbol.add("C.");
			symbol.add("D.");
			for (int i = 0; i < 4; ++i) {
				this.arrTxt.get(i).setText("<html>" + symbol.get(i) + " " + q.getOptions().get(i).Option + "</html>");
			}
		}
	}

	public void getAnswer() {
		String ans = txtAnswer.getText().trim().toUpperCase(); // get answer

		if (ans.equals(this.arr.get(this.currentQ).getAnswer())) { // answer right
			this.numRight++;
			System.out.println(this.currentQ+1);
		}
		// clean answer
		txtAnswer.setText("");
		// load next question
		this.currentQ++;
		if (this.currentQ < this.numQ) // has next question
			this.loadQuestion(this.arr.get(this.currentQ));
		else { // over
			timer.cancel();
			client.sendRequest(new SocketRequestIQTest(this.numQ, this.numRight));
			if (client.checkSend) {
				SocketResponsePlayer response = (SocketResponsePlayer) client.getResponse();
				client.player = response.getPlayer();
				JOptionPane.showMessageDialog(this, "Điểm IQ của bạn là:" + response.getPlayer().getIQPoint());
			} else {
				JOptionPane.showMessageDialog(this, "Kết quả kiểm tra IQ này của bạn sẽ không được lưu lại");
			}
			MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(this);
			parent.clickReturnHome();
			parent.btnHome.setSelected(true);
			parent.btnIQ.setSelected(false);
		}
		this.txtAnswer.requestFocus();
	}

	public void loadImage(String nameImage) {
		ImageIcon icon = new ImageIcon(nameImage); // load the image to a imageIcon
		Image image = icon.getImage(); // transform it
		Image newimg = image.getScaledInstance(550, 300, java.awt.Image.SCALE_SMOOTH); // scale it the// smooth way
		icon = new ImageIcon(newimg);
		lblImage.setIcon(icon);
	}
}
