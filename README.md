
Java Microsoft CryptoAPI (JMSCAPI)
========================

JMSCAPI provides Java programs access to MSCAPI functions without writing anything but Java code—no JNI or native code is required. It based on JNA.

For now it is just some JNA mappings of MSCAPI functions and util functions for MSCAPI routine. In future it will be a genuine JCE provider [or it will
be a separate project and this code will be pulled in JNA, I haven`t decided yet :) ]

*NOTE: It`s very raw and you probably you will not want to use it for now.

========================
But, if you do ;) I added jar.

It looks like this:

		Provider p = new Provider(420);
		//420 is provider type I used for tests (Avest CSP)
		// 1 is for Microsoft Base Cryptographic Provider v1.0 and some others		
		
		Key k = new Key(p);
		
		byte[] blob = p.exportPublicKey(k);
		
		String data = "Some data";
		
		byte[] sign = p.sign(data.getBytes(), k);
		
		System.out.println("signed");
		
		Key pk = p.importPublicKey(blob);
		
		System.out.println(p.verify(data.getBytes(), sign, pk));

For now Signing and Verifying is only things this test "provider" supports. Encryption/Decryption and hashing a little bit harder to use for now. (As it shown in corresponding demos)