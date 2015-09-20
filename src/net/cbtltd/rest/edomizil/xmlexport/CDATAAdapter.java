package net.cbtltd.rest.edomizil.xmlexport;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adaptor for formating the flipkey export
 * @author nibodha
 *
 */
public class CDATAAdapter extends XmlAdapter<String, String> {
 
    /* (non-Javadoc)
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
     */
    @Override
    public String marshal(String v) throws Exception {
    	if(v==null||"".equalsIgnoreCase(v)) return v;
        return "<![CDATA[" + v + "]]>";
    }
 
    /* (non-Javadoc)
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
     */
    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }
}