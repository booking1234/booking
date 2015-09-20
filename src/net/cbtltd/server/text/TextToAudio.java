/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.marytts.MaryClient;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.api.HasUrls;

import org.apache.log4j.Logger;

/**
 * @see <pre>http://mary.dfki.de/</pre>
 * @see <pre>http://www.dfki.de/pipermail/mary-users/ blog</pre>
 * @see <pre>http://mary.opendfki.de/wiki/FrequentlyAskedQuestions#bugreport</pre>
 * @see <pre>http://www.dfki.de/pipermail/mary-users/2011-February/000728.html for extra languages</pre>
 * @see <pre>http://www.dfki.de/pipermail/mary-users/2011-January/000698.html MP3 and OGG (Vorbis) support</pre>
 * @see <pre>http://www.dfki.de/pipermail/mary-users/2011-January/000699.html request for help</pre>
 * @see <pre>http://mary.opendfki.de/wiki/TranscriptionTool for language transcriptions</pre>
 * @see <pre>http://www.xuggle.com/xuggler/ to create new media formats</pre>
 * @see <pre>http://www.ffmpeg.org/ to record, convert and stream audio and video</pre>
 * @see <pre>http://ttssamples.syntheticspeech.de/</pre>
 * @see <pre>http://www.acapela-vaas.com/index.html text as a service</pre>
 *      
 * Use mary-component-installer.bat for additional languages
 * 
 * The standard way to call the MARY client when the output is to go to an output stream.
 * 
 * @param input a textual representation of the input data 
 * @param inputType the name of the input data type from TEXT, RAWMARYXML, TOKENS, WORDS, POS, PHONEMES, INTONATION, ALLOPHONES, ACOUSTPARAMS or MBROLA
 * @param outputType the name of the output data type from AUDIO, TOKENS, WORDS, POS, PHONEMES, INTONATION, ALLOPHONES, ACOUSTPARAMS or MBROLA
 * @param audioType the name of the audio format from AIFF, AU, WAVE, MP3, and Vorbis
 * @param defaultVoiceName the name of the voice to use, e.g. de7 or us1.
 * @param audioEffects the audio effects and their parameters to be applied as a post-processing step, e.g. Robot(Amount=100), Whisper(amount=50)
 * @param outputTypeParams any additional parameters, e.g. for output type TARGETFEATURES, the space-separated list of features to produce. Can be null. 
 * @param output the output stream into which the data from the server is to be written.
 * @throws IOException if communication with the server fails
 * @throws UnknownHostException if the host could not be found
 * 
 * @see #getInputDataTypes()
 * @see #getOutputDataTypes()
 * @see #getVoices()
 *
 *    public void process(
 *    	String input, 
 *    	String inputType, 
 *    	String outputType, 
 *    	String locale,
 *      String audioType, 
 *      String defaultVoiceName, 
 *      String defaultStyle, 
 *      String defaultEffects, 
 *      String outputTypeParams, //optional
 *      OutputStream output
 *    ) throws IOException {
 *        _process(input, inputType, outputType, locale, audioType, defaultVoiceName, defaultStyle, defaultEffects, output, 0, false, outputTypeParams, null);
 *    }
 *    
 *    We need both WAVE and MP3 to cover all major browsers
 *    Request must have 'INPUT_TEXT' and 'INPUT_TYPE' and 'OUTPUT_TYPE' and 'LOCALE'
 *    Example URL
 *    http://localhost:59125/process?INPUT_TEXT=Translate%20this%20please%20as%20fast%20as%20you%20can&INPUT_TYPE=TEXT&OUTPUT_TYPE=AUDIO&AUDIO=WAVE_FILE&LOCALE=en_US
 */
public class TextToAudio extends Thread {
	private static final Logger LOG = Logger.getLogger(TextToAudio.class.getName());
	private final Text text;
	
	public TextToAudio (Text text) {
		this.text = text;
	}

	public static final String getFn(Text text) {
		return RazorServer.ROOT_DIRECTORY + HasUrls.AUDIO_DIRECTORY + text.getId() + text.getLanguage().toUpperCase() + Text.AUDIO_WAV;
	}

	public void run() {
		LOG.debug("textToAudioFile value " + getFn(text));
		try {
			if (text == null || text.getNotes() == null || text.getNotes().isEmpty()) {return;}
			String inputType = "TEXT";
			String outputType = "AUDIO";
			String defaultVoiceName = getVoiceName(text.getLanguage()); //"cmu-slt-hsmm";
			String locale = getLocale(text.getLanguage());

			String audioType = "WAVE";
			MaryClient mary = MaryClient.getMaryClient(); // for localhost access
			LOG.debug("\ngetAudioEffects " + mary.getAudioEffects() + "\ngetAddress " + mary.getAddress() + "\ngetAllDataTypes " + mary.getAllDataTypes() + "\ngetAudioFileFormatTypes "+ mary.getAudioFileFormatTypes() + "\ngetAudioOutTypes " + mary.getAudioOutTypes() + "\ngetGeneralDomainVoices " + mary.getGeneralDomainVoices() + "\ngetInputDataTypes " + mary.getInputDataTypes() + "\ngetOutputDataTypes " + mary.getOutputDataTypes() + "\ngetVoices " + mary.getVoices());

			File file = new File(getFn(text));
			OutputStream fos = new FileOutputStream(file);
			mary.process(text.getNotes(), inputType, outputType, locale, audioType, defaultVoiceName, fos);
			fos.close();
		} 
		catch (Exception x) {MonitorService.log(x);}
	}
	
	private static String getVoiceName(String language) throws RuntimeException {
		if (Language.EN.equalsIgnoreCase(language)) {return "cmu-slt-hsmm";} 		//US English female
		else if (Language.DE.equalsIgnoreCase(language)) {return "bits1-hsmm";} 	//German female
		else if (Language.RU.equalsIgnoreCase(language)) {return "voxforge-ru-nsh";}//Russian male
		else if (Language.TR.equalsIgnoreCase(language)) {return "dfki-ot-hsmm";} 	//Turkish male
		else {throw new RuntimeException("Invalid language code " + language);}
	}
	
	private static String getLocale(String language) throws RuntimeException {
		if (Language.EN.equalsIgnoreCase(language)) {return "en_US";}
		else  {return language.toLowerCase();}
	}
}
