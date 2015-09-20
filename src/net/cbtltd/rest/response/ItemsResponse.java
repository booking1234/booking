package net.cbtltd.rest.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.Items;
import net.cbtltd.rest.error.ApiResponse;

@XmlRootElement(name="items_response")
public class ItemsResponse extends ApiResponse {
	private Items items;

	@XmlElement(name="items")
	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}
}