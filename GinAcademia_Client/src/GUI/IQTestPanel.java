package GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import Model.IQTest;
import Model.OptionIQ;
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
		lblTime.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblTime.setBounds(286, 11, 80, 25);
		add(lblTime);

		lblQuestion = new JLabel("question");
		lblQuestion.setBounds(25, 40, 550, 90);
//		lblQuestion.
		add(lblQuestion);

		txtAnswer = new JTextField();
		txtAnswer.setBounds(250, 450, 100, 20);
		add(txtAnswer);
		txtAnswer.setColumns(10);

		btnEnter = new JButton("Xác nhận");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getAnswer();
			}
		});
		btnEnter.setBounds(255, 480, 90, 23);
		add(btnEnter);

		lblImage = new JLabel("");
		lblImage.setBounds(25, 140, 550, 300);
		add(lblImage);

		panelOption = new JPanel();
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

		this.seedData();
		this.startIQTest();
		this.loadQuestion(this.arr.get(this.currentQ));
	}

	private void seedData() {
		arr.add(new IQTest("00001",
				"Trong bài test của bạn có một số câu hỏi. Bạn đã trả lời sai 10 câu hỏi. Kết quả thang điểm của bạn chỉ đạt 60%. Vậy bài test của bạn có tất cả bao nhiêu câu hỏi?",
				"", new ArrayList<OptionIQ>(Arrays.asList(new OptionIQ("A", "20"), new OptionIQ("B", "25"),
						new OptionIQ("C", "30"), new OptionIQ("D", "35"))),
				"B"));
		arr.add(new IQTest("00002", "Điền hình còn thiếu vào chỗ trống.", "./img/IQTest/00002.png", null, "A"));
		arr.add(new IQTest("00003", "Hình tiếp theo sẽ là hình nào?", "./img/IQTest/00003.png", null, "A"));
		arr.add(new IQTest("00004", "Trong 4 loại sau đây, loại nào có đặc tính ít giống với 3 loại còn lại?", "",
				new ArrayList<OptionIQ>(Arrays.asList(new OptionIQ("A", "Tỏi"), new OptionIQ("B", "Nho"),
						new OptionIQ("C", "Rau diếp cá"), new OptionIQ("D", "Nấm"))),
				"B"));
		arr.add(new IQTest("00005", "Trời âm u, đất tù mù, có 3 người gù, chung một cây dù. Hỏi người nào ướt?", "",
				new ArrayList<OptionIQ>(
						Arrays.asList(new OptionIQ("A", "Người thứ nhất"), new OptionIQ("B", "Người thứ hai"),
								new OptionIQ("C", "Người thứ ba"), new OptionIQ("D", "Không người nào ướt"))),
				"D"));
		arr.add(new IQTest("00006",
				"Bạn có 84 quả táo đựng trong 12 giỏ, nếu muốn ăn 1/3 số táo trong mỗi giỏ thì bạn cần ít nhất bao nhiêu lần cắn, biết rằng mỗi lần bạn cắn được 1/3 của 1/2 quả táo.",
				"", new ArrayList<OptionIQ>(Arrays.asList(new OptionIQ("A", "168"), new OptionIQ("B", "121"),
						new OptionIQ("C", "144"), new OptionIQ("D", "225"))),
				"A"));
		arr.add(new IQTest("00007",
				"Tám người sơn được 3 cái nhà trong 6 giờ. Hỏi với 12 người sẽ sơn được bao nhiêu cái nhà trong 12 giờ?",
				"", new ArrayList<OptionIQ>(Arrays.asList(new OptionIQ("A", "3 cái"), new OptionIQ("B", "5 cái"),
						new OptionIQ("C", "7 cái"), new OptionIQ("D", "9 cái"))),
				"D"));
		arr.add(new IQTest("00008",
				"Người ta đổ nước vào một bể cạn theo tỷ lệ không đổi, sau 8 giờ thì đổ được 3/5 thể tích của bể. Hỏi cần thêm thời gian là bao lâu nữa để đổ đầy bể nước đó?",
				"",
				new ArrayList<OptionIQ>(
						Arrays.asList(new OptionIQ("A", "5 giờ 30 phút"), new OptionIQ("B", "5 giờ 20 phút"),
								new OptionIQ("C", "4 giờ 48 phút"), new OptionIQ("D", "3 giờ 12 phút"))),
				"B"));
		arr.add(new IQTest("00009", "Sắp xếp các chữ cái: \"HÙCYẬNUC\" bạn được tên", "",
				new ArrayList<OptionIQ>(
						Arrays.asList(new OptionIQ("A", "Một tỉnh thành"), new OptionIQ("B", "Một nhà thơ"),
								new OptionIQ("C", "Một bài hát"), new OptionIQ("D", "Một vị vua"))),
				"B"));
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
		String title = "<html>" + "Câu " + (this.currentQ + 1) + " :" + q.getIQQuestion() + "</html>";
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
		String ans = txtAnswer.getText().trim().toLowerCase(); // get answer

		if (ans.equals(this.arr.get(this.currentQ).getAnswer().toLowerCase())) { // answer right
			this.numRight++;
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
			SocketResponsePlayer response = (SocketResponsePlayer) client.getResponse();
			client.player = response.getPlayer();
			JOptionPane.showMessageDialog(this, "Điểm IQ của bạn là:" + response.getPlayer().getIQPoint());
			MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(this);
			parent.clickReturnHome();
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
