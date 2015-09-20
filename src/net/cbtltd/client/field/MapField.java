/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;

import net.cbtltd.client.HasComponents;
import net.cbtltd.client.RemoteRequest;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Position;
import net.cbtltd.shared.location.LocationPosition;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.maps.client.MapImpl;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.events.dragend.DragEndMapEvent;
import com.google.gwt.maps.client.events.dragend.DragEndMapHandler;
import com.google.gwt.maps.client.events.mouseout.MouseOutMapEvent;
import com.google.gwt.maps.client.events.mouseout.MouseOutMapHandler;
import com.google.gwt.maps.client.events.mouseover.MouseOverMapEvent;
import com.google.gwt.maps.client.events.mouseover.MouseOverMapHandler;
import com.google.gwt.maps.client.events.zoom.ZoomChangeMapHandler;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.maps.client.services.Geocoder;
import com.google.gwt.maps.client.services.GeocoderRequest;
import com.google.gwt.maps.client.services.GeocoderRequestHandler;
import com.google.gwt.maps.client.services.GeocoderResult;
import com.google.gwt.maps.client.services.GeocoderStatus;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class MapField is to display and change positions on a map.
 * If the field is enabled a marker can be dragged to change its position.
 * 
 * @see <pre>https://github.com/branflake2267/GWT-Maps-V3-Api</pre>
 * @see <pre>http://branflake2267.github.io/GWT-Maps-V3-Api/javadoc/3.9.0-build-17/</pre>
 */
public class MapField
extends AbstractField<LatLng> {
	
	private final RemoteRequest<String, Position> locationPosition = new RemoteRequest<String, Position>() {
		
		public void execute(String locationid) {
			if (locationid == null 
					|| locationid.isEmpty() 
					|| locationid.equals(Model.ZERO)
			) {return;}
			else {super.send(new LocationPosition(locationid));}
		}
		
		public void receive(Position position) {
			setValue(LatLng.newInstance(position.getLatitude(), position.getLongitude()));
			fireChange(MapField.this);
		}
	};

	protected final HorizontalPanel panel = new HorizontalPanel();
	private final HorizontalPanel position = new HorizontalPanel();
	private final Label addressLabel = new Label(CONSTANTS.locationAddress());
	private final TextBox addressBox = new TextBox();
	private final Label latitudeLabel = new Label(CONSTANTS.locationLatitude());
	private final TextBox latitudeBox = new TextBox();
	private final Label longitudeLabel = new Label(CONSTANTS.locationLongitude());
	private final TextBox longitudeBox = new TextBox();
	private final FlowPanel emptyValue = new FlowPanel();
	private final FlowPanel field = new FlowPanel();
	private final ArrayList<Marker> markers = new ArrayList<Marker>();
	
	private MapWidget map;
	private InfoWindow infoWindow;
	private Marker marker;
	private MapTypeId mapTypeId = MapTypeId.HYBRID;
	private boolean streetView = false;
	private boolean scrollWheel = true;
	
	private HasMarkerFunction markerFunction;

	/**
	 * The Interface HasMarkerFunction.
	 */
	public interface HasMarkerFunction {
		
		/**
		 * On focus.
		 *
		 * @param value the value
		 */
		void onFocus(MouseOverMapEvent value);
		
		/**
		 * On blur.
		 *
		 * @param value the value
		 */
		void onBlur(MouseOutMapEvent value);
	}
	
	/**
	 * Instantiates a new map field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param tab index of the field.
	 */
	public MapField(
			HasComponents form,
			short[] permission,
			int tab) {
		initialize(panel, form, permission, CSS.cbtMapField());
		
		super.setDefaultValue(LatLng.newInstance(0.0, 0.0));

		addressLabel.addStyleName(CSS.cbtMapFieldLabel());
		addressBox.addStyleName(CSS.cbtLocationFieldField());
		addressBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				setName(addressBox.getText());
			}
		});

		addressBox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					setName(addressBox.getText());
				}
			}
		});

		latitudeLabel.addStyleName(CSS.cbtMapFieldLabel());
		latitudeBox.addStyleName(CSS.cbtMapFieldLatLng());
		latitudeBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				Double latitude = LF.parse(latitudeBox.getText());
				if (latitude < -90.0 || latitude > 90.0){addMessage(Level.ERROR, CONSTANTS.errPositionLatitude(), latitudeBox);}
				else {setValue(LatLng.newInstance(latitude, marker.getPosition().getLongitude()));}
			}
		});

		longitudeLabel.addStyleName(CSS.cbtMapFieldLabel());
		longitudeBox.addStyleName(CSS.cbtMapFieldLatLng());
		longitudeBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				Double longitude = LF.parse(longitudeBox.getText());
				if (longitude < -180.0 || longitude > 180.0){addMessage(Level.ERROR, CONSTANTS.errPositionLongitude(), longitudeBox);}
				else {setValue(LatLng.newInstance(marker.getPosition().getLatitude(), longitude));}
			}
		});

		position.addStyleName(CSS.cbtMapFieldTitle());
		position.add(addressLabel);
		position.add(addressBox);
		position.add(latitudeLabel);
		position.add(latitudeBox);
		position.add(longitudeLabel);
		position.add(longitudeBox);
		position.add(lock);

		panel.add(position);
		
		MapOptions mapOptions = MapOptions.newInstance();
		mapOptions.setDraggableCursor("crosshair");
		mapOptions.setDraggingCursor("text");
		mapOptions.setCenter(LatLng.newInstance(0.0, 0.0));
		mapOptions.setDraggable(true);
		mapOptions.setDisableDoubleClickZoom(true);
//		mapOptions.setHeading(heading);
		mapOptions.setKeyboardShortcuts(false);
		mapOptions.setMapMaker(true);
		mapOptions.setMapTypeControl(true);
		mapOptions.setMapTypeId(mapTypeId);
		mapOptions.setNoClear(false);
		mapOptions.setOverviewMapControl(false);
//		mapOptions.setMapTypeControlOptions(MapTypeControlOptions.)
		mapOptions.setPanControl(true);
//		mapOptions.setPanControlOptions(panControlOptions);
		mapOptions.setRotateControl(false);
//		mapOptions.setRotateControlOptions(rotateControlOptions)
		mapOptions.setScaleControl(true);
//		mapOptions.setScaleControlOptions(scaleControlOptions)
		mapOptions.setScrollWheel(scrollWheel);
//		StreetViewPanoramaImpl streetView = StreetViewPanoramaImpl.newInstance(field.getElement(), options);
//		mapOptions.setStreetView(streetView);
		mapOptions.setStreetViewControl(streetView);
//		mapOptions.setStreetViewControlOptions(streetViewControlOptions)
//		mapOptions.setStyles(styles)
		mapOptions.setZoom(15);
		mapOptions.setZoomControl(true);
		
	    map = MapWidget.newInstance(MapImpl.newInstance(field.getElement(), mapOptions));
	    
	    map.addClickHandler(new ClickMapHandler() {
			public void onEvent(ClickMapEvent event) {
				if (isEnabled()) {
					setValue(event.getMouseEvent().getLatLng());
					fireChange(MapField.this);
				}
			}
		});
		panel.add(field);

		emptyValue.addStyleName(CSS.cbtMapFieldEmpty());
		emptyValue.setVisible(false);
		panel.add(emptyValue);
		
	    InfoWindowOptions infowindowOpts = InfoWindowOptions.newInstance();
	    infowindowOpts.setMaxWidth(100);
	    infowindowOpts.setPosition(defaultValue);
	    infoWindow = InfoWindow.newInstance(infowindowOpts);
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
					setValue(location.getGeometry().getLocation());
				} else {
					addMessage(Level.ERROR, "Geocode failed: " + status.toString(), null);
				}			
			}
		});
	}
	
 	/**
	  * Checks if this is the specified widget. 
	  *
	  * @param widget the specified widget.
	  * @return true, if this is the specified widget.
	  */
	@Override
	public boolean is(Widget widget){
		return (widget == this);
	}
	
	/**
	 * Required by the Component interface.
	 */
	@Override
	public void onRefresh() {;}

	/**
	 * Sets if the scroll wheel zoom is enabled.
	 *
	 * @param enabled is true if the scroll wheel zoom is enabled.
	 */
	public void setScrollWheelZoomEnabled(boolean enabled) {
		this.scrollWheel = enabled;
	}

	/**
	 * Sets the marker function.
	 *
	 * @param markerFunction the new marker function
	 */
	public void setMarkerFunction(HasMarkerFunction markerFunction) {
		this.markerFunction = markerFunction;
	}

	/**
	 * Close the info widget.
	 */
	public void closeInfoWidget() {
		infoWindow.close();
	}
	
	/**
	 * Sets the info string.
	 *
	 * @param value the position of the info widget.
	 * @param html the HTML text displayed by the info widget.
	 */
	public void setInfoString(LatLng value,  String html) {
		infoWindow.setPosition(value);
		infoWindow.setContent(html);
		infoWindow.open(map);
	}

	/**
	 * Sets the current map type.
	 *
	 * @param type the new map type ID
	 */
	public void setMapTypeId(MapTypeId maptypeid) {
		this.mapTypeId = maptypeid; 
	}

	/**
	 * Display the empty widget if the field is empty.
	 *
	 * @param empty is true if the field is empty.
	 */
	public void setEmpty(boolean empty) {
		if (emptyValue.getWidgetCount() > 0) {
			position.setVisible(!empty);
			field.setVisible(!empty);
			emptyValue.setVisible(empty);
		}
	}
	
	/**
	 * Sets the empty widget.
	 *
	 * @param widget the new empty widget.
	 */
	public void setEmptyValue(Widget widget) {
		emptyValue.add(widget);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if (marker != null){marker.setDraggable(isEnabled());}
	}

	/**
	 * Sets if the position is visible.
	 *
	 * @param visible is true if the position is visible.
	 */
	public void setPositionVisible(boolean visible) {
		position.setVisible(visible);
	}

	/**
	 * Sets if the street view is clickable.
	 *
	 * @param streetview is true if the street view is clickable.
	 */
	public void setStreetviewClickable(boolean streetview) {
		this.streetView = streetview;
	}

	/**
	 * Sets if the street view is visible.
	 *
	 * @param visible is true if the street view controller is visible.
	 */
	public void setStreetviewVisible(boolean visible) {
		streetView = visible;
	}

	/**
	 * Sets the CSS style of the map.
	 *
	 * @param style the CSS style of the map.
	 */
	public void setFieldStyle(String style) {
		map.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the labels.
	 *
	 * @param style the CSS style of the labels.
	 */
	public void setLabelStyle(String style) {
		latitudeLabel.addStyleName(style);
		longitudeLabel.addStyleName(style);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){
		latitudeBox.setTabIndex(tab++);
		longitudeBox.setTabIndex(tab);
	}

	/**
	 * Adds the map zoom change handler.
	 *
	 * @param handler the handler
	 */
	public void addZoomChangeHandler(ZoomChangeMapHandler handler) {
		map.addZoomChangeHandler(handler);
	}

	/**
	 * Gets the center.
	 *
	 * @return the center
	 */
	public LatLng getCenter() {
		return map.getCenter();
	}
	
	/**
	 * Sets the zoom level.
	 *
	 * @param level the new zoom level
	 */
	public void setZoom(int level){
		map.setZoom(level);
		super.setChanged();
	}
	
	/**
	 * Gets the zoom level.
	 *
	 * @return the zoom level
	 */
	public Integer getZoom(){
		return map.getZoom();
	}
	
	/**
	 * Checks for default value.
	 *
	 * @return true, if successful
	 */
	public boolean hasDefaultValue() {
		return getValue() == null || isDefaultValue(getValue(), 0.0001);
	}
	
	/**
	 * Checks if is default value.
	 *
	 * @param value the value
	 * @param tolerance the tolerance
	 * @return true, if is default value
	 */
	public boolean isDefaultValue(LatLng value, double tolerance) {
		return value.getLatitude() < (defaultValue.getLatitude() + tolerance)
		&& value.getLatitude() > (defaultValue.getLatitude() - tolerance)
		&& value.getLongitude() < (defaultValue.getLongitude() + tolerance)
		&& value.getLongitude() > (defaultValue.getLongitude() - tolerance);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){
		latitudeBox.setFocus(focussed);
	}

	/**
	 * Sets the locationid.
	 *
	 * @param locationid the new locationid
	 */
	public void setLocationid(String locationid) {
		locationPosition.execute();
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public Double getLatitude() {
		return getValue() == null ? null : getValue().getLatitude();
	}
	
	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public Double getLongitude() {
		return getValue() == null ? null : getValue().getLongitude();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public LatLng getValue() {
		return marker == null ? null : marker.getPosition();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(LatLng value) {
		if (value == null){return;}
		map.panTo(value);
		map.setCenter(value);
		latitudeBox.setText(LF.format(value.getLatitude()));
		longitudeBox.setText(LF.format(value.getLongitude()));
		if (marker != null) {marker.clear();}
		addMarker(value, CONSTANTS.panoramaClickToSee());
		super.setChanged();
	}

	/**
	 * Adds a marker.
	 *
	 * @param value the position of the marker
	 * @param text the text when clicked
	 * @return the marker
	 */
	public Marker addMarker(LatLng value, String text) {
		if (value == null) {return null;}
	    MarkerOptions markerOptions = MarkerOptions.newInstance();
	    markerOptions.setClickable(true);
	    markerOptions.setDraggable(isEnabled());
	    markerOptions.setTitle(text);
	    markerOptions.setPosition(value);
	    markerOptions.setMap(map);
	    markerOptions.setVisible(visible);
	    marker = Marker.newInstance(markerOptions);
	    markers.add(marker);
	    
	    marker.addClickHandler(new ClickMapHandler() {
		      public void onEvent(ClickMapEvent event) {
				setValue(event.getMouseEvent().getLatLng());
		      }
		    });
	    
	    marker.addDragEndHandler(new DragEndMapHandler() {
	    	public void onEvent(DragEndMapEvent event) {
	    		setValue(marker.getPosition());
	    		fireChange(MapField.this);
	    	}
	    });
	    
	    marker.addMouseOverHandler(new MouseOverMapHandler() {
			public void onEvent(MouseOverMapEvent event) {
				if (markerFunction != null) {markerFunction.onFocus(event);}
			}
		});
	    
		marker.addMouseOutMoveHandler(new MouseOutMapHandler() {
			public void onEvent(MouseOutMapEvent event) {
				if (markerFunction != null) {markerFunction.onBlur(event);}
			}
		});
		return marker;
	}

	/**
	 * Clear markers.
	 */
	public void clear() {
		for (Marker marker : markers) {
			marker.setVisible(false);
		}
	}
	
	public void triggerResize() {
		map.triggerResize();
	}
}
