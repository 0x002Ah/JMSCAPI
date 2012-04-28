package JMSCAPI.misc;

/**
 * KP_MODE:
 * <ul>
 * <li>{@linkplain #CRYPT_MODE_CBC}</li>
 * <li>{@linkplain #CRYPT_MODE_CFB}</li>
 * <li>{@linkplain #CRYPT_MODE_CTS}</li>
 * <li>{@linkplain #CRYPT_MODE_ECB}</li>
 * <li>{@linkplain #CRYPT_MODE_OFB}</li>
 * </ul>
 */
public interface KP_MODE {
	
	/**
	 * The cipher mode is cipher block chaining.
	 */
	public static final int CRYPT_MODE_CBC     =    1;       // Cipher block chaining
	
	/**
	 * The cipher mode is electronic codebook.
	 */
	public static final int CRYPT_MODE_ECB     =    2;       // Electronic code book
	
	/**
	 * The cipher mode is Output Feedback (OFB). Microsoft CSPs currently do not support Output
	 * Feedback Mode.
	 */
	public static final int CRYPT_MODE_OFB     =    3;       // Output feedback mode
	
	/**
	 * The cipher mode is cipher feedback (CFB). Microsoft CSPs currently support only 8-bit
	 * feedback in cipher feedback mode.
	 */
	public static final int CRYPT_MODE_CFB     =    4;       // Cipher feedback mode
	
	/**
	 * The cipher mode is ciphertext stealing mode.
	 */
	public static final int CRYPT_MODE_CTS     =    5;       // Ciphertext stealing mode

}
