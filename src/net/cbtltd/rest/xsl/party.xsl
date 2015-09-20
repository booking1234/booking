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
    Styling for party details. Parties include customers, suppliers,
    users, guests etc. Simple table with label and field value on each
    row.
  -->
  <xsl:template match="/party">
    <html>
      <head>
        <title>Party Record</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt arial;}
  </style>
      </head>
      <body>
        <table>
          <tr>
            <td colspan="2">
              <h2>Party Record</h2>
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
              <xsl:value-of select="id" />
            </td>
          </tr>
          <tr>
            <td>Name</td>
            <td>
              <xsl:value-of select="name" />
            </td>
          </tr>
          <tr>
            <td>Status</td>
            <td>
              <xsl:value-of select="state" />
            </td>
          </tr>
          <tr>
            <td>Contact Name</td>
            <td>
              <xsl:value-of select="extraname" />
            </td>
          </tr>
          <tr>
            <td>Location Id</td>
            <td>
              <xsl:value-of select="locationid" />
            </td>
          </tr>
          <tr>
            <td>ID Number</td>
            <td>
              <xsl:value-of select="identitynumber" />
            </td>
          </tr>
          <tr>
            <td>Postal Address</td>
            <td>
              <xsl:value-of select="postaladdress" />
            </td>
          </tr>
          <tr>
            <td>Postal Code</td>
            <td>
              <xsl:value-of select="postcode" />
            </td>
          </tr>
          <tr>
            <td>Country Code</td>
            <td>
              <xsl:value-of select="country" />
            </td>
          </tr>
          <tr>
            <td>Email Address</td>
            <td>
              <xsl:value-of select="emailaddress" />
            </td>
          </tr>
          <tr>
            <td>Web Address</td>
            <td>
              <xsl:value-of select="webaddress" />
            </td>
          </tr>
          <tr>
            <td>Day Phone</td>
            <td>
              <xsl:value-of select="dayphone" />
            </td>
          </tr>
          <tr>
            <td>Night Phone</td>
            <td>
              <xsl:value-of select="nightphone" />
            </td>
          </tr>
          <tr>
            <td>Mobile Phone</td>
            <td>
              <xsl:value-of select="mobilephone" />
            </td>
          </tr>
          <tr>
            <td>Fax Phone</td>
            <td>
              <xsl:value-of select="faxphone" />
            </td>
          </tr>
          <tr>
            <td>Notes</td>
            <td>
              <xsl:value-of select="notes" />
            </td>
          </tr>
        </table>
      </body>
    </html>
  </xsl:template>




  <!-- Styling for list of party identity numbers and names.  -->
  <xsl:template match="/items">
    <html>
      <head>
        <title>Party List</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
  </style>
      </head>
      <body>
        <h2>
          <xsl:value-of select="type" />
          Parties
        </h2>
        <h3>ID#: Name</h3>
        <xsl:apply-templates select="item">
          <xsl:sort select="name" />
        </xsl:apply-templates>
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
