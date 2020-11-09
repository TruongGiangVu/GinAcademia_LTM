package GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import Model.Player;
import Model.Question;
import javax.swing.JButton;

import Module.OptionChoose;
import Socket.Client;
import Socket.Request.SocketRequest;
import Socket.Response.SocketResponse;
import Socket.Response.SocketResponseContest;
import Module.MyLabel;
import Module.MyPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

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
	
	private OptionChoose optionColor = new OptionChoose();
	private JLabel lblTime;
	private JButton btnNewButton;

	private int stt = 1;
	private ArrayList<Question> arrQ;
	private Question currentQ = new Question();
	private int pointTotal = 0;

	private Timer timer;

	private Player player;
	private JLabel lblEnemyname;
	private JLabel lblEnemyPoint;
//	private javax.swing.Timer timer = null;

	/**
	 * Create the panel.
	 */
	public Contest(Client client) {
		super(client);
		this.player = client.getPlayer();
		init();
	}

	public void init() {
		setLayout(null);
		this.setSize(600, 600);
		this.setBackground(Color.WHITE);
		lblQuestion = new JLabel("Question");
		lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestion.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblQuestion.setBounds(30, 80, 540, 101);
		add(lblQuestion);
		
		this.initQuestion();

		lblTime = new JLabel("10");
		lblTime.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setBounds(270, 10, 40, 40);
		add(lblTime);

		btnNewButton = new JButton("Test");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Contest.this.playGame();
			}
		});
		btnNewButton.setBounds(253, 43, 89, 23);
		add(btnNewButton);

		lblYourPoint = new JLabel("0");
		lblYourPoint.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblYourPoint.setBounds(50, 55, 98, 22);
		add(lblYourPoint);

		JLabel lblYourname = new JLabel(this.player.getName());
		lblYourname.setBounds(40, 23, 188, 35);
		add(lblYourname);

		lblEnemyname = new JLabel("abc");
		lblEnemyname.setBounds(402, 23, 188, 35);
		add(lblEnemyname);

		lblEnemyPoint = new JLabel("0");
		lblEnemyPoint.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEnemyPoint.setBounds(412, 55, 98, 22);
		add(lblEnemyPoint);
		
		
	}
	
	private void initQuestion() {
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

	public void playGame() {
		client.sendRequest(new SocketRequest(SocketRequest.Action.CONTEST,"start contest"));
		
		MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(this);
		parent.setActiveMenuButton(false);
		
		client.sendRequest(new SocketRequest(SocketRequest.Action.CONTEST));
		SocketResponse response = client.getResponse();
		if (response.getStatus().equals(SocketResponse.Status.SUCCESS)) {
			SocketResponseContest contest = (SocketResponseContest) response;
			arrQ = contest.getQuestionList();
			this.currentQ = arrQ.get(0);
		} else {
			System.out.println("Fail");
		}

		timer = new Timer();
		timer.scheduleAtFixedRate(new App(), 0, 1000);

	}

	class App extends TimerTask {
	    int countdown = 10;
	    int repeat = 0;
	    public App() {
	    	loadQuestion(currentQ);
	    }
	    public App(int repeat) {
	    	this.repeat = --repeat;
	    	if(repeat >= 5) {
        		JOptionPane.showMessageDialog(Contest.this, "Over");
        		this.cancel();
        	}	
	    	else {
	    		currentQ = arrQ.get(repeat);
		    	loadQuestion(currentQ);
	    	}
	    }
	    
	    public void run() {
	        countdown = countdown - 1;
	        if(this.countdown <= 10)
	        	Contest.this.lblTime.setText(countdown+"");
	        if(countdown == 0) {
	        	repeat++;
	        	countdown = 12;
	        	if(repeat >= 5) {
	        		JOptionPane.showMessageDialog(Contest.this, "Over");
	        		this.cancel();
	        	}	
	        	else {
	        		currentQ = arrQ.get(repeat);
	        		Contest.this.lblTime.setText(10+"");
		        	loadQuestion(currentQ);
	        	}
	        }	
	    }
	    
	}

	private void CancelAnRenewTimer() {
		if (stt <= 5) {
			Contest.this.lblTime.setText(10 + "");
			timer.cancel();
			timer = new Timer();
			timer.scheduleAtFixedRate(new App(this.stt), 0, 1000);
		} else {
			Contest.this.lblTime.setText("");
			timer.cancel();
		}
	}

	private void loadQuestion(Question q) {
		this.currentQ = q;
		this.lblQuestion.setText("Câu " + this.stt + ": " + q.getQuestion());
		stt++;

//		javax.swing.Timer time = new javax.swing.Timer(2000, new ActionListener() {
//		    @Override
//		    public void actionPerformed(ActionEvent arg0) {    
//		    	loadOption(currentQ);
//		    }
//		});
//		time.setRepeats(false);
//		time.start();

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
			this.arrTxt.get(i).theme = q.getOptions().get(i).OptionId + "";
//			this.arrTxt.get(i).setBackground(null);
		}
		this.setEnableOption(true);
	}

	private void setEnableOption(boolean en) {
		this.txtA.setEnabled(en);
		this.txtB.setEnabled(en);
		this.txtC.setEnabled(en);
		this.txtD.setEnabled(en);
	}
	
	public void contest() {
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		MyLabel source = (MyLabel) arg0.getSource();
		if (!source.isEnabled()) { // skip if label not enabled
			return;
		}

		String answer = source.getText();
		answer = answer.substring(2); // extract tag A,B,C,D
		source.setBackground(this.optionColor.getColor("choose"));

		if (this.currentQ.getAnswer() == Integer.parseInt(source.theme)) { // answer right
			source.setBackground(this.optionColor.getColor("right"));
			int a = updatePoint();
			this.lblYourPoint.setText("Point: " + a);
		} else { // answer wrong
			source.setBackground(this.optionColor.getColor("wrong"));
		}

		this.setEnableOption(false);
		CancelAnRenewTimer();
	}

	private int updatePoint() {
		int time = Integer.parseInt(this.lblTime.getText());
		this.pointTotal = this.pointTotal + time * 10;
		return this.pointTotal;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		MyLabel source = (MyLabel) arg0.getSource();
		source.setBackground(new Color(204,204,204));
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
}
