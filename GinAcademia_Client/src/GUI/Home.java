package GUI;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.util.TimerTask;
import java.util.Timer;

import Model.Question;
import Module.ImagePanel;
import Socket.Client;
import Socket.Request.SocketRequest;
import Socket.Response.SocketResponse;
import Socket.Response.SocketResponseContest;

@SuppressWarnings("serial")
public class Home extends ImagePanel implements ActionListener {
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
	private Font font;
	SocketResponseContest contest;
	Question question;
//	MainFrame parent;

	/**
	 * @wbp.parser.constructor
	 */
	public Home(Client client) {
		super(client);
		Image temp = new ImageIcon("./img/background.jpg").getImage().getScaledInstance(600, 600,
				java.awt.Image.SCALE_AREA_AVERAGING);
		this.img = new ImageIcon(temp);
//		this.parent = parent;
//		this.parent = (MainFrame) SwingUtilities.getWindowAncestor(this);
		init();
	}

	public void init() {
		this.setSize(600, 600);
		File font_file = new File("./Font/Ademo W00 Gray.ttf");
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		Font sizedFont = font.deriveFont(Font.PLAIN,49);

		btnStart = new JButton("START");
		btnStart.setOpaque(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setBorderPainted(false);
		btnStart.setForeground(Color.WHITE);
		btnStart.setFont(sizedFont);
		btnStart.setBackground(null);
		btnStart.setBorder(null);
		btnStart.setFocusable(false);
		btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnStart.setBounds(210, 330, 200, 60);
		add(btnStart);

		lblTime = new JLabel("-1");
		lblTime.setForeground(Color.WHITE);
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(font.deriveFont(Font.PLAIN,24));
		lblTime.setBounds(250, 343, 100, 80);
		add(lblTime);

		btnCancel = new JButton("CANCEL");
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBorder(null);
		btnCancel.setOpaque(false);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		btnCancel.setBackground(null);
		btnCancel.setFocusable(false);
		btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancel.setFont(font.deriveFont(Font.PLAIN,18));
		btnCancel.setBounds(250, 440, 100, 25);

		btnStart.addActionListener(this);
		btnCancel.addActionListener(this);
		this.displayWaitingGame(false);
		add(btnCancel);

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
		timer.scheduleAtFixedRate(waitTask, 0, 1000); // run timer
	}

	class WaitTask extends TimerTask {
		int time = 0;

		public WaitTask() {
			lblTime.setText("0");
		}

		@Override
		public void run() {
			lblTime.setText(time + "");
			if (joinGame == true) { 
				waitTask.cancel();
				timer.cancel();
				timer.purge();
				callParentToStartContest();
			}
			if(time == 60) {
				cancelButton();
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
//			while (waitGame) {
				try {

					SocketResponse response = client.getResponse(); 
					if(client.checkRequest) {
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
					}
					else {
						waitTask.cancel(); 
						timer.cancel();
						timer.purge();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
//					break;
				}
//			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton source = (JButton) arg0.getSource();
		if (source == btnStart) {
			waitGame = true;
			System.out.println("start button");
			client.sendRequest(new SocketRequest(SocketRequest.Action.CONTEST, "join"));
			if(client.checkSend) {
				// change on GUI
				this.displayWaitingGame(true);
//				parent.setActiveMenuButton(false);
				callParentToSetMenuButton(false);
				// start waiting timer and thread
				this.waittingContest();
				thread = new Thread(new GetMessage());
				thread.start();

				System.out.println("Contest join waiting .....");
			}
			

		} else if (source == btnCancel) {
			System.out.println("cancel button");
			if (waitGame) {
				this.cancelButton();
			}
		}
	}
	public void callParentToStartContest() {
		MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(this);
		parent.clickStart();
	}
	public void callParentToSetMenuButton(boolean active) {
		MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(this);
		parent.setActiveMenuButton(active);
	}
	public void cancelButton() {
		client.sendRequest(new SocketRequest(SocketRequest.Action.CANCELCONTEST, "cancel")); 
		// stop, kill timer and thread
		waitTask.cancel(); 
		timer.cancel();
		timer.purge();
		// change on GUI
		this.displayWaitingGame(false);
		lblTime.setText("0");
//		parent.setActiveMenuButton(true);
		callParentToSetMenuButton(true);
		System.out.println("Cancel join contest");
	}
}
