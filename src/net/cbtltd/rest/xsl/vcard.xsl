<?xml version="1.0" encoding="utf-8"?>

  <!--
    (c)2009 CBT Ltd controlling License at http://razor-cloud.com/razor/License.html
    You may use this XSL and any derivative product to transform data served
    by any Razor or other FlitePlan web service. Razor REST services are described at:
	http://razor-cloud.com/xml/rest?_wadl&type=xml
    Please adapt this XSL file to suit the layout that you prefer. 
    An overview of XSL transformation (XSLT) is at http://www.digital-web.com/articles/client_side_xslt 
    The W3C tutorial on XSLT is at http://www.w3schools.com/xsl/default.asp 
    Client and server side XSLT options are described at http://xmlfiles.com/xsl/xsl_intro.asp
  -->
  
<!DOCTYPE xsl:stylesheet [
<!ENTITY NL "<xsl:text>&#xa;&#xd;</xsl:text>">
]>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="utf-8"
    doctype-public="-//W3C//DTD HTML 4.0 Transitional//EN" media-type="text/html" />

  <!--
    Styling for party vcard. Parties include customers, suppliers,
    users, guests etc.
  -->
  <xsl:template match="/party">
	BEGIN:VCARD&NL;
	VERSION:3.0&NL;
	FN:<xsl:value-of select="name" />&NL;
	TEL;TYPE=WORK,VOICE:<xsl:value-of select="dayphone" />&NL;
	TEL;TYPE=HOME,VOICE:<xsl:value-of select="nightphone" />&NL;
	TEL;TYPE=MOBILE,VOICE:<xsl:value-of select="mobilephone" />&NL;
	TEL;TYPE=WORK,FAX:<xsl:value-of select="faxphone" />&NL;
	ADR;TYPE=HOME:<xsl:value-of select="postaladdress" />;<xsl:value-of select="postcode" />;<xsl:value-of select="country" />&NL;
	LABEL;TYPE=HOME:<xsl:value-of select="postaladdress" />;<xsl:value-of select="postcode" />;<xsl:value-of select="country" />&NL;
	EMAIL;TYPE=PREF,INTERNET:<xsl:value-of select="emailaddress" />&NL;
	URL:<xsl:value-of select="webaddress" />&NL;
	END:VCARD
  </xsl:template>
  
</xsl:stylesheet>
