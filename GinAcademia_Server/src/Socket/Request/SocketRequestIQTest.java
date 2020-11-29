package Socket.Request;

public class SocketRequestIQTest extends SocketRequest {

	private static final long serialVersionUID = 1L;
	public int numQ= 0;
	public int numRight = 0;
	public SocketRequestIQTest(int numQ, int numRight) {
		  super(SocketRequest.Action.IQTEST, "Send IQ test.");
		  this.numQ = numQ;
		  this.numRight = numRight;
	}
}
