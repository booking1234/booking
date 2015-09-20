/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.util.Date;
import java.util.HashMap;

import net.cbtltd.server.api.ImageMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Image;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.image.ImageTextRead;
import net.cbtltd.shared.text.TextDelete;
import net.cbtltd.shared.text.WidgetText;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.google.translate.api.v2.core.Translator;

/**
 * The Class TextService responds to text related requests.
 * It also translates from one language to another using Google or other web services
 * It can create an audio file for some languages
 * 
 * @see <pre>http://code.google.com/p/google-translate-api-v2-java/</pre>
 * @see <pre>http://mary.dfki.de/</pre>
 */
public class ImageTextService
implements IsService {

	private static final Logger LOG = Logger.getLogger(ImageTextService.class.getName());
	private static Translator translator;
	private static ImageTextService service;

	/**
	 * Gets the single instance of TextService to manage Text instances.
	 * @see net.cbtltd.shared.Text
	 *
	 * @return single instance of TextService.
	 */
	public static synchronized ImageTextService getInstance() {
		if (service == null){service = new ImageTextService();}
		return service;
	}

	/**
	 * Executes the WidgetText action to read a Text instance for a widget.
	 * Transforms the action product id to a text id for the specified type.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action
	 * @return the response.
	 */
	public final Text execute(SqlSession sqlSession, WidgetText action) {
		LOG.debug("WidgetText " + action);
		return textRead(sqlSession, action);
	}

	/**
	 * Executes the TextRead action to read a Text instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action
	 * @return the response.
	 */
	public final Text execute(SqlSession sqlSession, ImageTextRead action) {
		return textRead(sqlSession, action);
	}

	/**
	 * Executes the specified action to read a Text instance and to create a new one if does not exist.
	 * If a translation is empty and is translatable it will be translated using Google services and stored for future use.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action
	 * @return the response.
	 */
	public synchronized Text textRead (SqlSession sqlSession, Text action) {
		Text text = null;
		try {
			Image image = new Image();
			String productId = action.getId();
			productId = productId.substring(Text.IMAGE_PREFIX.length(), productId.indexOf('-'));
			image.setProductId(Integer.parseInt(productId));
			image.setName(action.getId());
			image = sqlSession.getMapper(ImageMapper.class).exists(image);
			

//			String to_language = action.getLanguage();
			
			
			LOG.debug("\ntextRead action " + action);
	
			if (image != null) {
				text = new Text(action.getId(), action.getName(), Text.Type.HTML, new Date(), null, action.getLanguage());
				text.setNotes(image.getNotes());
//				if (!Language.isTranslatable(to_language)) {return action;}
//				text.setName(translate(action.getName(), Language.EN, to_language));
//				text.setNotes(translate(text.getNotes(), Language.EN, to_language));
//				
//				EmailService.translatedText(sqlSession, text);
			}
		}
		catch (Throwable x) {MonitorService.log(x);}
		LOG.debug("textRead out " + text);
		return text;		
	}
	
	/**
	 * Executes the TextDelete action to delete a Text instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action
	 * @return the response.
	 */
	public final Text execute(SqlSession sqlSession, TextDelete action) {
		try {
			action.setState(Text.State.Final.name());
			sqlSession.getMapper(TextMapper.class).update(action);
		}
		catch (Throwable x) {MonitorService.log(x);}
		return null;
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
	
	/**
	 * Deletes the specified map of text instances. 
	 *
	 * @param sqlSession the current SQL session.
	 * @param texts the texts
	 */
	public static void delete(SqlSession sqlSession, HashMap<String, Text> texts){
		try {for (Text text : texts.values()){sqlSession.getMapper(TextMapper.class).deletebyexample(text);}}
		catch (Throwable x) {MonitorService.log(x);}
	}
	
	/**
	 * Translates long text using Google Translate API V2.
	 *
	 * @param text the text to be translated.
	 * @param source the language code from which to translate.
	 * @param target the language code to which to translate.
	 * @return the translated text.
	 */
	public static String translate(String text, String source, String target) {
		if (text == null || text.trim().isEmpty()) {return null;}
		final String[] args = Text.getSentences(text);
		if (args == null || args.length <= 0) {return null;}
		try {
			if (translator == null) {translator = new Translator(HasUrls.GOOGLE_API_KEY);}
//			Translation translation = translator.translate(text, source.toLowerCase(), target.toLowerCase());
//			return translation.getTranslatedText();
			StringBuilder sb = new StringBuilder();
			for (String arg : args) {
				if (arg == null || arg.trim().isEmpty()) {sb.append("\n\n");}
				else {sb.append(translator.translate(arg, source.toLowerCase(), target.toLowerCase()).getTranslatedText());}
			}
			return sb.toString();
		} 
		catch (Throwable x) {MonitorService.log(x);}
		return null;
	}
	
	/**
	 * Updates the specified text instance and creates an description
	 * 
	 * @param sqlSession
	 *            the current SQL session.
	 * @param text
	 *            the specified text instance.
	 */
	public static void updateDescription(SqlSession sqlSession, Text text) {
		try {
			if (text == null) {
				return;
			}
			Text exists = sqlSession.getMapper(TextMapper.class).readbyNameAndID(
					text);
			if (exists == null) {
				sqlSession.getMapper(TextMapper.class).create(text);
			} else {
				text.setVersion(new Date());
				sqlSession.getMapper(TextMapper.class).update(text);
			}

		} catch (Throwable x) {
			MonitorService.log(x);
		}
	}
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
	
	/**
	 * read the text for the given id
	 * @param sqlSession
	 * 				the current SQL session.
	 * @param id
	 * @return
	 * 		list of text
	 */
	public static final java.util.List<Text> readallbyid(SqlSession sqlSession, String id) {
		return sqlSession.getMapper(TextMapper.class).readallbyid(id);
	}
	

	
}