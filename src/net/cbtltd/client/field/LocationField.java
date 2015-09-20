/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.services.Geocoder;
import com.google.gwt.maps.client.services.GeocoderRequest;
import com.google.gwt.maps.client.services.GeocoderRequestHandler;
import com.google.gwt.maps.client.services.GeocoderResult;
import com.google.gwt.maps.client.services.GeocoderStatus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class LocationField is to determine a position given a location name, and vice versa.
 * The latitude and longitude of the position is in a LatLng object.
 */
public class LocationField
extends AbstractField<LatLng> {

	private final HorizontalPanel panel = new HorizontalPanel();
	//private final FlowPanel panel = new FlowPanel();
	private final TextBox field = new TextBox();
	private Label label;
	private Button button;
	private LatLng defaultValue;
	private LatLng value;
	private boolean nullable = false;
	
	/**
	 * Instantiates a new location field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param fieldLabel is the optional text to identify the field.  
	 * @param buttonLabel the text on the field refresh button.
	 * @param tab index of the field.
	 */
	public LocationField (
			HasComponents form,
			short[] permission,
			String fieldLabel,
			String buttonLabel,
			int tab) {

		initialize(panel, form, permission, CSS.cbtLocationField());

		if (fieldLabel != null){
			this.label  = new Label(fieldLabel);
			this.label.addStyleName(CSS.cbtLocationFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}
		
		field.addStyleName(CSS.cbtLocationFieldField());
		field.setText(Model.BLANK);
		field.setTabIndex(tab);
		panel.add(field);

		if (buttonLabel != null) {
			this.button = new Button(buttonLabel, new ClickHandler() {
				public void onClick(ClickEvent event) {
					setName(field.getText());
				}
			});
			button.addStyleName(CSS.cbtLocationFieldButton());
			panel.add(button);
		}
		else {
			field.addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					if (nullable && field.getText().isEmpty()) {setValueAndFireChange(defaultValue);}
					else {setName(field.getText());}
				}
			});
		}

		field.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if (nullable && field.getText().isEmpty()) {setValueAndFireChange(defaultValue);}
					else {setName(field.getText());}
				}
			}
		});

		panel.add(lock);
	}

 	/**
	  * Checks if this is the specified widget. 
	  *
	  * @param widget the specified widget
	  * @return true, if this is the specified widget. 
	  */
	@Override
	public boolean is(Widget widget){
		return (widget == this);
	}

	/**
	 * Sets the location name and invokes the geocoder to get its position.
	 *
	 * @param name the new location name.
	 */
	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			return;
		}
		GeocoderRequest rq = GeocoderRequest.newInstance();
		rq.setAddress(name);
		Geocoder geocoder = Geocoder.newInstance();
		geocoder.geocode(rq, new GeocoderRequestHandler() {
			
			@Override
			public void onCallback(JsArray<GeocoderResult> rs,
					GeocoderStatus status) {
				if (status == GeocoderStatus.OK) {
					GeocoderResult location = rs.get(0);
					setValueAndFireChange(location.getGeometry().getLocation());
				} else {
					addMessage(Level.ERROR, "Geocode failed: " + status.toString(), null);
				}				
			}
		});
	}
	
	/* Set the current position and fire a change event if the geolocation succeeds. */
	private void setValueAndFireChange(LatLng value) {
		this.value = value;
		fireChange(this);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onReset()
	 */
	@Override
	public void onReset() {
		value = defaultValue;
		field.setText(Model.BLANK);
		super.setChanged();
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
	 * Sets the CSS style of the list.
	 *
	 * @param style the CSS style of the list.
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the label.
	 *
	 * @param style  the CSS style of the label.
	 */
	public void setLabelStyle(String style) {
		if (label != null) {label.addStyleName(style);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){
		field.setTabIndex(tab++);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setDefaultValue(java.lang.Object)
	 */
	public void setDefaultValue(LatLng value){
		defaultValue = value;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){
		field.setFocus(focussed);
	}

	/**
	 * Gets the latitude of the current position.
	 *
	 * @return the latitude of the current position.
	 */
	public Double getLatitude() {
		return getValue() == null ? null : getValue().getLatitude();
	}
	
	/**
	 * Gets the longitude of the current position.
	 *
	 * @return the longitude of the current position.
	 */
	public Double getLongitude() {
		return getValue() == null ? null : getValue().getLongitude();
	}
	
	/**
	 * Sets if the field can be set to a null value.
	 *
	 * @param nullable is true if the field can be set to a null value.
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public LatLng getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(LatLng value) {
		this.value = value;
		super.setChanged();
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasValue#noValue()
	 */
	public boolean noValue() {return field == null || field.getValue() == null || field.getValue().isEmpty();}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#hasChanged()
	 */
	public boolean hasChanged() {return false;}

	/**
	 * Sets the location name given its position.
	 *
	 * @param value the position of the location.
	 */

//	public void setLocality(LatLng value) {
//		ReverseGeocoder.reverseGeocode(value, new ReverseGeocoderCallback() {
//			@Override
//			public void onFailure(LatLng point) {
//				field.setValue(Model.BLANK);
//			}
//			@Override
//			public void onSuccess(ExtendedPlacemark placemark) {
//				if (placemark == null) {field.setValue(Model.BLANK);}
//				else if (placemark.getStreet() == null){field.setValue(placemark.getCity());}
//				else if (placemark.getCity() == null){field.setValue(placemark.getStreet());}
//				else {field.setValue(placemark.getStreet() + " " + placemark.getCity());}
//			}
//		});
//	}
//}
}
