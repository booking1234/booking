<?xml version="1.0" encoding="utf-8"?>

<!-- 
  (c)2009-2012 CBT Limited controlling License at http://razor-cloud.com/License.html 
  This XSL and any derivatives may be used only to transform XML served by the Razor Cloud(c). 
  
  An overview of XSL transformation (XSLT) is at http://www.digital-web.com/articles/client_side_xslt 
  The W3C tutorial on XSLT is at http://www.w3schools.com/xsl/default.asp 
  Client and server side XSLT options are described at http://xmlfiles.com/xsl/xsl_intro.asp 
  HTML symbols at http://www.yellowpipe.com/yis/tools/ASCII-HTML-Characters/index.php
-->

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="utf-8"
    doctype-public="-//W3C//DTD HTML 4.0 Transitional//EN" media-type="text/html" />


  <!--
    Styling for product prices in several currencies.
  -->
  <xsl:template match="/pricelist">
    <html>
      <head>
        <title>Price Table</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana; width: 800px;}
          table th, table td { text-align: left; padding: 5px 10px; }
          table th { background-color: #ddd;font-weight: bold; }
          div, table td { background-color: #fff; border-top: 1px solid #ddd; min-width: 120px; vertical-align: top;}
          table, tr, th, td {border-collapse: collapse; margin: 0; border: 0 solid gray; padding: 1px 4px;}
        </style>
      </head>
      <body>
        <table>
          <thead>
            <tr><th>From Date</th><th>To Date</th><th>EUR Price</th><th>GBP Price</th><th>JPY Price</th><th>USD Price</th><th>USD Price</th></tr>
          </thead>
          <tbody>
            <xsl:apply-templates select="prices">
              <xsl:sort select="date" />
            </xsl:apply-templates>
          </tbody>
          <tfoot><tr><th colspan="7"></th></tr></tfoot>     
		</table>
      </body>
    </html>
  </xsl:template>

  <!-- Styling for price table row -->
  <xsl:template match="prices">
      <tr>
      	<td><xsl:value-of select="substring-before(date, 'T')" /></td>
      	<td><xsl:value-of select="substring-before(todate, 'T')" /></td>
      	<td><xsl:value-of select='format-number(EUR, "#.00")' /></td>
      	<td><xsl:value-of select='format-number(GBP, "#.00")' /></td>
      	<td><xsl:value-of select='format-number(JPY, "#.00")' /></td>
      	<td><xsl:value-of select='format-number(USD, "#.00")' /></td>
      	<td><xsl:value-of select='format-number(USD, "#.00")' /></td>
      	</tr>
  </xsl:template>
  
</xsl:stylesheet>