package GUI;

import java.awt.Color;

import Module.MyPanel;
import Socket.Client;

@SuppressWarnings("serial")
public class IQTest extends MyPanel {

	/**
	 * Create the panel.
	 */
	public IQTest(Client client) {
		super(client);
		this.setBackground(Color.WHITE);
	}

}
