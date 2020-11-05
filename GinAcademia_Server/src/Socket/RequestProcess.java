package Socket;

import Socket.Request.*;
import Socket.Response.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import BUS.PlayerBUS;
import Model.Player;

public class RequestProcess {
	ClientHandler client;
	public SocketRequest request;
	PlayerBUS bus;
	Thread thread;

	public RequestProcess(ClientHandler clientHandler, SocketRequest requestRaw, PlayerBUS bus) {
		this.client = clientHandler;
		this.request = requestRaw;
		this.bus = bus;
	}

	public void createThread() {
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
				default:
					break;
				}
			}
		};
	}

	public void init() {
		this.createThread();
		thread.start();
	}

	private void updateProfileProcess() {
		SocketRequestPlayer tempRequest = (SocketRequestPlayer) request;
		bus.update(tempRequest.player);
		client.sendResponse(new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.MESSAGE,
				"Cập nhật thành công!"));
	}

	private void rankProcess() {
		ArrayList<Player> arr = bus.Read();
		client.sendResponse(new SocketResponseRank(arr));
	}

	private void registerProcess() {
		SocketRequestPlayer tempRequest = (SocketRequestPlayer) request;
		if (bus.checkExistPlayer(tempRequest.player) == true) {
			client.sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MESSAGE,
					"Username này đã tồn tại, Xin hãy đổi tên khác!"));
		} else {
			bus.insert(tempRequest.player);
			client.sendResponse(new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.MESSAGE,
					"Tạo tài khoản thành công!"));
		}
	}
}
