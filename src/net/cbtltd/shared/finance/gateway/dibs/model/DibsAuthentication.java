package net.cbtltd.shared.finance.gateway.dibs.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="dibs_auth")
public class DibsAuthentication {
	private String merchantId;

	@XmlElement(name="merchant_id")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
}
