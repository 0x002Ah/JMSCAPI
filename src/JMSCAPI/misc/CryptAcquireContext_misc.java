package JMSCAPI.misc;


public interface CryptAcquireContext_misc {
/**
 * The error codes prefaced by NTE are generated by the particular CSP being used.
 * Some possible error codes returned by <b>GetLastError()</b>:
 *																										</p><table><tr>
 * <td>ERROR_BUSY</td>				<td>Some CSPs set this error if the <b>CRYPT_DELETEKEYSET</b>
 * 										flag value is set and another thread or process is
 * 										using this key container.										</td></tr><tr>
 * 
 * <td>ERROR_FILE_NOT_FOUND</td>	<td>The profile of the user is not loaded and cannot be
 * 										found. This happens when the application impersonates
 * 										a user, for	example, the <b>IUSR_ComputerName</b> account.		</td></tr><tr>
 * 
 * <td>ERROR_INVALID_PARAMETER = 87</td><td>One of the parameters contains a value that is not
 * 										valid. This is most often a pointer that is not valid.			</td></tr><tr>
 * 
 * <td>ERROR_NOT_ENOUGH_MEMORY</td>	<td>The operating system ran out of memory during
 * 										the operation.													</td></tr><tr> 
 * 
 * <td>NTE_BAD_FLAGS</td>			<td>The <b>dwFlags</b> parameter has a value that is not valid.		</td></tr><tr>
 * 
 * 
 * <td>NTE_BAD_KEY_STATE</td>		<td>The user password has changed since the private keys
 * 									 	were encrypted.													</td></tr><tr>
 * 
 * 
 * <td>NTE_BAD_KEYSET</td>			<td>The key container could not be opened. A common cause
 * 										of this error is that the key container does not exist.
 * 										To create a key container, call <b>CryptAcquireContext</b>
 * 										using the <b>CRYPT_NEWKEYSET</b> flag. This error code
 * 										can also indicate that access to an existing key
 * 										container is denied. Access rights to the container can
 * 										be granted by the key set creator by using
 * 										<b>CryptSetProvParam</b>.										</td></tr><tr>
 * 
 * <td>NTE_BAD_KEYSET_PARAM</td>	<td>The <b>pszContainer</b> or <b>pszProvider</b> parameter
 * 										is set to a	value that is not valid.							</td></tr><tr>
 * 
 * <td>NTE_BAD_PROV_TYPE</td>		<td>The value of the <b>dwProvType</b> parameter is out of
 * 										range. All provider types must be from 1 through 999,
 * 										inclusive.														</td></tr><tr>
 * 
 * <td>NTE_BAD_SIGNATURE</td>		<td>The provider DLL signature could not be verified. Either
 * 										the DLL or the digital signature has been tampered with.		</td></tr><tr>
 * 
 * <td>NTE_EXISTS</td> 				<td>The dwFlags parameter is <b>CRYPT_NEWKEYSET</b>, but the
 * 										key	container already exists.									</td></tr><tr>
 * 
 * <td>NTE_KEYSET_ENTRY_BAD</td>	<td>The <b>pszContainer</b> key container was found but
 * 										is corrupt.														</td></tr><tr>
 *  
 * <td>NTE_KEYSET_NOT_DEF</td>		<td>The requested provider does not exist.							</td></tr><tr>
 *  
 * <td>NTE_NO_MEMORY</td>			<td>The CSP ran out of memory during the operation.					</td></tr><tr>
 *  
 * <td>NTE_PROV_DLL_NOT_FOUND</td>	<td>The provider DLL file does not exist or is not on the
 * 										current path.													</td></tr><tr>
 *  
 * <td>NTE_PROV_TYPE_ENTRY_BAD</td>	<td>The provider type specified by <b>dwProvType</b> is
 * 										corrupt. This error can relate to either the user
 * 										default CSP list or the computer default CSP list.				</td></tr><tr>
 * 
 * <td>NTE_PROV_TYPE_NO_MATCH</td>	<td>The provider type specified by <b>dwProvType</b> does
 * 										not match the provider type found. Note that this error
 * 										can only occur when <b>pszProvider</b>specifies an
 * 										actual CSP name.												</td></tr><tr>
 * 
 * <td>NTE_PROV_TYPE_NOT_DEF</td>	<td>No entry exists for the provider type specified by
 * 										<b>dwProvType</b>.												</td></tr><tr>
 * 
 * <td>NTE_PROVIDER_DLL_FAIL</td>	<td>The provider DLL file could not be loaded or failed
 * 										to initialize.													</td></tr><tr>
 * 
 * <td>NTE_SIGNATURE_FILE_BAD</td>	<td>An error occurred while loading the DLL file image,
 * 										prior to verifying its signature.								</td></tr>
 *																										</table>
 * 																								<p>
*/
	public static String error_codes = "error codes";
/**
     *<b>Remarks</b>																					<p>
	 *The <b>pszContainer</b> parameter specifies the name of the container that is used to hold
	 *the key. Each container can contain one key. If you specify the name of an existing container
	 *when creating keys, the new key will overwrite a previous one.
	 *																									<p>
	 *The combination of the CSP name and the key container name uniquely identifies a single key
	 *on the system. If one application tries to modify a key container while another application
	 *is using it, unpredictable behavior may result.
	 *																									<p>
	 *If you set the <b>pszContainer</b> parameter to <code>NULL</code>, the default key container
	 *name is used. When the Microsoft software CSPs are called in this manner, a new container is
	 *created each time the <b>CryptAcquireContext</b> function is called. However, different CSPs
	 *may behave differently in this regard. In particular, a CSP may have a single default
	 *container that is shared by all applications accessing the CSP. Therefore, applications must
	 *not use the default key container to store private keys. Instead, either prevent key storage
	 *by passing the <code>CRYPT_VERIFYCONTEXT</code> flag in the <b>dwFlags</b> parameter, or use
	 *an application-specific container that is unlikely to be used by another application.
	 *																									<p>
	 *An application can obtain the name of the key container in use by using the 
	 *<b>CryptGetProvParam</b> function to read the <code>PP_CONTAINER</code> value.
	 *																									<p>
	 *For performance reasons, we recommend that you set the <b>pszContainer</b> parameter to
	 *<code>NULL</code> and the <b>dwFlags</b> parameter to <code>CRYPT_VERIFYCONTEXT</code> in all
	 *situations where you do not require a persisted key. In particular, consider setting the
	 *<b>pszContainer</b> parameter to <code>NULL</code> and the <b>dwFlags</b> parameter to
	 *<code>CRYPT_VERIFYCONTEXT</code> for the following scenarios:
	 *																									<p><ul>
	 *<li>You are creating a hash.																		</li>
	 *
	 *<li>You are generating a symmetric key to encrypt or decrypt data.								</li>
	 *
	 *<li>You are deriving a symmetric key from a hash to encrypt or decrypt data.						</li>
	 * 
	 *You are verifying a signature. It is possible to import a public key from a 
	 *<code>PUBLICKEYBLOB</code> or from a certificate by using <b>CryptImportKey</b> or
	 *<b>CryptImportPublicKeyInfo</b>. A context can be acquired by using the 
	 *<code>CRYPT_VERIFYCONTEXT</code> flag if you only plan to import the public key.					</li>
	 *
	 *<li>You plan to export a symmetric key, but not import it within the crypto context's
	 *lifetime. A context can be acquired by using the <code>CRYPT_VERIFYCONTEXT</code> flag if
	 *you only plan to import the public key for the last two scenarios. 								</li>
	 *
	 *<li>You are performing private key operations, but you are not using a persisted private
	 *key that is stored in a key container. 															</li></ul>
	 *																									<p>
	 *If you plan to perform private key operations, the best way to acquire a context is to try
	 *to open the container. If this attempt fails with <code>NTE_BAD_KEYSET</code>, then create
	 *the container by using the <code>CRYPT_NEWKEYSET</code> flag.

 */
	public static String remarks = "remarks";
	/**
	 * Possible dwFlags:																				<ul>
	 * <li>{@linkplain #CRYPT_VERIFYCONTEXT}																			</li>
	 * <li>{@linkplain #CRYPT_NEWKEYSET }																				</li>
	 * <li>{@linkplain #CRYPT_MACHINE_KEYSET}																			</li>
	 * <li>{@linkplain #CRYPT_DELETEKEYSET}																			</li>
	 * <li>{@linkplain #CRYPT_SILENT}																					</li>
	 * <li>{@linkplain #CRYPT_DEFAULT_CONTAINER_OPTIONAL}																</li></ul>
	 */
	public static String dwFlags = "dwFlags";
	
	
/* Some applications set one or more of the following flags */
	
	/** 
	 * This option is intended for applications that are using ephemeral keys, or applications
	 * that do not require access to persisted private keys, such as applications that perform
	 * only hashing, encryption, and digital signature verification. Only applications that
	 * create signatures or decrypt messages need access to a private key. In most cases, this
	 * flag should be set. 
	 * 																									<p>
	 * For file-based CSPs, when this flag is set, the <b>pszContainer</b> parameter must be set
	 * to <code>NULL</code>. The application has no access to the persisted private keys of
	 * public/private key pairs. When this flag is set, temporary public/private key pairs can
	 * be created, but they are not persisted. 
	 * 																									<p>
	 * For hardware-based CSPs, such as a smart card CSP, if the <b>pszContainer</b> parameter
	 * is <code>NULL</code> or blank, this flag implies that no access to any keys is required,
	 * and that no UI should be presented to the user. This form is used to connect to the CSP
	 * to query its capabilities but not to actually use its keys. If the <b>pszContainer</b>
	 * parameter is not <code>NULL</code> and not blank, then this flag implies that access to
	 * only the publicly available information within the specified container is required. The
	 * CSP should not ask for a PIN. Attempts to access private information (for example, the
	 * <b>CryptSignHash</b> function) will fail. 
	 * 																									<p>
	 * When <b>CryptAcquireContext</b> is called, many CSPs require input from the owning user
	 * before granting access to the private keys in the key container. For example, the private
	 * keys can be encrypted, requiring a password from the user before they can be used. 
	 * However, if the <code>CRYPT_VERIFYCONTEXT</code> flag is specified, access to the private
	 * keys is not required and the user interface can be bypassed.
	 */
	public static final int CRYPT_VERIFYCONTEXT = 0xF0000000;	
	/** 
	 * Creates a new key container with the name specified by <b>pszContainer</b>. If
	 * <b>pszContainer</b> is <code>NULL</code>, a key container with the default name
	 * is created.
	 */
	public static final int CRYPT_NEWKEYSET = 0x00000008;	
	/** 
	 * By default, keys and key containers are stored as user keys. For Base Providers, this
	 * means that user key containers are stored in the user's profile.
	 * 																									<p>
	 * A key container created without this flag by a user that is not an administrator can be
	 * accessed only by the user creating the key container and the local system account.
	 * 																									<p>
	 * The <code>CRYPT_MACHINE_KEYSET</code> flag can be combined with all of the other flags to
	 * indicate that the key container of interest is a computer key container and the CSP
	 * treats it as such. For Base Providers, this means that the keys are stored locally
	 * on the computer that created the key container. If a key container is to be a computer
	 * container, the <code>CRYPT_MACHINE_KEYSET</code> flag must be used with all calls to
	 * <b>CryptAcquireContext</b> that reference the computer container. The key container created
	 * with <code>CRYPT_MACHINE_KEYSET</code> by an administrator can be accessed only by its
	 * creator and by a user with administrator privileges unless access rights to the container
	 * are granted using <b>CryptSetProvParam</b>.
	 * 																									<p>
	 * The key container created with <code>CRYPT_MACHINE_KEYSET</code> by a user that is not an
	 * administrator can be accessed only by its creator and by the local system account unless
	 * access rights to the container are granted using <b>CryptSetProvParam</b>.
	 * 																									<p>
	 * The <code>CRYPT_MACHINE_KEYSET</code> flag is useful when the user is accessing from a
	 * service or user account that did not log on interactively. When key containers are created,
	 * most CSPs do not automatically create any public/private key pairs. These keys must be
	 * created as a separate step with the <b>CryptGenKey</b> function. 
	 */
	public static final int CRYPT_MACHINE_KEYSET = 0x00000020;
	/** 
	 * Delete the key container specified by <b>pszContainer</b>. If <b>pszContainer</b> is
	 * <code>NULL</code>, the key container with the default name is deleted. All key pairs in
	 * the key container are also destroyed. 
	 * 																									<p>
	 * When this flag is set, the value returned in phProv is undefined, and thus, the
	 * {@link JMSCAPI.Advapi32#CryptReleaseContext CryptReleaseContext} function need not be called
	 * afterward.	 
	 */	
	public static final int CRYPT_DELETEKEYSET = 0x00000010;
	/** 
	 * The application requests that the CSP not display any user interface (UI) for this context.
	 * If the CSP must display the UI to operate, the call fails and the 
	 * <code>NTE_SILENT_CONTEXT</code> error code is set as the last error. In addition, if calls
	 * are made to <b>CryptGenKey</b> with the <code>CRYPT_USER_PROTECTED</code> flag with a
	 * context that has been acquired with the <code>CRYPT_SILENT</code> flag, the calls fail and
	 * the CSP sets <code>NTE_SILENT_CONTEXT</code>.
	 * 																									<p>
	 * <code>CRYPT_SILENT</code> is intended for use with applications for which the UI cannot be
	 * displayed by the CSP.	 
	 */
	public static final int CRYPT_SILENT = 0x00000040;
	/** 
	 * Obtains a context for a smart card CSP that can be used for hashing and symmetric key
	 * operations but cannot be used for any operation that requires authentication to a
	 * smart card using a PIN. This type of context is most often used to perform operations
	 * on an empty smart card, such as setting the PIN by using <b>CryptSetProvParam</b>. This
	 * flag can only be used with smart card CSPs.
	 * 																									<p>
	 * In Windows Server 2003, Windows XP, and Windows 2000:  This flag is not supported. 
	 */
	public static final int CRYPT_DEFAULT_CONTAINER_OPTIONAL = 0x00000080;


}
