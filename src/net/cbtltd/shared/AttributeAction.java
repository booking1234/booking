/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;


public class AttributeAction extends NameIdAction {

	public AttributeAction(String type) {
		super(Service.ATTRIBUTE);
		this.type = type;
	}
	
	public AttributeAction(String type, String id) {
		super(Service.ATTRIBUTE);
		this.type = type;
		this.id = id;
	}
}
