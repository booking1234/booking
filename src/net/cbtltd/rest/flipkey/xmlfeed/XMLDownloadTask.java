package net.cbtltd.rest.flipkey.xmlfeed;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.mybookingpal.config.RazorConfig;

public class XMLDownloadTask implements Callable<DownloadTaskStatus> {
	
	private static final Logger LOG = Logger.getLogger(XMLDownloadTask.class);
	private String fileName;

	public XMLDownloadTask(String fileName) {
		super();
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public DownloadTaskStatus call() {
		
		DownloadTaskStatus downloadTaskStatus = new DownloadTaskStatus();
		downloadTaskStatus.setFileName(this.getFileName());
		try {
//			this.downloadFlipkeyGzipFile();
			this.unzipFile(this.downloadFlipkeyGzipFile());
		//	this.downloadFlipkeyXMLFile();
			
			downloadTaskStatus.setDownloadSucess(true);
			LOG.info("Read/write completed for  " + fileName);
		} catch (Exception e) {
			downloadTaskStatus.setDownloadSucess(false);
			LOG.error("Fatal Exception in Thread during reading the file "
					+ fileName, e);
		}
		return downloadTaskStatus;

	}
	
	public void unzipFile(File inputFile) throws IOException{
		FileInputStream fis=new FileInputStream(inputFile);
		GZIPInputStream gzis = new GZIPInputStream(fis);
		File outputFile = new File(FlipKeyUtils.FLIPKEY_XML_LOCAL_FILE_PATH
				+ fileName + FlipKeyUtils.XML_EXTENSION);
		FileOutputStream fos=new FileOutputStream(outputFile);
		byte[] buf = new byte[1024];
		int len = 0;

	
		
		while (gzis.available() == 1 && (len = gzis.read(buf)) != -1) {
			
			fos.write(buf, 0, len);
		//	Log.info("Unzip in progress for  " + fileName);

		}
		gzis.close();
		fos.flush();
		fos.close();
		
	}
	
	/**
	 * This method will download the gzip version of the file
	 * @throws IOException
	 */
	public File downloadFlipkeyGzipFile()
			throws IOException {
		OutputStream oStream = null;
	//	HttpURLConnection connection = null;
		File file = new File(FlipKeyUtils.FLIPKEY_XML_LOCAL_FILE_PATH
				+ fileName);
		try {
			LOG.info("Read/write started for  " + fileName);


			/*connection = FlipKeyUtils.getConnection(
					RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_URL)
							+ fileName, RazorConfig
							.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_USERNAME),
					RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_PWD));*/
			
			
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_URL)
					+ fileName);
			LOG.error("Flipkey Image URI Value : "+RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_URL)
					+ fileName);

			// add header
			Credentials credentials = new UsernamePasswordCredentials(
					RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_USERNAME),
					RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_PWD));
			LOG.error("Flipkey Inquiry Username & PWD  : "+RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_USERNAME)+
					RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_PWD));
			client.getCredentialsProvider().setCredentials(AuthScope.ANY,
					credentials);
			
			HttpResponse response = client.execute(getRequest);
			int statusCode = response.getStatusLine().getStatusCode();

			LOG.info("Response Code : " + statusCode);

				
			 
			oStream = new FileOutputStream(file);
			
			// 
			byte[] buf = new byte[1024];
			int len = 0;

			while ((len = response.getEntity().getContent().read(buf)) != -1) {
				oStream.write(buf, 0, len);
				//Log.info("Read/write in progress for  " + fileName);

			}

		} catch (Exception exp) {
			if (oStream != null) {
				try {
					oStream.flush();
					oStream.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
				
			}
			// http://bugs.java.com/bugdatabase/view_bug.do;jsessionid=53ede10dc8803210b03577eac43?bug_id=6519463
			LOG.info("Exception occured for file  so retrying" + fileName);
			
//			this.downloadFlipkeyGzipFile();
		} finally {
			if (oStream != null) {
				try {
					oStream.flush();
					oStream.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
			

		}
		return file;

	}

	/**
	 * This method will download the gzip version of xml while downloading from the website
	 * @param gzis
	 * @param oStream
	 * @throws IOException
	 */
	public void downloadFlipkeyXMLFile()
			throws IOException {
		OutputStream oStream = null;
		GZIPInputStream gzis = null;
	//	HttpURLConnection connection =null;
		try {
			LOG.info("Read/write started for  " + fileName);
			/* connection = FlipKeyUtils.getConnection(
					RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_URL)
							+ fileName, RazorConfig
							.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_USERNAME),
					RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_PWD));
			 */
			 
			 DefaultHttpClient client = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet(RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_URL)
						+ fileName);
				LOG.error("Flipkey Image URI Value : "+RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_URL)
						+ fileName);

				// add header
				Credentials credentials = new UsernamePasswordCredentials(
						RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_USERNAME),
						RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_PWD));
				LOG.error("Flipkey Inquiry Username & PWD  : "+RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_USERNAME)+
						RazorConfig.getValue(FlipKeyUtils.FLIPKEY_XML_FEED_PWD));
				client.getCredentialsProvider().setCredentials(AuthScope.ANY,
						credentials);
				
				HttpResponse response = client.execute(getRequest);
				int statusCode = response.getStatusLine().getStatusCode();

				LOG.error("Response Code : " + statusCode);

			 
			gzis = new GZIPInputStream(response.getEntity().getContent());

			File file = new File(FlipKeyUtils.FLIPKEY_XML_LOCAL_FILE_PATH
					+ fileName + FlipKeyUtils.XML_EXTENSION);
			oStream = new FileOutputStream(file);
			String line = "";
			// GZipInputStream "gunzip"s the data from the FileInputStream.
			byte[] buf = new byte[1024];
			int len = 0;

			while (gzis.available() == 1 && (len = gzis.read(buf)) != -1) {
				oStream.write(buf, 0, len);
				LOG.info("Read/write in progress for  " + fileName);

			}

		} catch (EOFException eofe) {
			if (gzis != null) {

				gzis.close();
			}
			if (oStream != null) {
				try {
					oStream.flush();
					oStream.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
			
			// http://bugs.java.com/bugdatabase/view_bug.do;jsessionid=53ede10dc8803210b03577eac43?bug_id=6519463
			LOG.info("EOFException occured for file so retrying " + fileName);
			
			this.downloadFlipkeyXMLFile();
		} finally {
			if (gzis != null) {

				gzis.close();
			}
			if (oStream != null) {
				try {
					oStream.flush();
					oStream.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
			

		}

	}
	
	

}
