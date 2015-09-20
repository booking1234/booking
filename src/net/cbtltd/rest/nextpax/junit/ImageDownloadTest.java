package net.cbtltd.rest.nextpax.junit;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageDownloadTest {
	
	public static final Logger LOG = LoggerFactory.getLogger(ImageDownloadTest.class);

	@Test
	public void test() {
		String fileURL = "http://secure.nextpax.net/img/bn/161932/161932_n_28ac8d4.jpg";
		
		try {
			long startTime = System.currentTimeMillis();
			LOG.error("Downloading file named: " + fileURL);
			URL url = new URL(fileURL.replace(" ", "%20"));
			BufferedImage image = ImageIO.read(url);
			long endTime = System.currentTimeMillis();
			LOG.error("Total time taken to download image " + url + " : " + (endTime - startTime) + " miliseconds.");
			
//			setText(sqlSession, new File(fn), fn, name,
//					Text.Type.Image.name(), imageData.getName(), language,
//					Text.FULLSIZE_PIXELS_VALUE,
//					Text.THUMBNAIL_PIXELS_VALUE);

		} catch (FileNotFoundException e) {
			LOG.error("Bad or Absent Image URL: " + fileURL + " " + e.getMessage());
		} catch (MalformedURLException e) {
			LOG.error("Bad or Absent Image URL: " + fileURL);
		} catch (IOException e) {
			LOG.error("Bad or Absent Image URL: " + fileURL);
		}
	}

}
