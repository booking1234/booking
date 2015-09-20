/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

/*****************************************************************************************
 * Fast implementation of RSA's MD5 hash generator in Java JDK Beta-2 or higher<br>
 * Originally written by Santeri Paavolainen, Helsinki Finland 1996 <br>
 * (c) Santeri Paavolainen, Helsinki Finland 1996 <br>
 * Some changes Copyright (c) 2002 Timothy W Macinta <br>
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * @author	Santeri Paavolainen <sjpaavol@cc.helsinki.fi>
 * @author	Timothy W Macinta (twm@alum.mit.edu) (optimizations and bug fixes)
 ****************************************************************************************/

class MD5State {
	int	state[];
	long count;
	byte buffer[];

	public MD5State() {
		buffer = new byte[64];
		count = 0;
		state = new int[4];
		state[0] = 0x67452301;
		state[1] = 0xefcdab89;
		state[2] = 0x98badcfe;
		state[3] = 0x10325476;

	}

	/** Create this State as a copy of another state */
	public MD5State (MD5State from) {
		this();
		int i;
		for (i = 0; i < buffer.length; i++){this.buffer[i] = from.buffer[i];}
		for (i = 0; i < state.length; i++){this.state[i] = from.state[i];}
		this.count = from.count;
	}
};
