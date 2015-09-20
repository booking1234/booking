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
import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.Sound.LoadState;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The Class AudioControl manages the audio associated with text fields.
 * It is displayed in the header of LabelField, RichTextField and TextAreaField objects.
 */
public class AudioControl
extends Composite
implements ClickHandler, HasChangeHandlers {

	private static final FieldConstants CONSTANTS = GWT.create(FieldConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final FieldBundle BUNDLE = FieldBundle.INSTANCE;
	private static final FieldCssResource CSS = BUNDLE.css();
	private enum State {Stopped, Started};
	private final HorizontalPanel panel = new HorizontalPanel();
	private final VerticalPanel arrowsPanel = new VerticalPanel();
	private final Image start = new Image(BUNDLE.pageNext());
	private final Image stop = new Image(BUNDLE.pageStop());
	private final Image down = new Image(BUNDLE.pageDown());
	private final Image up = new Image(BUNDLE.pageUp());
	
	private final SoundController soundController = new SoundController();
	private Sound sound;
	private LoadState loadState = LoadState.LOAD_STATE_UNINITIALIZED;
	private int volume = 50;
	private int step = 5;
	private State state = State.Stopped;

	/**
	 * Instantiates a new audio control.
	 */
	public AudioControl() {
		initWidget(panel);
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
		arrowsPanel.addStyleName(CSS.cbtSpinnerFieldArrows());
		arrowsPanel.add(up);
		arrowsPanel.add(down);
		arrowsPanel.setTitle(CONSTANTS.mediaVolume());
		panel.add(arrowsPanel);

		reset();
		setVisible(false);
	}

	/**
	 * Handles the clicking of any of the buttons in the widget.
	 *
	 * @param event clicked
	 */
	@Override
	public void onClick(ClickEvent event) {
		try {
			if (sound == null) {AbstractField.addMessage(Level.TERSE, CONSTANTS.errAudioExists(), this);}
			else if (event.getSource() == start) {
				if (loadState == LoadState.LOAD_STATE_UNINITIALIZED) {AbstractField.addMessage(Level.TERSE, CONSTANTS.errAudioPlay(), this);}
				else {
					state = State.Started;
					sound.setVolume(volume);
					sound.play();
				}
			}
			else if (event.getSource() == stop) {
				state = State.Stopped;
				sound.stop();
			}
			else if (event.getSource() == up) {volume = Math.min(volume + step, 100);}
			else if (event.getSource() == down) {volume = Math.max(volume - step, 0);}
			reset();
		} catch (Exception x) {Log.error("onClick " + sound.getUrl());}
	}

	/* Reset the volume and visible buttons according to current state. */
	private void reset() {
		if (sound != null) {sound.setVolume(volume);}
		start.setVisible(state == State.Stopped);
		stop.setVisible(state == State.Started);
	}
	
	/**
	 * Adds the specified change handler.
	 *
	 * @param handler to be added
	 * @return the handler registration
	 */
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}
	
	/**
	 * Sets the ID and language of the text for which audio is to be played.
	 *
	 * @param value the ID of the text to be played.
	 * @param language code of the audio to be played.
	 */
	public void setValue(String value, String language) {
		if (value == null) {return;}
		String url = HOSTS.rootUrl() + HasUrls.AUDIO_DIRECTORY + value + language.toUpperCase() + Text.AUDIO_WAV;
		Log.debug("setValue " + url + " was " + (sound == null ? "null" : sound.getUrl()));

		if (sound != null) {
			state = State.Stopped;
			sound.stop();
			reset();
		}
		
		if (sound == null || !sound.getUrl().equalsIgnoreCase(url)) {sound = soundController.createSound(Sound.MIME_TYPE_AUDIO_WAV_UNKNOWN, url);}

		sound.setVolume(volume);
		sound.addEventHandler(new SoundHandler() {

			public void onPlaybackComplete(PlaybackCompleteEvent event) {
				state = State.Stopped;
				reset();
				// WARNING: this method may in fact never be called; see Sound.LoadState
			}

			public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
				loadState = event.getLoadState();
				setVisible(loadState != LoadState.LOAD_STATE_UNINITIALIZED);
			}
		});
	}
}
