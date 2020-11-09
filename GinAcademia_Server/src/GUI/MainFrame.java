package GUI;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.FlowLayout;
import javax.swing.SwingConstants;

import Module.MenuButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel panelButton;
	private MenuButton btnHome;
	private MenuButton btnQuestion;
	private MenuButton btnPlayer;
	private JPanel panelContent;
	private JLabel lblNewLabel;
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
		panelButton.setAlignmentX(MAXIMIZED_HORIZ);;
		contentPane.add(panelButton);
		panelButton.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnHome = new MenuButton("Home");
		buttonGroup.add(btnHome);
		panelButton.add(btnHome);
		
		btnQuestion = new MenuButton("Question");
		buttonGroup.add(btnQuestion);
		panelButton.add(btnQuestion);
		
		btnPlayer = new MenuButton("Player");
		buttonGroup.add(btnPlayer);
		panelButton.add(btnPlayer);
		
//		panelContent = new JPanel();
		panelContent = new QuestionPanel();
		panelContent.setBounds(200, 0, 600, 600);
		panelContent.setBackground(Color.WHITE);
//		panelContent.setBorder(new MatteBorder(0,1,0,0, Color.BLACK));
		contentPane.add(panelContent);
		
		lblNewLabel = new JLabel("MENU");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(10, 15, 180, 40);
		contentPane.add(lblNewLabel);
	}
}
