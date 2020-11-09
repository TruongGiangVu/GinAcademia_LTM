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
import Socket.Response.SocketResponseAnswer;
import Socket.Response.SocketResponseContest;
import Socket.Response.SocketResponseQuestion;
import BUS.QuestionBUS;
import BUS.PlayerBUS;

public class ContestRoom {
	public int RoomId = -1;
	public GameConfig config;
	public QuestionBUS questionBus = new QuestionBUS();
	public PlayerBUS playerBus = new PlayerBUS();
	public ArrayList<Question> questions = new ArrayList<Question>();
	public int currentQ = 0;
	public Player winner = null;
	public int countAns = 0;

	public boolean isEndContest = false;

	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<Integer> points = new ArrayList<Integer>();
	ArrayList<Integer> answers = new ArrayList<Integer>();
	ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

	Timer contestTimer;

	public ContestRoom(int RoomId, GameConfig config) {
		this.RoomId = RoomId;
		this.config = config;
		this.questions = questionBus.ReadContest(this.config.getNumQuestion());
	}

	public void joinGame(Player player, ClientHandler client) {
		if (this.players.size() < this.config.getNumPlayer()) {
			this.players.add(player);
			this.clients.add(client);
			this.points.add(0);
			this.answers.add(0);
		} else if (this.players.size() == this.config.getNumPlayer()) {
			// start contest
			this.startContest();
		}
	}

	public void removePlayer(Player player) {
		int index = this.players.indexOf(player);
		if (index >= 0) {
			this.playerBus.updateLose(player);
			this.players.remove(index);
			this.points.remove(index);
			this.clients.remove(index);
			int num = this.config.getNumPlayer();
			this.config.setNumPlayer(num - 1);
		}
	}

	public boolean isRoomAvailable() {
		if (this.players.size() < this.config.getNumPlayer())
			return true;
		else
			return false;
	}

	public void startContest() {
		this.sendStartContest(); // announce to all players about starting game

		contestTimer = new Timer();
		contestTimer.scheduleAtFixedRate(new ContestTask(), 0, 1000);
	}

	class ContestTask extends TimerTask {
		int countdown = 10;

		public ContestTask() {
			sendQuestionToAll();
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
			this.sendAllAnswer(); // send answer
			Thread.sleep(2000); // stop 2s

			// send next question
			this.currentQ++;
			if (this.currentQ == this.config.getNumQuestion()) { // currentQ == 5, stop repeat
				this.endGame();
				return;
			}
			this.sendQuestionToAll();

			// create new timer for next question
			contestTimer = new Timer();
			contestTimer.scheduleAtFixedRate(new ContestTask(), 0, 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendALL(SocketResponse response) {
		int n = this.clients.size();
		for (int i = 0; i < n; ++i) {
			this.clients.get(i).sendResponse(response);
		}
	}

	public void sendQuestionToAll() {
		this.countAns = 0;
		this.sendALL(new SocketResponseQuestion(this.questions.get(this.currentQ)));
	}

	public void sendAllAnswer() {
		this.sendALL(
				new SocketResponseAnswer(this.players, this.answers, this.questions.get(this.currentQ).getAnswer()));
		this.refreshAnswer();
	}

	public void sendStartContest() {
		this.sendALL(new SocketResponseContest(this.players, this.points));
	}

	public void getAnswer(SocketRequest requestRaw) {
		SocketRequestAnswer request = (SocketRequestAnswer) requestRaw;
		int index = this.players.indexOf(request.player);
		this.answers.set(index, request.getAns());
		boolean ans = false;
		if (request.getAns() == this.questions.get(this.currentQ).getAnswer()) { // answer right
			ans = true;
		}
		int time = request.getTime();

		this.updatePoint(index, ans, time);
	}

	public void updatePoint(int index, boolean ans, int time) {
		int currentPoint = this.points.get(index);
		int res = currentPoint;
		if (ans) {
			res = currentPoint + time / 10;
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
		int n = this.points.size();
		int index = -1;
		int maxPoint = -1;
		for (int i = 0; i < n; ++i) {
			if (this.points.get(i) > maxPoint) {
				maxPoint = this.points.get(i);
				index = i;
			}
		}
		this.winner = this.players.get(index);
		this.clients.get(index).sendResponse(
				new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.MESSAGE, "Bạn thắng!"));
		this.playerBus.updateWin(this.winner);
		for (int i = 0; i < n; ++i) {
			if (i == index)
				continue;
			this.playerBus.updateLose(this.players.get(i));
			this.clients.get(i).sendResponse(
					new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.MESSAGE, "Bạn thua!"));

		}
		this.isEndContest = true;
	}

}
