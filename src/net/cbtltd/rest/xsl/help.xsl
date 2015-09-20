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


  <!-- Styling for help text.  -->
  <xsl:template match="/help">
    <html>
      <head>
        <title>Help Topics</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
  </style>
      </head>
      <body>
        <h2>
          Help Topic:
          <xsl:value-of select="topic" />
        </h2>
        <h3>
          Level:
          <xsl:value-of select="level" />
        </h3>
        <table border="0">
          <tr bgcolor="#e8eef7">
            <th>Item</th>
            <th>Description</th>
          </tr>
          <xsl:for-each select="item">
            <tr>
              <td>
                <b>
                  <i>
                    <xsl:value-of select="id" />
                  </i>
                </b>
              </td>
              <td>
                <xsl:value-of select="name" />
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



  <!-- Styling for Name-Id pairs which is used for many entity lists. -->
  <xsl:template match="item">
    <br />
    <xsl:value-of select="id" />
    :
    <xsl:value-of select="name" />
  </xsl:template>
  
</xsl:stylesheet>
