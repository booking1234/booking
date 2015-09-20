package com.mybookingpal.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;

public class FileUtils {

	private static final Logger LOG = Logger.getLogger(FileUtils.class.getName());

	/**
	 * Returns compressed/gzipped byte array
	 * 
	 * @param input
	 * @return byte[] gzipped/compressed byte array
	 */
	public static byte[] gzippedByteArrays(byte[] input) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(
				input.length);
		GZIPOutputStream zipStream = null;
		try {
			zipStream = new GZIPOutputStream(byteStream);
			zipStream.write(input);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				zipStream.close();
				byteStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return byteStream.toByteArray();
	}

}
