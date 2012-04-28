package JMSCAPI.misc;


public interface CryptReleaseContext_misc {
	/**
	 * Some possible error codes returned by <b>GetLastError()</b>:
	 * 																									</p><table><tr>
	 * <td>ERROR_BUSY</td>				<td>The CSP context specified by <code>hProv</code> is
	 * 										currently being	used by another process.					</td></tr><tr>
	 * 
	 * <td>ERROR_INVALID_HANDLE</td>	<td>One of the parameters specifies a handle that is not
	 * 										valid.														</td></tr><tr>
	 * 
	 * <td>ERROR_INVALID_PARAMETER</td>	<td>One of the parameters contains a value that is not 
	 * 										valid. This is most often a pointer that is not valid.		</td></tr><tr>
	 * 
	 * <td>NTE_BAD_FLAGS</td>			<td>The <code>dwFlags</code> parameter is nonzero.				</td></tr><tr>
	 * 
	 * <td>NTE_BAD_UID</td>				<td>The <code>hProv</code> parameter does not contain a
	 * 										valid context handle.										</td></tr>
	 *																									</table>
 * 
	 */
	public static String error_codes = "error codes";
}
