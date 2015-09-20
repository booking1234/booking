package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.shared.Image;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Text;

import org.apache.ibatis.session.RowBounds;

/**
 * @author	Chirayu Shah
 * @see License at http://abookingnet.com
 * @version	1.0.0
 */
public interface ImageMapper extends AbstractMapper<Image> {
	
	Image exists(Image action);
	Image readbyexample(Image action);
	void deletebyexample(Image action);
	void suspend(Image action);
	void restore(ArrayList<String> ids);
	String lastimage(String root);
	ArrayList<NameId> productfilenameid(String id); //ProductFile
	ArrayList<NameId> publicfilenameid(String sessionid); //PublicFile
	Image defaultimagebyproductid(NameId action);	
	ArrayList<Image> imagesbyproductid(NameId action);
	ArrayList<Image> imagesbyproductidsortorder(NameIdAction action);	
	ArrayList<NameId> imagesbyurl(NameId action);
	ArrayList<String> imageidsbynameid(NameId action);
	ArrayList<String> imageidsbyurl(NameId action);
	ArrayList<String> productidsofimages();
	//Added for Flipkey integration
	Image readbyNameAndID(Text action);
	List<Image> readoldproducts();
	List<Image> readimagesbeginswithnull();
	List<Image> readnewproducts(RowBounds criteria);
	
}