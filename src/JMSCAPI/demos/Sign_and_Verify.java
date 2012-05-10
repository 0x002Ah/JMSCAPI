package JMSCAPI.demos;

import JMSCAPI.JMSCAPIUtil;
import JMSCAPI.Exceptions.NoKey;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTHASH;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTKEY;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTPROV;

public class Sign_and_Verify {
	
	public static void printBytes(byte[] bytes){
		for(int i=0;i<bytes.length;i++){
			
			System.out.format("%02x", bytes[i]);
		}
		System.out.println();
	}
	
	public static void Run (){
		
		String data = "data to be signed and verifyed";
		HCRYPTPROV hProv  =null;
		HCRYPTKEY hKey = null ;
		HCRYPTKEY hPubKey = null ;
		HCRYPTHASH hHash =null;
		
		try {
			hProv  = JMSCAPIUtil.contextAcquire(null, null, 1);
			System.out.print("CSP context acquired.\n");
			
			try {
				hKey = JMSCAPIUtil.keyGetUsers(hProv, JMSCAPI.Advapi32.AT_SIGNATURE);
				System.out.print("The signature key has been acquired. \n");
			} catch (NoKey e) {
				hKey = JMSCAPIUtil.keyGenerate(hProv, JMSCAPI.Advapi32.AT_SIGNATURE);
				System.out.print("The signature key has been generated. \n");
			}
			
			byte[] blob = JMSCAPIUtil.keyExport(hKey, null, JMSCAPI.Advapi32.PUBLICKEYBLOB, 0);	
			System.out.print("Public key has been exported to BLOB. \n");
			//printBytes(blob);
			
			hHash = JMSCAPIUtil.hashCreate(hProv, JMSCAPI.Advapi32.CALG_MD5, data);
			System.out.print("The data buffer has been hashed.\n");
			
			byte[] sign = JMSCAPIUtil.signHash(hHash, JMSCAPI.Advapi32.AT_SIGNATURE, 0);
			System.out.print("Hash has been signed.\n");
			//printBytes(sign);
			
			JMSCAPIUtil.hashDestroy(hHash);
			System.out.print("The hash object has been destroyed.\n");
			System.out.print("The signing phase of this program is completed.\n\n");
			
			//-------------------------------------------------------------------
			// In the second phase, the hash signature is verified.
			// This would most often be done by a different user in a
			// separate program. The hash, signature, and the PUBLICKEYBLOB
			// would be read from a file, an email message, 
			// or some other source.
			
			// The contents of the "data" buffer must be the same data 
			// that was originally signed.
			
			//-------------------------------------------------------------------
			// Get the public key of the user who created the digital signature 
			// and import it into the CSP by using CryptImportKey.
			
			hPubKey = JMSCAPIUtil.keyImport(hProv, blob, null, 0);
			System.out.print("The key has been imported.\n");
			
			//data+=" ";
			
			hHash = JMSCAPIUtil.hashCreate(hProv, JMSCAPI.Advapi32.CALG_MD5, data);
			System.out.print("The data buffer has been rehashed.\n");
			
			
			//sign[0]++;
			
			if (JMSCAPIUtil.signVerify(hHash, sign, hPubKey, 0)) System.out.print("The signature has been verified.\n");
			else System.out.print("Signature not validated!\n");
			
			
		} finally {	
			if (hHash!=null) JMSCAPIUtil.hashDestroy(hHash);
			if (hPubKey!=null) JMSCAPIUtil.keyDestroy(hPubKey);
			if (hKey!=null) JMSCAPIUtil.keyDestroy(hKey);
			if (hProv!=null) JMSCAPIUtil.contextRelease(hProv);
		}		
				
		
	}

}
