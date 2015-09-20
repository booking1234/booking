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
    Styling for product details. Products include accommodation,
    inventory, consumables, spares etc. Simple table with label and field
    value on each row.
  -->
  <xsl:template match="/product">
    <html>
      <head>
        <title>Product Record</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt arial;}
  </style>
      </head>
      <body>
        <table>
          <tr>
            <td colspan="2">
              <h2>Product Record</h2>
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
            <td>Owner Id</td>
            <td>
              <xsl:value-of select="ownerid" />
            </td>
          </tr>
          <tr>
            <td>Location Id</td>
            <td>
              <xsl:value-of select="locationid" />
            </td>
          </tr>
          <tr>
            <td>Supplier Id</td>
            <td>
              <xsl:value-of select="supplierid" />
            </td>
          </tr>
          <tr>
            <td>Unit of Measure</td>
            <td>
              <xsl:value-of select="unit" />
            </td>
          </tr>
          <tr>
            <td>UNSPSC Code</td>
            <td>
              <xsl:value-of select="unspsc" />
            </td>
          </tr>
          <tr>
            <td>Product Code</td>
            <td>
              <xsl:value-of select="code" />
            </td>
          </tr>
          <tr>
            <td>Web Address</td>
            <td>
              <xsl:value-of select="webaddress" />
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
            <td>Notes</td>
            <td>
              <xsl:value-of select="notes" />
            </td>
          </tr>
        </table>
      </body>
    </html>
  </xsl:template>



  <!-- Styling for product price.  -->
  <xsl:template match="/price">
    <html>
      <head>
        <title>Product Price</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
        </style>
      </head>
      <body>
      Price of <xsl:value-of select="quantity" />&#160;<xsl:value-of select="unit" />
      of <xsl:value-of select="entity" />&#160;<xsl:value-of select="id" /> 
      on <xsl:value-of select="date" /> 
      is <xsl:value-of select="currency" />&#160;<xsl:value-of select='format-number(amount, "#.00")' />
      </body>
    </html>
  </xsl:template>



  <!-- Styling for list of product identity numbers and names.  -->
  <xsl:template match="/items">
    <html>
      <head>
        <title>Product List</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
        </style>
      </head>
      <body>
        <h2>
          <xsl:value-of select="type" />
          Products
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
  



  <!-- Styling for product attributes.  -->
  <xsl:template match="/attributes">
    <html>
      <head>
        <title><xsl:value-of select="type" />&#160;<xsl:value-of select="id" />&#160;Attributes</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
        </style>
      </head>
      <body>
        <h2>
          <xsl:value-of select="type" />&#160;<xsl:value-of select="id" />&#160;Attributes
        </h2>
        <table>
          <tr>
            <td>
              <b>Keys</b>
            </td>
            <td>
              <b>Values</b>
            </td>
          </tr>
          <xsl:apply-templates select="attribute">
            <xsl:sort select="key" />
          </xsl:apply-templates>
        </table>
      </body>
    </html>
  </xsl:template>

  <!-- Styling for attribute key / values. -->
  <xsl:template match="attribute">
    <tr valign="top">
      <td>
        <b><xsl:value-of select="key" />:&#160;</b>
      </td>
      <td>
        <xsl:for-each select="values/value">
          <xsl:value-of select="." /><br />
        </xsl:for-each>
      </td>
    </tr>
  </xsl:template>
  
  

</xsl:stylesheet>
