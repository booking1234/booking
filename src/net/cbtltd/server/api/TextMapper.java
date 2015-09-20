/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.Entity;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Text;

public interface TextMapper
extends AbstractMapper<Text> {
	
	Text exists(Text action);
	Text readbyexample(Text action);
	void deletebyexample(Text action);
	void suspend(Entity action);
	void restore(ArrayList<String> ids);
	String lastimage(String root);
	
	ArrayList<Text> readallhtml();
	
	ArrayList<NameId> productfilenameid(String id); //ProductFile
	ArrayList<NameId> publicfilenameid(String sessionid); //PublicFile
	ArrayList<NameId> imagesbynameid(NameId action);
	ArrayList<NameId> imagesbyurl(NameId action);
	ArrayList<String> imageidsbynameid(NameId action);
	ArrayList<String> imageidsbyurl(NameId action);
	//Added for Flipkey integration
	Text readbyNameAndID(Text action);
	Text readbyNameAndIDAndLanguage(Text action);
	ArrayList<Text> readallbyid(String id);
	
	ArrayList<Text> imagesAndVersionByNameId(NameId action);
	
	Text readByID(String id);
}