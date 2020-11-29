package GUI;



import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import Model.Player;
import Socket.Client;
import Socket.Request.SocketRequest;
import Socket.Request.SocketRequestPlayer;
import Socket.Response.SocketResponse;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class AvatarDialog extends JDialog implements ActionListener {

	private Map<String,ImageIcon> imageMap;
	private JList<String> listImg;
	private String[] arrStr;
	private JButton btnSelected;
	private JButton btnCancle;
	
	Client client;
	Player player;

	public AvatarDialog(Client client, Player player) {
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);
		this.client = client;
		this.player = player;
		getContentPane().setLayout(null);
		setBounds(100, 100, 500, 340);
		imageMap = this.loadImages();
		listImg = new JList<String>();
		
		listImg.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listImg.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    listImg.setVisibleRowCount(2);
		listImg.setListData(arrStr);
		listImg.setCellRenderer(new ImageListRenderer());
		JScrollPane scrollPane = new JScrollPane(listImg);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(new EmptyBorder(2, 8, 2, 0));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollPane.setPreferredSize(new Dimension(300, 400));
		scrollPane.setBounds(0, 0, 484, 259);
		getContentPane().add(scrollPane);
		
		btnSelected = new JButton("Chọn");
		btnSelected.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSelected.setBackground(Color.WHITE);
		btnSelected.setBounds(175, 270, 70, 25);
		getContentPane().add(btnSelected);
		
		btnCancle = new JButton("Hủy");
		btnCancle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCancle.setBackground(Color.WHITE);
		btnCancle.setBounds(255, 270, 70, 25);
		getContentPane().add(btnCancle);
		btnSelected.addActionListener(this);
		btnCancle.addActionListener(this);
		
	}
	public class ImageListRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.BOLD, 10);

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(imageMap.get(value.toString()));
            label.setVerticalTextPosition(JLabel.BOTTOM); 
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setFont(font);
            return label;
        }
    }
	private Map<String,ImageIcon> loadImages() {
		File folder = new File("./img/Avatar/");
		File[] listOfFiles = folder.listFiles();
        Map<String, ImageIcon> map = new HashMap<>();
        arrStr = new String[listOfFiles.length];
		int i = 0;
		for (File file : listOfFiles) {
			if (file.isFile()) {
		    	map.put(file.getName(), loadImage("./img/Avatar/"+file.getName())); 
		    	arrStr[i] = file.getName();
		    	i+=1;
			}
		}
		
		return map;
	}
	
	public ImageIcon loadImage(String nameImage) {
		ImageIcon icon = new ImageIcon(nameImage); // load the image to a imageIcon
		Image image = icon.getImage(); // transform it
		Image newimg = image.getScaledInstance(140,130, java.awt.Image.SCALE_SMOOTH); // scale it the// smooth way
		icon = new ImageIcon(newimg);
		return icon;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSelected) {
			this.player.setImage(listImg.getSelectedValue());
			if(client.checkSend) {
				client.sendRequest(new SocketRequestPlayer(SocketRequest.Action.UPDATEPROFILE, this.player));
				if(client.checkSend) {
					MainFrame.lblImage.setIcon(imageMap.get(listImg.getSelectedValue()));
					dispose();
				}
			}
		}
		else {
			if(e.getSource() == btnCancle) {
				dispose();
			}
		}
	}
}
