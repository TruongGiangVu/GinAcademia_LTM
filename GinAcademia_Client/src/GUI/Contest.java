package GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
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
import Socket.Request.SocketRequestAnswer;
import Socket.Response.SocketResponse;
import Socket.Response.SocketResponseContest;
import Socket.Response.SocketResponseGameRoom;
import Socket.Response.SocketResponsePlayer;
import Socket.Response.SocketResponseQuestion;

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
	private int maxTime = 0;
	private int numQ = 0;

	private Timer timer;
	private JLabel lblTime;
	private Player player;
	private JLabel lblEnemyname;
	private JLabel lblEnemyPoint;
	private JLabel lblYourname;

	private Thread thread;
	private JLabel lblYourImg;
	private JLabel lblEnemyImg;

	/**
	 * @wbp.parser.constructor
	 */
	public Contest(MainFrame parent, Client client, int i) {

		super(client);
		UIManager.put("Label[Disabled].textForeground",Color.WHITE);
		this.player = client.getPlayer();
		this.parent = parent;
		setLayout(null);
		this.setSize(600, 600);
		this.setBackground(Color.WHITE);

		lblTime = new JLabel("10");
		lblTime.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setBounds(270, 10, 40, 40);
		add(lblTime);

		lblYourPoint = new JLabel("0");
		lblYourPoint.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourPoint.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblYourPoint.setBounds(98, 70, 98, 20);
		add(lblYourPoint);

		lblYourname = new JLabel(this.player.getName());
		lblYourname.setBounds(98, 20, 125, 48);
		add(lblYourname);

		lblEnemyname = new JLabel("");
		lblEnemyname.setBounds(365, 20, 125, 48);
		add(lblEnemyname);

		lblEnemyPoint = new JLabel("");
		lblEnemyPoint.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnemyPoint.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEnemyPoint.setBounds(392, 70, 98, 20);
		add(lblEnemyPoint);
		
		lblQuestion = new JLabel("Question");
		lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestion.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblQuestion.setBounds(30, 100, 540, 80);
		add(lblQuestion);

		txtA = new MyLabel();
		txtA.setText("A");
		txtA.setBounds(50, 200, 500, 60);
		add(txtA);
		this.arrTxt.add(txtA);

		txtB = new MyLabel();
		txtB.setBounds(50, 280, 500, 60);
		txtB.setText("B");
		add(txtB);
		this.arrTxt.add(txtB);

		txtC = new MyLabel();
		txtC.setBounds(50, 360, 500, 60);
		txtC.setText("C");
		add(txtC);
		this.arrTxt.add(txtC);

		txtD = new MyLabel();
		txtD.setBounds(50, 440, 500, 60);
		txtD.setText("D");
		add(txtD);
		this.arrTxt.add(txtD);

		txtA.addMouseListener(this);
		txtB.addMouseListener(this);
		txtC.addMouseListener(this);
		txtD.addMouseListener(this);
		
		lblYourImg = new JLabel((String) null);
		lblYourImg.setBounds(20, 20, 68, 70);
		lblYourImg.setIcon(this.loadImage("./img/Avatar/"+this.player.getImage()));
		add(lblYourImg);
		
		lblEnemyImg = new JLabel((String) null);
		lblEnemyImg.setBounds(502, 20, 68, 70);
		add(lblEnemyImg);
		
		parent.setActiveMenuButton(false);
		this.initContest();
	}
	
	public Contest(MainFrame parent, Client client) {
		super(client);
		this.player = client.getPlayer();
		this.parent = parent;
	}

	private void loadQuestion(Question q) {
		this.currentQ = q;
		this.lblQuestion.setText("<html>" + "Câu " + this.stt + ": " + q.getQuestion() + "</html>");
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
			this.arrTxt.get(i).setText("<html>" + symbol.get(i) + " " + q.getOptions().get(i).Option + "</html>");
			this.arrTxt.get(i).theme = q.getOptions().get(i).OptionId;
		}
	}

	private void setEnableOption(boolean en) {
		this.txtA.setEnabled(en);
		this.txtB.setEnabled(en);
		this.txtC.setEnabled(en);
		this.txtD.setEnabled(en);
	}

	public void initContest() {

		SocketResponse responseContest = client.getResponse(); // get init contest
		if(client.checkRequest) {
			if (responseContest.getAction().equals(SocketResponse.Action.CONTEST)) {
				if (responseContest.getMessage().equals("contest")) {
					this.initNamePlayer(responseContest);
				}
			}
			SocketResponse responseQuestion = client.getResponse(); // get first question
			if(client.checkRequest) {
				if (responseQuestion.getAction().equals(SocketResponse.Action.CONTEST)) {
					if (responseQuestion.getMessage().equals("question")) {
						SocketResponseQuestion question = (SocketResponseQuestion) responseQuestion;
						this.loadQuestion(question.getQuestion());
					}
				}
			}
			
		}
		timer = new Timer();
		timer.scheduleAtFixedRate(new ContestTask(), 0, 1000);
	}
	private void initNamePlayer(SocketResponse response) { // update name init
		SocketResponseContest contest = (SocketResponseContest) response;
		index = indexOfPlayer(contest.players, player); // get index player
		this.maxTime = contest.config.getTime();
		this.numQ = contest.config.getNumQuestion();
		this.updatePoint(contest.points);
		int n = contest.players.size();
		for (int i = 0; i < n; ++i) {
			if (i != index) {
				this.lblEnemyname.setText(contest.players.get(i).getName());
				this.lblEnemyImg.setIcon(this.loadImage("./img/Avatar/"+contest.players.get(i).getImage()));
			}
				
		}
	}

	public ImageIcon loadImage(String nameImage) {
		ImageIcon icon = new ImageIcon(nameImage); // load the image to a imageIcon
		Image image = icon.getImage(); // transform it
		Image newimg = image.getScaledInstance(68, 70, java.awt.Image.SCALE_SMOOTH); // scale it the// smooth way
		icon = new ImageIcon(newimg);
		return icon;
	}
	class ContestTask extends TimerTask {
		int countdown = maxTime;

		public ContestTask() {
			clearColorOption();
		}

		@Override
		public void run() {
			lblTime.setText(this.countdown + "");
			if (this.countdown == 0 && isAnswer == false) {
				thread = new Thread(new ContestGame());
				thread.start();
			}
			this.countdown--;
		}
	}

	class ContestGame implements Runnable {
		public ContestGame() {
		}

		@Override
		public void run() {
			System.out.println("End turn \n");
			try {
				SocketResponse responseServer = (SocketResponse) client.getResponse();
				if(client.checkRequest) {
					if (responseServer.getAction().equals(SocketResponse.Action.CONTEST)) {
						timer.cancel();
						SocketResponseGameRoom responseGameRoom = (SocketResponseGameRoom) responseServer;
						index = indexOfPlayer(responseGameRoom.players, player); // get index player
						displayAnswer(responseGameRoom.answers, responseGameRoom.rightAnswer);
						setEnableOption(false);

						updateHeader(index, responseGameRoom.points); // update point

						// delay 2s
						Thread.sleep(2000);

						if (stt <= numQ) { // if has next question
							isAnswer = false;
							setEnableOption(true);
							loadQuestion(responseGameRoom.question);

						} else { // if over
							SocketResponse responseEnd = client.getResponse();
							SocketResponsePlayer player = (SocketResponsePlayer) responseEnd;
							showDialog(player.getMessage());
							client.player = player.getPlayer(); // update again info
						}

						timer = new Timer();
						timer.scheduleAtFixedRate(new ContestTask(), 0, 1000);
						Thread.currentThread().interrupt();
					}
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				Thread.currentThread().interrupt();
			}

		}
	}

	private void showDialog(String message) { // dialog end game
		JOptionPane.showMessageDialog(this, message, "Game", JOptionPane.INFORMATION_MESSAGE);
		parent.setActiveMenuButton(true);
		parent.clickReturnHome();
	}

	private void updateHeader(int index, ArrayList<Integer> points) { // get index and update point
		this.index = index;
		this.updatePoint(points);
	}

	
//	Cập nhật điểm của người chơi
	private void updatePoint(ArrayList<Integer> points) {
		int n = points.size();
		this.lblYourPoint.setText(points.get(index) + "");

		for (int i = 0; i < n; ++i) {
			if (i != index)
				this.lblEnemyPoint.setText(points.get(i) + "");
		}
	}

	private void displayAnswer(ArrayList<Integer> answers, int rightAnswer) {
		this.enemyAnswer(answers);
		this.rightAnswer(rightAnswer); // display right answer
	}

	private void enemyAnswer(ArrayList<Integer> answers) {
		// get enemy index
		int enemy = 0;
		if (this.index == 0)
			enemy = 1;
		if(answers.size() == 1)
			return;
		// display
		int choose = answers.get(enemy);
		if (choose == 0)
			return;
		int n = arrTxt.size();
		for (int i = 0; i < n; ++i) {
			if (choose == this.arrTxt.get(i).theme) {
				this.arrTxt.get(i).setBackground(this.optionColor.getColor("enemy"));
				this.arrTxt.get(i).setForeground(Color.WHITE);
				this.arrTxt.get(i).repaint();
				break;
			}
		}
	}

	private void rightAnswer(int right) {
		int n = arrTxt.size();
		for (int i = 0; i < n; ++i) {
			if (right == this.arrTxt.get(i).theme) {
				this.arrTxt.get(i).setBackground(this.optionColor.getColor("right"));
				this.arrTxt.get(i).setForeground(Color.WHITE);
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
		if(client.checkSend) {
			this.setEnableOption(false);
			source.setBackground(this.optionColor.getColor("choose"));
//			source.setForeground(Color.WHITE);
//			this.endTurn();
			thread = new Thread(new ContestGame());
			thread.start();
		}
		else {
			timer.cancel();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

		MyLabel source = (MyLabel) arg0.getSource();
		if (!source.isEnabled()) { // skip if label not enabled
			return;
		}
		source.setBackground(new Color(204, 204, 204));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		MyLabel source = (MyLabel) arg0.getSource();
		if (!source.isEnabled()) { // skip if label not enabled
			return;
		}
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
			this.arrTxt.get(i).setBackground(new Color(255, 255, 255));
			this.arrTxt.get(i).setForeground(Color.BLACK);
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
