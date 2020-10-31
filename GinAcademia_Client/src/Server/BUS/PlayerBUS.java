package Server.BUS;

import java.util.ArrayList;
import Model.Player;
import Server.DAO.PlayerDAO;

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

	public void update(Player p) {
		dao.update(p);
	}
	public Player getPlayerById(String id) {
		return dao.getPlayerById(id);
	}
	public void close() {
		dao.close();
	}
	public boolean loginCheck(String user, String pass) {
		boolean check = false;
		ArrayList<Player> arr = dao.read();
		for (Player p : arr) {
			if (p.getUsername().equals(user) && p.getPassword().equals(pass)) {
				check = true;
			}
		}
		return check;
	}
	public Player loginCheckPlayer(String user, String pass) {
		ArrayList<Player> arr = dao.read();
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
	public void updateWin(Player p) {
		if(p.getCurrentLoseSequence() > 0)
			p.setCurrentLoseSequence(0);
		int newWin = p.getCurrentWinSequence()+1;
		p.setCurrentWinSequence(newWin);
		if(newWin > p.getMaxWinSequence())
			p.setMaxWinSequence(newWin);
		this.update(p);
	}
	public void updateLose(Player p) {
		if(p.getCurrentWinSequence() > 0)
			p.setCurrentWinSequence(0);
		int newLose = p.getCurrentLoseSequence()+1;
		p.setCurrentLoseSequence(newLose);
		if(newLose > p.getMaxLoseSequence())
			p.setMaxLoseSequence(newLose);
		this.update(p);
	}
}
