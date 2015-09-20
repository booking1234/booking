package net.cbtltd.rest.mybookingpal;

import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("productschedule")
public class ProductSchedule {
	
	
	private String entity;
	private String id;
	@XStreamImplicit(itemFieldName="reserved")
	private List<ReservedDates> reserveddates;

	public ProductSchedule(String entity, String id,  List<ReservedDates> reserveddates) {
		this.entity = entity;
		this.id = id;
		this.reserveddates = reserveddates;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ReservedDates> getReservedDates() {
		return reserveddates;
	}

	public void setReservedDates(List<ReservedDates> reserveddates) {
		reserveddates = reserveddates;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductSchedule [entity=");
		builder.append(entity);
		builder.append(", id=");
		builder.append(id);
		builder.append(", reserveddates=");
		builder.append(reserveddates);
		builder.append("]");
		return builder.toString();
	}

}
