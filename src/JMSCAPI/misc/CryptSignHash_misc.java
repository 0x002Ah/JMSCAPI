package JMSCAPI.misc;

public interface CryptSignHash_misc {
	
	/**
	 * Possible dwFlags:<ul>
	 * <li>{@link SignatureFlags#CRYPT_NOHASHOID CRYPT_NOHASHOID}</li>
	 * <li>{@link SignatureFlags#CRYPT_TYPE2_FORMAT CRYPT_TYPE2_FORMAT}</li>
	 * <li>{@link SignatureFlags#CRYPT_X931_FORMAT CRYPT_X931_FORMAT}</li>
	 * </ul>
	 */
	public static String dwFlags = "dwFlags";
	
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
	 *<td>ERROR_MORE_DATA</td>				<td>The buffer specified by the <code>pbSignature</code>
	 *											parameter is not large enough to hold the returned data.
	 *											The required buffer size, in bytes, is in the
	 *											<code>pdwSigLen</code> DWORD value.							</td></tr><tr>
	 *
	 *<td>NTE_BAD_ALGID</td>				<td>The <code>hHash</code> handle specifies an algorithm that
	 *											this CSP does not support, or the <code>dwKeySpec</code>
	 *											parameter has an incorrect value.</td></tr><tr>
	 *
	 *<td>NTE_BAD_FLAGS</td>				<td>The dwFlags parameter specified is not valid.				</td></tr><tr>
	 *
	 *<td>NTE_BAD_HASH</td>					<td>The hash object specified by the <code>hHash</code>
	 *											parameter is not valid.										</td></tr><tr>
	 *
	 *<td>NTE_BAD_UID</td>					<td>The CSP context that was specified when the hash object
	 *											was created cannot be found.</td></tr><tr>
	 *
	 *<td>NTE_NO_KEY</td>					<td>The private key specified by <code>dwKeySpec</code> does
	 *											not exist.													</td></tr><tr>
	 *
	 *<td>NTE_NO_MEMORY</td>				<td>The CSP ran out of memory during the operation.				</td></tr>
	 *																										</table>
		 */
	public static String error_codes = "error codes";
	
	/**
	 * Remarks
	 * <p>
	 * Before calling this function, the <b>CryptCreateHash</b> function must be called to get a handle
	 * to a hash object. The <b>CryptHashData</b> or <b>CryptHashSessionKey</b> function is then used
	 * to add the data or session keys to the hash object. The <b>CryptSignHash</b> function completes
	 * the hash.
	 * 																										<p>
	 * While the DSS CSP supports hashing with both the MD5 and the SHA hash algorithms, the DSS CSP only
	 * supports signing SHA hashes.
	 * 																										<p>
	 * After this function is called, no more data can be added to the hash. Additional calls to 
	 * <b>CryptHashData</b> or <b>CryptHashSessionKey</b> fail.
	 * 																										<p>
	 * After the application finishes using the hash, destroy the hash object by calling the 
	 * <b>CryptDestroyHash</b> function.
	 * 																										<p>
	 * By default, the Microsoft RSA providers use the PKCS #1 padding method for the signature. The hash
	 * OID in the DigestInfo element of the signature is automatically set to the algorithm OID associated
	 * with the hash object. Using the <code>CRYPT_NOHASHOID</code> flag will cause this OID to be omitted
	 * from the signature.
	 * 																										<p>
	 * Occasionally, a hash value that has been generated elsewhere must be signed. This can be done by
	 * using the following sequence of operations:<ol>
	 * <li>Create a hash object by using <b>CryptCreateHash</b>.</li>
	 * <li>Set the hash value in the hash object by using the <code>HP_HASHVAL</code> value of the 
	 * <code>dwParam</code> parameter in <b>CryptSetHashParam</b>.</li>
	 * <li>Sign the hash value by using <b>CryptSignHash</b> and obtain a digital signature block.</li>
	 * <li>Destroy the hash object by using <b>CryptDestroyHash</b>.</li>
	 * </ol>
	 */
	public static String remarks = "remarks";
	

}