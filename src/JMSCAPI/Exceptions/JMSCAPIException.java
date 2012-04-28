package JMSCAPI.Exceptions;

import com.sun.jna.platform.win32.Kernel32Util;

/**
 * General exception class for JMSCAPI.
 * 
 *
 */
public class JMSCAPIException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5788791774029861929L;
	
	private int error;

	public JMSCAPIException() {		

	}

	public JMSCAPIException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public JMSCAPIException(int errorCode) {
		super(Kernel32Util.formatMessageFromLastErrorCode(errorCode));
		this.error = errorCode;
		
	}
	
	public JMSCAPIException(String arg0,int errorCode) {
		super(arg0);
		this.error = errorCode;
		
	}
	
	public int getErrorCode(){
		return error;
	}

}
