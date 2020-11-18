package GUI;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.Timer;

import Model.Question;
import Module.ImagePanel;
import Socket.Client;
import Socket.Request.SocketRequest;
import Socket.Response.SocketResponse;
import Socket.Response.SocketResponseContest;

;

@SuppressWarnings("serial")
public class Home extends ImagePanel implements ActionListener {
	private ExecutorService pool;

	private JButton btnStart;
	public JLabel lblTime;
	public JButton btnCancel;
	public Timer timer;
	public WaitTask waitTask;
	Thread thread;
	@SuppressWarnings("unused")
	private ImageIcon img;
	public boolean checked = false;
	public boolean waitGame = false;
	public boolean joinGame = false;

	SocketResponseContest contest;
	Question question;
	MainFrame parent;

	/**
	 * @wbp.parser.constructor
	 */
	public Home(Client client, MainFrame parent) {
		super(client);
		Image temp = new ImageIcon("./img/background.jpg").getImage().getScaledInstance(600, 600,
				java.awt.Image.SCALE_AREA_AVERAGING);
		this.img = new ImageIcon(temp);
		this.parent = parent;
		init();
	}

	public void init() {
		this.setSize(600, 600);

		btnStart = new JButton("START");
		btnStart.setOpaque(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setBorderPainted(false);
		btnStart.setForeground(Color.WHITE);
		btnStart.setFont(new Font("AdemoW00-Gray", Font.PLAIN, 49));
		btnStart.setBackground(null);
		btnStart.setBorder(null);
		btnStart.setFocusable(false);
		btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnStart.setBounds(205, 330, 200, 60);
		add(btnStart);

		lblTime = new JLabel("New label");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblTime.setBounds(250, 350, 100, 80);
		add(lblTime);

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(250, 440, 100, 25);
		add(btnCancel);

		btnStart.addActionListener(this);
		btnCancel.addActionListener(this);
		this.displayWaitingGame(false);
	}

	public Home(Client client, ImageIcon img) {
//		super("./img/background.jpg");
		super(client, img);
		this.img = img;
		init();
	}

	public void displayWaitingGame(boolean isDisplay) {
		btnStart.setVisible(!isDisplay);
		btnStart.setEnabled(!isDisplay);
		lblTime.setVisible(isDisplay);
		btnCancel.setVisible(isDisplay);
		btnCancel.setEnabled(isDisplay);
	}

	public void waittingContest() {
		waitTask = new WaitTask();
		timer = new Timer();
		timer.scheduleAtFixedRate(waitTask, 0, 1000);
	}

	class WaitTask extends TimerTask {
		int time = 0;

		public WaitTask() {
			waitGame = true;
		}

		@Override
		public void run() {
			lblTime.setText(time + "");
			if (joinGame == true) { 
				waitTask.cancel();
				timer.cancel();
				timer.purge();
				parent.clickStart();
//				
			}
			time++;
		}
	}

	class GetMessage implements Runnable { 
		public GetMessage() {
			waitGame = true;
		}

		@Override
		public void run() {
			while (waitGame) {
				try {

					SocketResponse response = client.getResponse(); 

					System.out.println("Get response: " + response.getMessage());
					if (response.getMessage().equals("HasGame")) {
						waitGame = false;
						joinGame = true; 
						System.out.println("Get start contest in Home");
					}
					else if(response.getMessage().equals("cancelGame")) {
						waitGame = false;
						joinGame = false; 
						System.out.println("Get start contest in Home");
						Thread.currentThread().interrupt();
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Thread was interrupted, Failed to complete operation");
					break;
				}
			}
			System.out.println("Thread stop by end while loop");
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) { // a` 1
		JButton source = (JButton) arg0.getSource();
		if (source == btnStart) {
			waitGame = true;
			System.out.println("start button");
			client.sendRequest(new SocketRequest(SocketRequest.Action.CONTEST, "join"));
			// change on GUI
			this.displayWaitingGame(true);
			parent.setActiveMenuButton(false);
			// start waiting timer and thread
			this.waittingContest();
			thread = new Thread(new GetMessage());
			thread.start();
//			thread = new WaitGame();
//			thread.start();

			System.out.println("Contest join waiting .....");

		} else if (source == btnCancel) {
			System.out.println("cancel button");
			if (waitGame) {
//				waitGame = false;
				client.sendRequest(new SocketRequest(SocketRequest.Action.CANCELCONTEST, "cancel")); 
				// stop, kill timer and thread
				waitTask.cancel(); 
				timer.cancel();
				timer.purge();
//				thread.interrupt();
				// change on GUI
				this.displayWaitingGame(false);
				lblTime.setText("0");
				parent.setActiveMenuButton(true);
				System.out.println("Cancel join contest");
			}
		}
	}
}
