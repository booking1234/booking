package net.cbtltd.client.secure;

import net.cbtltd.client.secure.crypto.BufferedBlockCipher;
import net.cbtltd.client.secure.crypto.CryptoException;
import net.cbtltd.client.secure.crypto.engines.DESedeEngine;
import net.cbtltd.client.secure.crypto.modes.CBCBlockCipher;
import net.cbtltd.client.secure.crypto.paddings.PaddedBufferedBlockCipher;
import net.cbtltd.client.secure.crypto.params.KeyParameter;

public class DESCrypEngine {

	/**
	 * Local instance
	 */
	private BufferedBlockCipher cipher;
	/**
	 * Local instance
	 */
	private KeyParameter key;
	/**
	 * Local instance
	 */
	private static DESCrypEngine deCrypEngine;

	/**
	 * Return instance of {@link DESCrypEngine}
	 *
	 * @return {@link DESCrypEngine}
	 */
	public static DESCrypEngine getInstance() {
		if (deCrypEngine == null) {
			byte[] key = {'a','b','c'};
			deCrypEngine = new DESCrypEngine(key);
		}
		return deCrypEngine;
	}

	/**
	 * Private instance constructor
	 *
	 * @param key
	 * byte array
	 */
	private DESCrypEngine(byte[] key) {
		cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()));
		this.key = new KeyParameter(key);
		//base64 = new Base64();
	}

	/**
	 * Private instance
	 *
	 * @param key
	 * {@link String}
	 */
//	private DESCrypEngine(String key) {
//		this(key.getBytes());
//	}

	/**
	 * Cipher engine
	 *
	 * @param data
	 * byte array
	 * @return byte array
	 * @throws CryptoException
	 */
	private byte[] callCipher(byte[] data) throws CryptoException {
		int size = cipher.getOutputSize(data.length);
		byte[] result = new byte[size];
		int olen = cipher.processBytes(data, 0, data.length, result, 0);
		olen += cipher.doFinal(result, olen);
		if (olen < size) {
			byte[] tmp = new byte[olen];
			System.arraycopy(result, 0, tmp, 0, olen);
			result = tmp;
		}
		return result;
	}

	/**
	 * Encrypt byte array
	 *
	 * @param data
	 * byte array
	 * @return byte array
	 * @throws CryptoException
	 */
	public synchronized byte[] encrypt(byte[] data) throws CryptoException {
		if ((data == null) || (data.length == 0)) {
			return new byte[0];
		}
		cipher.init(true, key);
		return callCipher(data);
	}

	/**
	 * Encrypt String
	 *
	 * @param data
	 * {@link String}
	 * @return byte array
	 * @throws CryptoException
	 */
//	public byte[] encryptString(String data) throws CryptoException {
//		if ((data == null) || (data.length() == 0)) {
//			return new byte[0];
//		}
//		return encrypt(data.getBytes());
//	}

	/**
	 * Decrypt byte array
	 *
	 * @param data
	 * byte array
	 * @return byte array
	 * @throws CryptoException
	 */
	public synchronized byte[] decrypt(byte[] data) throws CryptoException {
		if ((data == null) || (data.length == 0)) {
			return new byte[0];
		}
		cipher.init(false, key);
		return callCipher(data);
	}

	/**
	 * Decrypt string
	 *
	 * @param data
	 * byte array
	 * @return {@link String}
	 * @throws CryptoException
	 */
//	public String decryptString(byte[] data) throws CryptoException {
//		if ((data == null) || (data.length == 0)) {
//			return "";
//		}
//		return new String(decrypt(data));
//	}
}

//Due to I want to prevent multiple instance of encryption engine, I wrote it into singleton pattern. 
//As we test this code using the encrypt() and decrypt() method, things will just work fine on a standalone application. 
//This is not the case if you encrypt the data and send it over a HTTP connection, for example a HTTP Servlet service via web server.
//
//You might get the following error at the server side:
//
//javax.crypto.BadPaddingException pad block corrupted at org.bouncycastle.jce.provider.JCEBlockCipher.engineDoFinal() 
//
//The problem behind this error that does not occur in standalone application is due to extra bytes been embedded into 
//HTTP request before send across the network. Also googled some results that describe it is caused by String to byte 
//conversion that affected by Locale of the machine. So I tried convert String to base64 byte array before send to 
//encryption using the following code:
//
//
//public static void main(String[] args) {
//String inputString = "1800-13-1333";
//Base64 base64 = new Base64();
//try {
//System.out.println("Before base64 conversion: " + inputString);
//byte[] base64String = base64.encode(inputString.getBytes());
//System.out.println("Base64 encoding length: " + base64String.length);
//byte[] encryptedData = DESCrypEngine.getInstance().encrypt(base64String);
//System.out.println("Encrypted length: " + encryptedData.length);
//byte[] decryptedData = DESCrypEngine.getInstance().decrypt(encryptedData);
//System.out.println("Decrypted length: " + decryptedData.length);
//byte[] decodedByte = base64.decode(decryptedData);
//System.out.println("Translated: " + new String(decodedByte));
//} catch (CryptoException e) {
//e.printStackTrace();
//}
//}
//But this will still give me the same error, then I tried switching the order of encryption and base64 encoding by doing 
//the encryption first before encode into base64 byte array and send over the HTTP connection. Guess what? IT'S WORKING!
//
//So the conclusion is:
//
//On the sending side
//Data In String -> Data In Byte Array -> Encrypted Byte Array -> Encoded Base64 Byte Array
//
//On the receiving side
//Encoded Base64 Byte Array -> Decoded Base64 Byte Array -> Decrypted Byte Array -> Data In Byte Array -> Data In String

