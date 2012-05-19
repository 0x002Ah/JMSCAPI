package JMSCAPI.Providers.Avest;

import JMSCAPI.JMSCAPIUtil;
import JMSCAPI.misc.JMSCAPI_misc.HCRYPTKEY;

public class Key {
	
	private static final int defaultSessionKey = JMSCAPI.Advapi32.CALG_RC2;
	
	HCRYPTKEY _hKey=null;
	
	protected HCRYPTKEY getHandle(){
		return _hKey;
	}
	
	int KeyType;
	
	public int getKeyType() {
		return KeyType;
	}


	protected Key(HCRYPTKEY hKey){
		_hKey=hKey;
		KeyType = JMSCAPIUtil.keyGetAlgID(_hKey);
	}	

	
	public Key(Provider provider, int keyType){
		KeyType = keyType;
		_hKey = JMSCAPIUtil.keyGenerate(provider.getHandle(), KeyType);
	}
	
	public Key(Provider provider){
		KeyType = JMSCAPI.Advapi32.AT_SIGNATURE;
		_hKey = JMSCAPIUtil.keyGenerate(provider.getHandle(), KeyType);
	}
	
	/**
	 * ONLY for session keys generation
	 * @param provider
	 * @param passPhrase
	 * @param keyType
	 */
	public Key(Provider provider, String passPhrase, int keyType){
		KeyType = keyType;
		_hKey = JMSCAPIUtil.keyDerive(provider.getHandle(), KeyType,
				provider.HashType, passPhrase, 0);
	}
	
	/**
	 * ONLY for session keys generation
	 * @param provider
	 * @param passPhrase
	 */
	public Key(Provider provider, String passPhrase){
		KeyType = defaultSessionKey;

		_hKey = JMSCAPIUtil.keyDerive(provider.getHandle(), KeyType,provider.HashType, passPhrase, 0);
	}
	
	
}
