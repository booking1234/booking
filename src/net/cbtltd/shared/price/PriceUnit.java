/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.price;

import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasEntity;

public class PriceUnit
extends NameIdAction
implements HasEntity {

		private String entitytype;
		private String entityid;
		
		public PriceUnit(String name, String entitytype, String entityid) {
			super(Service.PRICE);
			this.name = name;
			this.entitytype = entitytype;
			this.entityid = entityid;
		}

		public String getEntitytype() {
			return entitytype;
		}

		public String getEntityid() {
			return entityid;
		}	
}
