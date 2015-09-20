package net.cbtltd.rest.ru.product;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "CancellationPolicy"
})
@XmlRootElement(name = "CancellationPolicies")
public class CancellationPolicies {

	protected List<CancellationPolicy> CancellationPolicy;

	public List<CancellationPolicy> getCancellationPolicy() {
		if (CancellationPolicy == null) {
			CancellationPolicy = new ArrayList<CancellationPolicy>();
		}
		return this.CancellationPolicy;
	}

	public void setCancellationPolicy(List<CancellationPolicy> cancellationPolicies) {
		this.CancellationPolicy = cancellationPolicies;
	}
}
