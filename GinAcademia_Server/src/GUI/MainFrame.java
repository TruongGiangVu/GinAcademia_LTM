package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;

import Module.MenuButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel panelButton;
	private MenuButton btnHome;
	private MenuButton btnQuestion;
	private CardLayout deck = new CardLayout();
	private MenuButton btnPlayer;
	private JPanel panelContent;
	private JLabel lblNewLabel;
	
	private JPanel pnQuestion;
	private JPanel pnPlayer;
	private JPanel pnHome;
	private final ButtonGroup buttonGroup = new ButtonGroup();

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

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		setBounds(100, 100, 800, 600);
		this.setTitle("GinAcademia - Admin");
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelButton = new JPanel();
		panelButton.setBackground(Color.WHITE);
//		panel.setLayout(new GridLayout(0, 1, 0, 0));
		panelButton.setBounds(10, 70, 180, 215);
		panelButton.setAlignmentX(MAXIMIZED_HORIZ);
		contentPane.add(panelButton);
		panelButton.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnHome = new MenuButton("Giao diện chính");
		buttonGroup.add(btnHome);
		panelButton.add(btnHome);
		
		btnQuestion = new MenuButton("Câu hỏi");
		buttonGroup.add(btnQuestion);
		panelButton.add(btnQuestion);
		
		btnPlayer = new MenuButton("Người chơi");
		buttonGroup.add(btnPlayer);
		panelButton.add(btnPlayer);
		
		panelContent = new JPanel();
		panelContent.setBounds(200, 0, 600, 600);
		panelContent.setBackground(Color.WHITE);
		panelContent.setBorder(new MatteBorder(1,1,0,0, (Color) new Color(8, 87, 40).brighter()));
		panelContent.setLayout(deck);
		contentPane.add(panelContent);
		
		lblNewLabel = new JLabel("QUẢN LÝ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(0, 0, 201, 55);
		lblNewLabel.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(8,87,40).brighter()));
		contentPane.add(lblNewLabel);
		
		pnHome = new Home();
		panelContent.add(pnHome,"home");
		
		pnQuestion = new QuestionPanel();
		panelContent.add(pnQuestion,"question");
		
		pnPlayer = new PlayerPanel();
		panelContent.add(pnPlayer,"player");
		
		deck.show(panelContent, "home");
		btnHome.addActionListener(this);
		btnPlayer.addActionListener(this);
		btnQuestion.addActionListener(this);
//		btnIQ.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		MenuButton source=(MenuButton) arg0.getSource();
		
		if(source == this.btnHome) {
			deck.removeLayoutComponent(pnHome);
			this.pnHome = new Home();
			panelContent.add(pnHome,"home");
			deck.show(panelContent, "home");

		}
		else if(source == this.btnQuestion) {
			deck.removeLayoutComponent(pnQuestion);
			this.pnQuestion= new QuestionPanel();
			panelContent.add(pnQuestion,"question");
			deck.show(panelContent,"question");
		}
		else if(source == this.btnPlayer) {
			deck.removeLayoutComponent(pnPlayer);
			this.pnPlayer= new PlayerPanel();
			panelContent.add(pnPlayer,"player");
			deck.show(panelContent, "player");
		}
		
	}
}
