/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.Model;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/** The Class TextField is to display and change a string value. */
public class TextField
extends AbstractField<String> {

	private final FlowPanel panel = new FlowPanel();
	private Label label;
	private String mask;
	protected TextBox field = new TextBox();

	/**
	 * Instantiates a new text field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.
	 * @param tab index of the field.
	 */
	public TextField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		this(form, permission, label, null, tab);
	}

	/**
	 * Instantiates a new text field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.
	 * @param mask is the optional text to mask the field.
	 * @param tab index of the field.
	 */
	public TextField(
			HasComponents form,
			short[] permission,
			String label,
			String mask,
			int tab) {

		initialize(panel, form, permission, CSS.cbtTextField());

		super.setDefaultValue(Model.BLANK);
		
		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtTextFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}
		if (mask != null) {
			this.mask = mask;
			field.addKeyPressHandler(new MaskHandler());
		}

		field.addStyleName(CSS.cbtTextFieldField());
		field.setText(Model.BLANK);
		field.setTabIndex(tab);
		field.addChangeHandler(form);
 		panel.add(field);
		panel.add(lock);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		field.setEnabled(isEnabled());
	}

	/**
	 * Sets the label.
	 *
	 * @param text the new label
	 */
	public void setLabel(String text) {
		if (label != null) {label.setText(text);}
	}

	/**
	 * Sets the CSS style of the field value.
	 *
	 * @param style the CSS style of the field.
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the field label.
	 *
	 * @param style the CSS style of the field label.
	 */
	public void setLabelStyle(String style) {
		if (label != null) {label.addStyleName(style);}
	}

	/** Sets the half width CSS style of the list. */
	public void setFieldHalf(){
		setFieldStyle(CSS.cbtTextFieldHalf());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
		setFieldStyle(CSS.cbtTextFieldSecure());
	}

	/**
	 * Sets the maximum length of the text.
	 *
	 * @param length the new maximum length.
	 */
	public void setMaxLength(int length) {
		field.setMaxLength(length);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){
		field.setFocus(focussed);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){
		field.setTabIndex(tab);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue() {
		return field == null || field.getText().trim().isEmpty();
	}

	/**
	 * Checks if this has the specified value.
	 *
	 * @param value the specified value.
	 * @return true, if this has the specified value.
	 */
	public boolean hasValue(String value) {
		return field.getText().trim().equals(value);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public String getValue() {
		if (secure) {return Model.encrypt(field.getText());}
		else {return field.getText();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(String value) {
		if (secure) {field.setText(Model.decrypt(value));}
		else {field.setText(value);}
		super.setChanged();
	}

	/**
	 * Gets the raw text.
	 *
	 * @return the raw text.
	 */
	public String getText() {
		return field.getText().trim();
	}

	/**
	 * Sets the raw text.
	 *
	 * @param value the raw text
	 */
	public void setText(String value) {
		field.setText(value);
		super.setChanged();
	}

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		return (field == null || field.getText() == null  || getValue().equals(Model.BLANK));
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){return (sender == field);}

	/*
	 * Handles keyboard events to apply the mask.
	 *
	 * Arrow keys are enabled to move within the field, and to move between fields using tab.
	 */
	private final class MaskHandler implements KeyPressHandler {
		@Override
		public void onKeyPress(final KeyPressEvent event) {
			final char code = event.getCharCode();
			final int keyCode = event.getNativeEvent().getKeyCode();
			switch (keyCode) {
			case KeyCodes.KEY_ALT:
			case KeyCodes.KEY_BACKSPACE:
			case KeyCodes.KEY_CTRL:
			case KeyCodes.KEY_DELETE:
			case KeyCodes.KEY_DOWN:
			case KeyCodes.KEY_END:
			case KeyCodes.KEY_ENTER:
			case KeyCodes.KEY_ESCAPE:
			case KeyCodes.KEY_HOME:
			case KeyCodes.KEY_LEFT:
			case KeyCodes.KEY_PAGEDOWN:
			case KeyCodes.KEY_PAGEUP:
			case KeyCodes.KEY_RIGHT:
			case KeyCodes.KEY_SHIFT:
			case KeyCodes.KEY_TAB:
			case KeyCodes.KEY_UP:
				break;

			default:
				TextBox source = (TextBox) event.getSource();
				String text = source.getText();
				if (text.length() - mask.length() > 0 && source.getSelectionLength() == 0) {source.cancelKey(); return;} //reached the end of mask
				int pos = source.getCursorPos();
				int nextIndex = mask.indexOf('#', pos);
				if (pos == nextIndex) {nextIndex++;}
				String currentMask = "";
				if (nextIndex >= 0) {currentMask = mask.substring(pos, nextIndex);}
				else if (pos >= mask.length() - 1){source.cancelKey(); return;}
				else {currentMask = mask.substring(pos);}

				if(currentMask.equals("#")) {
					if(!Character.isDigit(code)) {source.cancelKey();}
				}
				else {

					if(currentMask.charAt(0) != code) {//allow code

						if (pos == text.length()) {source.setText(text + currentMask);} //at the end so append
						else {
							String newText="";
							if (source.getSelectionLength() > 0) {newText = text.substring(0, pos) + currentMask + text.substring(pos + source.getSelectionLength());}
							else {newText=text.substring(0, pos)+currentMask+text.substring(pos+1);}
							source.setText(newText);
							source.setCursorPos(pos + currentMask.length());
						}
						if(!Character.isDigit(code)) {source.cancelKey();}
						// TODO: extend for non-numeric mask items
					}
				}
			}
		}
	}	
}

/*
	 * Checks for invalid characters
	 * in email addresses

	//TODO:
//	Regular Expressions Constructs
//	A regular expression is a pattern of characters that describes a set of strings. You can use the java.util.regex package to find, display, or modify some or all of the occurrences of a pattern in an input sequence.
//	The simplest form of a regular expression is a literal string, such as "Java" or "programming." Regular expression matching also allows you to test whether a string fits into a specific syntactic form, such as an email address.
//	To develop regular expressions, ordinary and special characters are used:
//
//	\$ ^ . *
//	+ ? [' ']
//	\.
//
//	Any other character appearing in a regular expression is ordinary, unless a \ precedes it.
//	Special characters serve a special purpose. For instance, the . matches anything except a new line. A regular expression like s.n matches any three-character string that begins with s and ends with n, including sun and son.
//	There are many special characters used in regular expressions to find words at the beginning of lines, words that ignore case or are case-specific, and special characters that give a range, such as a-e, meaning any letter from a to e.
//	Regular expression usage using this new package is Perl-like, so if you are familiar with using regular expressions in Perl, you can use the same expression syntax in the Java programming language. If you're not familiar with regular expressions here are a few to get you started:
//	Construct Matches
//
//	Characters
//	x The character x
//	\\ The backslash character
//	\0n The character with octal value 0n (0 <= n <= 7)
//	\0nn The character with octal value 0nn (0 <= n <= 7)
//	\0mnn The character with octal value 0mnn (0 <= m <= 3, 0 <= n <= 7)
//	xhh The character with hexadecimal value 0xhh
//	uhhhh The character with hexadecimal value 0xhhhh
//	\t The tab character ('\u0009')
//	\n The newline (line feed) character ('\u000A')
//	\r The carriage-return character ('\u000D')
//	\f The form-feed character ('\u000C')
//	\a The alert (bell) character ('\u0007')
//	\e The escape character ('\u001B')
//	\cx The control character corresponding to x
//
//	Character Classes
//	[abc] a, b, or c (simple class)
//	[^abc] Any character except a, b, or c (negation)
//	[a-zA-Z] a through z or A through Z, inclusive (range)
//	[a-z-[bc]] a through z, except for b and c: [ad-z] (subtraction)
//	[a-z-[m-p]] a through z, except for m through p: [a-lq-z]
//	[a-z-[^def]] d, e, or f

//	Predefined Character Classes
//	. Any character (may or may not match line terminators)
//	\d A digit: [0-9]
//	\D A non-digit: [^0-9]
//	\s A whitespace character: [ \t\n\x0B\f\r]
//	\S A non-whitespace character: [^\s]
//	\w A word character: [a-zA-Z_0-9]
//	\W A non-word character: [^\w]

//	public static void EmailValidation () throws Exception {
//
//		String input = "@sun.com";
//		//Checks for email addresses starting with
//		//inappropriate symbols like dots or @ signs.
//		Pattern p = Pattern.compile("^\\.|^\\@");
//		Matcher m = p.matcher(input);
//		if (m.find())
//			System.err.println("Email addresses don't start" +
//			" with dots or @ signs.");
//		//Checks for email addresses that start with
//		//www. and prints a message if it does.
//		p = Pattern.compile("^www\\.");
//		m = p.matcher(input);
//		if (m.find()) {
//			System.out.println("Email addresses don't start" +
//			" with \"www.\", only web pages do.");
//		}
//		p = Pattern.compile("[^A-Za-z0-9\\.\\@_\\-~#]+");
//		m = p.matcher(input);
//		StringBuffer sb = new StringBuffer();
//		boolean result = m.find();
//		boolean deletedIllegalChars = false;
//
//		while(result) {
//			deletedIllegalChars = true;
//			m.appendReplacement(sb, );
//			result = m.find();
//		}
//
//		// Add the last segment of input to the new String
//		m.appendTail(sb);
//
//		input = sb.toString();
//
//		if (deletedIllegalChars) {
//			System.out.println("It contained incorrect characters" +
//			" , such as spaces or commas.");
//		}
//	}
 */
