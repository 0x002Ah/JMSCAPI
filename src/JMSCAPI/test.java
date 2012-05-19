package JMSCAPI;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JFileChooser;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinNT.HANDLEByReference;
import com.sun.jna.ptr.IntByReference;

import JMSCAPI.Exceptions.JMSCAPIException;
import JMSCAPI.Exceptions.NoKey;
import JMSCAPI.Providers.Avest.Key;
import JMSCAPI.Providers.Avest.Provider;
import JMSCAPI.demos.CryptAcquireContextDemo;
import JMSCAPI.demos.CryptEnumProvidersDemo;
import JMSCAPI.demos.CryptKeysDemo;
import JMSCAPI.demos.EncryptDecryptDemo;
import JMSCAPI.demos.HashDemo;
import JMSCAPI.demos.Importing_a_Plaintext_Key;
import JMSCAPI.demos.Sign_and_Verify;
import JMSCAPI.misc.CryptGetProvParam_misc.PROV_ENUMALGS;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTHASH;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTHASHp;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTKEY;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTPROV;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTKEYp;



public class test {
	
	public static void printBytes(byte[] bytes){
		for(int i=0;i<bytes.length;i++){
			
			System.out.format("%02x", bytes[i]);
		}
		System.out.println();
	}

	
	public static void main(String[] args) throws FileNotFoundException{
		
		/*
		  	int err=Kernel32.INSTANCE.GetLastError();
			System.out.println("err ="+err);		
			System.out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
		 */
		


		//CryptEnumProvidersDemo.Run(System.out);
		//CryptAcquireContextDemo.Run(System.out);
		//CryptAcquireContextDemo.AvestTest(System.out);
		//CryptKeysDemo.Run(System.out, "Test Container", Advapi32.AT_KEYEXCHANGE);
		//HashDemo.Run();
		
		//HashDemo.Run2();
		
		//EncryptDecryptDemo.Run(420, 26154, 32808, "password");
		//EncryptDecryptDemo.Run(1, Advapi32.CALG_RC2, Advapi32.CALG_SHA1, "password");
		
		//Importing_a_Plaintext_Key.Run();
		//Sign_and_Verify.Run();
		


		
		int err;
			
		///*
		Provider p = new Provider(1);
		
		//System.out.println(p.getHashType());
				
		Key k = new Key(p);
		
		//System.out.println(k.getKeyType());
		
		byte[] blob = p.exportPublicKey(k);
		
		String data = "Some data";
		
		byte[] sign = p.sign(data.getBytes(), k);
		
		System.out.println("signed");
		
		Key pk = p.importPublicKey(blob);
		
		System.out.println(p.verify(data.getBytes(), sign, pk));
		//*/
		
		
		/*
		HCRYPTPROV hProv = JMSCAPIUtil.contextVerify(null, 1);
		
		HCRYPTKEY key= JMSCAPIUtil.keyDerive(hProv, JMSCAPI.Advapi32.AT_SIGNATURE,
				JMSCAPI.Advapi32.CALG_SHA1, "pass", 0);
		//HCRYPTKEY key= JMSCAPIUtil.keyGenerate(hProv, JMSCAPI.Advapi32.AT_KEYEXCHANGE);
		//HCRYPTKEY key= JMSCAPIUtil.keyDerive(hProv, JMSCAPI.Advapi32.AT_SIGNATURE,
		//		JMSCAPI.Advapi32.STB_1176_1, "pass", 0);
		//HCRYPTHASH hBaseData = JMSCAPIUtil.hashCreate(hProv, JMSCAPI.Advapi32.STB_1176_1, "pass");
		//System.out.println(JMSCAPIUtil.keyGetAlgID(key));
		//*/
		
		
		//System.out.println(JMSCAPIUtil.GetProvName(hProv));
		//System.out.println(JMSCAPIUtil.GetContName(hProv));		
		//JMSCAPIUtil.providerPrintAlgs(hProv, System.out);
		//JMSCAPIUtil.providerPrintAlgs_EX(hProv, System.out);
		//JMSCAPIUtil.PrintContainers(hProv, System.out);
		//System.out.println(JMSCAPIUtil.isMachineKeyset(hProv));		// does not work with avest
		//System.out.println(JMSCAPIUtil.GetType(hProv));
		//System.out.println(JMSCAPIUtil.GetUniqContName(hProv));
		
		//HCRYPTPROV hProv = JMSCAPIUtil.contextAcquire("qwqw", null, 420, 0);
		
		//HCRYPTPROV hProv = JMSCAPIUtil.contextAcquire(null, null, 1);		
		//HCRYPTPROV hProv = JMSCAPIUtil.contextAcquire(null, null, 420);	


		
		

		
		
		
		

		
		//JMSCAPIUtil.contextRelease(hProv);
		



	}

}


/*
HCRYPTKEY key = null;
//int keyType=8236;	//STB 1176.2
//int keyType=26154;	//GOST 28147
//int keyType=2;		
int keyType=Advapi32.CALG_RC2;
int hashAlgID =Advapi32.CALG_SHA1; 		
//int hashAlgID = 32808; //STB 1176.1		

try {
	key = JMSCAPIUtil.keyGetUsers(hProv, keyType);
} catch (JMSCAPIException e) {
	System.out.println(e.getMessage());
	key = JMSCAPIUtil.keyGenerate(hProv, keyType);
}

key = JMSCAPIUtil.keyDerive(hProv, keyType, hashAlgID, "password", 0);

//System.out.println(JMSCAPIUtil.keyGetAlgID(key));
//System.out.println(JMSCAPIUtil.keyGetBlokLen(key));
//System.out.println(JMSCAPIUtil.keyGetLen(key));
//System.out.println(JMSCAPIUtil.keyGetEffectiveLen(key));
//System.out.println(Integer.toBinaryString(JMSCAPIUtil.keyGetPermissions(key)).replace(' ', '0'));

JMSCAPIUtil.keyDestroy(key);
*/




/*/
/*
HANDLEByReference hProv = JMSCAPIUtil.AcquireContext("Test Container", null, 420, Advapi32.CRYPT_NEWKEYSET);
if (hProv!=null){
	System.out.println(JMSCAPIUtil.GetContName(hProv));
}else{
	System.out.println("null");
}
*/


/*
Pointer pbData = null;
IntByReference pdwDataLen = new IntByReference();

if(Advapi32.INSTANCE.CryptGetProvParam(hProv.getValue(), Advapi32.PP_UNIQUE_CONTAINER,
		pbData, pdwDataLen, 0)){
	pbData = new Memory(pdwDataLen.getValue());
	Advapi32.INSTANCE.CryptGetProvParam(hProv.getValue(), Advapi32.PP_UNIQUE_CONTAINER,
			pbData, pdwDataLen, 0);
	System.out.println(pbData.getString(0));
}else{
	err=Kernel32.INSTANCE.GetLastError();
	System.out.println("err ="+err);		
	System.out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
}
*/

/*
	boolean CryptGetProvParam(
			HCRYPTPROV hProv,			
			int dwParam,				
			Pointer pbData,					
			IntByReference pdwDataLen,	
			int dwFlags						
			);
 
 */

/*
LinkedList<String> Containers = JMSCAPIUtil.contextEnumContainers(hProv);

for (String c:Containers){
	System.out.println(c);
}

System.out.println(Containers.contains("Test Container3"));
*/


/*

byte[] bb =new byte[BlockLen];
for(int i=0; i<BlockLen; i++)bb[i]=bbe[i];	
printBytes(bb);	

byte[] bbd1=JMSCAPIUtil.cryptDecrypt(key, bb, false);
System.out.println("dercypt1:");
printBytes(bbd1);	

byte[] bb_ = new byte[bbe.length-BlockLen];
for(int i=BlockLen; i<bbe.length; i++)bb_[i-BlockLen]=bbe[i];	
printBytes(bb_);


byte[] bbd2=JMSCAPIUtil.cryptDecrypt(key, bb_, true);
System.out.println("dercypt2:");
printBytes(bbd2);

*/


/*
Pointer nll=null;

if(Advapi32.INSTANCE.CryptGetProvParam(hProv.getValue(), Advapi32.PP_ENUMEX_SIGNING_PROT, nll, null, 0)){
	System.out.println("true");		
}else{
	System.out.println("false");
	err=Kernel32.INSTANCE.GetLastError();
	System.out.println("err ="+err);		
	System.out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
}
*/


/*
Pointer pbData = new Memory(4);
IntByReference pdwDataLen = new IntByReference(4);

if(Advapi32.INSTANCE.CryptGetProvParam(hProv.getValue(), Advapi32.PP_KEYSET_TYPE,
		pbData, pdwDataLen, 0)){
	System.out.println(pbData.getInt(0)==Advapi32.CRYPT_MACHINE_KEYSET);
}else{
	err=Kernel32.INSTANCE.GetLastError();
	System.out.println("err ="+err);		
	System.out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
}
*/

/*
 		HANDLEByReference hProv = JMSCAPIUtil.AcquireContext(null, null, 1,
				Advapi32.CRYPT_VERIFYCONTEXT | Advapi32.CRYPT_MACHINE_KEYSET);
 */

/*
Pointer nll=new Memory(4);

if(Advapi32.INSTANCE.CryptGetProvParam(hProv.getValue(), Advapi32.PP_IMPTYPE, nll,
		new IntByReference(nll.SIZE), 0)){
	System.out.println("true, imptype="+nll.getInt(0));		
}else{
	System.out.println("false");
	err=Kernel32.INSTANCE.GetLastError();
	System.out.println("err ="+err);		
	System.out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
}
*/

/*
PROV_ENUMALGS algs = new PROV_ENUMALGS();

if(Advapi32.INSTANCE.CryptGetProvParam(hProv,
		Advapi32.PP_ENUMALGS,
		algs.getPointer(),
		new IntByReference(algs.size()),
		Advapi32.CRYPT_FIRST)){
	algs.autoRead();
	System.out.println(algs.aiAlgid+"\t"+algs.dwBitLen+"\t"
			+ new String(algs.szName,0,algs.dwNameLen.intValue()));
}else{
	int err=Kernel32.INSTANCE.GetLastError();
	System.out.println(err);
}	
*/	

		/*
		HANDLEByReference hProv = JMSCAPIUtil.AcquireContext("Test Container", null, 420, 0);
		if (hProv!=null){
			System.out.println(JMSCAPIUtil.GetContName(hProv));
		}else{
			System.out.println("null");
		}
		*/
		
		/*
		HANDLEByReference hProv = JMSCAPIUtil.AcquireContext("Test Container", null, 420, 0);
		if (hProv!=null){
			System.out.println(JMSCAPIUtil.GetContName(hProv));
		}else{
			System.out.println("null");
		}
		*/
		
		/*
		try{
			HCRYPTPROV hProv = JMSCAPIUtil.AcquireContext("", null, 420);
			JMSCAPIUtil.PrintContainers(hProv, System.out);
			Advapi32.INSTANCE.CryptReleaseContext(hProv, 0);		
		}catch (JMSCAPIException e) {
			//	System.out.print(e.getMessage());
			System.out.println(e.getErrorCode()+"\n"+
					e.getMessage());			
		}
		*/


/*
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(JMSCAPIUtil.containerExists(hProv, "Test Container3"));		
		
		JMSCAPIUtil.contextRelease(JMSCAPIUtil.contextAcquire("Test Container3", null, 1));	
		
		System.out.println(JMSCAPIUtil.containerExists(hProv, "Test Container3"));		
		JMSCAPIUtil.containerDelete(hProv, "Test Container3");
		
		System.out.println(JMSCAPIUtil.containerExists(hProv, "Test Container3"));
*/

/*
somemethod(){
	createlocalobject;
	try{
		if(somecondition1){
			keepgoing;
			if(somecondition2){						
				return result;
			}
		}
		throw new error();	
	}catch (error e){
		throw e;
	}finally{
		destroylocalobject;
	}
}
*/
