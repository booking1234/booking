/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.RemoteRequest;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.text.TextRead;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mybookingpal.config.RazorConfig;

/** 
 * The Class ImageField displays an image and optional descriptive text.
 * When enabled, the field can upload images to the server.
 */
public class ImageField 
extends AbstractField<ArrayList<String>>
implements ChangeHandler {

	private final RemoteRequest<TextRead, Text> textRead = new RemoteRequest<TextRead, Text>() {
		public void execute(TextRead action) {super.send(action);}
		public void receive(Text text) {
			if (text == null || text.getNotes() == null || text.getNotes().isEmpty()){return;}
			else {navigator.setLabel(text.getNotes());}
		}
	};

	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private final VerticalPanel panel = new VerticalPanel();
	private final Image field = new Image();
	private final Navigator navigator = new Navigator();
	private Label label;
	private ArrayList<String> value; 	//current image URLs
	private String uploadValue; 		// upload Value
	private Text.Type format = Text.Type.ImageBlob;
	private int thumbnailHeight = Text.THUMBNAIL_PIXELS_VALUE;
	private int fullsizeHeight = Text.FULLSIZE_PIXELS_VALUE;
	
	/**
	 * Instantiates a new image field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public ImageField(HasComponents form, 
			short[] permission, 
			String label,
			int tab) {

		initialize(panel, form, permission, CSS.cbtImageField());

		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		if (label != null) {
			this.label = new Label(label);
			this.label.addStyleName(CSS.cbtImageFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}
		field.addStyleName(CSS.cbtImageFieldField());
		field.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (isEnabled()) {new UploadPopup().center();}
				else {new ImagePopup(field.getUrl(), navigator.getIndex()).center();}
			}
		});
		field.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {}
		});
		field.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				field.setUrl(HOSTS.rootUrl() + HasUrls.PICTURES_DIRECTORY + Text.DEFAULT_IMAGE + Text.FULLSIZE_JPG + "?value=" + new Date().getTime());
			}
		});
		panel.add(field);
		navigator.addChangeHandler(this);
		panel.add(navigator);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender) {
		return (sender == this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ChangeHandler#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent event) {
		if (navigator.sent(event)) {
			String url = value.get(navigator.getIndex());
			field.setUrl(url);
		}
	}

	/**
	 * Sets the ID of the image after it has been uploaded.
	 * This is not the name of the local image file.
	 *
	 * @param uploadValue the new ID of the uploaded image.
	 */
	public void setUploadValue(String uploadValue) {
		this.uploadValue = uploadValue;
	}

	/**
	 * Sets the height of the thumb nail image in pixels.
	 *
	 * @param thumbnailHeight the new height of the thumb nail image in pixels.
	 */
	public void setThumbnailHeight(int thumbnailHeight) {
		this.thumbnailHeight = thumbnailHeight;
	}

	/**
	 * Sets the height of the full size image in pixels.
	 *
	 * @param fullsizeHeight the new height of the full size image in pixels.
	 */
	public void setFullsizeHeight(int fullsizeHeight) {
		this.fullsizeHeight = fullsizeHeight;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed) {}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab) {}

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

	/**
	 * Gets the list of the images managed by this field.
	 *
	 * @return the list of the images managed by this field.
	 */
	public ArrayList<String> getValue() {
		return value;
	}

	/**
	 * Adds a new image to be managed by this field.
	 *
	 * @param value the ID of a new image to be managed by this field.
	 */
	public void setValue(String value) {
		ArrayList<String> values = new ArrayList<String> ();
		values.add(value);
		setValue(values);
	}
	
	/**
	 * Adds a list of images to be managed by this field.
	 *
	 * @param value the new list of images to be managed by this field.
	 */
	public void setValue(ArrayList<String> value) {
		this.value = value;
		if (value == null || value.isEmpty()) {
			field.setUrl(BUNDLE.blankImage().getURL());
			navigator.setVisible(false);
		}
		else {
			field.setUrl(value.get(0));
			navigator.setIndex(0);
			navigator.setEnd(value.size() - 1);
			navigator.setVisible(value.size() > 1);
		}
		super.setChanged();
	}

	/*
	 * Gets the URL of a full size image with the specified ID.
	 * 
	 * @param value the specified image ID.
	 * @return the URL of a full size image.
	 */
//	private String getFullsizeUrl(String value) {
//		return HasUrls.ROOT_URL + HasUrls.PICTURES_DIRECTORY + value;
//	}
	
	/* Inner Class ImagePopup to display a full size image. */
	private class ImagePopup extends PopupPanel {

		public ImagePopup(String url, int index) {
			super(true);
			final VerticalPanel panel = new VerticalPanel();
			setWidget(panel);
			setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
			panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			
			final Label closeButton = new Label();
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ImagePopup.this.hide();
				}
			});
			closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
			panel.add(closeButton);

			final Image image = new Image();
			image.setUrl(url);
			panel.add(image);
			final Navigator navigator = new Navigator();
			panel.add(navigator);
			navigator.setIndex(index);
			navigator.setEnd(value.size() - 1);
			navigator.setVisible(value.size() > 1);
			navigator.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					String url = value.get(navigator.getIndex());
					image.setUrl(url);
				}
			});
		}
	}
	
	/* Inner Class UploadPopup to upload an image file. */
	@SuppressWarnings("deprecation")
	private class UploadPopup extends PopupPanel {

		public UploadPopup() {
			super(true);
			final FlowPanel form = new FlowPanel();
			setWidget(form);
			setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
			final Label label = new Label(CONSTANTS.uploadFile());
			label.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
			form.add(label);
			
			final Label closeButton = new Label();
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					UploadPopup.this.hide();
				}
			});
			closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
			form.add(closeButton);

			final TextBox typeTextBox = new TextBox();
			final TextBox thumbnailTextBox = new TextBox();
			final TextBox fullsizeTextBox = new TextBox();
			final FileUpload uploadFile = new FileUpload();
			final FormPanel uploadPanel = new FormPanel();
			
			Button uploadButton = new Button(CONSTANTS.allUpload(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					uploadPanel.submit();
				}
			});

			typeTextBox.setName(Text.FILE_TYPE);
			typeTextBox.setText(format.name());
			typeTextBox.setVisible(false);
			form.add(typeTextBox);

			thumbnailTextBox.setName(Text.THUMBNAIL_PIXELS);
			thumbnailTextBox.setText(String.valueOf(thumbnailHeight));
			thumbnailTextBox.setVisible(false);
			form.add(thumbnailTextBox);

			fullsizeTextBox.setName(Text.FULLSIZE_PIXELS);
			fullsizeTextBox.setText(String.valueOf(fullsizeHeight));
			fullsizeTextBox.setVisible(false);
			form.add(fullsizeTextBox);

			final HorizontalPanel buttons = new HorizontalPanel();
			buttons.addStyleName(AbstractField.CSS.cbtAbstractCommand());
			uploadFile.setStyleName(CSS.cbtImageGallerySetupUpload());
			uploadFile.setTitle(CONSTANTS.uploadFile());
			buttons.add(uploadFile);

//			uploadButton.addStyleName(CSS.cbtImageGallerySetupButton());
			uploadButton.setTitle(CONSTANTS.uploadsaveHelp());
			buttons.add(uploadButton);
			form.add(buttons);

			uploadPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
			uploadPanel.setMethod(FormPanel.METHOD_POST);
			uploadPanel.setAction(GWT.getModuleBaseURL() + "UploadFileService"); // UploadFileServlet
			uploadPanel.setWidget(form);
			uploadPanel.addFormHandler(new FormHandler() {

				@Override
				public void onSubmit(FormSubmitEvent event) { 
					if (thumbnailTextBox.getText().isEmpty()
							|| fullsizeTextBox.getText().isEmpty()
							|| uploadValue == null
							|| uploadValue.isEmpty()
							|| Text.notImageFile(uploadFile.getFilename())
					) {
						addMessage(Level.ERROR, CONSTANTS.errLoadFile() + " " + uploadFile.getFilename(), uploadFile);
						event.setCancelled(true);
					} 
					else {uploadFile.setName(HasUrls.PICTURES_DIRECTORY + uploadValue + "." + getType(uploadFile.getFilename()));}
				}

				@Override
				public void onSubmitComplete(FormSubmitCompleteEvent event) {
					String message = Text.stripHTML(event.getResults());
					if (message.startsWith("202")) {addMessage(Level.VERBOSE, CONSTANTS.uploadComplete(), uploadFile);}
					else {addMessage(Level.ERROR, CONSTANTS.uploadError() + message.substring(0,3), uploadFile);}
					hide();
				}
			});
			add(uploadPanel);
		}
		
		/*
		 * Gets the image type from its file extension.
		 * 
		 * @param url the URL of the image.
		 * @return the image type.
		 */
		private String getType(String url) {
			String[] args = url.split("\\.");
			return (args.length == 2) ? args[1].toLowerCase() : null;
		}
	}
}
