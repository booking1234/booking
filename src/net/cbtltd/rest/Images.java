/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Images {
	@XStreamImplicit(itemFieldName="image")
	public Collection<String> image;
	
	public Images() {}

	public Images(Collection<String> image) {
		super();
		this.image = image;
	}

	public Collection<String> getImage() {
		return image;
	}
	
	public Collection<String> sortImages(){
	    if (image != null && image instanceof List){		
			java.util.Collections.sort((List<String>)image);
	    } 
	    if(image == null) {
	    	image = new ArrayList<String>();
	    }
	    return image;
	}
}
