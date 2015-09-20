/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.party;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface PartyBundle
extends ClientBundle {

	PartyBundle INSTANCE = GWT.create(PartyBundle.class);

	@Source("Party.css") PartyCssResource css();
	@Source("../image/contracttableEmpty.png")	ImageResource contacttableEmpty();
	@Source("../image/contracttableEmpty.png")	ImageResource eventjornaltableEmpty();
	@Source("../image/financetableEmpty.png")	ImageResource financetableEmpty();
	@Source("../image/actortableEmpty.png")	ImageResource relationtableEmpty();
	@Source("../image/actortableEmpty.png")	ImageResource reservationtableEmpty();
	@Source("../image/shadow_control_600_20.png")	ImageResource shadow_control_wide();
}