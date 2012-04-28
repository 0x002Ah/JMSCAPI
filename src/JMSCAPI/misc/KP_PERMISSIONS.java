package JMSCAPI.misc;

/**
 * KP_PERMISSIONS:<ul>
 * <li>{@linkplain #CRYPT_ARCHIVE}</li>
 * <li>{@linkplain #CRYPT_DECRYPT}</li>
 * <li>{@linkplain #CRYPT_ENCRYPT}</li>
 * <li>{@linkplain #CRYPT_EXPORT}</li>
 * <li>{@linkplain #CRYPT_EXPORT_KEY}</li>
 * <li>{@linkplain #CRYPT_IMPORT_KEY}</li>
 * <li>{@linkplain #CRYPT_MAC}</li>
 * <li>{@linkplain #CRYPT_READ}</li>
 * <li>{@linkplain #CRYPT_WRITE}</li>
 * </ul>
 */
public interface KP_PERMISSIONS {	
	/**
	 * Allow encryption
	 */
	public static final int CRYPT_ENCRYPT		=	0x0001;
	
	/**
	 * Allow decryption
	 */
	public static final int CRYPT_DECRYPT       =   0x0002;  // 
	
	/**
	 * Allow key to be exported
	 */
	public static final int CRYPT_EXPORT        =   0x0004;  // 
	
	/**
	 * Allow parameters to be read
	 */
	public static final int CRYPT_READ          =   0x0008;  // 
	
	/**
	 * Allow parameters to be set
	 */
	public static final int CRYPT_WRITE         =   0x0010;  // 
	
	/**
	 * Allow MACs to be used with key
	 */
	public static final int CRYPT_MAC           =   0x0020;  // 
	
	/**
	 * Allow key to be used for exporting keys
	 */
	public static final int CRYPT_EXPORT_KEY    =   0x0040;  // 
	
	/**
	 * Allow key to be used for importing keys
	 */
	public static final int CRYPT_IMPORT_KEY    =   0x0080;  // 
	
	/**
	 * Allow key to be exported at creation only
	 */
	public static final int CRYPT_ARCHIVE       =   0x0100;  // 
}
