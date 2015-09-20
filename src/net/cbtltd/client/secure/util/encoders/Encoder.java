package net.cbtltd.client.secure.util.encoders;

import net.cbtltd.client.secure.javaio.IOException;
import net.cbtltd.client.secure.javaio.OutputStream;

/**
 * Encode and decode byte arrays (typically from binary to 7-bit ASCII
 * encodings).
 */
public interface Encoder
{
    int encode(byte[] data, int off, int length, OutputStream out) throws IOException;

    int decode(byte[] data, int off, int length, OutputStream out) throws IOException;

    int decode(String data, OutputStream out) throws IOException;
}
