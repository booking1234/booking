/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.search;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface SearchBundle
extends ClientBundle {

	SearchBundle INSTANCE = GWT.create(SearchBundle.class);

	@Source("Search.css")	SearchCssResource css();
	@Source("../image/lookbookEmpty.png") ImageResource tableEmpty();
	@Source("../image/searchLeft.png") ImageResource searchLeft();
	@Source("../image/searchCenter.png") ImageResource searchCenter();
	@Source("../image/searchRight.png") ImageResource searchRight();
}