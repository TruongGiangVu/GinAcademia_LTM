package GUI;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import Model.Player;
import Model.Question;
import Module.OptionChoose;
import Module.MyLabel;
import Module.MyPanel;

import Socket.Client;
import Socket.Request.SocketRequest;
import Socket.Request.SocketRequestAnswer;
import Socket.Response.SocketResponse;
import Socket.Response.SocketResponseContest;
import Socket.Response.SocketResponseGameRoom;
import Socket.Response.SocketResponseQuestion;

//import javax.swing.Timer;

@SuppressWarnings("serial")
public class Contest extends MyPanel implements MouseListener {
	private JLabel lblQuestion;
	private JLabel lblYourPoint;
	private MyLabel txtA;
	private MyLabel txtB;
	private MyLabel txtC;
	private MyLabel txtD;
	private ArrayList<MyLabel> arrTxt = new ArrayList<MyLabel>();
	private MainFrame parent;

	private OptionChoose optionColor = new OptionChoose();

	private int stt = 1;
	private Question currentQ = new Question();
	private int index = -1;
	private boolean isAnswer = false;

	private Timer timer;
	private JLabel lblTime;
	private Player player;
	private JLabel lblEnemyname;
	private JLabel lblEnemyPoint;
	private JLabel lblYourname;

	public Contest(MainFrame parent,Client client, int status) {
		super(client);
		this.player = client.getPlayer();
		this.parent = parent;
		if (status == 1)
			init();
	}

	public void init() {
		setLayout(null);
		this.setSize(600, 600);
		this.setBackground(Color.WHITE);

		this.initHeader();

		
		parent.setActiveMenuButton(false);
		this.initContest();
	}

	private void initQuestion() {
		lblQuestion = new JLabel("Question");
		lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestion.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblQuestion.setBounds(30, 80, 540, 101);
		add(lblQuestion);

		txtA = new MyLabel();
		txtA.setText("A");
		txtA.setBounds(50, 150, 500, 80);
		add(txtA);
		this.arrTxt.add(txtA);

		txtB = new MyLabel();
		txtB.setBounds(50, 240, 500, 80);
		txtB.setText("B");
		add(txtB);
		this.arrTxt.add(txtB);

		txtC = new MyLabel();
		txtC.setBounds(50, 330, 500, 80);
		txtC.setText("C");
		add(txtC);
		this.arrTxt.add(txtC);

		txtD = new MyLabel();
		txtD.setBounds(50, 420, 500, 80);
		txtD.setText("D");
		add(txtD);
		this.arrTxt.add(txtD);

		txtA.addMouseListener(this);
		txtB.addMouseListener(this);
		txtC.addMouseListener(this);
		txtD.addMouseListener(this);
	}

	private void initHeader() {
		lblTime = new JLabel("10");
		lblTime.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setBounds(270, 10, 40, 40);
		add(lblTime);

		lblYourPoint = new JLabel("0");
		lblYourPoint.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblYourPoint.setBounds(50, 55, 98, 22);
		add(lblYourPoint);

		lblYourname = new JLabel(this.player.getName());
		lblYourname.setBounds(40, 23, 188, 35);
		add(lblYourname);

		lblEnemyname = new JLabel("");
		lblEnemyname.setBounds(402, 23, 188, 35);
		add(lblEnemyname);

		lblEnemyPoint = new JLabel("");
		lblEnemyPoint.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEnemyPoint.setBounds(412, 55, 98, 22);
		add(lblEnemyPoint);
	}

	private void loadQuestion(Question q) {
		this.currentQ = q;
		this.lblQuestion.setText("Câu " + this.stt + ": " + q.getQuestion());
		stt++;
		loadOption(currentQ);
	}

	public void loadOption(Question q) {
		ArrayList<String> symbol = new ArrayList<String>();
		symbol.add("A.");
		symbol.add("B.");
		symbol.add("C.");
		symbol.add("D.");
		Collections.shuffle(q.getOptions()); // shuffle option
		for (int i = 0; i < 4; ++i) {
			this.arrTxt.get(i).setText(symbol.get(i) + " " + q.getOptions().get(i).Option);
			this.arrTxt.get(i).theme = q.getOptions().get(i).OptionId;
		}
		this.setEnableOption(true);
	}

	private void setEnableOption(boolean en) {
		this.txtA.setEnabled(en);
		this.txtB.setEnabled(en);
		this.txtC.setEnabled(en);
		this.txtD.setEnabled(en);
	}

	public void initContest() {
		System.out.println("Start contest");
		client.sendRequest(new SocketRequest(SocketRequest.Action.CONTEST, "start contest"));
		
		SocketResponse responseContest = client.getResponse(); // get init contest
		if (responseContest.getAction().equals(SocketResponse.Action.CONTEST)) {
			if (responseContest.getMessage().equals("contest")) {
				this.updateHeader(responseContest);
				this.initNamePlayer(responseContest);
			}
		}
		this.initQuestion();
		SocketResponse responseQuestion = client.getResponse(); // get first question
		if (responseQuestion.getAction().equals(SocketResponse.Action.CONTEST)) {
			if (responseQuestion.getMessage().equals("question")) {
				SocketResponseQuestion question = (SocketResponseQuestion) responseQuestion;
				this.loadQuestion(question.getQuestion());
			}
		
			timer = new Timer();
			timer.scheduleAtFixedRate(new ContestTask(), 0, 1000);
		}
		
	}

	class ContestTask extends TimerTask {
		int countdown = 10;

		public ContestTask() {
		}

		@Override
		public void run() {
			lblTime.setText(this.countdown + "");
			if (this.countdown == 0 && isAnswer == false) {
				endTurn();
			}
			this.countdown--;
		}
	}

	private void endTurn() {
		System.out.println("End turn \n");

		SocketResponse responseServer = (SocketResponse) client.getResponse();
		timer.cancel(); // timer stop
		if (responseServer.getAction().equals(SocketResponse.Action.CONTEST)) {
			SocketResponseGameRoom responseGameRoom = (SocketResponseGameRoom) responseServer;
			this.index = this.indexOfPlayer(responseGameRoom.players, this.player); // get index player
			this.displayAnswer(responseGameRoom.answers, responseGameRoom.rightAnswer); // display answer, both player
																						// and enemy
			this.updateHeader(index, responseGameRoom.points); // update point

			// delay 2s
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (this.stt <= 5) { // if has next question
				this.isAnswer = false;
				this.clearColorOption();
				this.loadQuestion(responseGameRoom.question);
				this.setEnableOption(true);
			} else { // if over
				SocketResponse responseEnd = client.getResponse();
				this.showDialog(responseEnd.getMessage());
			}

			timer = new Timer();
			timer.scheduleAtFixedRate(new ContestTask(), 0, 1000);
		}
	}

	private void showDialog(String message) { // dialog end game
		String[] options = { "Tiếp tục", "Dừng chơi" };
		int result = JOptionPane.showOptionDialog(this, message, "Bạn có muốn chơi tiếp không?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, // no custom icon
				options, // button titles
				options[0] // default button
		);
		if (result == JOptionPane.YES_OPTION) {
			parent.clickStart();
		} else if (result == JOptionPane.NO_OPTION) {
			parent.setActiveMenuButton(true);
			parent.clickReturenHome();

		} else {
			parent.setActiveMenuButton(true);
			parent.clickReturenHome();
		}
	}

	private void updateHeader(SocketResponse response) { // get index and update point
		SocketResponseContest contest = (SocketResponseContest) response;
		this.index = contest.players.indexOf(this.player);
		this.updatePoint(contest.points);
	}

	private void updateHeader(int index, ArrayList<Integer> points) { // get index and update point
		System.out.println("Header -> point");
		this.index = index;
		System.out.println("Index:" + index);
		this.updatePoint(points);
	}

	private void initNamePlayer(SocketResponse response) { // update name init
//		Socket nhận phản hồi answer từ server trả về
		SocketResponseContest contest = (SocketResponseContest) response;
		int n = contest.players.size();
		for (int i = 0; i < n; ++i) {
			if (i != index)
				this.lblEnemyname.setText(contest.players.get(i).getName());
		}
	}
//	Cập nhật điểm của người chơi
	private void updatePoint(ArrayList<Integer> points) {
		System.out.println("update Point");
		int n = points.size();
		this.lblYourPoint.setText(points.get(index) + "");
		for (int i = 0; i < n; ++i) {
			if (i != index)
				this.lblEnemyPoint.setText(points.get(i) + "");
		}

		System.out.print("Points: ");
		for (int p : points) {
			System.out.print(p + " ");
		}
		System.out.println();
	}

	private void displayAnswer(ArrayList<Integer> answers, int rightAnswer) {
		System.out.println("displayAnswer");
		this.enemyAnswer(answers);
		this.rightAnswer(rightAnswer); // display right answer
	}

	private void enemyAnswer(ArrayList<Integer> answers) {
		// get enemy index
		int enemy = 0;
		if (this.index == 0)
			enemy = 1;
		// display
		int choose = answers.get(enemy);
		System.out.println("enemy answer: " + choose);
		int n = arrTxt.size();
		for (int i = 0; i < n; ++i) {
			if (choose == this.arrTxt.get(i).theme) {
				this.arrTxt.get(index).setBackground(this.optionColor.getColor("enemy"));
				break;
			}
		}
	}

	private void rightAnswer(int right) {
		int n = arrTxt.size();
		System.out.println("right answer: " + right);
		for (int i = 0; i < n; ++i) {
			if (right == this.arrTxt.get(i).theme) {
				this.arrTxt.get(index).setBackground(this.optionColor.getColor("right"));
				break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		MyLabel source = (MyLabel) arg0.getSource();
		if (!source.isEnabled()) { // skip if label not enabled
			return;
		}
		this.isAnswer = true;
		client.sendRequest(
				new SocketRequestAnswer(this.player, source.theme, Integer.valueOf(this.lblTime.getText()) * 1000));
		this.setEnableOption(false);
		source.setBackground(this.optionColor.getColor("choose"));
		this.endTurn();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		MyLabel source = (MyLabel) arg0.getSource();
		source.setBackground(new Color(204, 204, 204));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		MyLabel source = (MyLabel) arg0.getSource();
		source.setBackground(Color.WHITE);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	private void clearColorOption() {
		int n = arrTxt.size();
		for (int i = 0; i < n; ++i) {
			this.arrTxt.get(index).setBackground(Color.WHITE);
		}
	}

	private int indexOfPlayer(ArrayList<Player> players, Player p) {
		int ind = -1;
		int n = players.size();
		for (int i = 0; i < n; ++i) {
			if (players.get(i).getId().equals(p.getId())) {
				ind = i;
				break;
			}
		}
		return ind;
	}
}
