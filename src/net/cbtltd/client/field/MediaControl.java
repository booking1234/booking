/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.client.resource.FieldConstants;
import net.cbtltd.client.resource.FieldCssResource;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.api.HasUrls;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.VideoElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mybookingpal.config.RazorConfig;

/** The Class MediaControl is to play HTML5 media object. */
public class MediaControl
extends Composite
implements ClickHandler {

	private static final FieldConstants CONSTANTS = GWT.create(FieldConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final FieldBundle BUNDLE = FieldBundle.INSTANCE;
	private static final FieldCssResource CSS = BUNDLE.css();
	private enum State {Initial, Started, Stopped};
	private HorizontalPanel panel = new HorizontalPanel();
	private Image first = new Image(BUNDLE.pageFirst());
	private Image start = new Image(BUNDLE.pageNext());
	private Image stop = new Image(BUNDLE.pageStop());
	private Image upload = new Image(BUNDLE.pageUpload());
	private final Image down = new Image(BUNDLE.pageDown());
	private final Image up = new Image(BUNDLE.pageUp());
	private Label label = new Label();

	private final MediaBase media;
	private double volume = 50.0;
	private double step = 5.0;
	private boolean enabled = true;
	private State state = State.Initial;
	private String value;

	/**
	 * Instantiates a new media control.
	 *
	 * @param media the media object to be controlled.
	 */
	public MediaControl(MediaBase media) {
		initWidget(panel);

		this.media = media;
		this.media.setControls(false);

		upload.addClickHandler(this);
		upload.addStyleName(CSS.cbtAbstractCursor());
		upload.setTitle(CONSTANTS.mediaUpload());
		panel.add(upload);

		first.addClickHandler(this);
		first.addStyleName(CSS.cbtAbstractCursor());
		first.setTitle(CONSTANTS.mediaFirst());
		panel.add(first);

		start.addClickHandler(this);
		start.addStyleName(CSS.cbtAbstractCursor());
		start.setTitle(CONSTANTS.mediaStart());
		panel.add(start);

		stop.addClickHandler(this);
		stop.addStyleName(CSS.cbtAbstractCursor());
		stop.setTitle(CONSTANTS.mediaStop());
		panel.add(stop);

		down.addClickHandler(this);
		down.addStyleName(CSS.cbtAbstractCursor());
		up.addClickHandler(this);
		up.addStyleName(CSS.cbtAbstractCursor());
		up.addStyleName(CSS.cbtAbstractTextUp());
		VerticalPanel arrowsPanel = new VerticalPanel();
		arrowsPanel.addStyleName(CSS.cbtSpinnerFieldArrows());
		arrowsPanel.add(up);
		arrowsPanel.add(down);
		arrowsPanel.setTitle(CONSTANTS.mediaVolume());
		panel.add(arrowsPanel);

		panel.add(label);
		reset();
	}

	/**
	 * Handles clicking of any of the buttons in the control.
	 *
	 * @param event when clicked.
	 */
	@Override
	public void onClick(ClickEvent event) {
		try {
			if (event.getSource() == upload) {new UploadPopup().center();}
			else if (event.getSource() == first) {
				state = State.Initial;
				media.load(); //setCurrentTime(0.0);
			}
			else if (event.getSource() == start) {
				state = State.Started;
				Log.debug("onClick play " + state);
				media.play();
			}
			else if (event.getSource() == stop) {
				state = State.Stopped;
				media.pause();
			}
			else if (event.getSource() == up) {volume = Math.min(volume + step, 100.0);}
			else if (event.getSource() == down) {volume = Math.max(volume - step, 0.0);}
			reset();
			fireChange(this);
		} catch (Exception x) {Log.error("onClick " + media.getError());}
	}

	/* Resets the visibility of control buttons depending on the current state. */
	private void reset() {
		label.setText(NumberFormat.getFormat("##0").format(volume));
		media.setVolume(volume / 100.0);
		upload.setVisible(enabled && state == State.Initial);
		first.setVisible(state == State.Stopped);
		start.setVisible(state == State.Initial || state == State.Stopped);
		stop.setVisible(state == State.Started);
	}

	/**
	 * Fires a change event when clicked.
	 *
	 * @param sender of the click event.
	 */
	private void fireChange(Widget sender) {
		NativeEvent nativeEvent = Document.get().createChangeEvent();
		DomEvent.fireNativeEvent(nativeEvent, sender);
	}

	/**
	 * Checks if this control sent the specified event.
	 *
	 * @param event the specified event.
	 * @return true, if this control sent the specified event.
	 */
	public boolean sent(ChangeEvent event) {
		return this == event.getSource();
	}

	/**
	 * Adds a new CSS style to the label.
	 *
	 * @param style the CSS style to be added.
	 */
	public void addLabelStyle(String style) {
		label.addStyleName(style);
	}

	/**
	 * Sets if the control is enabled.
	 *
	 * @param enabled is true if the control is enabled.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Sets the ID of the media object.
	 *
	 * @param value the new ID of the media object.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Sets the ID of an audio object.
	 *
	 * @param value the new ID of the audio object.
	 */
	public void setAudioValue(String value) {
		Log.debug("setAudioValue " + value);
		try {
			this.value = value;
			if (media == null || value == null || value.isEmpty()) {return;}

			else if (MediaElement.CAN_PLAY_PROBABLY.equals(media.canPlayType(AudioElement.TYPE_WAV))) {media.setSrc(HOSTS.rootUrl() + HasUrls.AUDIO_DIRECTORY + value + Text.AUDIO_WAV);} 
			else if (MediaElement.CAN_PLAY_PROBABLY.equals(media.canPlayType(AudioElement.TYPE_MP3))) {media.setSrc(HOSTS.rootUrl() + HasUrls.AUDIO_DIRECTORY + value + Text.AUDIO_MP3);}
			else if (MediaElement.CAN_PLAY_PROBABLY.equals(media.canPlayType(AudioElement.TYPE_OGG))) {media.setSrc(HOSTS.rootUrl() + HasUrls.AUDIO_DIRECTORY + value + Text.AUDIO_OGG);} 

			else if (MediaElement.CAN_PLAY_MAYBE.equals(media.canPlayType(AudioElement.TYPE_WAV))) {media.setSrc(HOSTS.rootUrl() + HasUrls.AUDIO_DIRECTORY + value + Text.AUDIO_WAV);}
			else if (MediaElement.CAN_PLAY_MAYBE.equals(media.canPlayType(AudioElement.TYPE_MP3))) {media.setSrc(HOSTS.rootUrl() + HasUrls.AUDIO_DIRECTORY + value + Text.AUDIO_MP3);}
			else if (MediaElement.CAN_PLAY_MAYBE.equals(media.canPlayType(AudioElement.TYPE_OGG))) {media.setSrc(HOSTS.rootUrl() + HasUrls.AUDIO_DIRECTORY + value + Text.AUDIO_OGG);}
			else {return;}

			media.load();
			//media.setPreload(MediaElement.PRELOAD_AUTO);
			Log.debug("loaded " + value);
		} catch (Exception x) {Log.error("setAudioValue " + HOSTS.rootUrl() + HasUrls.AUDIO_DIRECTORY + value + "\n" + media.getError());}
	}

	/**
	 * Sets the ID of a video object.
	 *
	 * @param value the new ID of the video object.
	 */
	public void setVideoValue(String value) {
		Log.debug("setVideoValue " + value);
		try {
			this.value = value;
			if (media == null || value == null || value.isEmpty()) {return;}

			else if (MediaElement.CAN_PLAY_PROBABLY.equals(media.canPlayType(VideoElement.TYPE_MP4))) {media.setSrc(HOSTS.rootUrl() + HasUrls.VIDEO_DIRECTORY + value + Text.VIDEO_MP4);}
			else if (MediaElement.CAN_PLAY_PROBABLY.equals(media.canPlayType(VideoElement.TYPE_OGG))) {media.setSrc(HOSTS.rootUrl() + HasUrls.VIDEO_DIRECTORY + value + Text.VIDEO_OGG);} 
			else if (MediaElement.CAN_PLAY_PROBABLY.equals(media.canPlayType(VideoElement.TYPE_WEBM))) {media.setSrc(HOSTS.rootUrl() + HasUrls.VIDEO_DIRECTORY + value + Text.VIDEO_WEBM);} 

			else if (MediaElement.CAN_PLAY_MAYBE.equals(media.canPlayType(VideoElement.TYPE_MP4))) {media.setSrc(HOSTS.rootUrl() + HasUrls.VIDEO_DIRECTORY + value + Text.VIDEO_MP4);}
			else if (MediaElement.CAN_PLAY_MAYBE.equals(media.canPlayType(VideoElement.TYPE_OGG))) {media.setSrc(HOSTS.rootUrl() + HasUrls.VIDEO_DIRECTORY + value + Text.VIDEO_OGG);}
			else if (MediaElement.CAN_PLAY_MAYBE.equals(media.canPlayType(VideoElement.TYPE_WEBM))) {media.setSrc(HOSTS.rootUrl() + HasUrls.VIDEO_DIRECTORY + value + Text.VIDEO_WEBM);}
			else {return;}
			media.load();
			//media.setPreload(MediaElement.PRELOAD_AUTO);
			Log.debug("loaded " + value);
		} catch (Exception x) {Log.error("setVideoValue " + HOSTS.rootUrl() + HasUrls.AUDIO_DIRECTORY + value);}
	}

	/* Inner Class UploadPopup to upload a media file. */
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
			final FileUpload uploadFile = new FileUpload();
			final FormPanel uploadPanel = new FormPanel();

			Button uploadButton = new Button(CONSTANTS.allUpload(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					uploadPanel.submit();
				}
			});

			typeTextBox.setName(Text.FILE_TYPE);
			//			typeTextBox.setText(type);
			typeTextBox.setVisible(false);
			form.add(typeTextBox);

			final HorizontalPanel buttons = new HorizontalPanel();
			buttons.addStyleName(AbstractField.CSS.cbtAbstractCommand());
			uploadFile.setStyleName(CSS.cbtImageGallerySetupUpload());
			uploadFile.setTitle(CONSTANTS.uploadFile());
			buttons.add(uploadFile);

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
					if (Text.isAudioFile(uploadFile.getFilename())
							) {
						AbstractField.addMessage(Level.ERROR, CONSTANTS.errLoadFile() + " " + uploadFile.getFilename(), uploadFile);
						event.setCancelled(true);
					} 
					else {
						String type = getType(uploadFile.getFilename());
						typeTextBox.setText(type);
						uploadFile.setName(HasUrls.AUDIO_DIRECTORY + value + "." + type);
					}
				}

				@Override
				public void onSubmitComplete(FormSubmitCompleteEvent event) {
					String message = Text.stripHTML(event.getResults());
					if (!message.startsWith("202")) {AbstractField.addMessage(Level.ERROR, CONSTANTS.errLoadFile() + " " + uploadFile.getFilename() + "\n" + message, uploadFile);}
					hide();
				}
			});
			add(uploadPanel);
		}

		/*
		 * Gets the media type from its file extension.
		 * 
		 * @param url the URL of the media.
		 * @return the media type.
		 */
		private String getType(String url) {
			String[] args = url.split("\\.");
			return (args.length == 2) ? args[1].toLowerCase() : null;
		}
	}
}