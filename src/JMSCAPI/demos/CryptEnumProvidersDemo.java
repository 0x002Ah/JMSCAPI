package JMSCAPI.demos;
import java.io.PrintStream;

import JMSCAPI.Advapi32;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * This is example of using CryptEnumProviders function of Advapi32.
 * It shows how to list all available CSPs and their types.
 * 
 * @author Vladimir Bubornev aka 0x002Ah
 */

public class CryptEnumProvidersDemo {
	/**
	 * This function lists all available CSPs and their types for given instance of
	 * {@link JMSCAPI.Advapi32 Advapi32} interface,  into given PrintStream.
	 * 
	 * 
	 * @param out	PrintStream for listing CSPs
	 */
	public static void Run (PrintStream out){
		int dwIndex = 0;
		IntByReference pdwProvType = new IntByReference();
		Pointer pszProvName;
		IntByReference pcbProvName = new IntByReference();
		
		out.print("Listing Available Providers:\n");
		out.print("Provider type\tProvider Name\n");
		out.print("_______\t__________________"+
	        "___________________\n");
		
		while(Advapi32.INSTANCE.CryptEnumProvidersW(dwIndex, null, 0,
											pdwProvType, null, pcbProvName)){
			pszProvName = new Memory(pcbProvName.getValue());			
			if (Advapi32.INSTANCE.CryptEnumProvidersW(dwIndex++, null, 0, pdwProvType,
											pszProvName, pcbProvName)) {
				out.println(pdwProvType.getValue() + "\t" + "\t"
						+ pszProvName.getString(0, true));			
			} else {
				out.println("some_bug");
			}
		}
	}
}
