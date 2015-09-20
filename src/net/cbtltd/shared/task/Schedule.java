/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.task;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.shared.ModelTable;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.api.HasTableService;

public class Schedule
extends ModelTable
implements HasTableService {
	
	public static final String[] STATES = {Task.CREATED};

	protected Date fromdate;
	protected Date todate;
	protected boolean finitecapacity;
	protected ArrayList<String> actors;
	protected ArrayList<String> products;
	protected ArrayList<String> resources;

	public Service service() {return Service.TASK;}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public boolean noFromdate() {
		return fromdate == null;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public boolean noTodate() {
		return todate == null;
	}

	public boolean isFinitecapacity() {
		return finitecapacity;
	}

	public void setFinitecapacity(boolean finitecapacity) {
		this.finitecapacity = finitecapacity;
	}

	public ArrayList<String> getActors() {
		return actors;
	}

	public void setActors(ArrayList<String> locations) {
		this.actors = locations;
	}

	public boolean noActors() {
		return (actors == null || actors.isEmpty());
	}

	public boolean hasActors() {
		return !noActors();
	}

	public String getActorlist() {
		return NameId.getCdlist(actors);
	}

	public ArrayList<String> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<String> products) {
		this.products = products;
	}

	public boolean noProducts() {
		return products == null || products.isEmpty();
	}

	public boolean hasProducts() {
		return !noProducts();
	}

	public String getProductlist() {
		return NameId.getCdlist(products);
	}

	public ArrayList<String> getResources() {
		return resources;
	}

	public void setResources(ArrayList<String> resources) {
		this.resources = resources;
	}

	public boolean noResources() {
		return resources == null || resources.isEmpty();
	}

	public boolean hasResources() {
		return !noResources();
	}

	public String getResourcelist() {
		return NameId.getCdlist(resources);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nSchedule ").append(super.toString());
		sb.append(" Fromdate ").append(getFromdate());
		sb.append(" Todate ").append(getTodate());
		sb.append(" FiniteCapacity ").append(isFinitecapacity());
		sb.append("\nActors ").append(getActors());
		sb.append("\nProducts ").append(getProducts());
		sb.append("\nResources ").append(getResources());
		return sb.toString();
	}
}
