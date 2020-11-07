package Socket;

import Socket.Request.*;
import Socket.Response.*;

import java.util.ArrayList;

import BUS.PlayerBUS;
import BUS.QuestionBUS;
import Model.Player;
import Model.Question;

public class RequestProcess {
	ClientHandler client;
	public SocketRequest request;
	PlayerBUS playerBus;
	QuestionBUS questionBUS; 
	Thread thread;

	public RequestProcess(ClientHandler clientHandler, SocketRequest requestRaw) {
		this.client = clientHandler;
		this.request = requestRaw;
		
		this.playerBus = new PlayerBUS();
		this.questionBUS = new QuestionBUS();
	}

	public void createThread() { // create a thread to solve request and send response
		this.thread = new Thread() {
			@Override
			public void run() {
				switch (request.getAction()) {
				case UPDATEPROFILE:
					updateProfileProcess();
					break;
				case RANK:
					rankProcess();
					break;
				case REGISTER:
					registerProcess();
					break;
				case IQTEST:
					break;
				case CONTEST:
					contestProcess();
					break;
				default:
					break;
				}
			}
		};
		// after finish, this thread will be deleted
	}

	public void init() { // run init to, start thread
		this.createThread();
		thread.start();
	}

	private void updateProfileProcess() {
		SocketRequestPlayer tempRequest = (SocketRequestPlayer) request;
		playerBus.update(tempRequest.player);
		client.sendResponse(new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.MESSAGE,
				"Cập nhật thành công!"));
	}

	private void rankProcess() {
		ArrayList<Player> arr = playerBus.Read();
		client.sendResponse(new SocketResponseRank(arr));
	}

	private void registerProcess() {
		SocketRequestPlayer tempRequest = (SocketRequestPlayer) request;
		if (playerBus.checkExistPlayer(tempRequest.player) == true) {
			client.sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
					"Username này đã tồn tại, Xin hãy đổi tên khác!"));
		} else {
			playerBus.insert(tempRequest.player);
			Player p = playerBus.loginCheckPlayer(tempRequest.player.getUsername(), tempRequest.player.getPassword());
			client.sendResponse(new SocketResponsePlayer(p));
		}
	}
	private void contestProcess() {
		ArrayList<Question> arr = questionBUS.ReadContest();
		client.sendResponse(new SocketResponseContest(arr));
	}
}
