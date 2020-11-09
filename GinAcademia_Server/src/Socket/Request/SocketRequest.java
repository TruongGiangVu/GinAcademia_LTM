package Socket.Request;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SocketRequest implements Serializable{
	public static enum Action {
        MESSAGE,
        LOGIN,
        REGISTER,
        DISCONNECT,
        PROFILE,
        RANK,
        UPDATEPROFILE,
        IQTEST,
        CONTEST
    }

    Action action;
    String message;

    public SocketRequest(Action action) {
        this.action = action;
    }
    public SocketRequest(Action action, String message) {
        this.action = action;
        this.message = message;
    }

    public Action getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }
}
