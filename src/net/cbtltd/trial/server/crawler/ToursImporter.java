package net.cbtltd.trial.server.crawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.price.PriceCSV;
import net.cbtltd.shared.price.PriceReplace;

import org.apache.ibatis.session.SqlSession;

public class ToursImporter {

	public static void main(String[] args)
			throws IOException {

		FileReader fr = null;
		BufferedReader in = null;

		try {
			fr = new FileReader("c:/temp/crawler/travel_agent.txt");
			in  = new BufferedReader(fr);

			//StringBuilder sb = new StringBuilder();
			String line = null;
			String lastline = null;
			while ((line = in.readLine()) != null) {
				if (!line.equals(lastline)) {
					Party party = new Party();
					//System.out.println("\n" + line);
					String[] tokens = line.split("\",\"");
					
					String token = tokens[0].replaceAll("\"", "");
					party.setName(token);
					party.setPostaladdress(tokens[2]);
					party.setEmailaddress(tokens[3]);
					party.setWebaddress(tokens[4]);
					party.setDayphone(tokens.length > 5 ? tokens[5] : "");
					party.setFaxphone(tokens.length > 5 ? tokens[5] : "");
					party.setNotes(tokens.length > 7 ? tokens[7].replaceAll("\"", "") : "");
					System.out.println(party.toString());
				}
				lastline = line;
			}
		}
		finally {
			if (in != null) {in.close();}
		}
	}

	public final PriceReplace execute(SqlSession sqlSession, PriceReplace action) {
	try {
		sqlSession.getMapper(PriceMapper.class).deletebyexample(action);
		BufferedReader inputStream = new BufferedReader(new FileReader(action.getFilename()));
		PriceCSV price = new PriceCSV();
		String csv = null;
		while ((csv = inputStream.readLine()) != null) {
			price.setCSV(csv);
			sqlSession.getMapper(PriceMapper.class).create(price);
		}
		inputStream.close();
	}
	catch (Throwable x) {MonitorService.log(x);}
	return action;
}

}
