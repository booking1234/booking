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
    Styling for location details. Simple table with label and field value on each row.
  -->
  <xsl:template match="/location">
    <html>
      <head>
        <title>Location Record</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt arial;}
  </style>
      </head>
      <body>
        <table>
          <tr>
            <td colspan="2">
              <h2>Location Record</h2>
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
            <td>Created On</td>
            <td>
              <xsl:value-of select="date" />
            </td>
          </tr>
          <tr>
            <td>Country</td>
            <td>
              <xsl:value-of select="country" />
            </td>
          </tr>
          <tr>
            <td>Province</td>
            <td>
              <xsl:value-of select="subdivision" />
            </td>
          </tr>
          <tr>
            <td>Function</td>
            <td>
              <xsl:value-of select="function" />
            </td>
          </tr>
          <tr>
            <td>IATA Code</td>
            <td>
              <xsl:value-of select="iata" />
            </td>
          </tr>
          <tr>
            <td>Latitude</td>
            <td>
              <xsl:value-of select="latitude" />
            </td>
          </tr>
          <tr>
            <td>Longitude</td>
            <td>
              <xsl:value-of select="longitude" />
            </td>
          </tr>
          <tr>
            <td>Altitude</td>
            <td>
              <xsl:value-of select="altitude" />
            </td>
          </tr>
          <tr>
            <td>Remarks</td>
            <td>
              <xsl:value-of select="remarks" />
            </td>
          </tr>
        </table>
      </body>
    </html>
  </xsl:template>




  <!--
    Styling for list of all countries and their ISO two character codes.
  -->
  <xsl:template match="/countries">
    <html>
      <head>
        <title>Country Codes</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
  </style>
      </head>
      <body>
        <h2>Country Codes</h2>
        <h3>Code Name</h3>
        <xsl:apply-templates select="item">
          <xsl:sort select="name" />
        </xsl:apply-templates>
      </body>
    </html>
  </xsl:template>




  <!-- Styling for list of location identity numbers and names.  -->
  <xsl:template match="/locations">
    <html>
      <head>
        <title>Location Ids</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
  </style>
      </head>
      <body>
        <h2>
          Locations for
          <xsl:value-of select="country" />
          <xsl:value-of select="subdivision" />
        </h2>
        <h3>ID#: Name</h3>
        <xsl:apply-templates select="item">
          <xsl:sort select="name" />
        </xsl:apply-templates>
      </body>
    </html>
  </xsl:template>



  <!--
    Styling for list of subdivision names and codes. Note that the country
    in which they are is also displayed in the header.
  -->
  <xsl:template match="/subdivisions">
    <html>
      <head>
        <title>Subdivisions</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
  </style>
      </head>
      <body>
        <h2>
          Subdivisions of
          <xsl:value-of select="country" />
        </h2>
        <h3>Code Name</h3>
        <xsl:apply-templates select="item">
          <xsl:sort select="name" />
        </xsl:apply-templates>
      </body>
    </html>
  </xsl:template>



  <!--
    Styling for list of distance units and their ISO three character codes.
  -->
  <xsl:template match="/units">
    <html>
      <head>
        <title>Distance Units</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
  </style>
      </head>
      <body>
        <h2>Distance Units</h2>
        <h3>Code Name</h3>
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
