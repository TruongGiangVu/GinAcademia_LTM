package Socket.Response;

import java.io.Serializable;

public class SocketResponse implements Serializable {
    public static enum Status {
        SUCCESS,
        FAILED
    }
    public static enum Action {
        MESSAGE,
        NET_CLOSE,
        UPDATE_GAMEROOM,
        PLAYER
    }

    Status status;
    Action action;
    String message;

    public SocketResponse(Status status, Action action, String message) {
        this.status = status;
        this.action = action;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public Action getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }
}
