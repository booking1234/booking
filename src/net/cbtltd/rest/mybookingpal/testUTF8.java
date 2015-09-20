package net.cbtltd.rest.mybookingpal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class testUTF8 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File fileDir = new File("c:\\temp\\test.txt");

		Writer out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileDir), "UTF8"));


			out.append(" <item> <name> 00027-Pere Lluis 2º</name>  <id>206872</id> </item>");

			out.flush();
			out.close();

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
