/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.ota;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.api.HasXsl;

@XmlRootElement(name="items")
public class Items implements HasXsl {

		public String type;
		public String message;
		public Collection<NameId> item;
		private String xsl; //NB HAS GET&SET = private

		public Items() {}

		public Items(String type, String message, Collection<NameId> item, String xsl) {
			this.type = type;
			this.message = message;
			this.item = item;
			this.xsl = xsl;
		}

		public String getXsl() {
			return xsl;
		}

		public void setXsl(String xsl) {
			this.xsl = xsl;
		}
	}