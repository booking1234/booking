package net.cbtltd.server.rapa;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;

public interface HttpClientAdapter {

	public int executeMethod(HttpMethod method) throws HttpException,
			IOException;

}