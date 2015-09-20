package net.cbtltd.rest.ru;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Currency"
})
@XmlRootElement(name = "Currencies")
public class Currencies {
	
	protected List<Currency> Currency;

    public List<Currency> getCurrency() {
        if (Currency == null) {
        	Currency = new ArrayList<Currency>();
        }
        return this.Currency;
    }
}
