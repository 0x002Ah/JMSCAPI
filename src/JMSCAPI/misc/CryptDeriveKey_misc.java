package JMSCAPI.misc;

public interface CryptDeriveKey_misc extends KeyFlags {
	/**
	 * Possible dwFlags:<ul>
	 * 
	 * <li>{@link KeyFlags#CRYPT_CREATE_SALT CRYPT_CREATE_SALT}</li>
	 * <li>{@link KeyFlags#CRYPT_EXPORTABLE CRYPT_EXPORTABLE}</li>
	 * <li>{@link KeyFlags#CRYPT_NO_SALT CRYPT_NO_SALT}</li>
	 * <li>{@link KeyFlags#CRYPT_UPDATE_KEY CRYPT_UPDATE_KEY}</li>
	 * 
	 * </ul>
	 */
	public static String dwFlags = "dwFlags";
	
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
	 *<td>NTE_BAD_ALGID</td>				<td>The <code>Algid</code> parameter specifies an algorithm
	 *											that this CSP does not support.								</td></tr><tr>
	 *
	 *<td>NTE_BAD_FLAGS</td>				<td>The <code>dwFlags</code> parameter contains a value
	 *											that is not valid.											</td></tr><tr>
	 *
	 *<td>NTE_BAD_HASH</td>					<td>The hash object specified by the <code>hBaseData</code>
	 *											parameter is not valid.										</td></tr><tr>
	 *
	 *<td>NTE_BAD_HASH_STATE</td>			<td>An attempt was made to add data to a hash object that
	 *											is already marked "finished."								</td></tr><tr>
	 *
	 *<td>NTE_BAD_UID</td>					<td>The <code>hProv</code> parameter does not contain
	 *											a valid context handle.										</td></tr><tr>
	 *
	 *<td>NTE_FAIL</td>						<td>The function failed in some unexpected way.					</td></tr><tr>
	 *
	 *<td>NTE_SILENT_CONTEXT</td>			<td>The provider could not perform the action because
	 *											the context was acquired as silent.							</td></tr>
	 *																										</table>
		 */
	public static String error_codes = "error codes";
		
	/**
	 * <b>Remarks</b><p>
	 * 
	 * When keys are generated for symmetric block ciphers, the key by default is set up in
	 * cipher block chaining (CBC) mode with an initialization vector of zero. This cipher mode
	 * provides a good default method for bulk-encrypting data. To change these parameters, use
	 * the <b>CryptSetKeyParam</b> function.
	 * <p>
	 * The <b>CryptDeriveKey</b> function completes the hash. After CryptDeriveKey has been called,
	 * no more data can be added to the hash. Additional calls to 											{@link JMSCAPI.Advapi32#CryptHashData 
	 * CryptHashData} or <b>CryptHashSessionKey</b> fail. After the application is done with the
	 * hash, {@link JMSCAPI.Advapi32#CryptDestroyHash CryptDestroyHash} must be called to destroy
	 * the hash object.
	 * <p>
	 * To choose an appropriate key length, the following methods are recommended.
	 * <ul><li>
	 * To enumerate the algorithms that the CSP supports and to get maximum and minimum key
	 * lengths for each algorithm, call {@link JMSCAPI.Advapi32#CryptGetProvParam CryptGetProvParam}
	 * with <code>PP_ENUMALGS_EX</code>.</li>
	 * <li>
	 * Use the minimum and maximum lengths to choose an appropriate key length. It is not always
	 * advisable to choose the maximum length because this can lead to performance issues.</li>
	 * <li>
	 * After the desired key length has been chosen, use the upper 16 bits of the <code>dwFlags</code>
	 * parameter to specify the key length.</li></ul>
	 * 
	 * Let <b>n</b> be the required derived key length, in bytes. The derived key is the first <b>n</b>
	 * bytes of the hash value after the hash computation has been completed by <b>CryptDeriveKey</b>.
	 * If the hash is not a member of the SHA-2 family and the required key is for either 3DES or
	 * AES, the key is derived as follows:
	 * <ol><li>
	 * Form a 64-byte buffer by repeating the constant <b>0x36</b> 64 times. Let <b>k</b> be the
	 * length of the hash value that is represented by the input parameter <code>hBaseData</code>.
	 * Set the first <b>k</b> bytes of the buffer to the result of an XOR operation of the first
	 * <b>k</b> bytes of the buffer with the hash value that is represented by the input parameter
	 * <code>hBaseData</code>.						</li>
	 * <li>
	 * Form a 64-byte buffer by repeating the constant <b>0x5C</b> 64 times. Set the first <b>k</b>
	 * bytes of the buffer to the result of an XOR operation of the first <b>k</b> bytes of the
	 * buffer with the hash value that is represented by the input parameter <code>hBaseData</code>. 	</li>
	 * <li>
	 * Hash the result of step 1 by using the same hash algorithm as that used to compute the hash
	 * value that is represented by the <code>hBaseData</code> parameter.</li>
	 * <li>
	 * Hash the result of step 2 by using the same hash algorithm as that used to compute the hash
	 * value that is represented by the <code>hBaseData</code> parameter.</li>
	 * <li>
	 * Concatenate the result of step 3 with the result of step 4.</li>
	 * <li>
	 * Use the first <b>n</b> bytes of the result of step 5 as the derived key.</li></ol>
	 */
	public static String remarks = "remarks";
}
