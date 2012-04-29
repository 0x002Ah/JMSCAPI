package JMSCAPI.misc;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinNT.HANDLEByReference;

public interface JMSCAPI_misc extends
							CryptEnumProviders_misc,
							CryptAcquireContext_misc,
							CryptReleaseContext_misc,
							CryptGetProvParam_misc,
							CryptGetUserKey_misc,
							CryptGetKeyParam_misc,
							CryptCreateHash_misc,
							CryptDestroyKey_misc,
							CryptGenKey_misc,
							CryptDestroyHash_misc,
							CryptGetHashParam_misc,
							CryptHashData_misc,
							CryptDuplicateHash_misc,
							CryptDecrypt_misc,
							CryptEncrypt_misc,
							CryptDeriveKey_misc,
							CryptExportKey_misc


																		{
	public static final int AT_KEYEXCHANGE = 1;
	public static final int AT_SIGNATURE = 2;
	
	public static final int CALG_SHA1 = 32772;
	public static final int CALG_MD5 = 32771;
	
	public static final int CALG_AES = 0x00006611;
	
	public static final int CALG_RC2 = 0x00006602;
	
	public static final int CALG_RC4 = 0x00006801;
	
	
    public static class HCRYPTPROV extends HANDLE{
        public HCRYPTPROV() {

        }

        public HCRYPTPROV(Pointer p) {
            super(p);
        }
        public HCRYPTPROV(HANDLE h) {
            super(h.getPointer());
        }
        

    }    
    
    public static class HCRYPTPROVp extends HANDLEByReference {
        public HCRYPTPROVp() {

        }

        public HCRYPTPROVp(HANDLE p) {
            super(p);
        }
        
        public HCRYPTPROVp(HCRYPTPROV h) {
            super(h);
            setValue(h);
        }       
        
        public HCRYPTPROV getValue(){
        	return new HCRYPTPROV(super.getValue());      	
        }
        
        public void setValue(HCRYPTPROV h){
        	getPointer().setPointer(0, h != null ? h.getPointer() : null);
        }
    }
    
    
    public static class HCRYPTKEY extends HANDLE {
        public HCRYPTKEY() {

        }

        public HCRYPTKEY(Pointer p) {
            super(p);
        }
        public HCRYPTKEY(HANDLE h) {
            super(h.getPointer());
        }
    }
    
    public static class HCRYPTKEYp extends HANDLEByReference {
        public HCRYPTKEYp() {

        }

        public HCRYPTKEYp(HANDLE p) {
            super(p);
        }
        public HCRYPTKEY getValue(){
        	return new HCRYPTKEY(super.getValue());      	
        }
    }
    
    
    public static class HCRYPTHASH extends HANDLE {
        public HCRYPTHASH() {

        }

        public HCRYPTHASH(Pointer p) {
            super(p);
        }
        public HCRYPTHASH(HANDLE h) {
            super(h.getPointer());
        }
    }
    
    public static class HCRYPTHASHp extends HANDLEByReference {
        public HCRYPTHASHp() {

        }

        public HCRYPTHASHp(HANDLE p) {
            super(p);
        }
        public HCRYPTHASH getValue(){
        	return new HCRYPTHASH(super.getValue());      	
        }
    }
}

/* 
 * <p><table><tr>
 * <td></td><td></td></tr><tr>
 * </td></tr>
 * </table> 
 */