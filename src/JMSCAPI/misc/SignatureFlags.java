package JMSCAPI.misc;

public interface SignatureFlags {

	/**
	 * Used with RSA providers. The hash object identifier (OID) is not placed in the RSA public
	 * key encryption. If this flag is not set, the hash OID in the default signature is as 
	 * specified in the definition of DigestInfo in PKCS #1.
	 */
	public static final int CRYPT_NOHASHOID        =	0x00000001;
	/**
	 * This flag is not used.
	 */
	public static final int CRYPT_TYPE2_FORMAT        =	0x00000002;
	/**
	 * Use the RSA signature padding method specified in the ANSI X9.31 standard.
	 */
	public static final int CRYPT_X931_FORMAT        =	0x00000004;

}
