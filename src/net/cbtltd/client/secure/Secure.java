/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.secure;

import net.cbtltd.client.secure.crypto.DataLengthException;
import net.cbtltd.client.secure.crypto.InvalidCipherTextException;
import net.cbtltd.client.secure.crypto.engines.DESedeEngine;
import net.cbtltd.client.secure.crypto.modes.CBCBlockCipher;
import net.cbtltd.client.secure.crypto.paddings.PaddedBufferedBlockCipher;
import net.cbtltd.client.secure.crypto.params.KeyParameter;
import net.cbtltd.client.secure.javalang.Str;
import net.cbtltd.client.secure.util.encoders.Hex;

/** The Class Secure is to encrypt plain text and decrypt cipher text. */
public class Secure {
	private PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(
			new CBCBlockCipher(new DESedeEngine()));
	private byte[] key = null;

	/** Instantiates a new secure service.*/
	public Secure() {
		super();
	}

	/**
	 * Instantiates a new service with the specified key.
	 *
	 * @param key the key
	 */
	public Secure(byte[] key) {
		super();
		this.key = key;
	}

	private static Secure deCrypEngine;

	/**
	 * Return instance of {@link DESCrypEngine}
	 * Nox = 16f2a2fd29a1b391e0c16ef7e38ada9d8501929eb3dac229
	 * 15a8ce311fcb3786e697798a8051fb0b1913a8325e0d79a7
	 * 4a1f023bfd75924ca454b049f4c2204f1592f798c27fa46e
	 * e9eafda29483b9d03df4e57cefe543ba075eb3293701c1fd
	 * 97c4641c150d6efdfe89925e0d4cd3bcd685a78a676d89c7
	 * 6d52c16b80b9791ae3a701578023b0918508a7e6ce43e091
	 * 2f2349a85719e51f54c4b9b6b5e0ce98fd15dc3d9dd39b58.
	 *
	 * @return {@link DESCrypEngine}
	 */
	public static Secure getInstance() {
		if (deCrypEngine == null) {
			byte[] key = decodeKey("16f2a2fd29a1b391e0c16ef7e38ada9d8501929eb3dac229");
			deCrypEngine = new Secure(key);
		}
		return deCrypEngine;
	}

	/**
	 * Decode key.
	 *
	 * @param testHexKey the test hex key
	 * @return the byte[]
	 */
	public static byte[] decodeKey(String testHexKey) {
		byte[] key = Hex.decode(testHexKey);
		return key;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public byte[] getKey() {
		return key;
	}

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(byte[] key) {
		this.key = key;
	}

	/**
	 * Encrypt the specified plain text.
	 *
	 * @param plainText the specified 
	 * @return the cipher text.
	 * @throws DataLengthException the data length exception
	 * @throws IllegalStateException the illegal state exception
	 * @throws InvalidCipherTextException the invalid cipher text exception
	 */
	public String encrypt(String plainText) throws DataLengthException,
	IllegalStateException, InvalidCipherTextException {
		cipher.reset();
		cipher.init(true, new KeyParameter(key));

		// byte[] input = plainText.getBytes();
		byte[] input = Str.toBytes(plainText.toCharArray());
		byte[] output = new byte[cipher.getOutputSize(input.length)];

		int length = cipher.processBytes(input, 0, input.length, output, 0);
		int remaining = cipher.doFinal(output, length);
		byte[] result = Hex.encode(output, 0, output.length);

		return new String(Str.toChars(result));
	}

	/**
	 * Decrypt the specified cipher text.
	 *
	 * @param cipherText the specified cipher text.
	 * @return the plain text.
	 * @throws DataLengthException the data length exception
	 * @throws IllegalStateException the illegal state exception
	 * @throws InvalidCipherTextException the invalid cipher text exception
	 */
	public String decrypt(String cipherText) throws DataLengthException,
	IllegalStateException, InvalidCipherTextException {
		cipher.reset();
		cipher.init(false, new KeyParameter(key));

		byte[] input = Hex.decode(Str.toBytes(cipherText.toCharArray()));
		byte[] output = new byte[cipher.getOutputSize(input.length)];

		int length = cipher.processBytes(input, 0, input.length, output, 0);
		int remaining = cipher.doFinal(output, length);
		return new String(Str.toChars(output), 0, length + remaining);
	}
}

//public String encrypt(String plainText) throws DataLengthException,
//IllegalStateException, InvalidCipherTextException {
//cipher.reset();
//cipher.init(true, new KeyParameter(key));
//
//// byte[] input = plainText.getBytes();
//String chunk = plainText.substring(0, 7);
//byte[] input = Str.toBytes(chunk.toCharArray());
////byte[] input = Str.toBytes(plainText.toCharArray());
//byte[] output = new byte[cipher.getOutputSize(input.length)];
//
//int length = cipher.processBytes(input, 0, input.length, output, 0);
//int remaining = cipher.doFinal(output, length);
//byte[] result = Hex.encode(output, 0, output.length);
//
//Window.alert("encrypt " + input.length + " " + cipher.getOutputSize(input.length));
//return new String(Str.toChars(result));
//}
//
//public String decrypt(String cipherText) throws DataLengthException,
//IllegalStateException, InvalidCipherTextException {
//cipher.reset();
//cipher.init(false, new KeyParameter(key));
//
//byte[] input = Hex.decode(Str.toBytes(cipherText.toCharArray()));
//Window.alert("decrypt " + input.length + " " + cipher.getOutputSize(input.length));
//byte[] output = new byte[cipher.getOutputSize(input.length)];
//
//int length = cipher.processBytes(input, 0, input.length, output, 0);
//int remaining = cipher.doFinal(output, length);
//return new String(Str.toChars(output), 0, length + remaining);
//}

//  http://blink4blog.blogspot.com/2008/05/using-bouncycastle-java-encryption-over.html
//  see <pre>http://forums.sun.com/thread.jspa?threadID=5237100
//  byte[] hexStringToByteArray(String data) {
//	int k = 0;
//	byte[] results = new byte[data.length() / 2];
//	for (int i = 0; i < data.length();) {
//		results[k] = (byte) (Character.digit(data.charAt(i++), 16) << 4);
//		results[k] += (byte) (Character.digit(data.charAt(i++), 16));
//		k++;
//	}
//
//	return results;
//}

