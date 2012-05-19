package JMSCAPI.Providers.Avest;

import JMSCAPI.JMSCAPIUtil;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTHASH;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTPROV;

public class Provider {
	
	HCRYPTPROV hProv = null;
	
	protected HCRYPTPROV getHandle(){
		return hProv;
	}
	
	int HashType;
	
	public int getHashType() {
		return HashType;
	}

	public void setHashType(int hashType) {
		HashType = hashType;
	}
	
	private void setDefaultHashType(int provType){
		switch (provType) {
		case 420:
			HashType = JMSCAPI.Advapi32.STB_1176_1;
			break;

		default:
			HashType = JMSCAPI.Advapi32.CALG_SHA1;
			break;
		}		
	}

	/**
	 * With this constructor keys stored only in temporary container. 
	 * @param type
	 */
	public Provider(int type){
		hProv = JMSCAPIUtil.contextVerify(null, type);
		setDefaultHashType(type);
	}
	
	public Provider(String name, int type, String container) {
		hProv = JMSCAPIUtil.contextAcquire(container, name, type);
		setDefaultHashType(type);
	}

	public byte[] sign(byte[] data,Key key){
		HCRYPTHASH hHash = null;
		try{
			hHash = JMSCAPIUtil.hashCreate(hProv, HashType, data);
			return JMSCAPIUtil.signHash(hHash, key.getKeyType(), 0);		
		}finally{
			if (hHash!=null) JMSCAPIUtil.hashDestroy(hHash);
		}
	}
	
	public boolean verify(byte[] data,byte[] signature,Key publicKey){
		HCRYPTHASH hHash = null;
		try{
			hHash = JMSCAPIUtil.hashCreate(hProv, HashType, data);
			return JMSCAPIUtil.signVerify(hHash, signature, publicKey.getHandle(), 0);
		}finally{
			if (hHash!=null) JMSCAPIUtil.hashDestroy(hHash);
		}		
	}
	
	public Key getUserKey(){
		return new Key(JMSCAPIUtil.keyGetUsers(hProv, JMSCAPI.Advapi32.AT_SIGNATURE));
	}
	
	public byte[] exportPublicKey(Key key) {
		return JMSCAPIUtil.keyExport(key.getHandle(), null, JMSCAPI.Advapi32.PUBLICKEYBLOB, 0);
		
	}
	
	public Key importSignedKey(byte[] blob, Key publicKey) {
		return new Key(JMSCAPIUtil.keyImport(hProv, blob, publicKey.getHandle(), 0));
		
	}
	
	public Key importPublicKey(byte[] blob) {
		return new Key(JMSCAPIUtil.keyImport(hProv, blob, null, 0));
		
	}
	
	

}
