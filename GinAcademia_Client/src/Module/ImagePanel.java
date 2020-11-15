package Module;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

import Socket.Client;
@SuppressWarnings("serial")
public class ImagePanel extends MyPanel{
	private ImageIcon img;
	public ImagePanel(Client client) {
		super(client);
		Image temp = new ImageIcon("./img/background.jpg").getImage().getScaledInstance(600, 600, java.awt.Image.SCALE_AREA_AVERAGING);
		this.img = new ImageIcon(temp);
		setLayout(null);
	}
	public ImagePanel(Client client,ImageIcon img) {
		super(client);
//		this(new ImageIcon(img).getImage());
	
		this.img = img;
		setLayout(null);
		
	}
	public ImagePanel(Client client,Image img,int width, int height) {
		super(client);
		this.img = new ImageIcon(img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
		
//		Dimension size = new Dimension(img.getWidth(null),img.getHeight(null));
//		Dimension size = new Dimension(600,600);
//		setPreferredSize(size);
//		setMinimumSize(size);
//		setSize(size);
		setLayout(null);
		
	}
	
	public ImageIcon getImg() {
		return img;
	}
	public void setImg(ImageIcon img) {
		this.img = img;
	}
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img.getImage(), 0, 0, null);
	}
}
