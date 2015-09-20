package net.cbtltd.json.text;

import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Text;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for the text of the specified id and language.
 */
public class TextHandler implements Handler {

	public String service() {return JSONRequest.TEXT.name();}

	/**
	 * Gets the text of the specified id and language, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the text, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 			// the point of sale code.
		String model = parameters.get("model"); 		// the model type
		String id = parameters.get("id"); 				// the model ID
		String language = parameters.get("language"); 	// the language code
		String type = parameters.get("type"); 			// the type of text
		
		if (model == null || model.isEmpty() || model.length() > 15) {throw new ServiceException(Error.text_model, model);}
		if (id == null || id.isEmpty() || id.length() > 10) {throw new ServiceException(Error.text_id, id);}
		if (language == null || language.isEmpty() || id.length() !=2) {language = Language.EN;}
		if (type == null || type.isEmpty()) {type = Text.Code.Public.name();}

		SqlSession sqlSession = RazorServer.openSession();
		TextWidgetItem result = new TextWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			String textid = model + id + type;
			Text record = sqlSession.getMapper(TextMapper.class).readbyexample(new Text(textid, language));
			result.setId(textid);
			result.setLanguage(record.getLanguage());
			result.setMessage(record.getNotes());
		}
		catch (Throwable x) {
			result.setId(id);
			result.setLanguage(language);
			result.setMessage(x.getMessage());
		}
		finally {sqlSession.close();}
		return result;
	}	

}
