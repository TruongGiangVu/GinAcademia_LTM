package Socket;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Model.GameConfig;
import Model.Player;
import Model.Question;
import Socket.Request.SocketRequest;
import Socket.Request.SocketRequestAnswer;
import Socket.Response.SocketResponse;
import Socket.Response.SocketResponseContest;
import Socket.Response.SocketResponseGameRoom;
import Socket.Response.SocketResponsePlayer;
import Socket.Response.SocketResponseQuestion;
import BUS.QuestionBUS;
import BUS.PlayerBUS;

public class ContestRoom {
	public int RoomId = -1;
	private boolean isClocked = false;
	public GameConfig config;
	public QuestionBUS questionBus = new QuestionBUS();
	public PlayerBUS playerBus = new PlayerBUS();
	public ArrayList<Question> questions = new ArrayList<Question>();
	public int currentQ = 0;
	public Player winner = null;
	public int countAns = 0;
	public int accept = 0;

	public boolean isEndContest = false;

	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<Integer> points = new ArrayList<Integer>();
	ArrayList<Integer> answers = new ArrayList<Integer>();
	ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

	Timer contestTimer;

	public ContestRoom(int RoomId, GameConfig config) {
		this.RoomId = RoomId;
		this.config =  new GameConfig(config);
		this.questions = questionBus.ReadContest(this.config.getNumQuestion());
	}

	public boolean isClocked() {
		return isClocked;
	}

	public void joinGame(ClientHandler client) {
		if (this.isClocked)
			return;
		if (this.players.size() < this.config.getNumPlayer()) {
			this.players.add(client.player);
			this.clients.add(client);
			this.points.add(0);
			this.answers.add(0);
		}

		if (this.players.size() == this.config.getNumPlayer()) {
			int n = this.clients.size();
			for (int i = 0; i < n; ++i) {
				this.clients.get(i).isInGame = true;
				System.out.println(this.clients.get(i).player.getId() + " ");
			}

			this.sendALL(new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.MESSAGE, "HasGame"),
					false);

			try { // delay 1s for client open ContestPanel
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.isClocked = true;
			System.out.println("start contest");
			// announce to all players about starting game
			this.sendALL(new SocketResponseContest(this.players, this.points, this.config), true);

			// start contest
			this.startContest();
		}

	}

	public void leaveRoom(ClientHandler client) {
		System.out.println(client.player.getId() + " leave Game " + RoomId);
		if (this.isClocked)
			return;
		int index = this.indexOfPlayer(client.player);
		if (index >= 0) {
			this.clients.remove(index);
			this.players.remove(index);
			this.points.remove(index);
			this.answers.remove(index);
		}
		System.out.println("Num player in Game:" + this.RoomId + " " + this.players.size());
	}

	public void removePlayer(ClientHandler client) {
		int index = this.indexOfPlayer(client.player);
		if (index >= 0) {
			this.playerBus.updateLose(client.player);
			this.players.remove(index);
			this.points.remove(index);
			this.clients.remove(index);
			this.answers.remove(index);
			int num = this.config.getNumPlayer();
			this.config.setNumPlayer(num - 1);
		}
//		if (this.players.size() == 0)
		System.out.println("Num player in Game:" + this.RoomId + " " + this.players.size());
	}

	public boolean isRoomAvailable() {
		if (this.isClocked)
			return false;
		if (this.players.size() < this.config.getNumPlayer())
			return true;
		else
			return false;
	}

	public int amountOfPlayerInGame() {
		return this.players.size();
	}

	public void startContest() {
		contestTimer = new Timer();
		contestTimer.scheduleAtFixedRate(new ContestTask(), 0, 1000);
		this.countAns = 0;
		this.sendALL(new SocketResponseQuestion(this.questions.get(this.currentQ)), true); // send first question
	}

	class ContestTask extends TimerTask {
		int countdown = config.getTime();

		public ContestTask() {
			System.out.println("start countdown");
		}

		public void run() {
			if (countAns == config.getNumPlayer()) { // all players have answered
				endTurn();
			}
			if (countdown == 0) { // time out
				endTurn();
			}
			countdown--;
		}
	}

	public void endTurn() {
		try {
			contestTimer.cancel(); // cancel current timer

			int next = 1;
			if (this.currentQ == this.config.getNumQuestion() - 1) // if has no next question
				next = 0;
			this.sendALL( // send to all
					new SocketResponseGameRoom(this.players, this.points, this.answers,
							this.questions.get(this.currentQ).getAnswer(), this.questions.get(this.currentQ + next)),
					true);
			refreshAnswer(); // reset answer
			this.currentQ++;
			Thread.sleep(2000); // delay 2s for players see answer

			this.countAns = 0; // reset number answer
			if (next == 0) { // end game and announce result
				this.endGame();
				return;
			}
			// create new timer for next question
			contestTimer = new Timer();
			contestTimer.scheduleAtFixedRate(new ContestTask(), 0, 1000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendALL(SocketResponse response, boolean flag) {
		int n = this.clients.size();
		for (int i = 0; i < n; ++i) {
			this.clients.get(i).sendResponse(response, flag);
		}
	}

	private int indexOfPlayer(Player p) {
		int ind = -1;
		int n = this.players.size();
		for (int i = 0; i < n; ++i) {
			if (this.players.get(i).getId().equals(p.getId())) {
				ind = i;
				break;
			}
		}
		return ind;
	}

	public void getAnswer(SocketRequest requestRaw) {
		SocketRequestAnswer request = (SocketRequestAnswer) requestRaw;
		int index = this.indexOfPlayer(request.player);
		this.answers.set(index, request.getAns());
		boolean ans = false;
		if (request.getAns() == this.questions.get(this.currentQ).getAnswer()) { // answer right
			ans = true;
		}
		int time = request.getTime();

		System.out.print(request.player.getId() + " " + request.getAns());
		this.updatePoint(index, ans, time);

	}

	public void updatePoint(int index, boolean ans, int time) {
		int currentPoint = this.points.get(index);
		int res = currentPoint;
		if (ans) {
			res = currentPoint + time / 100;
		}
		this.points.set(index, res);
		this.countAns++;
	}

	public void refreshAnswer() { // refresh to No (0) answer
		int n = this.answers.size();
		for (int i = 0; i < n; ++i) {
			this.answers.set(i, 0);
		}
	}

	public void endGame() {
		System.out.println("End game");
		if (this.players.size() == 0) {
			this.endRoom();
			return;
		} else if (this.players.size() == 1) {
			this.onlyOne();
		} else {
			// check tie
			if (this.checkTie2Player()) { // if no one wins
				this.noWinner();
			} else { // if it has winner
				this.hasWinner();
			}
		}

		this.refreshGameOfClient();
		this.endRoom();
	}
	
	private void endRoom() {
		this.isEndContest = true; // for deleting this game room
		Server.contestRoomManager.finishRoom(RoomId);
	}

	private void refreshGameOfClient() {
		int n = this.clients.size();
		for (int i = 0; i < n; ++i) {
			this.clients.get(i).isInGame = false;
			this.clients.get(i).contestRoom = null;
		}
	}

	private boolean checkTie2Player() {
		boolean ans = false;
		if (this.points.get(0) == this.points.get(1))
			ans = true;
		return ans;
	}

	private void noWinner() {
		int n = this.points.size();
		for (int i = 0; i < n; ++i) {
			this.clients.get(i).player = this.players.get(i);
			this.clients.get(i).sendResponse(new SocketResponsePlayer(this.clients.get(i).player,
					SocketResponse.Action.MESSAGE, "Trận đấu hòa!"), false);
		}
	}

	private void hasWinner() {
		// get index of winner
		int n = this.points.size();
		int index = -1;
		int maxPoint = -1;
		for (int i = 0; i < n; ++i) {
			if (this.points.get(i) > maxPoint) {
				maxPoint = this.points.get(i);
				index = i;
			}
		}
		// winner: update and send new info to player
		this.winner = this.players.get(index);
		this.clients.get(index).player = this.playerBus.updateWin(this.winner);
		this.clients.get(index).sendResponse(
				new SocketResponsePlayer(this.clients.get(index).player, SocketResponse.Action.MESSAGE, "Bạn thắng!"),
				false);

		// loser: update and send new info to player
		for (int i = 0; i < n; ++i) {
			if (i == index)
				continue;
			this.clients.get(i).player = this.playerBus.updateLose(this.players.get(i));
			this.clients.get(i).sendResponse(
					new SocketResponsePlayer(this.clients.get(i).player, SocketResponse.Action.MESSAGE, "Bạn thua!"),
					false);
		}
	}

	private void onlyOne() { // if it just has a player
		this.winner = this.players.get(0);
		this.clients.get(0).player = this.playerBus.updateWin(this.winner);
		this.clients.get(0).sendResponse(
				new SocketResponsePlayer(this.clients.get(0).player, SocketResponse.Action.MESSAGE, "Bạn thắng!"),
				false);
	}

}
