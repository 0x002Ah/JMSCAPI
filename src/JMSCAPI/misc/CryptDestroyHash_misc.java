package JMSCAPI.misc;

public interface CryptDestroyHash_misc {
	/**
	 * The error codes prefaced by NTE are generated by the particular CSP being used.
	 * Some possible error codes returned by <b>GetLastError()</b>:
	 *																										</p><table><tr>
	 *<td>ERROR_BUSY</td>					<td>The hash object specified by <b>hHash</b> is
	 *											currently being used and cannot be destroyed.				</td></tr><tr>
	 *
	 *<td>ERROR_INVALID_HANDLE</td>			<td>The <b>hHash</b> parameter specifies a handle that
	 *											is not valid.												</td></tr><tr>
	 *
	 *<td>ERROR_INVALID_PARAMETER = 87</td>	<td>The <b>hHash</b> parameter specifies a value that is
	 *											not	valid.													</td></tr><tr>
	 *
	 *<td>NTE_BAD_ALGID</td>				<td>The <code>hHash</code> handle specifies an algorithm
	 *											that this CSP does not support.								</td></tr><tr>
	 * 
	 *<td>NTE_BAD_HASH</td>					<td>The hash object specified by the <code>hHash</code>
	 *											parameter is not valid.										</td></tr><tr>
	 *
	 *<td>NTE_BAD_UID</td>					<td>The CSP context that was specified when the key was
	 *											created cannot be found.									</td></tr>
	 *																										</table>

	*/
		public static String error_codes = "error codes";
		
	/**
	 * <b>Remarks</b>
	 * 
	 * <p>
	 * When a hash object is destroyed, many CSPs overwrite the memory in the CSP where the hash
	 * object was held. The CSP memory is then freed.
	 * <p>
	 * There should be a one-to-one correspondence between calls to {@link JMSCAPI.Advapi32#CryptCreateHash CryptCreateHash}
	 * and <b>CryptDestroyHash</b>.
	 * <p>
	 * All hash objects that have been created by using a specific CSP must be destroyed before
	 * that CSP handle is released with the {@link JMSCAPI.Advapi32#CryptReleaseContext CryptReleaseContext} function.	
	 */
		public static String remarks = "remarks";
}
