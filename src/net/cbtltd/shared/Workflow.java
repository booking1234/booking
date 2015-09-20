/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import net.cbtltd.shared.api.HasServiceResponse;

public class Workflow implements HasServiceResponse {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
    public static final String CREATED = "Created";
    public static final String SUSPENDED = "Suspended";
    public static final String[] STATES = {INITIAL, CREATED, SUSPENDED, FINAL};

	private String id;
	private String process;
	private String state;
	private String datename;
	private Boolean enabled;
	private Boolean prior;
	private String unit;
	private Integer duration;
    private int status = 0;

	public Workflow() {}

	public Workflow(
			String id, 
			String process, 
			String state, 
			Boolean enabled, 
			Boolean prior,
			String datename,
			String unit, 
			Integer duration) {
		super();
		this.id = id;
		this.process = process;
		this.state = state;
		this.enabled = enabled;
		this.prior = prior;
		this.datename = datename;
		this.unit = unit;
		this.duration = duration;
	}

	public Service service() {return Service.WORKFLOW;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDatename() {
		return datename;
	}

	public void setDatename(String datename) {
		this.datename = datename;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Boolean notEnabled() {
		return !getEnabled();
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getPrior() {
		return prior;
	}

	public void setPrior(Boolean prior) {
		this.prior = prior;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getDuration() {
		return duration == null ? 0 : duration;
	}

	public Integer getSignedDuration() {
		return duration == null ? 0 : prior ? -duration : duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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
		builder.append("Workflow [status=");
		builder.append(status);
		builder.append(", id=");
		builder.append(id);
		builder.append(", process=");
		builder.append(process);
		builder.append(", state=");
		builder.append(state);
		builder.append(", datename=");
		builder.append(datename);
		builder.append(", enabled=");
		builder.append(enabled);
		builder.append(", prior=");
		builder.append(prior);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", duration=");
		builder.append(duration);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	} 
}
