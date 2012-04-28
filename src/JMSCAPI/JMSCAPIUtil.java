package JMSCAPI;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


import JMSCAPI.Exceptions.CancelledByUser;
import JMSCAPI.Exceptions.JMSCAPIException;
import JMSCAPI.Exceptions.NotImplemented;
import JMSCAPI.misc.JMSCAPI_misc;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTHASH;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTHASHp;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTKEY;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTKEYp;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTPROV;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTPROVp;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.ptr.IntByReference;

/**
 * TODO Javadoc
 * @author Vladimir Bubornev aka 0x002Ah
 *
 */
public abstract class JMSCAPIUtil {
	
	public static void printBytes(byte[] bytes){
		for(int i=0;i<bytes.length;i++){
			
			System.out.format("%02x", bytes[i]);
		}
		System.out.println();
	}

	/**
	 * Simple {@link JMSCAPI.Advapi32#CryptAcquireContextW CryptAcquireContext} wrapper.
	 * Passes all given params  into <b>CryptAcquireContext</b>.
	 * 
	 * @param container	The key container name. 																<br/>
	 * 					NOTE: When <b>flags</b> is set to <code>CRYPT_VERIFYCONTEXT</code>,
	 * 					<b>pszContainer</b> must be set to <code>NULL</code>.									<p>
							
	 * @param provider	String that contains the name of the CSP to be used. 									<br/>
						NOTE: If this parameter is <code>NULL</code>, the user default provider 
						of specified type is used. 
																												<p>
	 * @param type		The type of provider to acquire.
	 * 																											<p>
	 * @param flags
	 * @return
	 * 					NOTE: When you have finished using the CSP, release the handle by calling 				{@link #contextRelease 
	 * 					contextRelease}  function. 
 																												<p>		
	 * 
	 */
	public static HCRYPTPROV contextAcquire (String container, String provider,
			int type, int flags){
		HCRYPTPROVp hCryptProv = new HCRYPTPROVp();
		if (Advapi32.INSTANCE.CryptAcquireContextW(hCryptProv,container,provider,type,flags)){		
			return hCryptProv.getValue();
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
			//return null;
		}		
	}
	
	/**
	 * The contextAcquire function is a "smart" wrapper for 													{@link JMSCAPI.Advapi32#CryptAcquireContextW 
	 * CryptAcquireContext} function used to acquire a handle to a particular key container within
	 * a particular cryptographic service provider (CSP). This returned handle is used in calls
	 * to JMSCAPI functions that use the selected CSP. If key container does not exists, it will
	 * be created.
	 * 																									<p>
	 * Some CSPs does not exactly follow MSCAPI and does not return 									{@link JMSCAPI.misc.CryptAcquireContext_misc#error_codes 
	 * NTE_BAD_KEYSET} error code when key container does not exist, and even require password
	 * for unexisting container. In this function used small workaround to avoid such behavior;
	 * it would work with any CSP which correctly responds to a <code>PP_ENUMCONTAINERS</code> 
	 * flag in 																							{@link JMSCAPI.Advapi32#CryptGetProvParam
	 * CryptGetProvParam} function.
	 * 																									<p>
	 * This function first acquires (if it is possible) given CSP`s context, using 						{@link #contextVerify(String, int)
	 * VerifyContext} function, it will be used in order to determine existence of container with
	 * given name. 																						<br/>
	 * If name is <code>null</code> or empty String handle received from 
	 * <code>VerifyContext(Provider, Type)</code> will be returned.
	 * 																									<p>
	 * Then it checks if there is a container with given name, using 									{@link #containerExists 
	 * containerExists} function.
	 * <p>
	 * If such key container exist, function acquires and return handle to it, otherwise it 
	 * creates new container and return handle to newly created container.

	 * @param container	Name of key container. 															<br/>
	 * 					May be <code>null</code> or empty String, in such case, function works
	 * 					same as <code>VerifyContext(Provider, Type)</code>.
	 * 
	 * @param provider	Provider name.																	<br/>
	 * 					May be <code>null</code> if there is only one provider of such type.
	 * 
	 * @param type		The type of provider to acquire. 													<br/>
	 * 					Provider types may be learned by calling 											{@link JMSCAPI.Advapi32#CryptEnumProvidersW(int, Pointer, int, IntByReference, Pointer, IntByReference)
	 * 					CryptEnumProviders} function.
	 * @return			A handle to CSP.
	 * @throws	CancelledByUser	when user cancelled CSP`s password request.
	 * @throws	JMSCAPIException
	 */
	public static HCRYPTPROV contextAcquire (String container, String provider,
			int type){
		HCRYPTPROV hProv = contextVerify(provider, type);
		// There is no point of catching VerifyContext`s exception here...
		if (container == null || container ==""){ return hProv; }
		Boolean AlreadyExists = containerExists(hProv, container);
		Advapi32.INSTANCE.CryptReleaseContext(hProv, 0);
		
		HCRYPTPROVp hCryptProv = new HCRYPTPROVp();
		if(AlreadyExists){
			if(Advapi32.INSTANCE.CryptAcquireContextW(hCryptProv,container,provider,type,0)){
				return hCryptProv.getValue();
			}			
		}else{
			if(Advapi32.INSTANCE.CryptAcquireContextW(hCryptProv,container,provider,type,
					Advapi32.CRYPT_NEWKEYSET)){
				return hCryptProv.getValue();
			}
		}
		int err=Kernel32.INSTANCE.GetLastError();
		if (err==Kernel32.ERROR_CANCELLED){
			throw new CancelledByUser();
		}else{
			throw new JMSCAPIException(err);
		}
	}
	
	public static void contextRelease(HCRYPTPROV hProv){
		if(!Advapi32.INSTANCE.CryptReleaseContext(hProv, 0)){
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}

	
	/**
	 * This wrapper function of 																			{@link JMSCAPI.Advapi32#CryptAcquireContextW
	 * CryptAcquireContext} provide access to a CSP with a temporary key container if access
	 * to a private key is not required
	 * 
	 * @param provider	Provider name. 																		<br/>
	 * 					May be <code>null</code>, if there is only one provider of such type.
	 * @param type		The type of provider to acquire. 													<br/>
	 * 					Provider types may be learned by calling 											{@link JMSCAPI.Advapi32#CryptEnumProvidersW(int, Pointer, int, IntByReference, Pointer, IntByReference)
	 * 					CryptEnumProviders} function.
	 * @return			A handle to CSP.
	 * @throws JMSCAPIException	when <b>CryptAcquireContext</b> fails by any reason.
	 */
	public static HCRYPTPROV contextVerify (String provider, int type){
		HCRYPTPROVp hCryptProv = new HCRYPTPROVp();
		if (Advapi32.INSTANCE.CryptAcquireContextW(hCryptProv,null,provider,type,
				Advapi32.CRYPT_VERIFYCONTEXT)){		
			return hCryptProv.getValue();
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}
	
	/**
	 * Prints containers available to given context.
	 * NOTE: List of available containers depends on whether 											{@link JMSCAPI.misc.CryptAcquireContext_misc#CRYPT_MACHINE_KEYSET 
	 * CRYPT_MACHINE_KEYSET} flag, was set on context acquiring stage, or not. 
	 * @param hProv
	 * @param out
	 */
	public static void contextPrintContainers(HCRYPTPROV hProv, PrintStream out){
		Pointer pbData = null;
		IntByReference pdwDataLen = new IntByReference(0);

		
		if (Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_ENUMCONTAINERS,
				pbData, pdwDataLen, Advapi32.CRYPT_FIRST)){
			pbData = new Memory(pdwDataLen.getValue());
			
			if (Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_ENUMCONTAINERS,
					pbData, pdwDataLen, Advapi32.CRYPT_FIRST)){
				out.println(pbData.getString(0));
			}
			
			while(Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_ENUMCONTAINERS,
					pbData, pdwDataLen, Advapi32.CRYPT_NEXT)){
				out.println(pbData.getString(0));
			}			
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
		
	}
	
	/**
	 * Enumerates containers available to given context.
	 * NOTE: List of available containers depends on whether 											{@link JMSCAPI.misc.CryptAcquireContext_misc#CRYPT_MACHINE_KEYSET 
	 * CRYPT_MACHINE_KEYSET} flag, was set on context acquiring stage, or not. 
	 * @param hProv
	 * @return
	 */
	public static LinkedList<String> contextEnumContainers(HCRYPTPROV hProv){
		LinkedList<String> result = new LinkedList<String>();
		
		Pointer pbData = null;
		IntByReference pdwDataLen = new IntByReference(0);
		
		if (Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_ENUMCONTAINERS,
				pbData, pdwDataLen, Advapi32.CRYPT_FIRST)){
			pbData = new Memory(pdwDataLen.getValue());
			if (Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_ENUMCONTAINERS,
					pbData, pdwDataLen, Advapi32.CRYPT_FIRST)){
				result.add(pbData.getString(0));
			}
			while(Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_ENUMCONTAINERS,
					pbData, pdwDataLen, Advapi32.CRYPT_NEXT)){
				result.add(pbData.getString(0));				
			}	
			return result;
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			//System.out.print(err);
			switch (err) {
			case Kernel32.ERROR_NO_MORE_ITEMS:
				return result;

			default:				
				throw new JMSCAPIException(err);
				
			}			
		}
		
	}
	
	/**
	 * Checks if {@link JMSCAPI.misc.CryptAcquireContext_misc#CRYPT_MACHINE_KEYSET
	 * CRYPT_MACHINE_KEYSET} flag was set on context acquiring stage.
	 * @param hProv
	 * @return
	 */
	public static Boolean contextIsMachineKeyset(HCRYPTPROV hProv){
		Pointer pbData = new Memory(4);
		IntByReference pdwDataLen = new IntByReference(4);
		
		if(Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_KEYSET_TYPE,
				pbData, pdwDataLen, 0)){
			return (pbData.getInt(0)==Advapi32.CRYPT_MACHINE_KEYSET);
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}

//////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Returns provider name
	 * @param hProv
	 * @return
	 */
	public static String providerGetName(HCRYPTPROV hProv){
		IntByReference pdwDataLen = new IntByReference();
		Pointer pbData = null;
		
		Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_NAME,
				pbData, pdwDataLen, 0);
		
		pbData = new Memory(pdwDataLen.getValue());
		
		Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_NAME,
				pbData, pdwDataLen, 0);
		return pbData.getString(0);
		
	}
	
	/**
	 * Returns provider type.
	 * @param hProv
	 * @return
	 */
	public static Integer providerGetType(HCRYPTPROV hProv){
		Pointer pbData = new Memory(4);
		IntByReference pdwDataLen = new IntByReference(4);

		if(Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_PROVTYPE,
				pbData, pdwDataLen, 0)){
			return pbData.getInt(0);
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}
	
	/**
	 * Prints info about supported by provider algorithms.
	 * @param hProv
	 * @param out
	 */
	public static void providerPrintAlgs(HCRYPTPROV hProv, PrintStream out){
		Advapi32.PROV_ENUMALGS algs = new Advapi32.PROV_ENUMALGS();		
		IntByReference pdwDataLen = new IntByReference(algs.size());
		
		if(Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_ENUMALGS
				, algs.getPointer(), pdwDataLen, Advapi32.CRYPT_FIRST)){
			algs.autoRead();
			out.print("Listing available algorithms:\n");
			out.print("AlgID \tKey \tAlgorithm name\n");
			out.print("_____\t_____\t______________"+"_________\n");

			out.println(algs.aiAlgid+"\t"+algs.dwBitLen+"\t"
							+ new String(algs.szName,0,algs.dwNameLen.intValue()));
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}

		while (Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_ENUMALGS
				, algs.getPointer(), pdwDataLen, Advapi32.CRYPT_NEXT)){
			algs.autoRead();
			out.println(algs.aiAlgid+"\t"+algs.dwBitLen+"\t"
					+ new String(algs.szName,0,algs.dwNameLen.intValue()));
			}
	}
	
	/**
	 * Prints extended info about supported by provider algorithms
	 * @param hProv
	 * @param out
	 */
	public static void providerPrintAlgs_EX(HCRYPTPROV hProv, PrintStream out){
		Advapi32.PROV_ENUMALGS_EX algs = new Advapi32.PROV_ENUMALGS_EX();		
		IntByReference pdwDataLen = new IntByReference(algs.size());
		
		
		if(Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_ENUMALGS_EX
				, algs.getPointer(), pdwDataLen, Advapi32.CRYPT_FIRST)){
			
			algs.autoRead();
			
			System.out.print("Listing available algorithms:\n");
			System.out.format("%5s%8s%8s%8s%10s%20s%40s%n",
							"AlgID",
							"defKey",
							"minKey",
							"maxKey",
							"Protocols",
							"Algorithm name",
							"Long algorithm name"
			);
			System.out.format("%5s%8s%8s%8s%10s%20s%40s%n",
					"_____",
					"______",
					"______",
					"______",
					"_________",
					"__________________",
					"__________________________"
			);
			System.out.format("%5d%8d%8d%8d%10s%20s%40s%n",
							algs.aiAlgid,
							algs.dwDefaultLen.intValue(),
							algs.dwMinLen.intValue(),
							algs.dwMaxLen.intValue(),
							String.format("%8s",Integer.toBinaryString(
									algs.dwProtocols.intValue())).replace(' ', '0'),
							new String(algs.szName,0,algs.dwNameLen.intValue()),
							new String(algs.szLongName,0,algs.dwLongNameLen.intValue())
			);
			while (Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_ENUMALGS_EX
					, algs.getPointer(), pdwDataLen, Advapi32.CRYPT_NEXT)){
				algs.autoRead();
				System.out.format("%5d%8d%8d%8d%10s%20s%40s%n",
						algs.aiAlgid,
						algs.dwDefaultLen.intValue(),
						algs.dwMinLen.intValue(),
						algs.dwMaxLen.intValue(),
						String.format("%8s",Integer.toBinaryString(
								algs.dwProtocols.intValue())).replace(' ', '0'),
						new String(algs.szName,0,algs.dwNameLen.intValue()),
						new String(algs.szLongName,0,algs.dwLongNameLen.intValue())
				);
			}
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}

	}
	
	/* Does not work properly */
	/*
	public static Boolean hasHardwareRNG(HANDLEByReference hProv){
		Pointer pbData = null;	
		if(Advapi32.INSTANCE.CryptGetProvParam(hProv.getValue(), Advapi32.PP_USE_HARDWARE_RNG,
				pbData, null, 0)){
			return true;
		}else{
		  	int err=Kernel32.INSTANCE.GetLastError();
			System.out.println("err ="+err);		
			System.out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
			return false;
			
		}
	}
	*/
	
///////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Checks existence of container within given context
	 * @param hProv
	 * @param container
	 * @return
	 */
	public static Boolean containerExists(HCRYPTPROV hProv, String container){
		LinkedList<String> containers = contextEnumContainers(hProv);		
		return containers.contains(container);
	}
	
	/**
	 * Returns current container name
	 * @param hProv
	 * @return
	 */
	public static String containerGetName(HCRYPTPROV hProv){
		IntByReference pdwDataLen = new IntByReference();
		Pointer pbData = null;
		
		if(Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_CONTAINER,
				pbData, pdwDataLen, 0)){
		
			pbData = new Memory(pdwDataLen.getValue());
		
			Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_CONTAINER,
				pbData, pdwDataLen, 0);
			return pbData.getString(0);
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}		
	}
	
	/**
	 * Returns current container unique name.
	 * @param hProv
	 * @return
	 */
	public static String containerGetUniqName(HCRYPTPROV hProv){
		Pointer pbData = null;
		IntByReference pdwDataLen = new IntByReference();

		if(Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_UNIQUE_CONTAINER,
				pbData, pdwDataLen, 0)){
			pbData = new Memory(pdwDataLen.getValue());
			Advapi32.INSTANCE.CryptGetProvParam(hProv, Advapi32.PP_UNIQUE_CONTAINER,
					pbData, pdwDataLen, 0);
			return pbData.getString(0);
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}
	
	public static void containerDelete(HCRYPTPROV hProv, String container){
		if(containerExists(hProv, container)){
			if(Advapi32.INSTANCE.CryptAcquireContextW(new HCRYPTPROVp(hProv), container,
					providerGetName(hProv),
					providerGetType(hProv), Advapi32.CRYPT_DELETEKEYSET)){
				return;
			}
		}
		int err=Kernel32.INSTANCE.GetLastError();
		throw new JMSCAPIException(err);
	}

///////////////////////////////////////////////////////////////////////////////////////////////	
	
	public static HCRYPTKEY keyGetUsers(HCRYPTPROV hProv,int KeySpec){
		HCRYPTKEYp	phUserKey = new HCRYPTKEYp();
		
		if(Advapi32.INSTANCE.CryptGetUserKey(hProv, KeySpec, phUserKey)){
			return phUserKey.getValue();
		}
		int err=Kernel32.INSTANCE.GetLastError();
		throw new JMSCAPIException(err);
	}
	
	public static HCRYPTKEY keyGenerate(HCRYPTPROV hProv,int KeySpec){
		HCRYPTKEYp	phUserKey = new HCRYPTKEYp();
		
		if(Advapi32.INSTANCE.CryptGenKey(hProv, KeySpec, 0, phUserKey)){
			return phUserKey.getValue();
		}
		int err=Kernel32.INSTANCE.GetLastError();
		throw new JMSCAPIException(err);
	}
	
	/**
	 * 
	 * @param hProv
	 * @param KeySpec
	 * @param hashAlgID
	 * @param password
	 * @param dwFlags
	 * @return
	 * @see JMSCAPI.Advapi32#CryptDeriveKey
	 */
	public static HCRYPTKEY keyDerive(HCRYPTPROV hProv,int KeySpec,
			int hashAlgID,String password, int dwFlags){
		HCRYPTHASH hBaseData = hashCreate(hProv, hashAlgID, password);
		try{
			HCRYPTKEYp phKey=new HCRYPTKEYp();
			if(Advapi32.INSTANCE.CryptDeriveKey(hProv, KeySpec, hBaseData, dwFlags, phKey)){
				return phKey.getValue();
			}
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}finally{
			hashDestroy(hBaseData);
		}
	}
	
	public static void keyDestroy(HCRYPTKEY hKey){
		if(Advapi32.INSTANCE.CryptDestroyKey(hKey)){
			return;
		}
		int err=Kernel32.INSTANCE.GetLastError();
		throw new JMSCAPIException(err);
	}
	
	public static int keyGetAlgID(HCRYPTKEY hKey){
		Pointer pbData = new Memory(4);
		
		if(Advapi32.INSTANCE.CryptGetKeyParam(hKey, Advapi32.KP_ALGID, pbData,
				new IntByReference(4), 0)){
			return pbData.getInt(0);
		}
		int err=Kernel32.INSTANCE.GetLastError();
		throw new JMSCAPIException(err);
	}
	
	public static int keyGetBlokLen(HCRYPTKEY hKey){
		Pointer pbData = new Memory(4);
		
		if(Advapi32.INSTANCE.CryptGetKeyParam(hKey, Advapi32.KP_BLOCKLEN, pbData,
				new IntByReference(4), 0)){
			return pbData.getInt(0);
		}
		int err=Kernel32.INSTANCE.GetLastError();
		throw new JMSCAPIException(err);
	}
	
	/**
	 * 
	 * @param hKey
	 * @return
	 * @see JMSCAPI.misc.CryptGetKeyParam_misc#KP_KEYLEN KP_KEYLEN
	 */
	public static int keyGetLen(HCRYPTKEY hKey){
		Pointer pbData = new Memory(4);
		
		if(Advapi32.INSTANCE.CryptGetKeyParam(hKey, Advapi32.KP_KEYLEN, pbData,
				new IntByReference(4), 0)){
			return pbData.getInt(0);
		}
		int err=Kernel32.INSTANCE.GetLastError();
		throw new JMSCAPIException(err);
	}
	
	public static int keyGetEffectiveLen(HCRYPTKEY hKey){
		Pointer pbData = new Memory(4);
		
		if(Advapi32.INSTANCE.CryptGetKeyParam(hKey, Advapi32.KP_EFFECTIVE_KEYLEN, pbData,
				new IntByReference(4), 0)){
			return pbData.getInt(0);
		}
		int err=Kernel32.INSTANCE.GetLastError();
		throw new JMSCAPIException(err);
	}
	
	public static int keyGetPermissions(HCRYPTKEY hKey){
		Pointer pbData = new Memory(4);
		
		if(Advapi32.INSTANCE.CryptGetKeyParam(hKey, Advapi32.KP_PERMISSIONS, pbData,
				new IntByReference(4), 0)){
			return pbData.getInt(0);
		}
		int err=Kernel32.INSTANCE.GetLastError();
		throw new JMSCAPIException(err);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Creates empty hash object.
	 * @param hProv
	 * @param algid
	 * @return
	 */
	public static HCRYPTHASH hashCreate(HCRYPTPROV hProv,int  algid){
		HCRYPTHASHp phHash = new HCRYPTHASHp();
		
		if (Advapi32.INSTANCE.CryptCreateHash(hProv, algid, null, 0, phHash)){
			return phHash.getValue();
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}
	
	public static void hashDestroy(HCRYPTHASH hHash){
		if(!Advapi32.INSTANCE.CryptDestroyHash(hHash)){
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}
	
	/**
	 * Adds data to a specified hash object.
	 * Can be called multiple times to compute the hash of long or discontinuous data streams.
	 */
	public static void hashAddData(HCRYPTHASH hHash,String data){
		Pointer pbData = new Memory(data.length()+1);
		pbData.setString(0, data);
		
		if(!Advapi32.INSTANCE.CryptHashData(hHash, pbData , data.length(), 0)){
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}
	
	/**
	 * Adds data to a specified hash object.
	 * Can be called multiple times to compute the hash of long or discontinuous data streams.
	 * <code>Data</code> field must contain ONLY actual data to be hashed.
	 */
	public static void hashAddData(HCRYPTHASH hHash,byte[] data){
		Pointer pbData = new Memory(data.length+1);
		pbData.write(0, data, 0, data.length);
		
		if(!Advapi32.INSTANCE.CryptHashData(hHash, pbData , data.length, 0)){
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}
	
	/**
	 * 
	 * @param hHash
	 * @param data
	 * @param dataLen
	 */
	public static void hashAddData(HCRYPTHASH hHash,byte[] data, int dataLen){
		Pointer pbData = new Memory(dataLen);
		pbData.write(0, data, 0, dataLen);
		
		if(!Advapi32.INSTANCE.CryptHashData(hHash, pbData , dataLen, 0)){
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}
	
	/**
	 * Returns AlgID of hash.
	 * @param hHash
	 * @return
	 */
	public static int hashGetAlgID (HCRYPTHASH hHash){
		Pointer pbData = new Memory(4);
		
		if(Advapi32.INSTANCE.CryptGetHashParam(hHash,
				Advapi32.HP_ALGID,
				pbData, new IntByReference(4), 0)){
			return pbData.getInt(0);				
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}		
	}
	
	/**
	 * Performs actual hashing on hash object. If CSP support 											{@link JMSCAPI.Advapi32#CryptDuplicateHash 
	 * CryptDuplicateHash} method, uses it and hash object stays reusable.
	 * @param hHash
	 * @return
	 */
	public static byte[] hash(HCRYPTHASH hHash){		
		HCRYPTHASH dHash = null;
		Boolean Duplicate = false;
		try {
			dHash = hashDuplicate(hHash);
			Duplicate=true;
		} catch (NotImplemented e) {
			dHash=hHash;
		}
		try {			
			Pointer HASHSIZE = new Memory(4);
			if (Advapi32.INSTANCE.CryptGetHashParam(dHash,
					Advapi32.HP_HASHSIZE, HASHSIZE, new IntByReference(4), 0)) {				

				Pointer HASHVAL = new Memory(HASHSIZE.getInt(0));
				IntByReference size = new IntByReference(HASHSIZE.getInt(0));

				if (Advapi32.INSTANCE.CryptGetHashParam(dHash,
										Advapi32.HP_HASHVAL, HASHVAL, size, 0)) {
					return HASHVAL.getByteArray(0, HASHSIZE.getInt(0));
				}
			}
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}finally{
			if(Duplicate)hashDestroy(dHash);
		}
	}
	
	/**
	 * Creates hash object, adds data to it and performs hashing.
	 * @param hProv
	 * @param algid
	 * @param data
	 * @return
	 */
	public static byte[] hash(HCRYPTPROV hProv,int  algid, String data){
		HCRYPTHASH hHash = hashCreate(hProv,  algid);
		try{			
			hashAddData(hHash, data);
			return hash(hHash);
		}finally{
			hashDestroy(hHash);
		}
		
	}
	
	/**
	 * Creates hash object, adds data to it and performs hashing.
	 * @param hProv
	 * @param algid
	 * @param data
	 * @return
	 */
	public static byte[] hash(HCRYPTPROV hProv,int  algid, byte[] data){
		HCRYPTHASH hHash = hashCreate(hProv,  algid);
		try{			
			hashAddData(hHash, data);
			return hash(hHash);
		}finally{
			hashDestroy(hHash);
		}
		
	}
	

	
	public static byte[] hash(HCRYPTPROV hProv,int  algid, File in) {
		int BlockSize=4096;								//!!!!!!!!!!!!!!!!!!!!!!
		BufferedInputStream inF=null;
		HCRYPTHASH hHash = hashCreate(hProv,  algid);
		try{						
			inF = new BufferedInputStream(new FileInputStream(in), BlockSize);
			byte[] bb= new byte[BlockSize];				
			int readed=0;
			while(inF.available()>=BlockSize){
				readed = inF.read(bb);
				hashAddData(hHash, bb,readed);	
			}
			if(inF.available()>0){
				readed = inF.read(bb);
				hashAddData(hHash, bb,readed);	
			}
			return hash(hHash);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally{
			
				try {
					if(inF != null) inF.close();
					hashDestroy(hHash);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		return null;
	}
	
	
	
	public static HCRYPTHASH hashCreate(HCRYPTPROV hProv,int  algid, String data){
		HCRYPTHASH hHash = hashCreate(hProv,  algid);
		hashAddData(hHash, data);
		return hHash;
	}
	
	public static HCRYPTHASH hashDuplicate(HCRYPTHASH hHash){
		HCRYPTHASHp phHash = new HCRYPTHASHp();
		if(Advapi32.INSTANCE.CryptDuplicateHash(hHash, 0, 0, phHash)){
			return phHash.getValue();
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			switch (err) {
			case Kernel32.E_NOTIMPL:
				throw new NotImplemented();

			default:				
				throw new JMSCAPIException(err);
				
			}

		}
	}
	
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Works fine with Final==true up to 175000*keyBlockLen data size.
	 */
	public static byte[] cryptEncrypt(HCRYPTKEY hKey,byte[] data, int buffLen,boolean Final){
		IntByReference brBlockLen = new IntByReference(data.length);
		IntByReference brBuffLen = new IntByReference(buffLen);
		Pointer buff = new Memory(buffLen);
		
		buff.write(0, data, 0, data.length);
		
		if(Advapi32.INSTANCE.CryptEncrypt(hKey, null, Final, 0, buff,
				brBlockLen, brBuffLen)){
							//System.out.println("Final:"+Final+" encrypted: "+brBlockLen.getValue());
			return buff.getByteArray(0, brBlockLen.getValue());			
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}
	
	public static byte[] cryptDecrypt(HCRYPTKEY hKey,byte[] data,boolean Final){
		IntByReference brBlockLen = new IntByReference(data.length);
		
		Pointer buff = new Memory(data.length);
		
		buff.write(0, data, 0, data.length);
		
		if(Advapi32.INSTANCE.CryptDecrypt(hKey, null, Final, 0, buff, brBlockLen)){
							//System.out.println("Final:"+Final+" decrypted: "+brBlockLen.getValue());
			return buff.getByteArray(0, brBlockLen.getValue());			
		}else{
			int err=Kernel32.INSTANCE.GetLastError();
			throw new JMSCAPIException(err);
		}
	}
	
	
	private static int[] defineBlockParams(HCRYPTKEY hKey,int proposed, int available){
		int BuffLen;
		int BlockSize=proposed;
		int keyBlockLen=1;
		try{
			keyBlockLen=keyGetBlokLen(hKey)/8;
			if(keyBlockLen==0) keyBlockLen=1;
		}catch(JMSCAPIException e){throw e; };
		if ((proposed<keyBlockLen)||(proposed==0)) BlockSize = 2*175000*keyBlockLen;
				

		if(available>=BlockSize) BuffLen=BlockSize+keyBlockLen;
		else {
			BuffLen=(available/keyBlockLen +1)*keyBlockLen ;
			BlockSize=BuffLen;//-keyBlockLen;
		}
		return new int[]{BlockSize,BuffLen};
	}
	
	
	public static void cryptEncrypt(HCRYPTKEY hKey, File in, File out, int BlockSize){
		BufferedInputStream inS=null;
		BufferedOutputStream enS=null;
		try {
			inS = new BufferedInputStream(new FileInputStream(in));//, BlockSize);
			enS = new BufferedOutputStream(new FileOutputStream(out));//, BlockSize);
			//код отсюда
			int[] params = defineBlockParams(hKey, BlockSize, inS.available());			
			BlockSize = params[0];
			int BuffLen = params[1];
			//до сюда, вызывает очень сильные сомнения ибо написан в сонном виде...
			
			byte[] bb = new byte[BlockSize];
			int readed=0;
			
			while(inS.available()>BlockSize){
				readed = inS.read(bb);
				byte[] bbe=JMSCAPIUtil.cryptEncrypt(hKey, bb, BuffLen, false);				
				enS.write(bbe, 0, readed);
				//System.out.println(inS.available());
			}
			if(inS.available()>0){
				readed = inS.read(bb);
				byte[] bb2 = new byte[readed];
				for(int i=0;i<readed;i++)bb2[i]=bb[i];
				byte[] bbe=JMSCAPIUtil.cryptEncrypt(hKey, bb2, BuffLen, true);
				enS.write(bbe, 0, bbe.length);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally{
			try {
					if(inS!=null) inS.close();
					if(enS!=null) enS.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	
	public static void cryptDecrypt(HCRYPTKEY hKey, File in, File out, int BlockSize){
		BufferedInputStream inS=null;
		BufferedOutputStream deS=null;
		try {
			inS = new BufferedInputStream(new FileInputStream(in));//, BlockSize);
			deS = new BufferedOutputStream(new FileOutputStream(out));//, BlockSize);
			//код отсюда
			int[] params = defineBlockParams(hKey, BlockSize, inS.available());			
			BlockSize = params[0];
			int BuffLen = params[1];
			//до сюда, вызывает очень сильные сомнения ибо написан в сонном виде...
			
			byte[] bb = new byte[BlockSize];
			int readed=0;
			
			while(inS.available()>BlockSize){
				readed = inS.read(bb);
				byte[] bbe=JMSCAPIUtil.cryptDecrypt(hKey, bb, false);				
				deS.write(bbe, 0, readed);
			}
			if(inS.available()>0){
				readed = inS.read(bb);
				byte[] bb2 = new byte[readed];
				for(int i=0;i<readed;i++)bb2[i]=bb[i];
				byte[] bbe=JMSCAPIUtil.cryptDecrypt(hKey, bb2, true);
				deS.write(bbe, 0, bbe.length);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally{
			try {
					if(inS!=null) inS.close();
					if(deS!=null) deS.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	
}
