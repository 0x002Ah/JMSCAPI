package JMSCAPI.demos;

import java.io.PrintStream;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.ptr.IntByReference;

import JMSCAPI.Advapi32;
import JMSCAPI.JMSCAPIUtil;
//import JMSCAPI.Kernel32;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTKEY;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTPROV;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTKEYp;

public class CryptKeysDemo {
	public static void Run (PrintStream out, String Container, int KeySpec){	
		//HCRYPTPROV hProv = JMSCAPIUtil.AcquireContext(Container, null, 420);
		HCRYPTPROV hProv = JMSCAPIUtil.contextVerify(null, 420);
		
		HCRYPTKEYp	phUserKey = new HCRYPTKEYp();
		int err;
		
		if(Advapi32.INSTANCE.CryptGetUserKey(hProv, KeySpec, phUserKey)){
			out.println("User have key");
		}else{
			err=Kernel32.INSTANCE.GetLastError();
			out.println("err ="+err);		
			out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
			if(Advapi32.INSTANCE.CryptGenKey(hProv, KeySpec, 0, phUserKey)){
				out.println("Key have been successfully generated");
			}else{
				err=Kernel32.INSTANCE.GetLastError();
				out.println("err ="+err);		
				out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
			}
		}
		
		HCRYPTKEY hKey =  phUserKey.getValue();
		int dwParam = Advapi32.KP_ALGID;
		Pointer pbData = new Memory(4);
		IntByReference pdwDataLen = new IntByReference(0);
		
		if(Advapi32.INSTANCE.CryptGetKeyParam(hKey, dwParam, pbData, pdwDataLen, 0)){
			out.print(pbData.getInt(0));
		}else{
			err=Kernel32.INSTANCE.GetLastError();
			out.println("err ="+err);		
			out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
		}
		
		if(Advapi32.INSTANCE.CryptDestroyKey(hKey)){
			out.println("Key handle have been successfully destroyed");
		}else{
			err=Kernel32.INSTANCE.GetLastError();
			out.println("err ="+err);		
			out.println(Kernel32Util.formatMessageFromLastErrorCode(err));
		}
	}
}
