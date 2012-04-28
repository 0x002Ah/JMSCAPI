package JMSCAPI.misc;

public interface CryptGetKeyParam_misc extends
									KP_PERMISSIONS,
									KP_PADDING,
									KP_MODE
														{
	
	/**
	 * For all key types, this parameter can contain one of the following values:
	 * <ul>
	 * <li>{@linkplain #KP_ALGID}</li>
	 * <li>{@linkplain #KP_BLOCKLEN}</li>
	 * <li>{@linkplain #KP_CERTIFICATE}</li>
	 * <li>{@linkplain #KP_KEYLEN}</li>
	 * <li>{@linkplain #KP_PERMISSIONS}</li>
	 * <li>{@linkplain #KP_SALT}</li>
	 * </ul>
	 * If a Digital Signature Standard (DSS) key is specified by the <code>hKey</code> parameter,
	 * the <code>dwParam</code> value can also be set to one of the following values.
	 * <ul>
	 * <li>{@linkplain #KP_P}</li>
	 * <li>{@linkplain #KP_Q}</li>
	 * <li>{@linkplain #KP_G}</li>
	 * </ul>
	 * If a block cipher session key is specified by the <code>hKey</code> parameter, the 
	 * <code>dwParam</code> value can also be set to one of the following values.
	 * <ul>
	 * <li>{@linkplain #KP_EFFECTIVE_KEYLEN}</li>
	 * <li>{@linkplain #KP_IV}</li>
	 * <li>{@linkplain #KP_PADDING}</li>
	 * <li>{@linkplain #KP_MODE}</li>
	 * <li>{@linkplain #KP_MODE_BITS}</li>
	 * </ul>
	 * If a Diffie-Hellman algorithm or Digital Signature Algorithm (DSA) key is specified by 
	 * <code>hKey</code>, the <code>dwParam</code> value can also be set to the following value.
	 * <ul>
	 * <li>{@linkplain #KP_VERIFY_PARAMS}</li>
	 * </ul>
	 * If a certificate is specified by <code>hKey</code>, the <code>dwParam</code> value can also
	 * be set to the following value.
	 * <ul>
	 * <li>{@linkplain #KP_CERTIFICATE}</li>
	 * </ul>
	 */
	public static String dwParam = "dwParam";
	

	
	/**
	 * Verifies the parameters of a Diffie-Hellman algorithm or DSA key. The <code>pbData</code>
	 * parameter is not used, and the value pointed to by <code>pdwDataLen</code> receives zero.
	 * 																									<p>
	 * This function returns a nonzero value if the key parameters are valid or zero otherwise.
	 */
	public static final int KP_VERIFY_PARAMS = 40;
	
	/**
	 * Retrieve the number of bits to feed back. The <code>pbData</code> parameter is a pointer
	 * to a DWORD value that receives the number of bits that are processed per cycle when the
	 * OFB or CFB cipher modes are used.
	 */
	public static final int KP_MODE_BITS = 5;
	
	/**
	 * Retrieve the cipher mode. The <code>pbData</code> parameter is a pointer to a DWORD value
	 * that receives a cipher mode identifier. For more information about cipher modes, see 			<a href="http://msdn.microsoft.com/en-us/library/aa381939(v=vs.85).aspx">
	 * Data Encryption and Decryption</a>.
	 * 																									<p>
	 * This can be one of the {@link JMSCAPI.misc.KP_MODE KP_MODE} values.
	 */
	public static final int KP_MODE = 4;
	
	/**
	 * Retrieve the padding mode. The <code>pbData</code> parameter is a pointer to a DWORD
	 * value that receives a numeric identifier that identifies the padding method used by
	 * the cipher. This can be one of the {@link JMSCAPI.misc.KP_PADDING KP_PADDING} values.
	 */
	public static final int KP_PADDING = 3;
	
	/**
	 * Retrieve the initialization vector of the key. The <code>pbData</code> parameter is a
	 * pointer to a BYTE array that receives the initialization vector. The size of this array
	 * is the block size, in bytes. For example, if the block length is 64 bits, the
	 * initialization vector consists of 8 bytes.
	 */
	public static final int KP_IV = 1;
	
	/**
	 * Retrieve the effective key length of an RC2 key. The <code>pbData</code> parameter is
	 * a pointer to a DWORD value that receives the effective key length.
	 */
	public static final int KP_EFFECTIVE_KEYLEN = 19;
	
	/**
	 * Retrieve the modulus prime number P of the DSS key. The <code>pbData</code> parameter is
	 * a pointer to a buffer that receives the value in little-endian form. The <code>pdwDataLen</code>
	 * parameter contains the size of the buffer, in bytes.
	 */
	public static final int KP_P  =  11;      // DSS/Diffie-Hellman P value
	
	/**
	 * Retrieve the generator G of the DSS key. The <code>pbData</code> parameter is a pointer to
	 * a buffer that receives the value in little-endian form. The <code>pdwDataLen</code>
	 * parameter contains the size of the buffer, in bytes.
	 */
	public static final int KP_G  =  12;      // DSS/Diffie-Hellman G value
	
	/**
	 * Retrieve the modulus prime number Q of the DSS key. The <code>pbData</code> parameter is
	 * a pointer to a buffer that receives the value in little-endian form. The <code>pdwDataLen</code>
	 * parameter contains the size of the buffer, in bytes.
	 */
	public static final int KP_Q  =  13;      // DSS Q value
	
	/**
	 * Retrieve the key algorithm. The <code>pbData</code> parameter is a pointer to an
	 * <b>ALG_ID</b> value that receives the identifier of the algorithm that was specified
	 * when the key was created.
	 * 																									<p>
	 * When <b>AT_KEYEXCHANGE</b> or <b>AT_SIGNATURE</b> is specified
	 * for the <code>Algid</code> parameter of the 														{@link JMSCAPI.Advapi32#CryptGenKey 
	 * CryptGenKey} function, the algorithm identifiers that are used to generate the key
	 * depend on the provider used. For more information, see 											<a href="http://msdn.microsoft.com/en-us/library/aa375549(v=vs.85).aspx">
	 * ALG_ID																							</a>.
	 */
	public static final int KP_ALGID = 7;  
	
	/**
	 * If a session key is specified by the <code>hKey</code> parameter, retrieve the block
	 * length of the key cipher. The <code>pbData</code> parameter is a pointer to a DWORD value
	 * that receives the block length, in bits. For stream ciphers, this value is always zero.
	 * 																									<p>
	 * If a public/private key pair is specified by <code>hKey</code>, retrieve the encryption
	 * granularity of the key pair. The <code>pbData</code> parameter is a pointer to a DWORD
	 * value that receives the encryption granularity, in bits.
	 * 																									<p>
	 * For example, the Microsoft Base Cryptographic Provider generates 512-bit RSA key pairs,
	 * so a value of 512 is returned for these keys. If the public key algorithm does not support
	 * encryption, the value retrieved is undefined.
	 */
	public static final int KP_BLOCKLEN = 8;
	
	/**
	 * <code>pbData</code> is the address of a buffer that receives the X.509 certificate that
	 * has been encoded by using Distinguished Encoding Rules (DER). The public key in the
	 * certificate must match the corresponding signature or exchange key.
	 * <p>
	 * If a certificate is specified by <code>hKey</code>, the <code>dwParam</code> value can also
	 * be set this value. In such case, the <code>pbData</code> parameter is not used, and the
	 * value pointed to by <code>pdwDataLen</code> receives zero.
	 * 																									<p>
	 * This function returns a nonzero value if the key parameters are valid or zero otherwise.
	 */
	public static final int KP_CERTIFICATE = 26;
	
	/**
	 * Retrieve the actual length of the key. The <code>pbData</code> parameter is a pointer to
	 * a DWORD value that receives the key length, in bits. <b>KP_KEYLEN</b> can be used to get
	 * the length of any key type. Microsoft cryptographic service providers (CSPs) return a key
	 * length of 64 bits for <b>CALG_DES</b>, 128 bits for <b>CALG_3DES_112</b>, and 192 bits for
	 * <b>CALG_3DES</b>. 
	 * 																									<p>
	 * These lengths are different from the lengths returned when you are
	 * enumerating algorithms with the <code>dwParam</code> value of the 								{@link JMSCAPI.Advapi32#CryptGetProvParam 
	 * CryptGetProvParam} function set to <code>PP_ENUMALGS</code>. The length returned by this
	 * call is the actual size of the key, including the parity bits included in the key.
	 * 																									<p>
	 * Microsoft CSPs that support the <b>CALG_CYLINK_MEK</b> 											<a href="http://msdn.microsoft.com/en-us/library/aa375549(v=vs.85).aspx">
	 * ALG_ID</a> return 64 bits for that algorithm. <b>CALG_CYLINK_MEK</b> is a 40-bit key but
	 * has parity and zeroed key bits to make the key length 64 bits.
	 */
	public static final int KP_KEYLEN = 9;
	
	/**
	 * Retrieve the salt value of the key. The <code>pbData</code> parameter is a pointer to a
	 * <b>BYTE</b> array that receives the salt value in little-endian form. The size of the salt
	 * value varies depending on the CSP and algorithm being used. Salt values do not apply to
	 * public/private key pairs.
	 */
	public static final int KP_SALT = 2;
	
	/**
	 * Retrieve the key permissions. The <code>pbData</code> parameter is a pointer to a DWORD
	 * value that receives the permission flags for the key.
	 * 																									<p>
	 * The following permission identifiers are currently defined. The key permissions can be zero
	 * or a combination of one or more of the {@link JMSCAPI.misc.KP_PERMISSIONS KP_PERMISSIONS}
	 * values.
	 */
	public static final int KP_PERMISSIONS = 6;
	
	/**
 * The error codes prefaced by NTE are generated by the particular CSP being used.
 * Some possible error codes returned by <b>GetLastError()</b>:
 *																										</p><table><tr>
 *<td>ERROR_INVALID_HANDLE</td>			<td>One of the parameters specifies a handle that is
 *											not valid.													</td></tr><tr>
 *
 *<td>ERROR_INVALID_PARAMETER = 87</td>	<td>One of the parameters contains a value that is not
 * 										valid. This is most often a pointer that is not valid.			</td></tr><tr>
 * 
 *<td>ERROR_MORE_DATA</td>				<td>If the buffer specified by the <code>pbData</code>
 * 										parameter is not large enough to hold the returned data,
 * 										the function sets the <b>ERROR_MORE_DATA</b> code and
 * 										stores the required buffer size, in bytes, in the variable
 * 										pointed to by <code>pdwDataLen</code>.							</td></tr><tr>
 * 
 *<td>NTE_BAD_FLAGS</td>				<td>The <code>dwFlags</code> parameter is nonzero.				</td></tr><tr>
 *
 *<td>NTE_BAD_KEY or NTE_NO_KEY</td>	<td>The key specified by the <code>hKey</code> parameter
 *											is not valid.												</td></tr><tr>
 *
 *<td>NTE_BAD_TYPE</td>					<td>The <code>dwParam </code>parameter specifies an unknown
 *											value number.												</td></tr><tr>
 *
 *<td>NTE_BAD_UID</td>					<td>The CSP context that was specified when the key was
 *											created cannot be found.									</td></tr></table>
	 */
	public static String error_codes = "error codes";

}
