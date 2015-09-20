package net.cbtltd.client.secure;

import net.cbtltd.client.secure.crypto.KeyGenerationParameters;
import net.cbtltd.client.secure.crypto.generators.DESedeKeyGenerator;
import net.cbtltd.client.secure.crypto.params.DESedeParameters;
import net.cbtltd.client.secure.javalang.Str;
import net.cbtltd.client.secure.javasecurity.SecureRandom;
import net.cbtltd.client.secure.util.encoders.Hex;

//import org.bouncycastle.crypto.KeyGenerationParameters;
//import org.bouncycastle.crypto.generators.DESedeKeyGenerator;
//import org.bouncycastle.crypto.params.DESedeParameters;
//import org.bouncycastle.java.security.SecureRandom;
//import org.bouncycastle.util.encoders.Hex;

public class TripleDesKeyGenerator {
	private static final SecureRandom secureRandom = new SecureRandom();

	public TripleDesKeyGenerator() {
		super();
	}

	public byte[] generateNewKey() {
		KeyGenerationParameters keyGenerationParameters = new KeyGenerationParameters(
				secureRandom, DESedeParameters.DES_EDE_KEY_LENGTH * 8);
		DESedeKeyGenerator keyGenerator = new DESedeKeyGenerator();
		keyGenerator.init(keyGenerationParameters);
		byte[] key = keyGenerator.generateKey();
		return key;
	}

	public String encodeKey(byte[] key) {
		byte[] keyhex = Hex.encode(key);
		return new String(Str.toChars(keyhex));
	}

	public byte[] decodeKey(String testHexKey) {
		byte[] key = Hex.decode(testHexKey);
		return key;
	}

}
