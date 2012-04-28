package JMSCAPI.demos;

import javax.swing.JFileChooser;

import JMSCAPI.Advapi32;
import JMSCAPI.JMSCAPIUtil;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTHASH;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTPROV;

public class HashDemo {
	public static void printHash(byte[] hash){
		for(int i=0;i<hash.length;i++){
			
			System.out.format("%02x", hash[i]);
		}
		System.out.println();
	}
	
	public static void Run (){
		HCRYPTPROV hProv = JMSCAPIUtil.contextVerify(null, 1);
		
		String str = new String("Hash me");
		
		HCRYPTHASH hHash = JMSCAPIUtil.hashCreate(hProv,  Advapi32.CALG_SHA1);			
		JMSCAPIUtil.hashAddData(hHash, str);
		
		HCRYPTHASH hHash2 = JMSCAPIUtil.hashDuplicate(hHash);
		
		System.out.println("phase 1");	
		
		printHash(JMSCAPIUtil.hash(hHash));
		printHash(JMSCAPIUtil.hash(hHash2));
		
		JMSCAPIUtil.hashAddData(hHash, str);
		JMSCAPIUtil.hashAddData(hHash, str);
		JMSCAPIUtil.hashAddData(hHash2,str+str);
		
		System.out.println("phase 2");	
		
		printHash(JMSCAPIUtil.hash(hHash));
		printHash(JMSCAPIUtil.hash(hHash2));
		
		JMSCAPIUtil.hashDestroy(hHash);
		JMSCAPIUtil.hashDestroy(hHash2);
		JMSCAPIUtil.contextRelease(hProv);
	}
	
	public static void Run2 (){
		HCRYPTPROV hProv = JMSCAPIUtil.contextAcquire(null, null, 420);	
		
		JFileChooser fc = new JFileChooser();
		
		fc.setDialogTitle("Select file to be hashed");
		fc.showOpenDialog(null);
		
		long timeStart = System.currentTimeMillis();
		JMSCAPIUtil.printBytes(JMSCAPIUtil.hash(hProv, Advapi32.CALG_SHA1, fc.getSelectedFile()));
		long time = System.currentTimeMillis() - timeStart;
		System.out.println("Hashing time: " + time + " millisec");
		
		JMSCAPIUtil.contextRelease(hProv);
		
	}
}
