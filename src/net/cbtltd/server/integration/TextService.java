/**
 * 
 */
package net.cbtltd.server.integration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Text;

import org.apache.ibatis.session.SqlSession;

/**
 * @author Suresh Kumar S, Nibodha
 *
 */
public class TextService implements IsService {

	/**
	 * create the specified text instance and creates an description and dont up
	 * 
	 * @param sqlSession
	 *            the current SQL session.
	 * @param text
	 *            the specified text instance.
	 */
	public static void createDescription(SqlSession sqlSession, Text text) {
		try {
			if (text == null) {
				return;
			}
			Text exists = sqlSession.getMapper(TextMapper.class).readbyNameAndID(
					text);
			if (exists == null) {
				sqlSession.getMapper(TextMapper.class).create(text);
			} 

		} catch (Throwable x) {
			MonitorService.log(x);
		}
	}
	
	public static void createProductDescription(SqlSession sqlSession, Product product, Map<String, String> languageTodescription) {
		
		for (String language : languageTodescription.keySet()) { 
//			LOG.debug("language " + language + " notes " + languageTodescription.get(language));
			product.setPublicText(new Text(product.getPublicId(), product.getPublicLabel(), Text.Type.HTML, new Date(), languageTodescription.get(language), language));
			TextService.update(sqlSession, product.getTexts());
		}
		
	}
	/**
	 * Updates the specified text instance and creates an audio file if it is new or has changed.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param text the specified text instance.
	 */
	public static void update(SqlSession sqlSession, Text text) {
		try {
			if (text == null) {return;}
			Text exists = sqlSession.getMapper(TextMapper.class).readbyexample(text);
			if (exists == null) {sqlSession.getMapper(TextMapper.class).create(text);}
			else {
				text.setVersion(new Date());
				sqlSession.getMapper(TextMapper.class).update(text);
			}
//TODO:			File f = new File(TextToAudio.getFn(text));
//			if (!f.isFile() || exists == null || exists.getNotes().hashCode() != text.getNotes().hashCode()) {new TextToAudio(text).start();} //textToAudioFile(text);}
		}
		catch (Throwable x) {MonitorService.log(x);}
	}
	/**
	 * Updates the specified map of text instances. 
	 * An entity typically has several text instances, indexed by keys, for descriptions, instructions, contents etc.
	 *
	 * @param sqlSession the current SQL session.
	 * @param texts the map of text records indexed by keys.
	 */
	public static void update(SqlSession sqlSession, HashMap<String, Text> texts) {
		if (texts == null || texts.isEmpty()) {return;}
		for (Text text : texts.values()) {update(sqlSession, text);}
	}
	
	public static Text readByID(SqlSession sqlSession, String id){
		return sqlSession.getMapper(TextMapper.class).readByID(id);
	}
	public static List<Text> readallbyid(SqlSession sqlSession, String id){
		return sqlSession.getMapper(TextMapper.class).readallbyid(id);
	}
}
