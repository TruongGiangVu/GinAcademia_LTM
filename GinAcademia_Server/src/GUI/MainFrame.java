package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

import Module.MenuButton;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel panelButton;
	private MenuButton btnHome;
	private MenuButton btnQuestion;
	private MenuButton btnPlayer;
	private JPanel panelContent;

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelButton = new JPanel();
		panelButton.setBackground(new Color(31, 22, 127));
//		panel.setLayout(new GridLayout(0, 1, 0, 0));
		panelButton.setBounds(0, 0, 200, 600);
		panelButton.setAlignmentX(MAXIMIZED_HORIZ);;
		contentPane.add(panelButton);
		panelButton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnHome = new MenuButton("Home");
		panelButton.add(btnHome);
		
		btnQuestion = new MenuButton("Question");
		btnQuestion.setVerticalAlignment(SwingConstants.TOP);
		panelButton.add(btnQuestion);
		
		btnPlayer = new MenuButton("Player");
		panelButton.add(btnPlayer);
		
//		panelContent = new JPanel();
		panelContent = new QuestionPanel();
		panelContent.setBounds(200, 0, 600, 600);
		contentPane.add(panelContent);
	}
}
