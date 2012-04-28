/**
 * 
 */
package JMSCAPI;

import JMSCAPI.misc.JMSCAPI_misc;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

/**
 * @author Vladimir Bubornev aka 0x002Ah
 *
 */

public interface Advapi32 extends StdCallLibrary,JMSCAPI_misc {
	Advapi32 INSTANCE = (Advapi32) Native.loadLibrary("Advapi32",
			Advapi32.class, W32APIOptions.UNICODE_OPTIONS);
	
	/**
	 * The <b>CryptEnumProviders</b> function retrieves the first or next available cryptographic
	 * service providers (CSPs). Used in a loop, this function can retrieve in sequence all
	 * of the CSPs available on a computer.
	 *																									<p>
	 * @param dwIndex			Index of next provider				(<b>in</b>		DWORD	)					
	 * @param pdwReserved		reserved, must be <code>null</code>	(<b>in</b>		DWORD*	)
	 * @param dwFlags			reserved, must be <code>0</code>	(<b>in</b>		DWORD	)
	 * @param pdwProvType		provider type						(<b>out</b>		DWORD*	)
	 * @param pszProvName		provider name pointer				(<b>out</b>		LPTSTR	)			<br />
	 * 							NOTE: This parameter can be NULL to set the size of the name
	 * 							for memory allocation purposes.
	 * @param pcbProvName		size of ProvName					(<b>out</b>		DWORD*	)
	 *
	 * @return	If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError},
	 * 
	 * then see 
	 * {@link JMSCAPI.misc.CryptEnumProviders_misc#error_codes error codes}.
	 * 
	 * @see JMSCAPI.demos.CryptEnumProvidersDemo 
	 */
	boolean CryptEnumProvidersW(
			int dwIndex,					//in	DWORD	index of next provider	
			Pointer pdwReserved,			//in	DWORD*	reserved, must be null
			int dwFlags,					//in	DWORD	reserved, must be 0
			IntByReference pdwProvType,		//out	DWORD*	provider type		
			Pointer pszProvName,			//out	LPTSTR	provider name 
			IntByReference pcbProvName		//out	DWORD*	size of prov_name
		);

/////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * The <b>CryptAcquireContext</b> function is used to acquire a handle to a particular key
	 * container within a particular cryptographic service provider (CSP). This returned
	 * handle is used in calls to CryptoAPI functions that use the selected CSP.						
	 * 																									<p>
	 * This function first attempts to find a CSP with the characteristics described in
	 * the <b>dwProvType</b> and <b>pszProvider</b> parameters. If the CSP is found, the function
	 * attempts to find a key container within the CSP that matches the name specified by the
	 * <b>pszContainer</b> parameter. To acquire the context and the key container of a private
	 * key associated with the public key of a certificate, use
	 * <b>CryptAcquireCertificatePrivateKey</b>.																
	 * 																									<p>
	 * With the appropriate setting of <b>dwFlags</b>, this function can also create and destroy
	 * key containers and can provide access to a CSP with a temporary key container if
	 * access to a private key is not required.
	 * 																									<p>
	 * See {@link JMSCAPI.misc.CryptAcquireContext_misc#remarks remarks}
	 * for more details.
	 * 
	 * @param phProv		A pointer to a handle of a CSP. 		(<b>out</b>		HCRYPTPROV*	)		<br />										<p>
	 * 						NOTE: When you have finished using the CSP, release the handle by
	 * 						calling the {@link #CryptReleaseContext CryptReleaseContext}
	 * 						function.																	<p>
	 * 
	 * @param pszContainer	The key container name.					(<b>in</b>		LPCTSTR		)		<br />
	 * 						This is a null-terminated string that identifies the key container
	 * 						to the CSP. 																<br />
	 * 						NOTE: When <b>dwFlags</b> is set to <code>CRYPT_VERIFYCONTEXT</code>,
	 * 						<b>pszContainer</b> must be set to <code>NULL</code>.						<p>
	 * 
	 * @param pszProvider	A null-terminated string that contains the name of the CSP to be used.
	 * 																(<b>in</b>		LPCTSTR		)		<br />
	 * 						NOTE: If this parameter is NULL, the user default provider is used. 		<p>
	 * 
	 * @param dwProvType	The type of provider to acquire.		(<b>in</b>		DWORD		)		<p>
	 * 
	 * @param dwFlags		Flag values. 							(<b>in</b>		DWORD		)		<br />
	 * 						NOTE: This parameter is usually set to zero, but some applications set
	 * 						one or more of flags listed below in source.								<p>
	 * 						See {@link JMSCAPI.misc.CryptAcquireContext_misc#dwFlags dwFlags}
	 * 						for possible values.
	 * 
	 * 
	 * @return	If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError},
	 * 
	 * 
	 * then see {@link JMSCAPI.misc.CryptAcquireContext_misc#error_codes error codes}.
	 * 
	 *
	 * @see JMSCAPI.demos.CryptAcquireContextDemo
	 */
	boolean CryptAcquireContextW(
			HCRYPTPROVp phProv,				//out	HCRYPTPROV*	pointer to a handle of CSP
			String pszContainer,			//in	LPCTSTR		key container name.
			String pszProvider,				//in	LPCTSTR		name of the CSP.
			int dwProvType,					//in	DWORD		Specifies the type of CSP
			int dwFlags						//in	DWORD		Flag values. default=0
		);

	
///////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * The <b>CryptReleaseContext</b> function releases the handle of a cryptographic service provider
	 * (CSP) and a key container. At each call to this function, the reference count on the CSP
	 * is reduced by one. When the reference count reaches zero, the context is fully released
	 * and it can no longer be used by any function in the application.
	 * 																									<p>
	 * An application calls this function after finishing the use of the CSP. After this function
	 * is called, the released CSP handle is no longer valid. This function does not destroy key
	 * containers or key pairs.
	 * 																									<p>																								<p>
	 * NOTE: After this function has been called, the CSP session is finished and all existing
	 * session keys and hash objects created by using the <code>hProv</code> handle are no longer
	 * valid.																							<br/>
	 * In practice, all of these objects should be destroyed with calls to <b>CryptDestroyKey</b>
	 * and <b>CryptDestroyHash</b> before <b>CryptReleaseContext</b> is called.
	 * 
	 * @param hProv		Handle of a cryptographic service provider (CSP) created by a call to
	 * 					{@link #CryptAcquireContextW
	 * 					CryptAcquireContext}.						(<b>in</b>		HCRYPTPROV	)
	 * 																									<p>
	 * @param dwFlags	Reserved for future use and must be zero. 	(<b>in</b>		DWORD		)		<br />
	 * 					NOTE: If <b>dwFlags</b> is not set to zero, this function returns
	 * 					<code>FALSE</code> but the CSP is released.
	 * 
	 * @return	If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError},
	 * 
	 * 
	 * then see {@link JMSCAPI.misc.CryptReleaseContext_misc#error_codes error codes}.
	 * 
	 *																							
	 */
	boolean CryptReleaseContext(
			HCRYPTPROV hProv,		//in	HCRYPTPROV	a handle of CSP
			int dwFlags				//in	DWORD		Reserved, must be 0
			);
	
///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The <b>CryptGetProvParam</b> function retrieves parameters that govern the operations of a
	 * cryptographic service provider (CSP). 
	 * 
	 * @param hProv			A handle of the CSP target of the query. 	(<b>in</b>		HCRYPTPROV	)	<br/>
	 * 						This handle must have been created by using the 							{@link #CryptAcquireContextW
	 * 						CryptAcquireContext} function.
	 * 																									<p>
	 * @param dwParam		The nature of the query.					(<b>in</b>		DWORD		)	<br/>
	 * 						See {@link JMSCAPI.misc.CryptGetProvParam_misc#dwParam dwParam}
	 * 						possible values.		
	 * 																									<p>
	 * @param pbData		A pointer to a buffer to receive the data.	(<b>out</b>		BYTE*		)	<br/>
	 * 						The form of this data varies depending on the value of <b>dwParam</b>.
	 * 						When <b>dwParam</b> is set to <code>PP_USE_HARDWARE_RNG</code>,
	 * 						<b>pbData</b> must be set to <code>NULL</code>. 							<p>
	 * 						NOTE: This parameter can be <code>NULL</code> to set the size of this
	 * 						information for	memory allocation purposes.
	 * 																									<p>
	 * @param pdwDataLen	A pointer to a DWORD value that specifies the size, in bytes, of the
	 * 						buffer pointed to by the <b>pbData</b> parameter.
	 * 																	(<b>inout</b>	DWORD*		)	<br/>
	 * 						When the function returns, the DWORD value contains the number of bytes
	 * 						stored or to be stored in the buffer.										
	 *																									 <p>
	 * 						If <code>PP_ENUMALGS</code>, or <code>PP_ENUMALGS_EX</code> is set,
	 * 						the <b>pdwDataLen</b> parameter works somewhat differently. If
	 * 						<b>pbData</b> is <code>NULL</code> or the value pointed to by
	 * 						<b>pdwDataLen</b> is too small, the value returned in this parameter
	 * 						is the size of the largest item in the enumeration list instead of the
	 * 						size of the item currently being read.										
	 *																									<p>
	 * 						If <code>PP_ENUMCONTAINERS</code> is set, the first call to the function
	 * 						returns the size of the maximum key-container allowed by the current
	 * 						provider. This is in contrast to other possible behaviors, like returning
	 * 						the length of the longest existing container, or the length of the
	 * 						current container. Subsequent enumerating calls will not change the
	 * 						<b>dwLen</b> parameter. For each enumerated container, the caller can
	 * 						determine the length of the null-terminated string programmatically, if
	 * 						desired. If one of the enumeration values is read and the <b>pbData</b>
	 * 						parameter is <code>NULL</code>, the <code>CRYPT_FIRST</code> flag must
	 * 						be specified for the size information to be correctly retrieved.
	 * 																									<p>
	 * @param dwFlags		Flag values. 							(<b>in</b>		DWORD		)		<br />
	 * 						The following values are defined for use with <code>PP_ENUMALGS</code>,
	 * 						<code>PP_ENUMALGS_EX</code>, and <code>PP_ENUMCONTAINERS</code>.
	 * 																									<p><table><tr>
	 * <td>CRYPT_FIRST</td>	<td>Retrieve the first element in the enumeration. This has the same
	 * 							effect as resetting the enumerator.										</td></tr><tr>
	 * <td>CRYPT_NEXT</td>	<td>Retrieve the next element in the enumeration. When there are no
	 * 							more elements to retrieve, this function will fail and set the 
	 * 							last error to <code>ERROR_NO_MORE_ITEMS</code>.							</td></tr>
	 *																									</table>
	 * @return	If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError},
	 * 

	 * then see {@link JMSCAPI.misc.CryptGetProvParam_misc#error_codes error codes}.
	 * 
	 * 
	 */
	boolean CryptGetProvParam(
			HCRYPTPROV hProv,			//in	HCRYPTPROV  a handle of CSP
			int dwParam,				//in	DWORD 		nature of the query, details below
			Pointer pbData,				//out	BYTE*		pointer to a buffer to receive data 	
			IntByReference pdwDataLen,	//inout DWORD*		size of pbData, details below
			int dwFlags					//in    DWORD 		
			);
	
	
	/** 
	 * @deprecated
	 * @see JMSCAPI.Advapi32#CryptGetProvParam
	 */
	boolean CryptGetProvParam(
			HCRYPTPROV hProv,			//in	HCRYPTPROV  a handle of CSP
			int dwParam,				//in	DWORD 		nature of the query, details below
			PROV_ENUMALGS pbData,		//out	BYTE*		pointer to a buffer to receive data 	
			IntByReference pdwDataLen,	//inout DWORD*		size of pbData, details below
			int dwFlags					//in    DWORD 		
			);

	/**
	 * @deprecated
	 * @see JMSCAPI.Advapi32#CryptGetProvParam
	 */
	boolean CryptGetProvParam(
			HCRYPTPROV hProv,			//in	HCRYPTPROV  a handle of CSP
			int dwParam,				//in	DWORD 		nature of the query, details below
			PROV_ENUMALGS_EX pbData,	//out	BYTE*		pointer to a buffer to receive data 	
			IntByReference pdwDataLen,	//inout DWORD*		size of pbData, details below
			int dwFlags					//in    DWORD 		
			);

	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The <b>CryptGetUserKey</b> function retrieves a handle of one of a user's two public/private key
	 * pairs. This function is used only by the owner of the public/private key pairs and only when
	 * the handle of a cryptographic service provider (CSP) and its associated key container is
	 * available. If the CSP handle is not available and the user's certificate is, use
	 * <b>CryptAcquireCertificatePrivateKey</b>.
	 * 
	 * @param hProv			A handle of a cryptographic service provider created by	a call to 			{@link #CryptAcquireContextW
	 * 						CryptAcquireContext}.			(<b>in</b>		HCRYPTPROV	)
	 * 																									<p>
	 * @param dwKeySpec		Identifies the private key to use from the key container.
	 * 																(<b>in</b>		DWORD		)		<br/>
	 * 						It can be <code>AT_KEYEXCHANGE</code> or <code>AT_SIGNATURE</code>.
	 * 																									<p>
	 * 						Additionally, some providers allow access to other user-specific keys
	 * 						through this function. For details, see the documentation on the
	 * 						specific provider.
	 * 																									<p>
	 * @param phUserKey		A pointer to the handle of the retrieved keys.
	 * 																(<b>out</b>		HCRYPTKEY*	)		<br/>
	 * 						When you have finished using the key, delete the handle by calling the
	 * 						<b>CryptDestroyKey</b> function.
	 * @return If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError},
	 * 
	 * 
	 * then see {@link JMSCAPI.misc.CryptGetUserKey_misc#error_codes error codes}.
	 */
	boolean CryptGetUserKey(
			HCRYPTPROV hProv,			//in   HCRYPTPROV 	a handle of CSP
			int dwKeySpec,				//in   DWORD 		AT_KEYEXCHANGE or AT_SIGNATURE.
			HCRYPTKEYp phUserKey		//out  HCRYPTKEY*	A pointer to handle of retrieved keys. 
			);
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The <b>CryptDestroyKey</b> function releases the handle referenced by the <code>hKey</code>
	 * parameter. After a key handle has been released, it is no longer valid and cannot be
	 * used again.
	 * 																									<p>
	 * If the handle refers to a session key, or to a public key that has been imported into the
	 * cryptographic service provider (CSP) through <b>CryptImportKey</b>, this function destroys
	 * the key and frees the memory that the key used. Many CSPs overwrite the memory where the
	 * key was held before freeing it. However, the underlying public/private key pair is not
	 * destroyed by this function. Only the handle is destroyed.
	 * 
	 * @param hKey		The handle of the key to be destroyed.		(<b>in</b>		HCRYPTKEY	)
	 * 
	 * @return If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError},
	 * 
	 * 
	 * then see {@link JMSCAPI.misc.CryptDestroyKey_misc#error_codes error codes}.
	 * 
	 */
	boolean CryptDestroyKey(
			HCRYPTKEY hKey				//in  HCRYPTKEY 	The handle of the key to be destroyed.
			);
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The <b>CryptGenKey</b> function generates a random cryptographic session key or a
	 * public/private key pair. A handle to the key or key pair is returned in <code>phKey</code>.
	 * This handle can then be used as needed with any CryptoAPI function that requires a key handle.
	 * 																									<p>
	 * The calling application must specify the algorithm when calling this function. Because this
	 * algorithm type is kept bundled with the key, the application does not need to specify the
	 * algorithm later when the actual cryptographic operations are performed.
	 * 
	 * @param hProv		A handle to a cryptographic service provider (CSP) created by a call to 		{@link #CryptAcquireContextW
	 * 					CryptAcquireContext}.						(<b>in</b>		HCRYPTPROV	)
	 * 																									<p>
	 * @param Algid		An ALG_ID value that identifies the algorithm for which the key is
	 * 					to be generated. 							(<b>in</b>		DWORD		)		<br/>
	 * 					Values for this parameter vary depending on the CSP used.
	 * 																									
	 * 					{@link JMSCAPI.misc.CryptGenKey_misc#AlgidInfo More about AlgID...}		
	 * 																									<p>
	 * @param dwFlags	Specifies the type of key generated. 		(<b>in</b>		DWORD		)		<br/>
	 * 					The key size, representing the length of the key modulus in bits, is set with
	 * 					the <b>upper 16 bits</b> of this parameter.
	 * 					{@link JMSCAPI.misc.CryptGenKey_misc#KeySizesInfo More about key sizes...} 
	 * 																									<p>
	 * 					If the upper 16 bits is zero, the default key size is generated. If a key
	 * 					larger than the maximum or smaller than the minimum is specified, the call
	 * 					fails with the <code>ERROR_INVALID_PARAMETER</code> code.
	 * 																									<p>
	 * 					For session key lengths, see <b>CryptDeriveKey</b>.
	 * 																									<p>
	 * 					The <b>lower 16-bits</b> of this parameter can be zero or a combination of one or
	 * 					more of the {@link JMSCAPI.misc.CryptGenKey_misc#dwFlags dwFlags}.
	 * 
	 * @param phKey		Address to which the function copies the handle of the newly generated key.
	 * 																(<b>out</b>		HCRYPTKEY*	)		<br/>
	 * 					When you have finished using the key, delete the handle to the key by calling
	 * 					the {@link #CryptDestroyKey CryptDestroyKey} function.
	 * 
	 * @return If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError}, 
	 * then see {@link JMSCAPI.misc.CryptGenKey_misc#error_codes error codes}.
	 */
	boolean CryptGenKey(
			HCRYPTPROV hProv,			//in   HCRYPTPROV 	a handle of CSP
			int  Algid,					//in   ALG_ID		An ALG_ID value 
			int  dwFlags,				//in   DWORD 		Specifies the type of key generated.
			HCRYPTKEYp phKey			//out  HCRYPTKEY*	A pointer to handle of retrieved keys.
			);
	
	
	/**
	 * The <b>CryptGetKeyParam</b> function retrieves data that governs the operations of a key.
	 * 
	 * @param hKey			The handle of the key being queried.	(<b>in</b>		HCRYPTKEY	)
	 * 																									<p>
	 * @param dwParam		Specifies the type of query being made.	(<b>in</b>		DWORD		)		<br/>
	 * 						For all key types, this parameter can contain one of the 					{@link JMSCAPI.misc.CryptGetKeyParam_misc#dwParam 
	 * 						following} values.
	 * 																									<p>
	 * @param pbData		A pointer to a buffer that receives the data. (<b>out</b>	BYTE*	)		<br/>
	 * 						The form of this data depends on the value of dwParam.
	 * 																									<p>
	 * 						If the size of this buffer is not known, the required size can be
	 * 						retrieved at run time by passing <code>NULL</code> for this parameter
	 * 						and setting the value pointed to by <code>pdwDataLen</code> to zero.
	 * 						This function will place the required size of the buffer, in bytes, in
	 * 						the value pointed to by <code>pdwDataLen</code>.
	 * 																									<p>
	 * @param pdwDataLen	A pointer to a DWORD value that, on entry, contains the size, in bytes,
	 * 						of the buffer pointed to by the pbData parameter. When the function
	 * 						returns, the DWORD value contains the number of bytes stored in the
	 * 						buffer.									(<b>inout</b>	DWORD*		)		<p>
	 * 						
	 * 						NOTE:  When processing the data returned in the buffer, applications
	 * 						must use the actual size of the data returned. The actual size may be
	 * 						slightly smaller than the size of the buffer specified on input. On
	 * 						input, buffer sizes are sometimes specified large enough to ensure
	 * 						that the largest possible output data fits in the buffer. On output,
	 * 						the variable pointed to by this parameter is updated to reflect the
	 * 						actual size of the data copied to the buffer.
	 * 																									<p>
	 * @param dwFlags		This parameter is reserved for future use and must be set to zero.
	 * 																(<b>in</b>		DWORD		)
	 * @return If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError}, 
	 * then see {@link JMSCAPI.misc.CryptGetKeyParam_misc#error_codes error codes}.
	 */
	boolean CryptGetKeyParam(
			HCRYPTKEY hKey,				//in     HCRYPTKEY 	handle of the key being queried.
			  int dwParam,				//in     DWORD 		type of query being made
			  Pointer pbData,			//out    BYTE*		pointer to a buffer that receives data.
			  IntByReference pdwDataLen,//inout  DWORD*		size of pbData / number of bytes stored in
			  int dwFlags				//in     DWORD 		reserved, must be zero
			);
	
	/**
	 * The <b>CryptCreateHash</b> function initiates the hashing of a stream of data. It creates
	 * and returns to the calling application a handle to a CSP hash object. This handle is used
	 * in subsequent calls to <b>CryptHashData</b> and <b>CryptHashSessionKey</b> to hash session
	 * keys and other streams of data.
	 * 																									<p>
	 * See {@link JMSCAPI.misc.CryptCreateHash_misc#remarks remarks}
	 * for more details.
	 * 
	 * @param hProv		A handle to a cryptographic service provider (CSP) created by a call to 		{@link #CryptAcquireContextW
	 * 					CryptAcquireContext}.						(<b>in</b>		HCRYPTPROV	)
	 * 																									<p>
	 * @param Algid		An ALG_ID value that identifies the hash algorithm to use.
	 *																(<b>in</b>		ALG_ID		)		<br/>
	 *					Valid values for this parameter vary, depending on the CSP that is used.		
	 *																									<p>
	 * @param hKey		If the type of hash algorithm is a keyed hash, such as the Hash-Based
	 * 					Message Authentication Code (HMAC) or Message Authentication Code (MAC)
	 * 					algorithm, the key for the hash is passed in this parameter. 
	 * 																(<b>in</b>		HCRYPTKEY	)		<br/>
	 * 					For nonkeyed algorithms, this parameter must be set to zero.
	 * 																									<p>
	 * 					For keyed algorithms, the key must be to a block cipher key, such as RC2,
	 * 					that has a cipher mode of Cipher Block Chaining (CBC).
	 * 																									<p>
	 * @param dwFlags	Reserved, must be zero 						(<b>in</b>		DWORD		)		<p>
	 * 
	 * @param phHash	The address to which the function copies a handle to the new hash object.
	 * 																(<b>out</b>		HCRYPTHASH*	)		<br/>
	 *  				When you have finished using the hash object, release the handle by calling
	 *  				the <b>CryptDestroyHash</b> function.
	 *  
	 * @return If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError}, 
	 * then see {@link JMSCAPI.misc.CryptCreateHash_misc#error_codes error codes}.
	 */
	boolean CryptCreateHash(
			HCRYPTPROV hProv,			//in	HCRYPTPROV 	a handle of CSP
			int  Algid,					//in	ALG_ID		An ALG_ID value 
			HCRYPTKEY hKey,				//in	HCRYPTKEY 	the key for the hash, 0 for nonkeyed
			int dwFlags,				//in	DWORD 		reserved, must be zero
			HCRYPTHASHp phHash			//out	HCRYPTHASH*	pointer to a handle of hash object
			);
	
	/**
	 * The <b>CryptDestroyHash</b> function destroys the hash object referenced by the <code>hHash</code>
	 * parameter. After a hash object has been destroyed, it can no longer be used.
	 * 																									<p>
	 * To help ensure security, we recommend that hash objects be destroyed after they have
	 * been used.
	 * 																									<p>
	 * See {@link JMSCAPI.misc.CryptDestroyHash_misc#remarks remarks}
	 * for more details. 
	 * 
	 * @param hHash The handle of the hash object to be destroyed.	(<b>in</b>		HCRYPTHASH	)
	 * 
	 * @return If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError}, 
	 * then see {@link JMSCAPI.misc.CryptDestroyHash_misc#error_codes error codes}.
	 */
	boolean CryptDestroyHash(
			HCRYPTHASH hHash			//in  HCRYPTHASH	handle of the hash object to be destroyed.
			);
	
	/**
	 * The <b>CryptHashData</b> function adds data to a specified hash object. This function
	 * and <b>CryptHashSessionKey</b> can be called multiple times to compute the hash of long
	 * or discontinuous data streams.
	 * 																									<p> 
	 * Before calling this function, {@link #CryptCreateHash CryptCreateHash} must be called to
	 * create a handle of a hash object.
	 * 
	 * @param hHash 	Handle of the hash object.					(<b>in</b>		HCRYPTHASH	)
	 * 																									<p>
	 * @param pbData 	A pointer to a buffer that contains the data to be added to the hash object.
	 * 																(<b>in</b>		BYTE*		)		<p>
	 * 
	 * @param dwDataLen Number of bytes of data to be added. 		(<b>in</b>		DWORD		)		<br/>
	 * 					This must be zero if the <code>CRYPT_USERDATA</code> flag is set.				<p>
	 * 
	 * @param dwFlags	Zero or {@link JMSCAPI.misc.CryptHashData_misc#CRYPT_USERDATA CRYPT_USERDATA}
	 * 
	 * @return If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError}, 
	 * then see {@link JMSCAPI.misc.CryptHashData_misc#error_codes error codes}.
	 */
	boolean CryptHashData(
			HCRYPTHASH hHash,			//in  HCRYPTHASH	Handle of the hash object.
			Pointer pbData,				//in  BYTE*			pointer to a buffer with data to hash 
			int dwDataLen,				//in  DWORD 		must be zero if CRYPT_USERDATA flag is set.
			int dwFlags					//in  DWORD 
			);
	
	/**
	 * The <b>CryptGetHashParam</b> function retrieves data that governs the operations of a hash
	 * object. The actual hash value can be retrieved by using this function.
	 * 
	 * @param hHash 		Handle of the hash object to be queried.	(<b>in</b>		HCRYPTHASH	)		<p>
	 * 
	 * @param dwParam 		Query type. 								(<b>in</b>		DWORD		)		<br/>
	 * 						This parameter can be set to one of the 										{@link JMSCAPI.misc.CryptGetHashParam_misc#dwParam
	 * 						following} queries.
	 * 																										<p>
	 * @param pbData		A pointer to a buffer that receives the specified value data.
	 * 																	(<b>out</b>		BYTE*		)		<br/> 
	 * 						The form of this data varies, depending on the value number.					<p>
	 * 
	 * 						This parameter can be <b>NULL</b> to determine the memory size
	 * 						required. However, this parameter should not be set to <b>NULL</b>
	 * 						when <code>dwParam</code> is set to <b>HP_HASHVAL</b>. Instead, use
	 * 						<b>HP_HASHSIZE</b> to determine the size of the hash object.
	 * 																										<p>
	 * @param pdwDataLen	A pointer to a DWORD value specifying the size, in bytes, of the
	 * 						<code>pbData</code> buffer.					(<b>inout</b>	DWORD*		)		<br/>
	 * 						When the function returns, the DWORD value contains the number of bytes
	 * 						stored in the buffer. If <code>pbData</code> is <b>NULL</b>, set the
	 * 						value of <code>pdwDataLen</code> to zero.
	 * 																										<p>
	 * 						NOTE:  When processing the data returned in the buffer, applications must
	 * 						use the actual size of the data returned. The actual size can be slightly
	 * 						smaller than the size of the buffer specified on input. (On input, buffer
	 * 						sizes are usually specified large enough to ensure that the largest
	 * 						possible output data fits in the buffer.) On output, the variable pointed
	 * 						to by this parameter is updated to reflect the actual size of the data
	 * 						copied to the buffer.
	 * 																										<p>
	 * @param dwFlags		Reserved for future use and must be zero.
	 * 
	 * @return If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError}, 
	 * then see {@link JMSCAPI.misc.CryptGetHashParam_misc#error_codes error codes}.
	 */
	boolean CryptGetHashParam(
			HCRYPTHASH hHash,			//in	HCRYPTHASH 	Handle of the hash object
			int dwParam,				//in	DWORD 		Query type.
			Pointer pbData,				//out	BYTE*		pointer to a buffer
			IntByReference pdwDataLen,	//inout	DWORD*		size of pbData
			int dwFlags					//in	DWORD		reserved, must be 0 
			);
	
	/**
	 * The <b>CryptDuplicateHash</b> function makes an exact copy of a hash to the point when the
	 * duplication is done. The duplicate hash includes the state of the hash.
	 * 																									<p>
	 * A hash can be created in a piece-by-piece way. The <b>CryptDuplicateHash</b> function can be
	 * used to create separate hashes of two different contents that begin with the same content.
	 * 
	 * @param hHash			Handle of the hash to be duplicated.	(<b>in</b>		HCRYPTHASH	)
	 * <p>
	 * @param pdwReserved	Reserved for future use and must be zero.
	 * <p>
	 * @param dwFlags		Reserved for future use and must be zero.
	 * <p>
	 * @param phHash		Address of the handle of the duplicated hash. (<b>out</b>HCRYPTHASH*)		<br/>
	 * 						When you have finished
	 * 						using the hash, release the handle by calling the 							{@link #CryptDestroyHash
	 * 						CryptDestroyHash} function.
	 * 
	 * @return If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link  com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError}, 
	 * then see {@link JMSCAPI.misc.CryptDuplicateHash_misc#error_codes error codes}.
	 */
	boolean CryptDuplicateHash(
			HCRYPTHASH hHash,			//in   HCRYPTHASH 	Handle of the hash to be duplicated.
			int pdwReserved,			//in   DWORD*		Reserved for future use and must be zero.
			int dwFlags,				//in   DWORD 		Reserved for future use and must be zero.
			HCRYPTHASHp phHash			//out  HCRYPTHASH*	Address of handle of duplicated hash.
			);
	
	/**
	 * The <b>CryptDeriveKey</b> function generates cryptographic session keys derived from a base
	 * data value. This function guarantees that when the same cryptographic service provider (CSP)
	 * and algorithms are used, the keys generated from the same base data are identical. The base
	 * data can be a password or any other user data.
	 * 																									<p>
	 * This function is the same as {@link #CryptGenKey CryptGenKey}, except that the generated
	 * session keys are derived from base data instead of being random. <b>CryptDeriveKey</b> can
	 * only be used to generate session keys. It cannot generate public/private key pairs.
	 * 																									<p>
	 * A handle to the session key is returned in the <code>phKey</code> parameter. This handle can
	 * be used with any CryptoAPI function that requires a key handle.
	 * <p>
	 * See {@link JMSCAPI.misc.CryptDeriveKey_misc#remarks remarks}
	 * for more details.
	 * 
	 * @param hProv		A HCRYPTPROV handle of a CSP created by a call to to 							{@link #CryptAcquireContextW
	 * 					CryptAcquireContext}.						(<b>in</b>		HCRYPTPROV	)
	 * 																									<p>
	 * @param algid		An ALG_ID structure that identifies the symmetric encryption algorithm for
	 * 					which the key is to be generated. 			(<b>in</b>		ALG_ID		)		<br/>
	 * 					The algorithms available will most likely be different for each CSP.
	 * 																									<p>
	 * @param hBaseData	A handle to a hash object that has been fed the exact base data.
	 * 																(<b>in</b>		HCRYPTHASH	)		<br/>
	 * 					To obtain this handle, an application must first create a hash object with 		{@link #CryptCreateHash 
	 * 					CryptCreateHash} and then add the base data to the hash object with 			{@link #CryptHashData 
	 * 					CryptHashData}. This process is described in detail in 							<a href="http://msdn.microsoft.com/en-us/library/aa382450(v=vs.85).aspx">
	 * 					Hashes and Digital Signatures</a>.
	 * 																									<p>
	 * @param dwFlags	Specifies the type of key generated.		(<b>in</b>		DWORD		)		<br/>
	 * 					The sizes of a session key can be set when the key is generated. The key
	 * 					size, representing the length of the key modulus in bits, is set with the
	 * 					<b>upper 16 bits</b> of this parameter. Thus, if a 128-bit RC4 session key
	 * 					is to be generated, the value 0x00800000 is combined with any other
	 * 					<code>dwFlags</code> predefined value with a bitwise-OR operation.
	 * <p>
	 * 					The <b>lower 16 bits</b> of this parameter can be zero or you can specify
	 * 					one or more of the {@link JMSCAPI.misc.CryptDeriveKey_misc#dwFlags 
	 * 					following} flags by using the bitwise-OR operator to combine them.
	 * <p>
	 * @param phKey		A pointer to a HCRYPTKEY variable to receive the address of the handle of
	 * 					the newly generated key. When you have finished using the key, release the
	 * 					handle by calling the {@link #CryptDestroyKey CryptDestroyKey} function.
	 * @return If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link  com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError}, 
	 * then see {@link JMSCAPI.misc.CryptDeriveKey_misc#error_codes error codes}.
	 */
	boolean CryptDeriveKey(
			HCRYPTPROV hProv,			//in   HCRYPTPROV	A handle of a CSP
			int algid,					//in   ALG_ID 		ID of algorithm
			HCRYPTHASH hBaseData,		//in   HCRYPTHASH 	A handle to a hash object
			int dwFlags,				//in   DWORD 		type of key to be generated
			HCRYPTKEYp phKey			//inout HCRYPTKEY*	A pointer to a HCRYPTKEY
			);
	
	/**
	 * The <b>CryptEncrypt</b> function encrypts data. The algorithm used to encrypt the data is
	 * designated by the key held by the CSP module and is referenced by the
	 * <code>hKey</code> parameter.
	 * 
	 * @param hKey			A handle to the encryption key.			(<b>in</b>		HCRYPTKEY	)		<br/>
	 * 						An application obtains this handle by using either the 						{@link #CryptGenKey
	 * 						CryptGenKey} or the {@link #CryptImportKey CryptImportKey} function.
	 * 																									<p>
	 * 						The key specifies the encryption algorithm used.
	 * 																									<p>
	 * @param hHash			A handle to a hash object.				(<b>in</b>		HCRYPTHASH	)		<br/>
	 * 						If data is to be hashed and encrypted simultaneously, a handle to a
	 * 						hash object can be passed in the <code>hHash</code> parameter. The hash
	 * 						value is updated with the plaintext passed in. This option is useful
	 * 						when generating signed and encrypted text.
	 * 																									<p>
	 * 						Before calling <b>CryptEncrypt</b>, the application must obtain a
	 * 						handle to the hash object by calling the 									{@link #CryptCreateHash 
	 * 						CryptCreateHash} function. After the encryption is complete, the hash
	 * 						value can be obtained by using the 											{@link #CryptGetHashParam 
	 * 						CryptGetHashParam} function, or the hash can be signed by using the
	 * 						<b>CryptSignHash</b> function.
	 * 																									<p>
	 * 						If no hash is to be done, this parameter must be <code>NULL</code>.
	 * 																									<p>
	 * @param Final			A Boolean value that specifies whether this is the last section in a
	 * 						series being encrypted. 				(<b>in</b>		BOOL		)		<br/>
	 * 						Final is set to <b>TRUE</b> for the last or only block and to <b>FALSE</b>
	 * 						if there are more blocks to be encrypted. For more information, 
	 * 						see {@link JMSCAPI.misc.CryptDeriveKey_misc#remarks Remarks}.
	 * 																									<p>
	 * @param dwFlags		Reserved for future use.				(<b>in</b>		DWORD		)
	 * 																									<p>
	 * @param pbData		A pointer to a buffer that contains the plaintext to be encrypted.
	 * 																(<b>in</b>		BYTE*		)		<br/>
	 * 						The plaintext in this buffer is overwritten with the ciphertext created
	 * 						by this function.
	 * 																									<p>
	 * 						The <code>pdwDataLen</code> parameter points to a variable that contains
	 * 						the length, in bytes, of the plaintext. The <code>dwBufLen</code>
	 * 						parameter contains the total size, in bytes, of this buffer.
	 * 																									<p>
	 * 						If this parameter contains <b>NULL</b>, this function will calculate the
	 * 						required size for the ciphertext and place that in the value pointed to
	 * 						by the <code>pdwDataLen</code> parameter.
	 * 																									<p>
	 * @param pdwDataLen	A pointer to a DWORD value that , on entry, contains the length, in
	 * 						bytes, of the plaintext in the pbData buffer. On exit, this DWORD
	 * 						contains the length, in bytes, of the ciphertext written to the
	 * 						<code>pbData</code> buffer.				(<b>inout</b>	DWORD*		)		
	 * 																									<p>
	 * 						If the buffer allocated for <code>pbData</code> is not large enough
	 * 						to hold the encrypted data, <b>GetLastError</b> returns 
	 * 						<code>ERROR_MORE_DATA</code> and stores the required buffer size, in
	 * 						bytes, in the DWORD value pointed to by <code>pdwDataLen</code>.
	 * 																									<p>
	 * 						If <code>pbData</code> is <b>NULL</b>, no error is returned, and the
	 * 						function stores the size of the encrypted data, in bytes, in the DWORD
	 * 						value pointed to by <code>pdwDataLen</code>. This allows an application
	 * 						to determine the correct buffer size.
	 * 																									<p>
	 * 						When a block cipher is used, this data length must be a multiple of 
	 * 						the block size unless this is the final section of data to be encrypted
	 * 						and the <code>Final</code> parameter is <b>TRUE</b>.
	 * 																									<p>
	 * @param dwBufLen		Specifies the total size, in bytes, of the input <code>pbData</code>
	 * 						buffer.									(<b>in</b>		DWORD*		)		
	 * 																									<p>
	 * 						Note that, depending on the algorithm used, the encrypted text can be
	 * 						larger than the original plaintext. In this case, the <code>pbData</code>
	 * 						buffer needs to be large enough to contain the encrypted text and any
	 * 						padding.
	 * 																									<p>
	 * 						As a rule, if a stream cipher is used, the ciphertext is the same size
	 * 						as the plaintext. If a block cipher is used, the ciphertext is up to a
	 * 						block length larger than the plaintext. 
	 * 						
	 * @return If the function <b>succeeds</b>, the return value is nonzero <b>(TRUE)</b>.
	 * If the function <b>fails</b>, the return value is zero <b>(FALSE)</b>. For extended error
	 * information, call {@link  com.sun.jna.platform.win32.Kernel32#GetLastError() GetLastError}, 
	 * then see {@link JMSCAPI.misc.CryptEncrypt_misc#error_codes error codes}.
	 */
	boolean CryptEncrypt(
			HCRYPTKEY hKey,				//in	HCRYPTKEY 	A handle to the encryption key
			HCRYPTHASH hHash,			//in    HCRYPTHASH 	A handle to a hash object
			boolean Final,				//in    BOOL 		last section?
			int  dwFlags,				//in    DWORD		reserved
			Pointer pbData,				//inout BYTE*		data to be encrypted/encrypted data
			IntByReference pdwDataLen,	//inout DWORD*		data length
			IntByReference dwBufLen		//in    DWORD 		data buffer length
			);
	
	boolean CryptDecrypt(
			HCRYPTKEY hKey,				//in	HCRYPTKEY 	A handle to the decryption key
			HCRYPTHASH hHash,			//in    HCRYPTHASH 	A handle to a hash object
			boolean Final,				//in    BOOL 		last section?
			int  dwFlags,				//in    DWORD		
			Pointer pbData,				//inout BYTE*		data to be decrypted
			IntByReference pdwDataLen	//inout DWORD*		data length
			);
	
	/**
	 * TODO CryptExportKey


	 
	 *
	 * TODO CryptEncrypt			!
	 */
	
	static int fake = 0;
}
