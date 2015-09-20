/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.reservation;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ClientBundle.Source;

public interface ReservationBundle
extends ClientBundle {

	ReservationBundle INSTANCE = GWT.create(ReservationBundle.class);

	@Source("Reservation.css")	ReservationCssResource css();
	@Source("../image/eventactiontableEmpty.png")	ImageResource eventactiontableEmpty();
	@Source("../image/financetableEmpty.png")	ImageResource featuretableEmpty();
	@Source("../image/maintenancetableEmpty.png")	ImageResource maintenancetableEmpty();
}