package JMSCAPI.Exceptions;

public class NotImplemented extends JMSCAPIException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2156123922341123291L;
	
	private static final int error = 0x80004001;

	public NotImplemented() {
		super("Not implemented.", error);
	}

	public NotImplemented(String arg0) {
		super(arg0,error);		
	}

	public int getErrorCode(){
		return error;
	}



}
