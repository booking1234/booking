package net.cbtltd.rest.flipkey.xmlfeed;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Senthilnathan
 * 
 */
public class HTMLLinkExtractor {

	private Pattern patternTag;
	private Pattern patternLink;
	private Matcher matcherTag;
	private Matcher matcherLink;

	private static final String HTML_A_TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";
	private static final String HTML_A_HREF_TAG_PATTERN = "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";

	public HTMLLinkExtractor() {
		patternTag = Pattern.compile(HTML_A_TAG_PATTERN);
		patternLink = Pattern.compile(HTML_A_HREF_TAG_PATTERN);
	}

	/**
	 * Validate html with regular expression
	 * 
	 * @param html
	 *            html content for validation
	 * @return Vector links and link text
	 */
	public List<HTMLLink> grabHTMLLinks(final String html) {

		List<HTMLLink> result = new ArrayList<HTMLLink>();

		matcherTag = patternTag.matcher(html);

		while (matcherTag.find()) {

			String href = matcherTag.group(1); // href
			String linkText = matcherTag.group(2); // link text

			matcherLink = patternLink.matcher(href);

			while (matcherLink.find()) {

				String link = matcherLink.group(1); // link
				HTMLLink obj = new HTMLLink();
				obj.setLink(link);
				obj.setLinkText(linkText);

				result.add(obj);

			}

		}

		return result;

	}

}
