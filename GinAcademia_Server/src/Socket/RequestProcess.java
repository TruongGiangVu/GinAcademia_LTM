package Socket;

import Socket.Request.*;
import Socket.Response.*;

import java.util.ArrayList;

import BUS.PlayerBUS;
import BUS.QuestionBUS;
import Model.Player;

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
				case IQTEST:
					iQTestProcess();
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

	private void updateProfileProcess() { // update profile
		SocketRequestPlayer tempRequest = (SocketRequestPlayer) request;
		playerBus.update(tempRequest.player);
		client.sendResponse(new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.MESSAGE,
				"Cập nhật thành công!"),false);
	}

	private void rankProcess() { // send list player
		ArrayList<Player> arr = playerBus.Read();
		client.sendResponse(new SocketResponseRank(arr),false);
	}

	private void iQTestProcess() {
		SocketRequestIQTest iqtest = (SocketRequestIQTest) this.request;
		int point = iqtest.numRight * 155 / iqtest.numQ;
		client.player.setIQPoint(point);
		playerBus.update(client.player);
		client.sendResponse(new SocketResponsePlayer(client.player),false);
	}
}
