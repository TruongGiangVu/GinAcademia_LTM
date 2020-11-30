package GUI;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.GridLayout;
import java.awt.Image;

import Model.Player;
import Module.MenuButton;
import Module.ImagePanel;
import Module.MyFrame;
import Module.MyPanel;
import Socket.Client;

import javax.swing.border.MatteBorder;
import javax.swing.ButtonGroup;

@SuppressWarnings("serial")
public class MainFrame extends MyFrame implements MouseListener, ActionListener {

	private JPanel panelMain;
	private JPanel panelMenu;
	public static JLabel lblImage;
	public JLabel lblName;
	private JPanel panelButton;
	private MenuButton btnHome;
	private MenuButton btnIQ;
	private MenuButton btnRank;
	private MenuButton btnProfile;
	int xx, xy;
	private MyPanel panelContent;
	private Player player = null;

	private ImageIcon img;
	private CardLayout deck = new CardLayout();
	private MyPanel pnRank;
	private MyPanel pnProfile;
	private ImagePanel pnHome;
	private MyPanel pnIQTest;
	private MyPanel pnContest;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public MainFrame(Client client) {// ./img/profile.png
		super(client);
		this.player = client.player;
		this.init();
	}

	/**
	 * @wbp.parser.constructor
	 */
	public MainFrame(Client client, Player p) {
		super(client);
		this.player = p;
		this.init();
	}

	public void init() {
		UIManager.put("Label[Disabled].textForeground",Color.WHITE);

		Image temp = new ImageIcon("./img/background.jpg").getImage().getScaledInstance(600, 600,
				java.awt.Image.SCALE_AREA_AVERAGING);
		this.img = new ImageIcon(temp);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		panelMain = new JPanel();

		this.setTitle("GinAcademia");
		panelMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelMain);
		panelMain.setLayout(null);

		panelMenu = new JPanel();
		panelMenu.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));

		panelMenu.setBounds(0, 0, 200, 600);
		panelMenu.setBackground(Color.WHITE);
		panelMain.add(panelMenu);
		panelMenu.setLayout(null);

		lblImage = new JLabel();
		lblImage.addMouseListener(this);
		this.loadImage("./img/Avatar/"+this.player.getImage());
		lblImage.setBounds(30, 23, 140, 130);
		lblImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panelMenu.add(lblImage);

		lblName = new JLabel(this.player.getName());
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblName.setForeground(Color.BLACK);
		lblName.setBounds(30, 165, 140, 30);
		panelMenu.add(lblName);

		panelButton = new JPanel();
		panelButton.setBounds(5, 212, 190, 200);
		panelButton.setBackground(Color.WHITE);
		panelMenu.add(panelButton);
		panelButton.setLayout(new GridLayout(0, 1, 0, 0));

		btnHome = new MenuButton("     Trang chủ");
		buttonGroup.add(btnHome);
		buttonGroup.setSelected(btnHome.getModel(), true);
		panelButton.add(btnHome);

		btnIQ = new MenuButton("     Kiểm tra IQ");
		buttonGroup.add(btnIQ);
		panelButton.add(btnIQ);

		btnRank = new MenuButton("     Bảng xếp hạng");
		buttonGroup.add(btnRank);
		panelButton.add(btnRank);

		btnProfile = new MenuButton("     Thông tin cá nhân");
		buttonGroup.add(btnProfile);
		panelButton.add(btnProfile);

		panelContent = new MyPanel(client);
		panelContent.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panelContent.setLayout(deck);

		panelContent.setBackground(new Color(204, 204, 255));
		panelContent.setBounds(200, 0, 600, 600);
		panelMain.add(panelContent);

//		pnHome = new Home(this.img);
		pnHome = new Home(client);
		panelContent.add(pnHome, "home");

		pnProfile = new Profile(client);
		panelContent.add(pnProfile, "profile");

		pnIQTest = new IQTestPanel(client);
		panelContent.add(pnIQTest, "iqTest");

		pnRank = new Rank(client, 0);
		panelContent.add(pnRank, "rank");

		pnContest = new Contest(this, client);
		pnContest.setClientSocket(client);
		panelContent.add(pnContest, "contest");

//		btnHome.addMouseListener(this);
//		btnProfile.addMouseListener(this);
//		btnRank.addMouseListener(this);
//		btnIQ.addMouseListener(this);

		deck.show(panelContent, "home");
		btnHome.addActionListener(this);
		btnProfile.addActionListener(this);
		btnRank.addActionListener(this);
		btnIQ.addActionListener(this);

//		this.setUndecorated(true);
		setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(arg0.getSource() != lblImage) {
			MenuButton a = (MenuButton) arg0.getSource();
			a.setForeground(Color.WHITE);
			a.setBackground(new Color(8, 87, 40).brighter());
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() != lblImage) {
			MenuButton a = (MenuButton) e.getSource();
			a.setBackground(Color.white);
			a.setForeground(Color.black);	
		}
		

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource() == lblImage) {
        	AvatarDialog dlg = new AvatarDialog(client,player);
			dlg.setLocationRelativeTo(null);
			dlg.setModal(true);
			dlg.setResizable(false);
			dlg.setVisible(true);
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		MenuButton source = (MenuButton) arg0.getSource();

		if (source == this.btnHome) {
			deck.removeLayoutComponent(pnHome);
			this.pnHome = new Home(client, this.img);
			panelContent.add(pnHome, "home");
			deck.show(panelContent, "home");

		} else if (source == this.btnProfile) {
			deck.removeLayoutComponent(pnProfile);
			this.pnProfile = new Profile(client);
			panelContent.add(pnProfile, "profile");
			deck.show(panelContent, "profile");
		} else if (source == this.btnIQ) {
			deck.removeLayoutComponent(pnIQTest);
			this.pnIQTest = new IQTestPanel(client);
			panelContent.add(pnIQTest, "iqTest");
			deck.show(panelContent, "iqTest");
		} else if (source == this.btnRank) {
			deck.removeLayoutComponent(pnRank);
			this.pnRank = new Rank(client, 1);
			panelContent.add(pnRank, "rank");
			deck.show(panelContent, "rank");
		}
	}

	public void setActiveMenuButton(boolean isActive) {
		btnHome.setEnabled(isActive);
		btnProfile.setEnabled(isActive);
		btnRank.setEnabled(isActive);
		btnIQ.setEnabled(isActive);
	}

	public void clickStart() {
		deck.removeLayoutComponent(pnContest);
		this.pnContest = new Contest(this, client, 1);
		panelContent.add(pnContest, "contest");
		deck.show(panelContent, "contest");
	}
	public void clickReturnHome() {
		deck.removeLayoutComponent(pnHome);
//		this.pnHome = new Home(client, this.img);
		this.pnHome = new Home(client);
		panelContent.add(pnHome, "home");
		deck.show(panelContent, "home");
	}

	public void loadImage(String nameImage) {
		ImageIcon icon = new ImageIcon(nameImage); // load the image to a imageIcon
		Image image = icon.getImage(); // transform it
		Image newimg = image.getScaledInstance(140, 130, java.awt.Image.SCALE_SMOOTH); // scale it the// smooth way
		icon = new ImageIcon(newimg);
		lblImage.setIcon(icon);
	}

}



