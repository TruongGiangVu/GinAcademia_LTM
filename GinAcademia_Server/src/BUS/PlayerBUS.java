package BUS;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import Model.Player;
import DAO.PlayerDAO;

public class PlayerBUS {
	private PlayerDAO dao = new PlayerDAO();

	public ArrayList<Player> ReadAll() {
		return dao.readAll();
	}

	public ArrayList<Player> Read() {
		return dao.read();
	}

	public void insert(Player p) {
		p = this.hashPasswordPlayer(p);
		dao.insert(p);
	}

	public void delete(String id) {
		dao.delete(id);
	}

	public void block(String id) {
		dao.disable(id);
	}

	public boolean isBlock(String id) {
		boolean isBlock = false;
		if (this.getPlayerById(id).getStatus() == 1)
			isBlock = true;
		return isBlock;
	}

	public void active(String id) {
		dao.active(id);
	}

	public Player update(Player p) {
		dao.update(p);
		return p;
	}

	public Player getPlayerById(String id) {
		return dao.getPlayerById(id);
	}

	public void close() {
		dao.close();
	}

	public boolean loginCheck(String user, String pass) {
		boolean check = false;
		ArrayList<Player> arr = dao.readAll();
		pass = this.digestString(pass.getBytes());
		for (Player p : arr) {
			if (p.getUsername().equals(user) && this.comparePass(pass, p)) {
				check = true;
			}
		}
		return check;
	}

	public Player loginCheckPlayer(String user, String pass) {
		ArrayList<Player> arr = dao.readAll();
		pass = this.digestString(pass.getBytes());
		for (Player p : arr) {
			if (p.getUsername().equals(user) && this.comparePass(pass, p)) {
				return p;
			}
		}
		return null;
	}

	public boolean checkExistPlayer(Player x) {
		boolean check = false;
		String user = x.getUsername();
		ArrayList<Player> arr = dao.read();
		for (Player p : arr) {
			if (p.getUsername().equals(user)) {
				check = true;
			}
		}
		return check;
	}

	public Player updateWin(Player p) {
		if (p.getCurrentLoseSequence() > 0)
			p.setCurrentLoseSequence(0);
		int newWin = p.getCurrentWinSequence() + 1;
		p.setCurrentWinSequence(newWin);
		if (newWin > p.getMaxWinSequence())
			p.setMaxWinSequence(newWin);

		int temp = p.getWins();
		p.setWins(temp + 1);
		this.update(p);
		return p;
	}

	public Player updateLose(Player p) {
		if (p.getCurrentWinSequence() > 0)
			p.setCurrentWinSequence(0);
		int newLose = p.getCurrentLoseSequence() + 1;
		p.setCurrentLoseSequence(newLose);
		if (newLose > p.getMaxLoseSequence())
			p.setMaxLoseSequence(newLose);
		//
		int temp = p.getLoses();
		p.setLoses(temp + 1);
		this.update(p);
		return p;
	}

	public boolean comparePlayer(Player p1, Player p2) {
		if (p1.getUsername().equals(p2.getUsername()))
			return true;
		else
			return false;
	}

	public boolean comparePlayer(String user1, Player p2) {
		if (user1.equals(p2.getUsername()))
			return true;
		return false;
	}

	public boolean comparePlayer(String user1, String user2) {
		if (user1.equals(user2))
			return true;
		return false;
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	private String digestString(byte[] input) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
		byte[] result = md.digest(input);
		return bytesToHex(result);
	}

	private Player hashPasswordPlayer(Player p) {
		String pass = p.getPassword();
		p.setPassword(this.digestString(pass.getBytes()));
		return p;
	}

	private boolean comparePass(String pass, Player p) {
		if (pass.equals(p.getPassword()))
			return true;
		else
			return false;
	}

}
