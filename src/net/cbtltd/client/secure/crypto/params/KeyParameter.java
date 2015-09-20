package net.cbtltd.client.secure.crypto.params;

import net.cbtltd.client.secure.crypto.CipherParameters;
import net.cbtltd.client.secure.javalang.Sys;

public class KeyParameter implements CipherParameters {
	private byte[] key;

	public KeyParameter(byte[] key) {
		this(key, 0, key.length);
	}

	public KeyParameter(byte[] key, int keyOff, int keyLen) {
		this.key = new byte[keyLen];

		Sys.arraycopyBytes(key, keyOff, this.key, 0, keyLen);
	}

	public byte[] getKey() {
		return key;
	}
}
