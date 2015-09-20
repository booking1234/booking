/* 
* @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.RemoteRequest;
import net.cbtltd.client.panel.LoadingPopup;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.text.TextDelete;
import net.cbtltd.shared.text.TextRead;
import net.cbtltd.shared.image.ImageDelete;
import net.cbtltd.shared.image.ImageTextRead;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class ImageGallery is to display a gallery of thumbnail images.
 * If enabled the class can add and upload images to the server, edit image text, and delete images.
 * If not enabled the class can display a full size image when a thumbnail is clicked.
 */
public class ImageGallery 
extends AbstractField<ArrayList<String>>
implements ClickHandler {

	/* Guarded request to retrieve image text from the database */
	private final RemoteRequest<ImageTextRead, Text> textRead = new RemoteRequest<ImageTextRead, Text>() {
		public void execute(ImageTextRead action) {super.send(action);}
		public void receive(Text text) {
			if (isEnabled()) {setupPanel.setText(text);} 
			else {viewPanel.setText(text);}
		}
	};

	private final ScrollPanel scroll = new ScrollPanel();
	private final FlowPanel field = new FlowPanel();
	private ViewPanel viewPanel;
	private SetupPanel setupPanel;
	private Label popupLabel;
	private Label setupLabel;
	private String root;
	private int thumbnailHeight = Text.THUMBNAIL_PIXELS_VALUE;
	private int fullsizeHeight = Text.FULLSIZE_PIXELS_VALUE;
	private FlowPanel emptyValue = new FlowPanel();
	
	private String rootURL;
	private String lastImageUrl = "";
	private String lastImageName = "";

	/**
	 * Instantiates a new image gallery.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param popupLabel the popup label
	 * @param tab the tab
	 */
	public ImageGallery(HasComponents form, 
			short[] permission, 
			String popupLabel,
			int tab) {
		this(form, permission, popupLabel, null, null, tab);
	}

	/**
	 * Instantiates a new image gallery.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param popupLabel the popup label
	 * @param setupLabel the setup label
	 * @param value the value
	 * @param tab the tab
	 */
	public ImageGallery(HasComponents form, 
			short[] permission, 
			String popupLabel,
			String setupLabel, 
			String value, 
			int tab) {

		initialize(scroll, form, permission, CSS.cbtImageGallery());
		VerticalPanel panel = new VerticalPanel();
		scroll.add(panel);

		if (setupLabel != null) {
			this.setupLabel = new Label(setupLabel);
			this.setupLabel.addClickHandler(this);
			this.setupLabel.setVisible(isEnabled());
			this.setupLabel.addStyleName(CSS.cbtImageGalleryLabelSetup());
			panel.add(this.setupLabel);
		}

		if (popupLabel != null) {
			this.popupLabel = new Label(popupLabel);
			this.popupLabel.addClickHandler(this);
			this.popupLabel.setVisible(!isEnabled());
			this.popupLabel.addStyleName(CSS.cbtImageGalleryLabelPopup());
			panel.add(this.popupLabel);
		}

		field.addStyleName(CSS.cbtImageGalleryField());
		field.sinkEvents(Event.MOUSEEVENTS);
		panel.add(field);
		
		emptyValue.addStyleName(CSS.cbtImageGalleryEmpty());
		emptyValue.setVisible(false);
		panel.add(emptyValue);

		setValue(value);
		setTabIndex(tab);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (isEnabled()) {
			if (root == null) {
				addMessage(Level.ERROR, CONSTANTS.errImageNoRoot(), setupLabel);
				return;
			}
			if (setupPanel == null) {setupPanel = new SetupPanel();}
			setupPanel.setPopupPosition(getAbsoluteLeft() + 100, getAbsoluteTop());
			setupPanel.show(sender);
		} else {
			if (viewPanel == null) {viewPanel = new ViewPanel();}
			viewPanel.setPopupPosition(getAbsoluteLeft() + 200,	getAbsoluteTop() + 10);
			viewPanel.show(sender);
		}
		if (sender instanceof Image) {textRead.execute(new ImageTextRead(sender.getTitle(), AbstractRoot.getLanguage()));}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onReset()
	 */
	@Override
	public void onReset() {
		root = null;
		super.onReset();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#fireChange()
	 */
	public void fireChange() {
		fireChange(this);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onRefresh()
	 */
	@Override
	public void onRefresh() {
		;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (popupLabel != null) {popupLabel.setVisible(!isEnabled());}
		if (setupLabel != null) {setupLabel.setVisible(isEnabled());}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#isEnabled()
	 */
	public boolean isEnabled() {
		return super.isEnabled() && setupLabel != null;
	}

	/*
	 * Sets if the gallery is empty.
	 * 
	 * @param empty is true if the gallery is empty 
	 */
	private void setEmpty(boolean empty) {
		if (emptyValue.getWidgetCount() > 0) {
			field.setVisible(!empty);
			emptyValue.setVisible(empty);
		}
	}
	
	/**
	 * Sets the widget to be displayed if the gallery is empty.
	 *
	 * @param content the empty widget to be displayed.
	 */
	public void setEmptyValue(Widget content) {
		emptyValue.add(content);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab) {}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed) {}

	/**
	 * Sets the CSS style of the list.
	 *
	 * @param style the CSS style of the list.
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the pop up label.
	 *
	 * @param style the CSS style of the pop up label.
	 */
	public void changePopupLabelStyle(String style) {
		popupLabel.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the setup label.
	 *
	 * @param style the CSS style of the setup label.
	 */
	public void changeSetupLabelStyle(String style) {
		setupLabel.addStyleName(style);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
	}

	/**
	 * Gets the root ID of the gallery.
	 *
	 * @return the root ID of the gallery.
	 */
//	public String getRoot() {
//		return root;
//	}

	/**
	 * Sets the root ID of the gallery.
	 *
	 * @param entitytype the type of entity to which the gallery refers.
	 * @param entityid the ID of the entity to which the gallery refers.
	 */
	public void setRoot(String entitytype, String entityid) {
		if (entitytype == null || entitytype.isEmpty() || entityid == null
				|| entityid.isEmpty()) {
			root = null;
		} else {root = entitytype + entityid;}
	}

	/**
	 * Sets the height of the specified image.
	 * 
	 * @param image the specified image.
	 * @param height the of image in pixels.
	 */
	private void setImageSize(Image image, int height) {
		if (image == null || image.getHeight() <= 0) {return;}
		image.setPixelSize(height * image.getWidth() / image.getHeight(), height);
	}

	/**
	 * Adds the specified image to the gallery.
	 * 
	 * @param value the ID of the image to be added.
	 */
	private Image addImage(String value) {
//		if (value == null) {return null;}
//		final Image image = new Image();
//		String title = Text.getS3ImageFilename(value);
////		Window.alert("Image title: " + title);
//		image.setTitle(title);
////		image.setUrl(HasUrls.ROOT_URL + HasUrls.PICTURES_DIRECTORY + Text.trimExtension(value) + Text.THUMBNAIL_JPG + "?value=" + new Date().getTime());
//		image.setUrl(value + "?value=" + new Date().getTime());
//		//TODO: to force browser refresh image.setUrl(AbstractRoot.ROOT_URL + Text.PICTURES_DIRECTORY + value + Text.THUMBNAIL_JPG);
//		//https://groups.google.com/group/google-web-toolkit/browse_thread/thread/be9f1da56b5b1c18?hl=en
//		image.setStyleName(CSS.cbtImageGalleryImage());
//		setImageSize(image, thumbnailHeight);
//		image.setVisible(true);
//		image.addClickHandler(this);
//		image.addLoadHandler(new LoadHandler() {
//			@Override
//			public void onLoad(LoadEvent event) {}
//		});
//		image.addErrorHandler(new ErrorHandler() {
//			@Override
//			public void onError(ErrorEvent event) {
//				//addMessage(Level.ERROR, CONSTANTS.errLoadImage() + ((Widget) event.getSource()).getTitle(), setupLabel);
//				image.setUrl(Text.DEFAULT_IMAGE_URL);
//			}
//		});
//		field.add(image);
//		return image;
		return this.addImage(value, true);
	}
	
	private Image addImage(String value, boolean visible) {
		if (value == null) {return null;}
		final Image image = new Image();
		String title = Text.getS3ImageFilename(value);
		image.setTitle(title);
		image.setUrl(value + "?value=" + new Date().getTime());
		//TODO: to force browser refresh image.setUrl(AbstractRoot.ROOT_URL + Text.PICTURES_DIRECTORY + value + Text.THUMBNAIL_JPG);
		//https://groups.google.com/group/google-web-toolkit/browse_thread/thread/be9f1da56b5b1c18?hl=en
		image.setStyleName(CSS.cbtImageGalleryImage());
		setImageSize(image, thumbnailHeight);
		image.setVisible(visible);
		image.addClickHandler(this);
		image.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {}
		});
		image.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				//addMessage(Level.ERROR, CONSTANTS.errLoadImage() + ((Widget) event.getSource()).getTitle(), setupLabel);
				image.setUrl(Text.DEFAULT_IMAGE_URL);
			}
		});
		field.add(image);
		return image;
	}
	
	

	/**
	 * Gets the index of the specified value in the gallery.
	 * 
	 * @param value the specified value.
	 * @return index of value in the gallery.
	 */
	private int indexOf(String value) {
		for (int i = 0; i < field.getWidgetCount(); i++) {
			if (value.equalsIgnoreCase(field.getWidget(i).getTitle())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes the specified image from the gallery.
	 * 
	 * @param value of the image to be removed.
	 */
	public void removeImage(String value) {
		field.remove(indexOf(value));
	}

	/**
	 * Gets the list of images in the gallery.
	 * 
	 * @return the list of images in the gallery.
	 */
	public ArrayList<String> getValue() {
		ArrayList<String> value = new ArrayList<String>();
		for (int i = 0; i < field.getWidgetCount(); i++) {
			//String id = field.getWidget(i).getTitle(); // + Text.IMAGE_JPG;
			value.add(field.getWidget(i).getTitle());
		}
//		Window.alert("ImageGallery getValue " + value);
		return value;
	}

	/**
	 * Set the base value of the gallery of images.
	 * 
	 * @param value the base value of the gallery of images.
	 */
	public void setValue(String value) {
		if (value != null) {
			//value = Text.trimExtension(value);
			String[] args = value.split("-");
			if (args.length >= 1) {root = args[0];}
			addImage(value);
		}
		super.setChanged();
	}

	public String getRootURL() {
		return rootURL;		
	}

	public void setRootURL(String rootURL) {
		this.rootURL = rootURL;
	}

	/**
	 * Sets the list of images in the gallery.
	 * 
	 * @param values of the images in the gallery.
	 */
	public void setValue(ArrayList<String> values) {
		field.clear();
		if (values == null || values.isEmpty()) {
			setEmpty(true);
			this.lastImageName = "";
			this.lastImageUrl = "";
		}
		else {
			setEmpty(false);
			Collections.sort(values);
			this.lastImageName = Text.getS3ImageFilename(values.get(values.size()-1));
			this.lastImageUrl = values.get(values.size()-1); 
			for (String value : values) {
//				Window.alert("ImageGallery setValue " + value);
				int index = value.indexOf(Text.THUMBNAIL_INFIX);
				if (index > 0){
					addImage(value); //Text.trimExtension(value.getId()));
				}
			}
		}
		super.setChanged();
	}

	/**
	 * Checks if the gallery has no images.
	 * 
	 * @return true, if the gallery has no images.
	 */
	public boolean noValue() {
		return field == null || field.getWidgetCount() == 0;
	}
	
	/**
	 * Checks if this is the specified widget.
	 * 
	 * @param widget the specified widget.
	 * @return true, if this is the specified widget.
	 */
	@Override
	public boolean is(Widget widget) {
		return (widget == this);
	}

	/* The Inner Class SetupPanel is to add images to the gallery and upload them to the server. */
	private class SetupPanel extends PopupPanel {

		private final GuardedRequest<Text> textUpdate = new GuardedRequest<Text> () {
			protected boolean error() {
				return (
						image.getTitle() == null
						|| image.getTitle().isEmpty()
						|| AbstractRoot.noLanguage()
				);
			}
			protected void send() {super.send(new Text(image.getTitle(), image.getTitle(), Text.Type.Image, new Date(), descriptionTextArea.getText(), AbstractRoot.getLanguage()));}
			protected void receive(Text text) {
				hide();
				fireChange();
			}
		};
		
		private final GuardedRequest<net.cbtltd.shared.Image> imageDelete = new GuardedRequest<net.cbtltd.shared.Image> () {
			protected boolean error() {
				return (
						image.getTitle() == null 
						|| image.getTitle().isEmpty() 
						|| AbstractRoot.noLanguage()
				);
			}
			protected void send() {super.send(new ImageDelete(image.getTitle(), AbstractRoot.getLanguage()));}
			protected void receive(net.cbtltd.shared.Image image) {
				removeImage(image.getName());
				hide();
				fireChange();
			}
		};

		Image image;
		final Label uploadcreateLabel = new Label(CONSTANTS.uploadcreateLabel());
		final Label uploadchangeLabel = new Label(CONSTANTS.uploadchangeLabel());
		final TextBox typeTextBox = new TextBox();
		final Label thumbnailLabel = new Label(CONSTANTS.uploadThumbnail());
		final TextBox thumbnailTextBox = new TextBox();
		final Label fullsizeLabel = new Label(CONSTANTS.uploadFullsize());
		final TextBox fullsizeTextBox = new TextBox();
		final Label descriptionLabel = new Label(CONSTANTS.uploadDescription());
		final TextArea descriptionTextArea = new TextArea();
//		final Label uploadLabel = new Label(CONSTANTS.uploadFile());
		final FileUpload uploadFile = new FileUpload();
		final FormPanel uploadPanel = new FormPanel();

		Button uploadButton = new Button(CONSTANTS.allUpload(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				uploadPanel.submit();
			}
		});

		Button saveButton = new Button(CONSTANTS.allSave(),
				new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setImageSize(image, Integer.valueOf(thumbnailTextBox.getText()));
				textUpdate.execute();
			}
		});

		Button deleteButton = new Button(CONSTANTS.allDelete(),
				new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				imageDelete.execute();
			}
		});

		@SuppressWarnings("deprecation")
		
		public SetupPanel() {
			super(true);
			setStylePrimaryName(CSS.cbtAbstractPopup());
			final VerticalPanel form = new VerticalPanel();
			uploadcreateLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
			form.add(uploadcreateLabel);
			uploadchangeLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
			form.add(uploadchangeLabel);
			
			final Label closeButton = new Label();
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					SetupPanel.this.hide();
				}
			});
			closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
			form.add(closeButton);
			
			setWidget(form);
			
			typeTextBox.setName(Text.FILE_TYPE);
			typeTextBox.setText(Text.Type.Image.name());
			typeTextBox.setVisible(false);
			form.add(typeTextBox);

			thumbnailLabel.addStyleName(CSS.cbtImageGallerySetupLabel());
			form.add(thumbnailLabel);
			thumbnailTextBox.setName(Text.THUMBNAIL_PIXELS);
			thumbnailTextBox.setText(String.valueOf(thumbnailHeight));
//			thumbnailTextBox.setEnabled(enabled);
			thumbnailTextBox.addStyleName(CSS.cbtImageGallerySetupField());
			thumbnailTextBox.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					thumbnailHeight = Integer.valueOf(thumbnailTextBox.getText());
				}
			});
			form.add(thumbnailTextBox);

			fullsizeLabel.addStyleName(CSS.cbtImageGallerySetupLabel());
			form.add(fullsizeLabel);
			fullsizeTextBox.setName(Text.FULLSIZE_PIXELS);
			fullsizeTextBox.setText(String.valueOf(fullsizeHeight));
			fullsizeTextBox.addStyleName(CSS.cbtImageGallerySetupField());
			fullsizeTextBox.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					fullsizeHeight = Integer.valueOf(fullsizeTextBox.getText());
				}
			});
			form.add(fullsizeTextBox);

			descriptionLabel.addStyleName(CSS.cbtImageGallerySetupLabel());
			form.add(descriptionLabel);
			descriptionTextArea.setName(Text.FILE_NOTES);
			descriptionTextArea.addStyleName(CSS.cbtImageGallerySetupDescription());
			form.add(descriptionTextArea);

			final HorizontalPanel buttons = new HorizontalPanel();
			buttons.addStyleName(AbstractField.CSS.cbtAbstractCommand());
			uploadFile.setStyleName(CSS.cbtImageGallerySetupUpload());
			uploadFile.setTitle(CONSTANTS.uploadFile());
			buttons.add(uploadFile);

			uploadButton.setTitle(CONSTANTS.uploadsaveHelp());
			buttons.add(uploadButton);
			buttons.add(saveButton);
			buttons.add(deleteButton);
			form.add(buttons);

			uploadPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
			uploadPanel.setMethod(FormPanel.METHOD_POST);
			uploadPanel.setAction(GWT.getModuleBaseURL() + "UploadFileService"); // UploadFileServlet  https://www.mybookingpal.com/razor/razor
			uploadPanel.setWidget(form);
			uploadPanel.addFormHandler(new FormHandler() {

				@Override
				public void onSubmit(FormSubmitEvent event) {
					if (thumbnailTextBox.getText().isEmpty()
							|| fullsizeTextBox.getText().isEmpty()
							|| Text.notImageFile(uploadFile.getFilename())
					) {
						if(Text.notImageFile(uploadFile.getFilename())) {
							addMessage(Level.ERROR, CONSTANTS.errLoadImageExtension() + " " + uploadFile.getFilename(), uploadFile);
						}
						else {
							addMessage(Level.ERROR, CONSTANTS.errLoadFile() + " " + uploadFile.getFilename(), uploadFile);
						}
						event.setCancelled(true);
					} 
					else {
						if (field.getWidgetCount() == 0){
							setEmpty(false);
						}						
						image = addImage(newValue(uploadFile.getFilename()), false);
//						uploadFile.setName(Text.trimExtension(image.getTitle()) + "." + Text.getExtension(uploadFile.getFilename()));  // removed line...will use the uploaded file name
						uploadFile.setName(image.getTitle());  // removed line...will use the uploaded file name
//						addMessage(Level.VERBOSE, CONSTANTS.uploadStarted() + uploadFile.getFilename() + " -> " + HasUrls.ROOT_URL + uploadFile.getName(), uploadFile);
						LoadingPopup.getInstance().center();
					}
				}

				@Override
				public void onSubmitComplete(FormSubmitCompleteEvent event) {
					LoadingPopup.getInstance().hide();
					String message = Text.stripHTML(event.getResults());
					if (message.startsWith("202")) {
						addMessage(Level.VERBOSE, CONSTANTS.uploadComplete(), uploadFile);
						Image lastImage = (Image)field.getWidget(field.getWidgetCount() - 1);
						String lastURL = getLastImageURL(uploadFile.getFilename());						
						lastImage.setUrl(lastURL + "?value=" + new Date().getTime());
						lastImage.setVisible(true);
					}
					else {addMessage(Level.ERROR, CONSTANTS.uploadError() + message.substring(0,3), uploadFile);}
					//image = addImage(newValue(uploadFile.getFilename()), false);
					hide();
				}
			});
			add(uploadPanel);
		}

		/**
		 * Sets the specified text record.
		 * 
		 * @param text the specified text record.
		 */
		public void setText(Text text) {
			if (text == null) {descriptionTextArea.setText(Model.BLANK);}
			else {descriptionTextArea.setText(text.getNotes());}
		}

		/**
		 * Gets the next value in the series for this gallery.
		 * 
		 * @return the next value in the series for this gallery.
		 */
		private String newValue(String filename) {
			if (field.getWidgetCount() == 0 && lastImageName.isEmpty()) {
				lastImageUrl = rootURL + root + "-000" + Text.THUMBNAIL_INFIX + Text.getExtension(filename);
				lastImageName = root + "-000";				
				return lastImageUrl;
			}
			
			/*String last = Model.BLANK;
			String lastURL = Model.BLANK;
			for (int i = 0; i < field.getWidgetCount(); i++) {
				String value = field.getWidget(i).getTitle();
				String url = ((Image)field.getWidget(i)).getUrl();
				if (value.compareTo(last) > 0) {
					last = value;
					lastURL = url;
				}

			}
//			Window.alert("last image name/URL: " + lastURL);
			String[] args = last.split("-");

			}*/
//			Window.alert("last image name/URL: " + lastURL);
			
			String[] args = lastImageName.split("-");

			if (args.length != 2) {return null;}
			String countPostfix = args[1].split("\\.")[0];
			if(countPostfix.contains("Thumb")) {
				countPostfix = countPostfix.substring(0, countPostfix.indexOf("Thumb"));
			}
			
			double count = Double.valueOf(countPostfix);
			String url =  rootURL + args[0] + "-"
			+ NumberFormat.getFormat("000").format(count + 1)
			+ Text.THUMBNAIL_INFIX
			+ Text.getExtension(filename);
			lastImageName = args[0] + "-" + NumberFormat.getFormat("000").format(count + 1);
			lastImageUrl = url;
//			Window.alert("Upload image name/URL: " + url);
			return url;
		}
		
		/**
		 * Gets the next value in the series for this gallery.
		 * 
		 * @return the next value in the series for this gallery.
		 */
		private String getLastImageURL(String filename) {
			if (field.getWidgetCount() == 1 && lastImageName.isEmpty()) {				
				return rootURL + root + "-000" + Text.THUMBNAIL_INFIX + Text.getExtension(filename);
			}
			
			/*String last = Model.BLANK;
			String lastURL = Model.BLANK;
			for (int i = 0; i < field.getWidgetCount(); i++) {
				String value = field.getWidget(i).getTitle();
				if (value.compareTo(last) > 0) {
					last = value;
				}
			}*/

			String[] args = lastImageName.split("-");
			if (args.length != 2) {return null;}
			String countPostfix = args[1].split("\\.")[0];
			if(countPostfix.contains("Thumb")) {
				countPostfix = countPostfix.substring(0, countPostfix.indexOf("Thumb"));
			}
			
			double count = Double.valueOf(countPostfix);
			String url =  rootURL + args[0] + "-"
			+ NumberFormat.getFormat("000").format(count)
			+ Text.THUMBNAIL_INFIX
			+ Text.getExtension(filename);
			return url;
		}

		/**
		 * Sets the specified image and shows it or its associated text.
		 * 
		 * @param widget the specified image.
		 */
		public void show(Widget widget) {
			if (widget instanceof Image) {image = (Image) widget;}
			else {
				image = null;
				descriptionTextArea.setText(Model.BLANK);
			}
			uploadcreateLabel.setVisible(image == null);
			uploadchangeLabel.setVisible(image != null);
			thumbnailLabel.setVisible(image == null);
			thumbnailTextBox.setVisible(image == null);
			fullsizeLabel.setVisible(image == null);
			fullsizeTextBox.setVisible(image == null);
			uploadFile.setVisible(image == null);
			uploadButton.setVisible(image == null);
			saveButton.setVisible(image != null);
			deleteButton.setVisible(image != null);
			super.show();
		}
	}

	/* The Inner Class ViewPanel to display the selected image in full size. */
	private class ViewPanel extends PopupPanel implements ClickHandler,
	ChangeHandler {
		private final TextArea descriptionTextArea = new TextArea();
		private final Image fullsize = new Image();
		private final Navigator navigator = new Navigator();

		public ViewPanel() {
			super(true);
			setStylePrimaryName(CSS.cbtAbstractPopup());
			final VerticalPanel form = new VerticalPanel();
			final Label title = new Label(CONSTANTS.uploadcreateLabel());
			title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
			form.add(title);
			
			final Label closeButton = new Label();
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ViewPanel.this.hide();
				}
			});
			closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
			form.add(closeButton);
			
			setWidget(form);

			form.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			descriptionTextArea.setReadOnly(true);
			descriptionTextArea.addStyleName(CSS.cbtImageGalleryPopupDescription());
			form.add(descriptionTextArea);
			fullsize.addLoadHandler(new LoadHandler() {
				@Override
				public void onLoad(LoadEvent event) {}
			});
			fullsize.addErrorHandler(new ErrorHandler() {
				@Override
				public void onError(ErrorEvent event) {
					//addMessage(Level.ERROR, CONSTANTS.errLoadImage() + ((Widget) event.getSource()).getTitle(), setupLabel);
					fullsize.setUrl(Text.DEFAULT_IMAGE_URL + "?value=" + new Date().getTime());
				}
			});
			fullsize.addClickHandler(this);
			form.add(fullsize);
			navigator.addChangeHandler(this);
			form.add(navigator);
		}

		/**
		 * Sets the current image text
		 * 
		 * @param text the current image text
		 */
		public void setText(Text text) {
			if (text == null || text.noNotes()) {descriptionTextArea.setVisible(false);}
			else {
				descriptionTextArea.setText(text.getNotes());
				descriptionTextArea.setVisible(true);
			}
		}

		@Override
		public void onChange(ChangeEvent event) {
			if (navigator.sent(event)) {
				Widget thumbnail = field.getWidget(navigator.getIndex());
				fullsize.setUrl(getFullsizeUrl(thumbnail.getTitle()));
//				fullsize.setUrl(HasUrls.ROOT_URL + HasUrls.PICTURES_DIRECTORY + thumbnail.getTitle());
				textRead.execute(new ImageTextRead(thumbnail.getTitle(), AbstractRoot.getLanguage()));
			}
		}

		/**
		 * Displays the specified image.
		 * 
		 * @param widget the specified image.
		 */
		public void show(Widget widget) {
			if (widget instanceof Image) {
				fullsize.setUrl(getFullsizeUrl(widget.getTitle()));
//				fullsize.setUrl(HasUrls.ROOT_URL + HasUrls.PICTURES_DIRECTORY + widget.getTitle());
				navigator.setIndex(indexOf(widget.getTitle()));
				navigator.setEnd(field.getWidgetCount() - 1);
				navigator.setVisible(field.getWidgetCount() > 1);
				super.center();
			}
		}

		/**
		 * Gets the full size URL from the specified image ID.
		 * 
		 * @param value the specified image ID.
		 * @return the full size URL
		 */
		private String getFullsizeUrl(String value) {
//			return HasUrls.ROOT_URL + HasUrls.PICTURES_DIRECTORY + value + Text.IMAGE_JPG; //Text.FULLSIZE_JPEG;
			return rootURL + value; //Text.FULLSIZE_JPEG;
		}

		/**
		 * Handles click events.
		 * 
		 * @param event when clicked.
		 */
		@Override
		public void onClick(ClickEvent event) {
			super.hide();
		}
	}
}