/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.text.TextRead;
import net.cbtltd.shared.text.TextUpdate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mybookingpal.config.RazorConfig;

/** 
 * The Class FileField is to manage a list of files on the server.
 * When enabled, the field can upload files to the server.
 * Otherwise the field lists the files that have been uploaded.
 */
public class FileField
extends AbstractField <ArrayList<NameId>>
implements ClickHandler, ChangeHandler {

	private final GuardedRequest<Text> textRead = new GuardedRequest<Text>() {
		protected boolean error () {
			return (
					field == null
					|| field.getItemCount() == 0 
					|| field.getSelectedIndex() == 0
			);
		}
		
		protected void send() {
			super.send(new TextRead(field.getValue(field.getSelectedIndex()), AbstractRoot.getLanguage()));
		}

		protected void receive(Text text) {
			if (isEnabled() && Text.Type.PublicFile != format) {setup(text);}
			else if (text != null) {Window.open(HOSTS.rootUrl() + directory + text.getId(), "Report", "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");}
		}
	};

	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private final FlowPanel panel = new FlowPanel();
	private Label label;
	private SetupPanel setupPanel;
	private Label setupLabel;
	private final ListBox field = new ListBox();
	private String directory = HasUrls.PICTURES_DIRECTORY;
	private Text.Type format = Text.Type.File;
	private String name;
	
	/**
	 * Instantiates a new file field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public FileField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		this(form, permission, label, label, tab);
	}

	/**
	 * Instantiates a new file field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param popupLabel the optional text to identify the field in pop up mode (not enabled).
	 * @param setupLabel the optional text to identify the field in setup mode (enabled).
	 * @param tab index of the field.
	 */
	public FileField(
			HasComponents form,
			short[] permission,
			String popupLabel,
			String setupLabel,
			int tab) {

		initialize(panel, form, permission, CSS.cbtFileField());

		if (setupLabel != null) {
			this.setupLabel  = new Label(setupLabel);
			this.setupLabel.addClickHandler(this);
			this.setupLabel.setVisible(isEnabled());
			this.setupLabel.addStyleName(CSS.cbtFileFieldLabelSetup());
			panel.add(this.setupLabel);
		}

		if (popupLabel != null) {
			this.label  = new Label(popupLabel);
			this.label.addClickHandler(this);
			this.label.setVisible(!isEnabled());
			this.label.addStyleName(CSS.cbtFileFieldLabelPopup());
			panel.add(this.label);
		}

		field.addStyleName(CSS.cbtFileFieldField());
		field.addChangeHandler(this);
		panel.add(field);
		setTabIndex(tab);
		setEnabled(false);
	}

	/**
	 * Handles click events.
	 *
	 * @param event when clicked.
	 */
	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == setupLabel && isEnabled()){setup(null);}
		else if (event.getSource() == field) {textRead.execute();}
	}

	/**
	 * Handles change events.
	 *
	 * @param event when changed.
	 */
	@Override
	public void onChange(ChangeEvent event) {
		if (event.getSource() == field){textRead.execute();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#fireChange()
	 */
	public void fireChange() {
		super.fireChange(this);
	}

	/*
	 * Display setup form for file upload.
	 * 
	 * @param text the optional text object with a description of the file.
	 */
	private void setup(Text text) {
		if (setupPanel == null) {setupPanel = new SetupPanel();}
		setupPanel.setPopupPosition(getAbsoluteLeft()+ 100, getAbsoluteTop());
		setupPanel.setText(text);
		setupPanel.show();
	}

	/**
	 * Sets if the field is enabled.
	 *
	 * @param enabled is true if files can be uploaded.
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if (label != null) {label.setVisible(!isEnabled());}
		if (setupLabel != null) {setupLabel.setVisible(isEnabled());}
	}

	/**
	 * Sets the CSS style of the field.
	 *
	 * @param style the CSS style of the field.
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
		label.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the setup label.
	 *
	 * @param style the CSS style of the setup label.
	 */
	public void changeSetupLabelStyle(String style) {
		setupLabel.addStyleName(style);
	}

	/**
	 * Gets the name of the file.
	 *
	 * @return the name of the file
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the file.
	 *
	 * @param name the new file name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if there is no name.
	 *
	 * @return true, if there is no name.
	 */
	public boolean noName() {
		return name == null || name.isEmpty();
	}

	/**
	 * Checks if there is a name.
	 *
	 * @return true, if there is a name.
	 */
	private boolean hasName() {
		return !noName();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){;}

	/**
	 * Gets the directory in which the file is (to be) stored.
	 *
	 * @return the directory in which the file is (to be) stored.
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * Sets the directory in which the file is (to be) stored..
	 *
	 * @param directory the new directory in which the file is (to be) stored.
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}

	/**
	 * Gets the file type (state).
	 *
	 * @return the file type.
	 */
//	public String getState() {
//		return format;
//	}

	/**
	 * Sets the file type (state).
	 *
	 * @param state the new file type.
	 */
//	public void setState(String state) {
//		this.format = state;
//	}

	/**
	 * Gets the list of files managed by this field.
	 * 
	 * @return values of the list of files managed by this field.
	 */
	public ArrayList<NameId> getValue(){
		if (field == null || field.getItemCount() < 1) {return null;}
		ArrayList<NameId> values = new ArrayList<NameId>(field.getItemCount());
		for (int i = 1; i < field.getItemCount(); i++) {values.add(new NameId(field.getItemText(i), field.getValue(i)));}
		return values;
	}

	/**
	 * Sets the list of files managed by this field.
	 * 
	 * @param values the list of files managed by this field.
	 */
	public void setValue(ArrayList<NameId> values) {
		field.clear();
		field.addItem(Model.BLANK, Model.ZERO);
		if (values == null) {return;}
		for (NameId value : values) {field.addItem(value.getName(), value.getId());}
		super.setChanged();
	}

	/**
	 * @param sender widget.
	 * @return true if the sender is this widget.
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == this);
	}

	/* Inner Class SetupPanel to upload a file. */
	@SuppressWarnings("deprecation")
	private class SetupPanel
	extends PopupPanel {

		private final GuardedRequest<Text> textUpdate = new GuardedRequest<Text> () {
			
			public boolean error () {
				return (
						text == null 
						|| nameTextBox.getText().isEmpty() 
						|| AbstractRoot.noLanguage()
				);	
			}
			
			public void send() {
				super.send(new TextUpdate(text.getId(), nameTextBox.getText(), descriptionTextArea.getText(), AbstractRoot.getLanguage()));
			}
			
			public void receive(Text text) {
				setText(text);
				hide();
			}
		};

		private final GuardedRequest<Text> textDelete = new GuardedRequest<Text> (){
			public boolean error () {
				return (
						field == null
						|| field.getItemCount() == 0 
						|| field.getSelectedIndex() == 0
				);
			}
			
			public void send() {
				super.send(new TextRead(field.getValue(field.getSelectedIndex()), AbstractRoot.getLanguage()));
			}
			
			public void receive(Text text) {
				setText(text);
				if (field.getSelectedIndex() > 0){field.removeItem(field.getSelectedIndex());}
			}
		};

		Text text;

		TextBox typeTextBox = new TextBox();
		Label nameLabel = new Label(CONSTANTS.uploadName());
		TextBox nameTextBox = new TextBox();
		Label descriptionLabel = new Label(CONSTANTS.uploadDescription());
		TextArea descriptionTextArea = new TextArea();
		Label uploadDirectoryLabel = new Label(CONSTANTS.uploadDirectory());
		ListBox uploadDirectory = new ListBox();
		Label uploadLabel = new Label(CONSTANTS.uploadFile());
		FileUpload uploadFile = new FileUpload();
		FormPanel uploadPanel = new FormPanel();

		Button create = new Button(CONSTANTS.allUpload(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				uploadPanel.submit();
			}
		});
		
		Button update = new Button(CONSTANTS.allSave(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (field.getSelectedIndex() > 0){field.setItemText(field.getSelectedIndex(), nameTextBox.getText());}
				textUpdate.execute();
			}
		});
		
		Button delete = new Button(CONSTANTS.allDelete(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				textDelete.execute();
				fireChange();
				hide();
			}
		});
		
		Button cancel = new Button(CONSTANTS.allCancel(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		public SetupPanel () {
			super(true);
			setStylePrimaryName(CSS.cbtFileFieldSetup());
			
			Grid grid = new Grid(6, 2);
			add(grid);
			int row = 0;

			//Used to transfer type information to file upload server
			typeTextBox.setName(Text.FILE_TYPE);
			typeTextBox.setText(format.name());
			typeTextBox.setVisible(false);
			//			typeTextBox.addStyleName(CSS.cbtFileFieldSetupField());
			grid.setWidget(row++, 1, typeTextBox);

			nameLabel.addStyleName(CSS.cbtFileFieldSetupLabel());
			nameLabel.setVisible(noName());
			grid.setWidget(row, 0, nameLabel);

			nameTextBox.addStyleName(CSS.cbtFileFieldSetupField());
			nameTextBox.setName(Text.FILE_NAME);
			nameTextBox.setVisible(noName());
			grid.setWidget(row++, 1, nameTextBox);

			descriptionLabel.addStyleName(CSS.cbtFileFieldSetupLabel());
			descriptionLabel.setVisible(noName());
			grid.setWidget(row, 0, descriptionLabel);

			descriptionTextArea.setName(Text.FILE_NOTES);
			descriptionTextArea.addStyleName(CSS.cbtFileFieldSetupDescription());
			descriptionTextArea.setVisible(noName());
			grid.setWidget(row++, 1, descriptionTextArea);

			uploadDirectoryLabel.addStyleName(CSS.cbtFileFieldSetupLabel());
			uploadDirectoryLabel.setVisible(noName());
			grid.setWidget(row, 0,uploadDirectoryLabel);

			uploadDirectory.addStyleName(CSS.cbtFileFieldSetupField());
			uploadDirectory.setVisible(noName());
			uploadDirectory.addItem(Text.Code.Public.name(), HasUrls.PUBLIC_DIRECTORY);
			uploadDirectory.addItem(Text.Code.Pictures.name(), HasUrls.PICTURES_DIRECTORY);
			uploadDirectory.setSelectedIndex(getIndex(directory));
			uploadDirectory.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					directory = uploadDirectory.getValue(uploadDirectory.getSelectedIndex());
				}
			});
			grid.setWidget(row++, 1, uploadDirectory);

			uploadLabel.addStyleName(CSS.cbtFileFieldSetupLabel());
			grid.setWidget(row, 0,uploadLabel);

			uploadFile.addStyleName(CSS.cbtFileFieldSetupFile());
			grid.setWidget(row++, 1, uploadFile);

			FlowPanel buttons = new FlowPanel();
			//			buttons.setSpacing(1);
			create.addStyleName(CSS.cbtFileFieldSetupButton());
			buttons.add(create);
			update.setVisible(noName());
			update.addStyleName(CSS.cbtFileFieldSetupButton());
			buttons.add(update);
			delete.setVisible(noName());
			delete.addStyleName(CSS.cbtFileFieldSetupButton());
			buttons.add(delete);
			cancel.addStyleName(CSS.cbtFileFieldSetupButton());
			buttons.add(cancel);
			grid.setWidget(row++, 1, buttons);

			uploadPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
			uploadPanel.setMethod(FormPanel.METHOD_POST);
			uploadPanel.setAction(GWT.getModuleBaseURL() + "UploadFileService"); //UploadFileServlet
			uploadPanel.setWidget(grid);
			uploadPanel.addFormHandler(new FormHandler() {
				@Override
				public void onSubmit(FormSubmitEvent event) {   // This event is fired just before the form is submitted.
					if (hasName()){nameTextBox.setText(getName());}
					if (noValue(uploadFile.getFilename())
							|| noValue(nameTextBox.getText())
					) {
						addMessage(Level.ERROR, CONSTANTS.errLoadFile(), uploadFile);
						event.setCancelled(true);
					}
					else {
						Text text =  new Text(getFilename(), nameTextBox.getText(), format, new Date(), descriptionTextArea.getText(), AbstractRoot.getLanguage());
						uploadFile.setName(directory + text.getId());
						for (int i = 1; i < field.getItemCount(); i++) {if (field.getValue(i).equalsIgnoreCase(getFilename())) {return;}}
						field.addItem(nameTextBox.getText(), getFilename());
					}
				}

				@Override
				public void onSubmitComplete(FormSubmitCompleteEvent event) {
					String message = event.getResults().replaceAll("\\<.*?>","");
					if (message.startsWith("202")) {
						addMessage(Level.VERBOSE, CONSTANTS.uploadComplete(), uploadFile);
						fireChange();
						hide();
						nameTextBox.setText(Model.BLANK);
					}
					else {addMessage(Level.ERROR, CONSTANTS.uploadError() + message.substring(0,3), uploadFile);}
//					if (message.startsWith("202,OK")){
//						fireChange();
//						hide();
//						nameTextBox.setText(Model.BLANK);
//					}
//					else {addMessage(Level.ERROR, message, nameTextBox);}
				}
			});
			add(uploadPanel);
		}

		/**
		 * Set the current text record
		 * @param text
		 */
		public void setText(Text text) {
			this.text = text;
			if (text == null) {
				nameTextBox.setText(Model.BLANK);
				descriptionTextArea.setText(Model.BLANK);
			}
			else {
				nameTextBox.setText(text.getName());
				descriptionTextArea.setText(text.getNotes());
			}
		}

		/**
		 * @return index of value
		 * @param value
		 */
		private int getIndex(String value) {
			for (int i = 0; i < uploadDirectory.getItemCount(); i++ ){
				if (uploadDirectory.getValue(i).equalsIgnoreCase(value)){return i;}
			}
			return 0;
		}

		/**
		 * @return filename without path
		 * @param filename with path
		 */
		private String getFilename() {
			return uploadFile.getFilename() + ".jpg";
		}

		/**
		 * @return true if the parameter is not a valid image URL
		 * @param url of image
		 */
		private boolean noValue(String url) {
			return url == null
			|| url.trim().isEmpty();
		}
	}
}
