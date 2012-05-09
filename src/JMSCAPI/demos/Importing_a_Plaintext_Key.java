package JMSCAPI.demos;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import JMSCAPI.JMSCAPIUtil;
import JMSCAPI.misc.JMSCAPI_misc;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTKEY;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTKEYp;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTPROV;

public class Importing_a_Plaintext_Key {
	
	public static void printBytes(byte[] bytes){
		for(int i=0;i<bytes.length;i++){
			
			System.out.format("%02x", bytes[i]);
		}
		System.out.println();
	}
	
	public static void Run (){
 
		 byte[]DesKeyBlob = {
			    0x08,0x02,0x00,0x00,0x01,0x66,0x00,0x00, // BLOB header 
			    0x08,0x00,0x00,0x00,                     // key length, in bytes
			    (byte) 0xf1,0x0e,0x25,0x7c,0x6b,(byte) 0xce,0x0d,0x34  // DES key with parity
			    };		 
		 printBytes(DesKeyBlob);
		 
		 HCRYPTPROV hProv  = JMSCAPIUtil.contextAcquire(null, null, 1);
		 HCRYPTKEY hKey    = JMSCAPIUtil.keyImport(hProv, DesKeyBlob, null, JMSCAPI.Advapi32.CRYPT_EXPORTABLE);
		 printBytes(JMSCAPIUtil.keyExport(hKey, null, JMSCAPI.Advapi32.PLAINTEXTKEYBLOB, 0));

		
	}
}
