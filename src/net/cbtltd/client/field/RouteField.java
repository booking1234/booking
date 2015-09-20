/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.Design;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Report;
import net.cbtltd.shared.Text;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.services.DirectionsLeg;
import com.google.gwt.maps.client.services.DirectionsRequest;
import com.google.gwt.maps.client.services.DirectionsResult;
import com.google.gwt.maps.client.services.DirectionsResultHandler;
import com.google.gwt.maps.client.services.DirectionsRoute;
import com.google.gwt.maps.client.services.DirectionsService;
import com.google.gwt.maps.client.services.DirectionsStatus;
import com.google.gwt.maps.client.services.TravelMode;
import com.google.gwt.maps.client.services.UnitSystem;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class RouteField is to create text directions and map route from one point 
 * to another using Google Directions.
 * @see <pre>http://code.google.com/apis/maps/documentation/directions/</pre>
 *
 */
public class RouteField 
extends AbstractField<String> {

	private final ScrollPanel panel = new ScrollPanel();
	private FlowPanel field = new FlowPanel();
	private Label label;
	private DirectionsResult result;
	private ReportButton routeReport;
	private boolean printable = true;
	
	/**
	 * Instantiates a new directions field.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param label the label
	 * @param tab the tab
	 */
	public RouteField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {

		initialize(panel, form, permission, CSS.cbtTextField());

		if (label != null) {
			this.label  = new Label(label);
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		routeReport = new ReportButton(null, CONSTANTS.routereportLabel(), tab++) {
			public Report getReport() {
				Report report = new Report();
				//report.setOrganizationid(AbstractRoot.getOrganizationid());
				//report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.ROUTE);
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				report.setFormat(Report.PDF);
				report.setNotes(getValue());
				return report;
			}
		};
		routeReport.setVisible(false);
		panel.add(routeReport);
		
		panel.setVisible(false);
		panel.add(field);
//		Window.alert("" + Window.Location.getHost());
//		Maps.loadMapsApi(HasUrls.MAPS_KEY, "2", false, new Runnable() {
//			public void run() {;}
//		});
	}

	/* Display error message if it fails. */
	private void addMessage(int statusCode) {
		addMessage(Level.ERROR, CONSTANTS.errAddress() + " " + statusCode, this);
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
	 * Sets if the request must be sent via JSON.
	 *
	 * @param enabled is true if the request must be sent via JSON.
	 */
	public void setPrintable(boolean printable) {
		this.printable = printable;
	}

	/**
	 * Sets the CSS style of the field label.
	 *
	 * @param style the CSS style of the field label.
	 */
	public void setLabelStyle(String style) {
		label.addStyleName(style);
	}

	/**
	 * Sets if the field is visible.
	 *
	 * @param true, if the field is visible.
	 */
	public void setFieldVisible(boolean visible) {
		field.setVisible(visible);
	}

	/**
	 * Sets if the label is visible.
	 *
	 * @param true, if the label is visible.
	 */
	public void setLabelVisible(boolean visible) {
		if (label != null) {label.setVisible(visible);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab) {}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){return (sender == field);}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(String value) {
		String args[] = value.split("-");
		DirectionsRequest rq = DirectionsRequest.newInstance();
		rq.setAvoidHighways(false);
		rq.setAvoidTolls(true);
		rq.setOrigin(args[0]);
		rq.setDestination(args[1]);
		if (args.length > 2) {rq.setRegion(args[2]);}
		rq.setOptimizeWaypoints(true);
		rq.setProvideRouteAlternatives(false);
		rq.setTravelMode(TravelMode.DRIVING);
		rq.setUnitSystem(UnitSystem.METRIC);

		DirectionsService directions = DirectionsService.newInstance();
		directions.route(rq, new DirectionsResultHandler() {
			public void onCallback(DirectionsResult rs, DirectionsStatus status) {
				setResult(rs);
			}
		});
	}

	/* Sets the current result value. */
	private void setResult(DirectionsResult result) {
		this.result = result;
		field.setVisible(result != null && result.getRoutes().length() > 0);
		routeReport.setVisible(printable & result != null && result.getRoutes().length() > 0);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public String getValue() {
		StringBuilder sb = new StringBuilder();
		JsArray<DirectionsRoute> routes = result.getRoutes();
		if (routes == null) {sb.append("No directions available");}
		else {
			sb.append("Route\n");
			for (int i = 0; i < routes.length(); i++) {
				DirectionsRoute route = routes.get(i);
				sb.append(route.getWarnings());
				JsArray<DirectionsLeg> legs = route.getLegs();
				for (int j = 0; j < routes.length(); j++) {
					DirectionsLeg leg = legs.get(j);
					sb.append(leg.getStart_Address());
					sb.append(" to ");
					sb.append(leg.getEnd_Address());
					sb.append(" ");
					sb.append(leg.getDistance().getText());
					sb.append(" (");
					sb.append(leg.getDuration().getText());
					sb.append(")\n\n");
				}
			}
		}
		return Text.stripHTML(sb.toString()); 
	}

	/**
	 * Gets the HTML table of the route directions.
	 * 
	 * @return the HTML table of the route directions.
	 */
	public String getHTML() {
		return getHTML(false);
	}
	
	/**
	 * Gets the HTML table of the route directions.
	 * 
	 * @param all is true if the <pre><html><body>...</body></html></pre> tags are to be included. 
	 * @return the HTML table of the route directions.
	 */
	public String getHTML(boolean all) {
		StringBuilder sb = new StringBuilder();
		if (all) {sb.append("<html><body>");}
		JsArray<DirectionsRoute> routes = result.getRoutes();
		if (routes == null) {sb.append("No directions available");}
		for (int i = 0; i < routes.length(); i++) {
			DirectionsRoute route = routes.get(i);
			sb.append(route.getWarnings());
			JsArray<DirectionsLeg> legs = route.getLegs();
			for (int j = 0; j < routes.length(); j++) {
				DirectionsLeg leg = legs.get(j);
				sb.append("<tr>");
				sb.append("<td>");
				sb.append(leg.getStart_Address() + " to " + leg.getEnd_Address());
				sb.append("</td>");
				sb.append("<td>");
				sb.append(leg.getDistance().getText());
				sb.append("</td>");
				sb.append("<td>(");
				sb.append(leg.getDuration().getText());
				sb.append(")</td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
		}
		if (all) {sb.append("</body></html>");}
		return sb.toString(); 
	}
}