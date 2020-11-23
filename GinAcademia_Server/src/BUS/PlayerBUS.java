package BUS;

import java.util.ArrayList;
import Model.Player;
import DAO.PlayerDAO;

public class PlayerBUS {
	private PlayerDAO dao = new PlayerDAO();

	public ArrayList<Player> ReadAll() {
		return dao.readAll();
	}
	public ArrayList<Player> Read() {
		return dao.readAll();
	}
	public void insert(Player p) {
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
		if(this.getPlayerById(id).getStatus() == 1)
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
		for (Player p : arr) {
			if (p.getUsername().equals(user) && p.getPassword().equals(pass)) {
				check = true;
			}
		}
		return check;
	}
	public Player loginCheckPlayer(String user, String pass) {
		ArrayList<Player> arr = dao.readAll();
		for (Player p : arr) {
			if (p.getUsername().equals(user) && p.getPassword().equals(pass)) {
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
		if(p.getCurrentLoseSequence() > 0)
			p.setCurrentLoseSequence(0);
		int newWin = p.getCurrentWinSequence()+1;
		p.setCurrentWinSequence(newWin);
		if(newWin > p.getMaxWinSequence())
			p.setMaxWinSequence(newWin);
		
		int temp=p.getWins();
		p.setWins(temp +1);
		this.update(p);
		return p;
	}
	public Player updateLose(Player p) {
		if(p.getCurrentWinSequence() > 0)
			p.setCurrentWinSequence(0);
		int newLose = p.getCurrentLoseSequence()+1;
		p.setCurrentLoseSequence(newLose);
		if(newLose > p.getMaxLoseSequence())
			p.setMaxLoseSequence(newLose);
		// 
		int temp=p.getLoses();
		p.setLoses(temp +1);
		this.update(p);
		return p;
	}
	public boolean comparePlayer(Player p1, Player p2) {
		if(p1.getUsername().equals(p2.getUsername())) 
			return true;
		else return false;
	}
	public boolean comparePlayer(String user1, Player p2) {
		if(user1.equals(p2.getUsername())) 
			return true;
		return false;
	}
	public boolean comparePlayer(String user1, String user2) {
		if(user1.equals(user2)) 
			return true;
		return false;
	}
}
