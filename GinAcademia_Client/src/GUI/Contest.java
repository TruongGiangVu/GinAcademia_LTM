package GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import Server.BUS.QuestionBUS;
import Model.Player;
import Model.Question;
import javax.swing.JButton;
import javax.swing.JLabel;

import Module.OptionChoose;
import Module.MyLabel;
import Module.MyPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//import javax.swing.Timer;

public class Contest extends MyPanel implements MouseListener {
	private JLabel lblQuestion;
	private JPanel panelOption;
	
	
	private QuestionBUS bus = new QuestionBUS();
	
	private ArrayList<MyLabel> arrTxt = new ArrayList<MyLabel>();
	private MyLabel txtA;
	private MyLabel txtB;
	private MyLabel txtC;
	private MyLabel txtD;
	
	private Question currentQ = new Question();
	private int pointTotal = 0;
	
	private OptionChoose optioncColor = new OptionChoose(); 
	private JLabel lblTime;
	private JButton btnNewButton;
	
	private int stt = 1;
	private JLabel lblYourPoint;
	private ArrayList<Question> arrQ ;
	Timer timer;
	
	private Player player;
	private JLabel lblEnemyname;
	private JLabel lblEnemyPoint;
//	private javax.swing.Timer timer = null;

	/**
	 * Create the panel.
	 */
	public Contest() {
		init();
		
	}
	public Contest(Player p) {
		this.player = p;
		init();
		
	}
	public void init() {
		setLayout(null);
		this.setSize(600,600);
		lblQuestion = new JLabel("Question");
		lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestion.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblQuestion.setBounds(30, 80, 540, 101);
		add(lblQuestion);
		
		panelOption = new JPanel();
		panelOption.setBounds(30, 177, 540, 331);
		add(panelOption);
		panelOption.setVisible(false);
		panelOption.setLayout(new GridLayout(0, 1, 0, 0));
		
		txtA = new MyLabel();
		txtA.setText("A");
		txtA.setOpaque(true);
		panelOption.add(txtA);
		
		txtB = new MyLabel();
		txtB.setText("B");
		txtB.setOpaque(true);
		panelOption.add(txtB);
		
		txtC = new MyLabel();
		txtC.setText("C");
		txtC.setOpaque(true);
		panelOption.add(txtC);
		
		txtD = new MyLabel();
		txtD.setText("D");
		txtD.setOpaque(true);
		panelOption.add(txtD);
		
		txtA.addMouseListener(this);
		txtB.addMouseListener(this);
		txtC.addMouseListener(this);
		txtD.addMouseListener(this);
		
		this.arrTxt.add(txtA);
		this.arrTxt.add(txtB);
		this.arrTxt.add(txtC);
		this.arrTxt.add(txtD);
		
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
	public void playGame() {
		// socket
		arrQ = bus.ReadContest();

		this.currentQ = arrQ.get(0);
		timer = new Timer();
        timer.scheduleAtFixedRate(new App(), 0, 1000);
	}
	class App extends TimerTask {
	    int countdown = 10;
	    int repeat = 0;
	    public App() {
	    	loadData(currentQ);
	    }
	    public App(int repeat) {
	    	this.repeat = --repeat;
	    	if(repeat >= 5) {
        		JOptionPane.showMessageDialog(Contest.this, "Over");
        		this.cancel();
        	}	
	    	else {
	    		currentQ = arrQ.get(repeat);
		    	loadData(currentQ);
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
		        	loadData(currentQ);
	        	}
	        }	
	    }

	}
	
	private void loadData(Question q) {
		this.currentQ = q;
		this.lblQuestion.setText("Câu " +this.stt +": "+q.getQuestion());
		stt++;
		this.panelOption.setVisible(true);
		
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
	private void setEnableOption(boolean en) {
		this.txtA.setEnabled(en);
		this.txtB.setEnabled(en);
		this.txtC.setEnabled(en);
		this.txtD.setEnabled(en);
	}
	final private void loadOption(Question q) {
		ArrayList<String> symbol = new ArrayList<String>();
		symbol.add("A.");
		symbol.add("B.");
		symbol.add("C.");
		symbol.add("D.");
		Collections.shuffle(q.getOptions());
		for(int i= 0 ; i<4;++i) {
			this.arrTxt.get(i).setText(symbol.get(i)+ " "+ q.getOptions().get(i).Option);
			this.arrTxt.get(i).theme=q.getOptions().get(i).OptionId+"";
			this.arrTxt.get(i).setBackground(null);
		}
		this.panelOption.setVisible(true);
		this.setEnableOption(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		MyLabel source = (MyLabel) arg0.getSource();
		if(!source.isEnabled()){ // skip if label not enabled
            return;
        }
		String answer = source.getText();
		answer = answer.substring(2);
		source.setBackground(this.optioncColor.getColor("choose"));
		
		if(this.currentQ.getAnswer() == Integer.parseInt(source.theme)) {
			source.setBackground(this.optioncColor.getColor("right"));
			int a = updatePoint();
			this.lblYourPoint.setText("Point: "+a);
		}
		else {
			source.setBackground(this.optioncColor.getColor("wrong"));
		}

		this.setEnableOption(false);
		CancelAnRenewTimer();
	}
	private void CancelAnRenewTimer() {
		if(stt <= 5) {
			Contest.this.lblTime.setText(10+"");
			timer.cancel();
			timer = new Timer();
			timer.scheduleAtFixedRate(new App(this.stt), 0, 1000);
		}
		else {
			Contest.this.lblTime.setText("");
			timer.cancel();
		}
			
	}
	private int updatePoint() {
		int time = Integer.parseInt(this.lblTime.getText());
		this.pointTotal = this.pointTotal + time * 10;
		return this.pointTotal;
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
