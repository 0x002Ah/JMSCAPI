package JMSCAPI.misc;

public interface KeyFlags {
	
	/**
	 * Allows for the import of an RC2 key that is larger than 16 bytes. If this flag is not set, calls
	 * to the CryptImportKey function with RC2 keys that are greater than 16 bytes fail, and a call to
	 * GetLastError will return NTE_BAD_DATA.
	 */
	public static final int CRYPT_IPSEC_HMAC_KEY = 0x00000100;
	
	/**
	 * This flag causes PKCS #1 version 2 formatting to be created with the RSA encryption and decryption when
	 * importing/exporting SIMPLEBLOBs.
	 */
	public static final int CRYPT_OAEP =   0x00000040;

	/**For <b>CryptGenKey()</b>:<p>
	 * If this flag is set, then the key is assigned a random salt value automatically. 
	 * <p>
	 * For <b>CryptDeriveKey()</b>:<p>
	 * Typically, when a session key is made from a hash value, there are a number of leftover bits.
	 * For example, if the hash value is 128 bits and the session key is 40 bits, there will be
	 * 88 bits left over. If this flag is set, then the key is assigned a salt value based on
	 * the unused hash value bits. 
	 * <p>
	 * You can retrieve this salt value by using the <b>CryptGetKeyParam</b> function with the 
	 * <code>dwParam</code> parameter set to <code>KP_SALT</code>.
	 * 																									<p>
	 * If this flag is not set, then the key is given a salt value of zero.
	 * 																									<p>
	 * When keys with nonzero salt values are exported (through <b>CryptExportKey</b>), then the
	 * salt value must also be obtained and kept with the key BLOB.
	 */
	public static final int CRYPT_CREATE_SALT       =	0x00000004;
	
	/**For <b>CryptGenKey()</b>:<p>
	 * If this flag is set, then the key can be transferred out of the CSP into a key BLOB by
	 * using the <b>CryptExportKey</b> function. Because session keys generally must be exportable,
	 * this flag should usually be set when they are created.
	 * 																									<p>
	 * If this flag is not set, then the key is not exportable. For a session key, this means
	 * that the key is available only within the current session and only the application that
	 * created it will be able to use it. For a public/private key pair, this means that the 
	 * private key cannot be transported or backed up.
	 * 																									<p>
	 * This flag applies only to session key and private key BLOBs. It does not apply to public
	 * keys, which are always exportable.																<p>
	 * 
	 * For <b>CryptImportKey()</b>:<p>
	 * 
	 * The key being imported is eventually to be reexported. If this flag is not used, then calls
	 * to CryptExportKey with the key handle fail.
	 */
	public static final int CRYPT_EXPORTABLE        =	0x00000001;
	
	/**
	 * This flag specifies that a no salt value gets allocated for a forty-bit symmetric key. For
	 * more information, see <a href="http://msdn.microsoft.com/en-us/library/aa387695(v=vs.85).aspx">Salt Value Functionality</a>.
	 */
	public static final int CRYPT_NO_SALT           =	0x00000010;
	
	/**
	 * Some CSPs use session keys that are derived from multiple hash values. When this is the
	 * case, <b>CryptDeriveKey</b> must be called multiple times.
	 * <p>
	 * If this flag is set, a new session key is not generated. Instead, the key specified by
	 * <code>phKey</code> is modified. The precise behavior of this flag is dependent on the type
	 * of key being generated and on the particular CSP being used.
	 */
	public static final int CRYPT_UPDATE_KEY		=	0x00000008;

	/**
	 * If this flag is set, the user is notified through a dialog box or another method when
	 * certain actions are attempting to use this key. The precise behavior is specified by
	 * the CSP being used. If the provider context was opened with the <code>CRYPT_SILENT</code>
	 * flag set, using this flag causes a failure and the last error is set to 
	 * <code>NTE_SILENT_CONTEXT</code>.
	 */
	public static final int CRYPT_USER_PROTECTED    =	0x00000002;

	/**
	 * This flag specifies an initial Diffie-Hellman or DSS key generation. This flag is useful
	 * only with Diffie-Hellman and DSS CSPs. When used, a default key length will be used unless
	 * a key length is specified in the upper 16 bits of the dwFlags parameter. If parameters
	 * that involve key lengths are set on a PREGEN Diffie-Hellman or DSS key using 
	 * <b>CryptSetKeyParam</b>, the key lengths must be compatible with the key length set here.
	 */
	public static final int CRYPT_PREGEN            =	0x00000040;

	/**
	 * If this flag is set, the key can be exported until its handle is closed by a call to 			{@link JMSCAPI.Advapi32#CryptDestroyKey 
	 * CryptDestroyKey}. This allows newly generated keys to be exported upon creation for
	 * archiving or key recovery. After the handle is closed, the key is no longer exportable.
	 */
	public static final int CRYPT_ARCHIVABLE        =	0x00004000;

	/**
	 * This flag specifies strong key protection. When this flag is set, the user is prompted
	 * to enter a password for the key when the key is created. The user will be prompted to
	 * enter the password whenever this key is used.
	 * 																									<p>
	 * This flag is only used by the CSPs that are provided by Microsoft. Third party CSPs will
	 * define their own behavior for strong key protection.
	 * 																									<p>
	 * Specifying this flag causes the same result as calling this function with the
	 * <code>CRYPT_USER_PROTECTED</code> flag when strong key protection is specified in the
	 * system registry.
	 * 																									<p>
	 * If this flag is specified and the provider handle in the <code>hProv</code> parameter was
	 * created by using the <code>CRYPT_VERIFYCONTEXT</code> or CRYPT_SILENT flag, this function
	 * will set the last error to NTE_SILENT_CONTEXT and return zero.
	 */
	public static final int CRYPT_FORCE_KEY_PROTECTION_HIGH =	0x00008000;

}
