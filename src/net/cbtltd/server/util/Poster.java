package net.cbtltd.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

public class Poster {
	public static <T> String postXmlRequest(String apiUrl, T requestEntity)
			throws IOException, JAXBException {
		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		String stringRequest = MarshallerHelper.toXML(requestEntity);

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/xml");
		connection.setRequestProperty("Content-Length",
				"" + stringRequest.length());

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);

		connection.setDoOutput(true);

		PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
		printWriter.print(stringRequest);
		printWriter.close();

		BufferedReader inputStreamReader = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = inputStreamReader.readLine()) != null) {
			response.append(inputLine);
		}
		inputStreamReader.close();

		return response.toString();
	}
}