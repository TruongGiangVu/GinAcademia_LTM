package Socket.Request;

public class SocketRequestLogin extends SocketRequest {
	private static final long serialVersionUID = 1L;
	public String username;
    public String password;

    public SocketRequestLogin(String username, String password) {
        super(SocketRequest.Action.LOGIN, "Login request.");
        this.username = username;
        this.password = password;
    }
}
