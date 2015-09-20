package net.cbtltd.client.secure.crypto.params;

import net.cbtltd.client.secure.crypto.CipherParameters;
import net.cbtltd.client.secure.javasecurity.SecureRandom;


//import org.bouncycastle.crypto.CipherParameters;
//import org.bouncycastle.java.security.SecureRandom;


public class ParametersWithRandom
    implements CipherParameters
{
    private SecureRandom        random;
    private CipherParameters    parameters;

    public ParametersWithRandom(
        CipherParameters    parameters,
        SecureRandom        random)
    {
        this.random = random;
        this.parameters = parameters;
    }

    public ParametersWithRandom(
        CipherParameters    parameters)
    {
        this.random = null;
        this.parameters = parameters;
    }

    public SecureRandom getRandom()
    {
        if (random == null)
        {
            random = new SecureRandom();
        }
        return random;
    }

    public CipherParameters getParameters()
    {
        return parameters;
    }
}
