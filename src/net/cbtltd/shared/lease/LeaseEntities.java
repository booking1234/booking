/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.lease;

import java.util.ArrayList;

import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Lease;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasServiceResponse;

public class LeaseEntities
implements HasServiceResponse {

	private Lease lease;
	private Party manager;
	private Party owner;
	private Party agent;
	private Party customer;
	private Product product;
	private int status = 0;

	public LeaseEntities() {}

	public LeaseEntities(Lease lease) {
		this.lease = lease;
	}

	public Service service() {return Service.RESERVATION;}

	public String getId() {
		return lease == null ? null : lease.getId();
	}

	public boolean noId() {
		return getId() == null || getId().isEmpty();
	}

	public ArrayList<String> getIds() {
		ArrayList<String> ids = new ArrayList<String>();
		if (manager != null) {ids.add(manager.getId());}
		if (owner != null) {ids.add(owner.getId());}
		if (agent != null) {ids.add(agent.getId());}
		if (customer != null) {ids.add(customer.getId());}
		return ids;
	}

	public String getPayerid(String payer) {
		switch (Price.Payer.valueOf(payer)) {
		case Customer: return customer.getId();
		case Organization: return manager.getId();
		case Agent: return agent.getId();
		case Owner: return owner.getId();
		default:  return customer.getId();
		}
	}
	
	public Lease getLease() {
		return lease;
	}

	public void setLease(Lease lease) {
		this.lease = lease;
	}

	public boolean noLease() {
		return lease == null;
	}
	
	public boolean hasLease() {
		return !noLease();
	}
	
	public Party getManager() {
		return manager;
	}

	public void setManager(Party manager) {
		this.manager = manager;
	}

	public Party getOwner() {
		return owner;
	}

	public void setOwner(Party owner) {
		this.owner = owner;
	}

	public Party getAgent() {
		return agent;
	}

	public void setAgent(Party agent) {
		this.agent = agent;
	}

	public boolean noAgent() {
		return agent == null || agent.getName() == null || agent.getName().isEmpty();
	}
	
	public boolean hasAgent() {
		return !noAgent();
	}
	
	public boolean hasAgent(String agentid) {
		return agent != null && agent.getId() != null && agent.hasId(agentid);
	}

	public Party getCustomer() {
		return customer;
	}

	public void setCustomer(Party customer) {
		this.customer = customer;
	}

	public boolean noCustomer() {
		return customer == null || customer.getName() == null || customer.getName().isEmpty();
	}
	
	public boolean hasCustomer() {
		return !noCustomer();
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean hasQuantity() {
		return product != null && product.getQuantity() != null;
	}
	
	public boolean noRank() {
		return product != null && product.noRank();
	}
	
	public String getCurrency() {
		return product == null ? null : product.getCurrency();
	}
	
	public boolean noCurrency() {
		return product == null || product.getCurrency() == null || product.getCurrency().isEmpty();
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LeaseJournal [lease=");
		builder.append(lease);
		builder.append("\n\nsupplier=");
		builder.append(manager);
		builder.append("\n\nowner=");
		builder.append(owner);
		builder.append("\n\nagent=");
		builder.append(agent);
		builder.append("\n\ncustomer=");
		builder.append(customer);
		builder.append("\n\nproduct=");
		builder.append(product);
		builder.append("\n\nstatus=");
		builder.append(status);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}

}
