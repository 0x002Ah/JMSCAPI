package JMSCAPI.demos;

import java.io.File;

import javax.swing.JFileChooser;

import JMSCAPI.Advapi32;
import JMSCAPI.JMSCAPIUtil;
import JMSCAPI.Exceptions.JMSCAPIException;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTKEY;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTPROV;

public class EncryptDecryptDemo {
	public static void Run (int provType, int keyType, int hashAlgID, String password ){
		
		HCRYPTPROV hProv = JMSCAPIUtil.contextAcquire(null, null, provType);	
		
		HCRYPTKEY key = null;
		

		
		key = JMSCAPIUtil.keyDerive(hProv, keyType, hashAlgID, password, 0);
		
		int keyBlockLen=1;
		try{
			keyBlockLen=JMSCAPIUtil.keyGetBlokLen(key)/8;
			if(keyBlockLen==0) keyBlockLen=1;
		}catch(JMSCAPIException e){throw e; };
		
		
		int BlockLen=2*175000*keyBlockLen;
		
		System.out.println(BlockLen);

		JFileChooser fc = new JFileChooser();
		
		fc.setDialogTitle("Select file to be encrypted");
		fc.showOpenDialog(null);
		
		File raw=fc.getSelectedFile();
		
		fc.setDialogTitle("Create file for encrypted data");
		fc.showSaveDialog(null);
		
		File encrypt=fc.getSelectedFile();
		
			long timeStart = System.currentTimeMillis();
			JMSCAPIUtil.cryptEncrypt(key, raw, encrypt, BlockLen);
			long time = System.currentTimeMillis() - timeStart;
			System.out.println("Encrypting time: " + time + " millisec");
			

	
			fc.setDialogTitle("Create file for decrypted data");
			fc.showSaveDialog(null);
			File decrypt=fc.getSelectedFile();
			
			timeStart = System.currentTimeMillis();
			JMSCAPIUtil.cryptDecrypt(key, encrypt, decrypt, BlockLen);
			time = System.currentTimeMillis() - timeStart;
			System.out.println("Decrypting time: " + time + " millisec");		

		
		
		JMSCAPIUtil.keyDestroy(key);
		

		
		JMSCAPIUtil.contextRelease(hProv);
	}
}
