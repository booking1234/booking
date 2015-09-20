/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.NameIdRequest;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Text.Type;
import net.cbtltd.shared.text.TextRead;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class AbstractTextField has code common to LabelField, RichTextField and TextAreaField.
 * It manages the getting and setting of text objects via the TextService.
 * It translates text from the primary language into any language listed in the type list box.
 * It plays an audio version of the text in the target language, if available.
 *
 * @param <T> the generic text type managed by the field.
 */
public abstract class AbstractTextField<T extends Widget>
extends AbstractField<String> {

	private GuardedRequest<Text> textRead = getTextRead();
	
	/** 
	 * Gets a text read command to send an asynchronous request to the TextService with the text ID and language code.
	 * The response sets the text field value if in the requested language.
	 * If not, the TranslationService is used to translate the text into the requested language.
	 * This is done for the label as a whole and line by line for the text value (notes).
	 * If the text ID is new or if the value has changed, a new audio file is created on the server.
	 * 
	 * @return a text read command.
	 */
	private final GuardedRequest<Text> getTextRead() {
		
		final GuardedRequest<Text> textReadRequest = new GuardedRequest<Text>() {
			protected boolean error () {return text == null || text.noId();}
			protected void send() {super.send(getValue(new TextRead()));}
			public void receive(Text response) {setValue(response);}
		};
		return textReadRequest;
	}

	private Text getValue(Text action) {
		action.setId(text.getId());
		action.setType(text.getType());
		action.setLanguage(getLanguage());
		action.setState(Text.State.Created.name());
		if (label != null) {action.setName(label.getText());}
		return action;
	}

	private void setValue(Text response) {
		if (response != null) {
			if (label != null && !response.hasLanguage(text.getLanguage())) {label.setText(response.getName());}
			this.text = response;
			setValue(response.getNotes());
			setLanguage(response.getLanguage());
		}
	}

	/**
	 * Gets the current text object.
	 *
	 * @return the current text object if it exists, otherwise create a new text object.
	 */
	public Text getText() {
		if (text != null) {
			text.setLanguage(getLanguage());
			text.setNotes(getValue());
		}
		return text;
	}

	/**
	 * Sets the current text object and refresh it from the TextService.
	 *
	 * @param text object must have at least its ID and language to retrieve the target object.
	 */
	public void setText(Text text) {
		this.text = text;
		textRead.execute(true);
	}

	/* Request sent to a NameId service to refresh the type (language) list box. */
	private NameIdRequest typeRequest;
	
	private Label label;
	
	/** The panel which contains the field elements. */
	protected final VerticalPanel panel = new VerticalPanel();
	
	/** The audio control to play and control the volume of the audio version of the text contents. */
	private AudioControl audio;
	
	/** The type is a list box of the available languages. It is not displayed if only one language is available. */
	protected ListBox language;
	
	/** The field value, which is a Label, RichText or TextArea widget. */
	protected T field;
	
	/** The text DTO communicates textual data to and from the TextService. */
	private Text text;
	
	/** The default language is the primary language code for the text. */
	private String defaultLanguage = AbstractRoot.getLanguage();
	
	/** The maxlength is the maximum length of the text value (notes). */
	protected int maxlength = Integer.MAX_VALUE;
	
	/** The type index indicates the currently selected type (language). */
	protected int languageIndex = 0;
	
	/**
	 * The fired field is the hash code of the most recent text value (notes).
	 * It determines if the field value has changed to update the TextService.
	 */
	protected int fired = -1;

	/**
	 * Instantiates a new abstract text field.
	 *
	 * @param popup the widget that contains the field elements
	 * @param form the form or other HasComponents element that contains the widget.
	 * @param permission to control the visibility and accessibility of the field.
	 * @param label the text displayed in the header of the field.
	 * @param languages the list of name id pairs to populate the type (language) list box.
	 * @param tab number of the field.
	 */
	protected AbstractTextField (
			T field,
			HasComponents form,
			short[] permission,
			String label,
			ArrayList<NameId> languages,
			int tab) {
		this(field, form, permission, label, tab);
		if (language != null) {setLanguages(languages);}
	}
	
	/**
	 * Instantiates a new abstract text field.
	 *
	 * @param popup is the widget that contains the field elements
	 * @param form is the form or other HasComponents element that contains the widget.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label text displayed in the header of the field.
	 * @param action to populate the type (language) list box using a NameId service.
	 * @param tab number of the field.
	 */
	protected AbstractTextField (
			T field,
			HasComponents form,
			short[] permission,
			String label,
			NameIdAction action,
			int tab) {
		this(field, form, permission, label, tab);
		if (action != null) {
			typeRequest = new NameIdRequest(action) {
				public void receive(Table<NameId> nameids) {
					setLanguages(nameids.getValue());
				}
			};
		}
	}

	
	/*
	 * Instantiates a new abstract text field.
	 *
	 * @param panel is the widget that contains the field elements
	 * @param form is the form or other HasComponents element that contains the widget.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label text displayed in the header of the field.
	 * @param action to populate the type (language) list box using a NameId service.
	 * @param tab number of the field.
	 */
	private AbstractTextField (
			T field,
			HasComponents form,
			short[] permission,
			String label,
			int tab) {

		initialize(panel, form, permission, CSS.cbtAbstractText());
		this.field = field;

		if (label != null) {
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtAbstractTextLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});

			audio = new AudioControl();
			audio.addStyleName(CSS.cbtAbstractTextAudio());
			
			language  = new ListBox();
			language.addStyleName(CSS.cbtAbstractTextType());
			language.setTitle(CONSTANTS.helpTextType());
			language.setVisible(false);
			language.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {textRead.execute();}
			});

			// It is not safe to make calls into the translation API until the run() method is invoked. 
			// Use LanguageUtils.loadTransliteration() for the transliteration API.
//			LanguageUtils.loadTranslation(new Runnable() {
//				public void run() {}
//			});

			FlowPanel title = new FlowPanel();
			title.add(this.label);
			title.add(this.language);
			title.add(this.audio);
			panel.add(title);
		}
		this.field.addStyleName(CSS.cbtLabelFieldField());
		panel.add(field);
	}

	/*
	 * Sets the list of types (languages).
	 * 
	 * @param list of language name id pairs.
	 */
	private void setLanguages(ArrayList<NameId> nameids) {
		language.clear();
		if (nameids == null || nameids.isEmpty()) {language.setVisible(false);}
		else {
			for (NameId nameid : nameids) {language.addItem(nameid.getName(), nameid.getId());}
			language.setSelectedIndex(getTypeIndex(defaultLanguage));
			language.setVisible(language.getItemCount() > 1);
		}
		if (language.getItemCount() > 0) {fireChange(language);}
	}
	
	/**
	 * Reset the field to its default value.
	 */
	@Override
	public void onReset() {
		if (language != null && language.getItemCount() > 0) {language.setSelectedIndex(0);}
		super.onReset();
	}

	/**
	 * Refresh field types (languages).
	 */
	@Override
	public void onRefresh() {
		if (typeRequest != null) {typeRequest.execute();}
	}

	/*
	 * @return the index of the selected type (language).
	 * @param type
	 */
	private int getTypeIndex(String type) {
		if (this.language == null || type == null) {return 0;}
		for (int i = 0; i < this.language.getItemCount(); i++ ){
			if (this.language.getValue(i).equalsIgnoreCase(type)){return i;}
		}
		return 0;
	}

	/**
	 * Add a new CSS style to format the field label.
	 * This may be called repeatedly to apply cascading styles.
	 *
	 * @param style to format the label.
	 */
	public void addLabelStyle(String style) {
		label.addStyleDependentName(style);
	}

	/**
	 * Set the CSS style to format the field label.
	 *
	 * @param style to override format of the label.
	 */
	public void setLabelStyle(String style) {
		label.addStyleName(style);
	}

	public boolean notFormat(Type format) {
		return text == null || format == null || this.text.notType(format);
	}

	public boolean hasFormat(Type format) {
		return !notFormat(format);
	}

	/**
	 * Sets the maximum length of field value.
	 *
	 * @param length if the new maximum length.
	 */
	public void setMaxLength(int length) {
		this.maxlength = length;
	}

	/**
	 * Set the CSS style to format the type (language) list box.
	 *
	 * @param style the new type style
	 */
	public void setTypeStyle(String style) {
		if (language != null) {language.addStyleName(style);}
	}

	/**
	 * Sets that values for all organizations are to be included in the list.
	 * If this is not set the list is populated with values only for the current organization.
	 *
	 * @param allOrganizations is true if the list contains values for all organizations.
	 */
	public void setAllOrganizations(boolean allOrganizations) {
		if (typeRequest != null) {typeRequest.setAllOrganizations(allOrganizations);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
		//TODO:		setFieldStyle(CSS.cbtLabelFieldSecure());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){
		//field.setTabIndex(tab);
	}

	/**
	 * Sets the default type (language).
	 *
	 * @param defaultType the new default language code, which must be in the types list.
	 */
	public void setDefaultType(String defaultType) {
		this.defaultLanguage = defaultType;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){}

	/**
	 * Sets the label text.
	 *
	 * @param text of the label.
	 */
	public void setLabel(String text) {
		if (label != null) {
			label.setText(text);
			textRead = getTextRead();
		}
	}

	/**
	 * Sets if the list of allowed types (languages) is visible.
	 *
	 * @param true, if the list of allowed types (languages) is visible.
	 */
	public void setTypeVisible(boolean visible) {
		if (language != null) {language.setVisible(visible);}
	}
	
	/**
	 *  Gets the currently selected type (language) 
	 *  
	 *  @return the currently selected type (language) 
	 */
	public String getLanguage() {
		if (language == null || language.getItemCount() == 0) {return defaultLanguage;}
		return language.getValue(language.getSelectedIndex());
	}

	/**
	 * Sets the selected type (language).
	 *
	 * @param language the new language code.
	 */
	public void setLanguage(String language) {
		if (this.language == null || language == null) {return;}
		this.language.setSelectedIndex(getTypeIndex(language));
	}

	/**
	 * Sets the selected type (language) and loads the translated text
	 *
	 * @param language the new language code.
	 */
	public void resetLanguage(String language) {
		if (getLanguage() == null || getLanguage().equalsIgnoreCase(language)) {return;}
		setLanguage(language);
		textRead.execute();
	}

	/**
	 * @return true, if the field has a default value.
	 */
	public boolean hasDefaultValue() {
		return getValue().equals(defaultValue);
	}

	/**
	 * @param value of the field.
	 * @return true, if the field has a value.
	 */
	public boolean hasValue(String value) {
		return getValue().equals(value);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue(){
		return (getValue() == null || getValue().isEmpty());
	}

	/**
	 * @param sender is the widget that sent a type change event.
	 * @return true, if this field sent the type change event.
	 */
//	public boolean typeIs(Widget sender){
//		return (sender == language);
//	}
	public boolean languageSent(ChangeEvent change) {
		return (change.getSource() == language);
	}
}