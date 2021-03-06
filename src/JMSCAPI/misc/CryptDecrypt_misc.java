package JMSCAPI.misc;

public interface CryptDecrypt_misc {
	
	/**
	 * <b>Remarks</b><p>
	 * 
	 * If a large amount of data is to be decrypted, it can be done in sections by calling
	 * <b>CryptDecrypt</b> repeatedly. The <code>Final</code> parameter must be set to <b>TRUE</b>
	 * on the last call to <b>CryptDecrypt</b>, so that the decryption engine can properly finish
	 * the decryption process. The following extra actions are performed when <code>Final</code>
	 * is <b>TRUE</b>:
	 * <ul>
	 * <li>If the key is a block cipher key, the data is padded to a multiple of the block size
	 * of the cipher. To find the block size of a cipher, use {@link JMSCAPI.Advapi32#CryptGetKeyParam
	 * CryptGetKeyParam} to get the <b>KP_BLOCKLEN</b> value of the key.</li>
	 * <li>If the cipher is operating in a chaining mode, the next <b>CryptDecrypt</b> operation
	 * resets the cipher's feedback register to the <b>KP_IV</b> value of the key.</li>
	 * <li>If the cipher is a stream cipher, the next <b>CryptDecrypt</b> resets the cipher to
	 * its initial state.</li>
	 * </ul>
	 * There is no way to set the cipher's feedback register to the <b>KP_IV</b> value of the key
	 * without setting the <code>Final</code> parameter to <b>TRUE</b>. If this is necessary, as
	 * in the case where you do not want to add an additional padding block or change the size of
	 * each block, you can simulate this by creating a duplicate of the original key by using the
	 * <b>CryptDuplicateKey</b> function, and passing the duplicate key to the <b>CryptEncrypt</b>
	 * function. This causes the <b>KP_IV</b> of the original key to be placed in the duplicate key.
	 * After you create or import the original key, you cannot use the original key for encryption
	 * because the feedback register of the key will be changed. The following pseudocode shows how
	 * this can be done.
	 * <code><pre>
	// Set the IV for the original key. Do not use the original key for 
	// encryption or decryption after doing this because the key's 
	// feedback register will get modified and you cannot change it.
	CryptSetKeyParam(hOriginalKey, KP_IV, newIV)

	while(block = NextBlock())
	{
    		// Create a duplicate of the original key. This causes the 
    		// original key's IV to be copied into the duplicate key's 
    		// feedback register.
    		hDuplicateKey = CryptDuplicateKey(hOriginalKey)

    		// Encrypt the block with the duplicate key.
    		CryptEncrypt(hDuplicateKey, block)

    		// Destroy the duplicate key. Its feedback register has been 
    		// modified by the CryptEncrypt function, so it cannot be used
    		// again. It will be re-duplicated in the next iteration of the 
    		// loop.
    		CryptDestroyKey(hDuplicateKey)
	}
	 * </pre></code>
	 * The Microsoft Enhanced Cryptographic Provider supports direct encryption with RSA public
	 * keys and decryption with RSA private keys. The encryption uses <b>PKCS #1</b> padding. On
	 * decryption, this padding is verified. The length of plaintext data that can be encrypted
	 * with a call to <b>CryptEncrypt</b> with an RSA key is the length of the key modulus minus
	 * eleven bytes. The eleven bytes is the chosen minimum for <b>PKCS #1</b> padding. The
	 * ciphertext is returned in little-endian format.
	 */
	public static String remarks = "remarks";

	/**
	 * Possible dwFlags:<ul>
	 * <li>{@link #CRYPT_OAEP CRYPT_OAEP}</li>
	 * <li>{@link #CRYPT_DECRYPT_RSA_NO_PADDING_CHECK CRYPT_DECRYPT_RSA_NO_PADDING_CHECK}</li>

	 * </ul>
	 */
	public static String dwFlags = "dwFlags";
	
	/**
	 * Use Optimal Asymmetric Encryption Padding (OAEP) (PKCS #1 version 2). This flag is only
	 * supported by the Microsoft Enhanced Cryptographic Provider with RSA encryption/decryption.
	 * This flag cannot be combined with the CRYPT_DECRYPT_RSA_NO_PADDING_CHECK flag.
	 */
	public static final int	CRYPT_OAEP	=	0x00000040;
	
	/**
	 * Perform the decryption on the BLOB without checking the padding. This flag is only supported
	 * by the Microsoft Enhanced Cryptographic Provider with RSA encryption/decryption. This flag
	 * cannot be combined with the CRYPT_OAEP flag.
	 */
	public static final int	CRYPT_DECRYPT_RSA_NO_PADDING_CHECK	=	0x00000020;
	
	/**
	 * 	
	 * The error codes prefaced by NTE are generated by the particular CSP being used.
	 * Some possible error codes returned by <b>GetLastError()</b>:
	 *																										</p><table><tr>
	 *<td>ERROR_INVALID_HANDLE</td>			<td>One of the parameters specifies a handle that is
	 *											not valid.													</td></tr><tr>
	 *
	 *<td>ERROR_INVALID_PARAMETER = 87</td>	<td>One of the parameters contains a value that is not
	 * 										valid. This is most often a pointer that is not valid.			</td></tr><tr>
	 * 
	 *<td>NTE_BAD_ALGID</td>				<td>The <code>hKey</code> session key specifies an
	 *											algorithm that this CSP does not support.					</td></tr><tr>
	 *
	 *<td>NTE_BAD_DATA</td>					<td>The data to be decrypted is not valid. For example,
	 *											when a block cipher is used and the <code>Final</code>
	 *											flag is <b>FALSE</b>, the value specified by
	 *											<code>pdwDataLen</code> must be a multiple of the
	 *											 block size.												</td></tr><tr>
	 *
	 *<td>NTE_BAD_FLAGS</td>				<td>The <code>dwFlags</code> parameter is nonzero.				</td></tr><tr>
	 *
	 *<td>NTE_BAD_HASH</td>					<td>The <code>hHash</code> parameter contains a handle
	 *											that is not valid.											</td></tr><tr>
	 *
	 *<td>NTE_BAD_KEY</td>					<td>The <code>hKey</code> parameter does not contain a
	 *											valid handle to a key.										</td></tr><tr>
	 *
	 *<td>NTE_BAD_LEN</td>					<td>The size of the output buffer is too small to hold
	 *											the generated plaintext.									</td></tr><tr>
	 *
	 *<td>NTE_DOUBLE_ENCRYPT</td>			<td>The application attempted to decrypt the same data
	 *											twice.														</td></tr><tr>
	 *
	 *<td>NTE_BAD_UID</td>					<td>The CSP context that was specified when the key was
	 *											created cannot be found.									</td></tr><tr>
	 *
	 *<td>NTE_FAIL</td>						<td>The function failed in some unexpected way.					</td></tr><tr>
	 *
	 *<td>NTE_NO_MEMORY</td>				<td>The CSP ran out of memory during the operation.				</td></tr>
	 *																										</table>
		 */
	public static String error_codes = "error codes";
}
