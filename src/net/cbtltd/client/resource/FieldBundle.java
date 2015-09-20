/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

/** The Interface FieldBundle is the stub for css and image resources. */
public interface FieldBundle
extends ClientBundle {

	FieldBundle INSTANCE = GWT.create(FieldBundle.class);

	@Source("Field.css") @CssResource.NotStrict FieldCssResource css();
//	@Source("Field.css") FieldCssResource css();
	@Source("image/alert.png")	ImageResource alert();
	@Source("image/arrowDown.png")	ImageResource arrowDown();
	@Source("image/arrowDownDisabled.png") ImageResource arrowDownDisabled();
	@Source("image/arrowDownHover.png") ImageResource arrowDownHover();
	@Source("image/arrowDownPressed.png") ImageResource arrowDownPressed();
	@Source("image/arrowUp.png")	ImageResource arrowUp();
	@Source("image/arrowUpDisabled.png") ImageResource arrowUpDisabled();
	@Source("image/arrowUpHover.png") ImageResource arrowUpHover();
	@Source("image/arrowUpPressed.png") ImageResource arrowUpPressed();
//	@Source("image/cellTableLoading.gif") ImageResource cellTableLoading(); //The loading indicator used while the table is waiting for data.
//	@Source("image/chris.gif")		ImageResource actor();
	@Source("image/cross15.gif")	ImageResource cross();
	@Source("image/downArrow.png")	ImageResource downArrow();
	@Source("image/godaddy.png")	ImageResource godaddy();
	@Source("image/cellTableHeaderBackground.png") @ImageOptions(repeatStyle = RepeatStyle.Horizontal) ImageResource cellTableHeaderBackground(); //The background used for header cells.
	@Source("image/help.gif")		ImageResource blankImage();
	@Source("image/info_20x20.png")	ImageResource info();
	@Source("image/loader.gif")		ImageResource loader();
	@Source("image/lock_12x16.png") ImageResource lock();
	@Source("image/logo.png")		ImageResource logo();
	@Source("image/logopower.png")	ImageResource logopower();
	@Source("image/mastercard.png")	ImageResource mastercard();
	@Source("image/messageBottom.png") ImageResource messageBottom();
	@Source("image/messageMiddle.png") @ImageOptions(repeatStyle = RepeatStyle.Vertical) ImageResource messageMiddle();
	@Source("image/messageTop.png") ImageResource messageTop();
	@Source("image/open.png")	ImageResource open();
	@Source("image/simplePagerFirstPage.png")	ImageResource pageFirst();
	@Source("image/simplePagerLastPage.png")	ImageResource pageLast();
	@Source("image/simplePagerNextPage.png")	ImageResource pageNext();
	@Source("image/simplePagerPreviousPage.png") ImageResource pagePrevious();
	@Source("image/simplePagerStop.png") ImageResource pageStop();
	@Source("image/simplePagerUpload.png") ImageResource pageUpload();
	@Source("image/simplePagerUp.png") ImageResource pageUp();
	@Source("image/simplePagerDown.png") ImageResource pageDown();
	@Source("image/paygate.png")	ImageResource paygate();
	@Source("image/paypal.gif")		ImageResource paypal();
	@Source("image/plus_20x20.png")	ImageResource plus();
	@Source("image/printer20.png")	ImageResource printer();
	@Source("image/progressBar_300_15.png") ImageResource progressBar();
	@Source("image/rating_0-1.png")	ImageResource star0_1();
	@Source("image/rating_0.png")	ImageResource star0();
	@Source("image/rating_1-2.png")	ImageResource star1_2();
	@Source("image/rating_1.png")	ImageResource star1();
	@Source("image/rating_2-3.png")	ImageResource star2_3();
	@Source("image/rating_2.png")	ImageResource star2();
	@Source("image/rating_3-4.png")	ImageResource star3_4();
	@Source("image/rating_3.png")	ImageResource star3();
	@Source("image/rating_4-5.png")	ImageResource star4_5();
	@Source("image/rating_4.png")	ImageResource star4();
	@Source("image/rating_5.png")	ImageResource star5();
	@Source("image/searchCenter150.png") ImageResource searchCenter();
	@Source("image/searchLeft.png") ImageResource searchLeft();
	@Source("image/searchLoading.gif") ImageResource searchLoading();
	@Source("image/searchRight.png") ImageResource searchRight();
	@Source("image/shadow_control.png") ImageResource shadow_control();
	@Source("image/tabBackground.png") @ImageOptions(repeatStyle = RepeatStyle.Horizontal) ImageResource tabBackground(); //The background used for tabs.
	@Source("image/tabSelectedBackground.png") @ImageOptions(repeatStyle = RepeatStyle.Horizontal) ImageResource tabSelectedBackground(); //The background used for tabs.
	@Source("image/thawte.png")	ImageResource thawte();
	@Source("image/upArrow.png")	ImageResource upArrow();
	@Source("image/visa.gif")	ImageResource visa();

}
