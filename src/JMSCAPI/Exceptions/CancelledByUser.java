package JMSCAPI.Exceptions;


public class CancelledByUser extends JMSCAPIException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8030423822182658550L;
	
	private static final int error = 1223;
	
	

	public CancelledByUser() {		
		super("The operation was canceled by the user.", error);
	}

	public CancelledByUser(String arg0) {
		super(arg0,error);
		// TODO Auto-generated constructor stub
	}
	
	public int getErrorCode(){
		return error;
	}

}
