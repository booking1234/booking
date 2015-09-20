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



  <!-- Styling for dropdown list of identity numbers and names.  -->
  <xsl:template match="/items">
    <html>
      <head>
        <title><xsl:value-of select="type" />&#160;<xsl:value-of select="entity" />&#160;List</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
        </style>
      </head>
      <body>
        <xsl:value-of select="type" />&#160;<xsl:value-of select="entity" />&#160;List
        <select name="name-id-list">
          <xsl:apply-templates select="item">
            <xsl:sort select="name" />
          </xsl:apply-templates>
	</select>
      </body>
    </html>
  </xsl:template>

  <!-- Styling for dropdown list of country codes and names.  -->
  <xsl:template match="/countries">
    <html>
      <head>
        <title>Country&#160;List</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
        </style>
      </head>
      <body>
        Country&#160;List
        <select name="name-id-list">
          <xsl:apply-templates select="item">
            <xsl:sort select="name" />
          </xsl:apply-templates>
	</select>
      </body>
    </html>
  </xsl:template>


  <!-- Styling for dropdown list of subdivision codes and names.  -->
  <xsl:template match="/subdivisions">
    <html>
      <head>
        <title><xsl:value-of select="country" />&#160;Subdivision List</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
        </style>
      </head>
      <body>
        <xsl:value-of select="subdivision" />&#160;Subdivision List
        <select name="name-id-list">
          <xsl:apply-templates select="item">
            <xsl:sort select="name" />
          </xsl:apply-templates>
	</select>
      </body>
    </html>
  </xsl:template>


  <!-- Styling for dropdown list of location codes and names.  -->
  <xsl:template match="/locations">
    <html>
      <head>
        <title><xsl:value-of select="country" />&#160;<xsl:value-of select="subdivision" />&#160;List</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
        </style>
      </head>
      <body>
        <xsl:value-of select="country" />&#160;<xsl:value-of select="subdivision" />&#160;List
        <select name="name-id-list">
          <xsl:apply-templates select="item">
            <xsl:sort select="name" />
          </xsl:apply-templates>
	</select>
      </body>
    </html>
  </xsl:template>


  <!-- Option with value attribute set to id. -->
  <xsl:template match="item">
     <option>
       <xsl:attribute name="value"><xsl:value-of select="id"/></xsl:attribute>
       <xsl:value-of select="name" />
     </option>
  </xsl:template>
  

</xsl:stylesheet>