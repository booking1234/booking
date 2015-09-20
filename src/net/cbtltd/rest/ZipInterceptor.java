/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.cbtltd.shared.License;

import org.apache.cxf.binding.soap.interceptor.SoapPreProtocolOutInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/** The Class ZipInterceptor is to intercept outgoing XML messages for ZIP compression. */
public class ZipInterceptor extends AbstractPhaseInterceptor<Message> {

	/** Instantiates a new zip interceptor. */
	public ZipInterceptor() {
		super(Phase.PRE_STREAM);
		addBefore(SoapPreProtocolOutInterceptor.class.getName());
	}

	/* (non-Javadoc)
	 * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
	 */
	public void handleMessage(Message message) {
		//TODO: check implementation

		boolean isOutbound = false;
		isOutbound = message == message.getExchange().getOutMessage()
		|| message == message.getExchange().getOutFaultMessage();

		if (isOutbound) {
			OutputStream os = message.getContent(OutputStream.class);
			CachedStream cs = new CachedStream();
			message.setContent(OutputStream.class, cs);

			message.getInterceptorChain().doIntercept(message);

			try {
				cs.flush();
				CachedOutputStream csnew = (CachedOutputStream) message
				.getContent(OutputStream.class);
				GZIPOutputStream zipOutput = new GZIPOutputStream(os);
				CachedOutputStream.copyStream(csnew.getInputStream(), zipOutput, 1024);

				cs.close();
				zipOutput.close();
				os.flush();

				message.setContent(OutputStream.class, os);
			}
			catch (IOException ioe) {ioe.printStackTrace();}
		}
		else {
			try {
				InputStream is = message.getContent(InputStream.class);
				GZIPInputStream zipInput = new GZIPInputStream(is);
				message.setContent(InputStream.class, zipInput);
			} catch (IOException ioe) {ioe.printStackTrace();}
		}
	}

	/* (non-Javadoc)
	 * @see org.apache.cxf.phase.AbstractPhaseInterceptor#handleFault(org.apache.cxf.message.Message)
	 */
	public void handleFault(Message message) {
	}


	private class CachedStream extends CachedOutputStream {
		public CachedStream() {
			super();
		}

		protected void doFlush() throws IOException {
			currentStream.flush();
		}

		protected void doClose() throws IOException {
		}

		protected void onWrite() throws IOException {
		}
	}
}
