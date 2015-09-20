package net.cbtltd.client.secure.crypto.generators;

import net.cbtltd.client.secure.crypto.CipherKeyGenerator;
import net.cbtltd.client.secure.crypto.params.DESParameters;

//import org.bouncycastle.crypto.CipherKeyGenerator;
//import org.bouncycastle.crypto.params.DESParameters;

public class DESKeyGenerator
    extends CipherKeyGenerator
{
    public byte[] generateKey()
    {
        byte[]  newKey = new byte[DESParameters.DES_KEY_LENGTH];

        do
        {
            random.nextBytes(newKey);

            DESParameters.setOddParity(newKey);
        }
        while (DESParameters.isWeakKey(newKey, 0));

        return newKey;
    }
}
