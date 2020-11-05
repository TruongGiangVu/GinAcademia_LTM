package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseMotionAdapter;
import javax.swing.border.LineBorder;

import Model.Player;
import Module.MenuButton;
import Module.ImagePanel;
import Module.MyFrame;
import Module.MyPanel;

import javax.swing.border.MatteBorder;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;

public class MainFrame extends MyFrame implements MouseListener,ActionListener{

	private JPanel panelMain;
	private JPanel panelMenu;
	private JLabel lblImage;
	public JLabel lblName;
	private JPanel panelButton;
	private MenuButton btnHome;
	private MenuButton btnIQ;
	private MenuButton btnRank;
	private MenuButton btnProfile;
	int xx,xy;
	private MyPanel panelContent;
	private Player player = null;
	
	private ImageIcon img;
	private CardLayout deck = new CardLayout();
	private MyPanel pnRank ;
	private MyPanel pnProfile ;
	private ImagePanel pnHome ;
	private MyPanel pnIQTest ;
	private MyPanel pnContest ;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {//./img/profile.png
		this.init();
	}
	public MainFrame(Player p) {
		this.player = p;
		this.init();
	}
	public void init() {
		Image temp = new ImageIcon("./img/background.jpg").getImage().getScaledInstance(600, 600, java.awt.Image.SCALE_AREA_AVERAGING);
		this.img = new ImageIcon(temp);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		panelMain = new JPanel();
		

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
		this.loadImage("./img/profile.png");
		lblImage.setBounds(5, 15, 190, 140);
		panelMenu.add(lblImage);
		
		lblName = new JLabel(this.player.getName());
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("SansSerif", Font.PLAIN, 13));
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
		
		panelContent = new MyPanel();
		panelContent.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panelContent.setLayout(deck);

		panelContent.setBackground(new Color(204, 204, 255));
		panelContent.setBounds(200, 0, 600, 600);
		panelMain.add(panelContent);
		
		pnRank = new Rank(this.player);
		panelContent.add(pnRank,"rank");
		
//		pnHome = new Home(this.img);
		pnHome = new Home();
		panelContent.add(pnHome,"home");
		
		pnProfile = new Profile(this.player);
		pnProfile.setClientSocket(client);
		panelContent.add(pnProfile,"profile");
		
		pnIQTest = new IQTest();
		panelContent.add(pnIQTest,"iqTest");
		
		pnContest = new Contest(this.player);
		panelContent.add(pnContest,"contest");
		
		deck.show(panelContent, "home");
		btnHome.addActionListener(this);
		btnProfile.addActionListener(this);
		btnRank.addActionListener(this);
		btnIQ.addActionListener(this);
		
//		btnHome.addMouseListener(this);
//		btnIQ.addMouseListener(this);
//		btnRank.addMouseListener(this);
//		btnProfile.addMouseListener(this);
		

		
//		this.setUndecorated(true);
		setResizable(false);
		this.setVisible(true);
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		MenuButton a=(MenuButton) arg0.getSource();
	
		a.setBorder(null);
		
		a.setBackground(new Color(8, 87, 40).brighter());
		a.setForeground(Color.WHITE);
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		MenuButton a=(MenuButton) e.getSource();
		a.setBackground(Color.white);
		a.setForeground(Color.black);
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		MenuButton a=(MenuButton) arg0.getSource();
		a.setForeground(Color.WHITE);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		MenuButton a = (MenuButton) arg0.getSource();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
			}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		MenuButton source=(MenuButton) arg0.getSource();
		
		if(source == this.btnHome) {
//			this.panelContent = new Home();
			deck.removeLayoutComponent(pnHome);
			this.pnHome = new Home(this.img);
			panelContent.add(pnHome,"home");
			deck.show(panelContent, "home");

		}
		else if(source == this.btnProfile) {
//			panelContent = new Profile();
			deck.removeLayoutComponent(pnProfile);
			this.pnProfile= new Profile(this.player);
			pnProfile.setClientSocket(client);
			panelContent.add(pnProfile,"profile");
			deck.show(panelContent, "profile");
		}
		else if(source == this.btnIQ) {
//			panelContent = new Profile();
			deck.removeLayoutComponent(pnIQTest);
			this.pnIQTest= new IQTest();
			panelContent.add(pnIQTest,"iqTest");
			deck.show(panelContent, "iqTest");
		}
		else if(source == this.btnRank) {
//			panelContent = new Rank();
			deck.removeLayoutComponent(pnRank);
			this.pnRank= new Rank(this.player);
			panelContent.add(pnRank,"rank");
			deck.show(panelContent, "rank");
		}
	}
	public void clickStart() {
		deck.removeLayoutComponent(pnContest);
		this.pnContest= new Contest(this.player);
		panelContent.add(pnContest,"contest");
		deck.show(panelContent, "contest");
	}
	public void loadImage(String nameImage) {
		ImageIcon icon = new ImageIcon(nameImage); // load the image to a imageIcon
		Image image = icon.getImage(); // transform it
		Image newimg = image.getScaledInstance(190, 140, java.awt.Image.SCALE_SMOOTH); // scale it the// smooth way
		icon = new ImageIcon(newimg);
		lblImage.setIcon(icon);
	}

}
