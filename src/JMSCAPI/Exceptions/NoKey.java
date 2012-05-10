package JMSCAPI.Exceptions;

public class NoKey extends JMSCAPIException {
	
	private static final int error = -2146893811;

	public NoKey() {		
		super("Requested key does not exist.", error);
	}

	public NoKey(String arg0) {
		super(arg0,error);
		// TODO Auto-generated constructor stub
	}
	
	public int getErrorCode(){
		return error;
	}
}
