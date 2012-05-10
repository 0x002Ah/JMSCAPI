package JMSCAPI.demos;
import java.io.PrintStream;


import JMSCAPI.Advapi32;
//import JMSCAPI_old.Advapi32;
//import JMSCAPI.Kernel32;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTPROVp;


import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;

/**
 * The following example shows acquiring a cryptographic context. If the requested key container
 * does not exist, it is created.
 * 																										<p>
 * NOTE: Due to strange behavior of CSP with which this demo has been used, it`s incorrect.
 * instead of: 																							<p><code>
 *	...																									<br/>
 *	if(err==Kernel32.NTE_BAD_KEYSET||err==Kernel32.ERROR_CANCELLED){									<br/>
 *	...																									</code><p>
 * there must be: 																						<p><code>
 * 	...																									<br/>
 * if(err==Kernel32.NTE_BAD_KEYSET){																	<br/>
 * 	... 																								</code><p>
 * Current version of this Demo can not distinguish the situation, when key container already
 * exists, and, when it`s not. 
 */

public class CryptAcquireContextDemo {
	public static void Run (PrintStream out){		
		HCRYPTPROVp hCryptProv = new HCRYPTPROVp();
		String contName = "TestKeyContainer";
		
		if (Advapi32.INSTANCE.CryptAcquireContextW(hCryptProv,contName,null,420,0)){			
			out.println("A cryptographic context with the " + contName +
					"key container has been aquired");			
			}else{
				int err=Kernel32.INSTANCE.GetLastError();
				if(err==Kernel32.NTE_BAD_KEYSET||err==Kernel32.ERROR_CANCELLED){
					if(Advapi32.INSTANCE.CryptAcquireContextW(hCryptProv, contName, null, 420,
							Advapi32.CRYPT_NEWKEYSET)){
						out.println("A new key container has been created.");
					}else{
						err=Kernel32.INSTANCE.GetLastError();
						out.println("Could not create a new key container. err= "+err);
					}
				}else{
					out.println("A cryptographic service handle could " +
							"not be acquired, err= "+err);
				}
			}
	}
	
	public static void AvestTest (PrintStream out){
		HCRYPTPROVp hCryptProv = new HCRYPTPROVp();	
		//String contName = "TestKeyContainer";
		
		
		int err=Kernel32.INSTANCE.GetLastError();
		out.println("err ="+err);		
		out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
		if (Advapi32.INSTANCE.CryptAcquireContextW(hCryptProv,null,null,420,
														Advapi32.CRYPT_VERIFYCONTEXT)){	
			err=Kernel32.INSTANCE.GetLastError();
			out.println("True, err ="+err);		
			out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
			
			/* ?????!!!!!!!!
			 * Result:    
			 * True, err =87 Параметр задан неверно.
			 * 
			 * But in MS VS 2010 works in same way...
			 */
			
			err=Kernel32.INSTANCE.GetLastError();
			out.println("True, err ="+err);		
			out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
			
			if (Advapi32.INSTANCE.CryptReleaseContext(hCryptProv.getValue(), 0)){
				err=Kernel32.INSTANCE.GetLastError();
				out.println("Released, err ="+err);		
				out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
			}else{
				err=Kernel32.INSTANCE.GetLastError();
				out.println("Not released, err ="+err);		
				out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
			}
		}else{
			err=Kernel32.INSTANCE.GetLastError();
			out.println("False, err ="+err);
			out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
		}

	}
}
