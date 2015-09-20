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
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="utf-8"
    doctype-public="-//W3C//DTD HTML 4.0 Transitional//EN" media-type="text/html" />

  <!--
    Styling for reservation history.
  -->
  <xsl:template match="/items">
    <html>
      <head>
        <title>Reservation History</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
  </style>
      </head>
      <body>
        <h1><xsl:value-of select="title" /></h1>
        <h2>Reservation History of <xsl:value-of select="manager" /></h2>
        <h3><xsl:value-of select="license" /></h3>
        <table border="0">
          <tr bgcolor="#e8eef7">
            <th>Property Name</th>
            <th>Guest Contact</th>
            <th>Checkout Date</th>
          </tr>
          <xsl:for-each select="item">
            <tr>
              <td>
                <xsl:value-of select="name" />
              </td>
              <td>
                <xsl:value-of select="email" />
              </td>
              <td>
                <xsl:value-of select="checkout" />
              </td>
            </tr>
          </xsl:for-each>
          <tr bgcolor="#e8eef7">
            <th>&#160;</th>
            <th>&#160;</th>
          </tr>
        </table>
      </body>
    </html>
  </xsl:template>

  <!--
    Styling for property details. Simple table with label and field
    value on each row.
  -->
  <xsl:template match="/property">
    <html>
      <head>
        <title>Property Record</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt arial;}
  </style>
      </head>
      <body>
        <table>
          <tr>
            <td colspan="2">
              <h2>Property Record</h2>
            </td>
          </tr>
          <tr>
            <td>
              <b>
                <u>Field</u>
              </b>
            </td>
            <td>
              <b>
                <u>Value</u>
              </b>
            </td>
          </tr>
          <tr>
            <td>Identity</td>
            <td>
              <xsl:value-of select="property_id" />
            </td>
          </tr>
          <tr>
            <td>Name</td>
            <td>
              <xsl:value-of select="name" />
            </td>
          </tr>
          <tr>
            <td>Address</td>
            <td>
              <xsl:value-of select="address" />
            </td>
          </tr>
          <tr>
            <td>Details</td>
            <td>
              <xsl:value-of select="details" />
            </td>
          </tr>
          <tr>
            <td>Suitability</td>
            <td>
              <xsl:value-of select="suitability" />
            </td>
          </tr>
          <tr>
            <td>Amenities</td>
            <td>
              <xsl:value-of select="amenities" />
            </td>
          </tr>
          <tr>
            <td>Photos</td>
            <td>
              <xsl:value-of select="photos" />
            </td>
          </tr>
          <tr>
            <td>Rates</td>
            <td>
              <xsl:value-of select="rates" />
            </td>
          </tr>
        </table>
      </body>
    </html>
  </xsl:template>


  <!-- Styling for Name-Id pairs which is used for many entity lists. -->
  <xsl:template match="item">
    <br />
    <xsl:value-of select="id" />
    :
    <xsl:value-of select="name" />
  </xsl:template>
  
</xsl:stylesheet>