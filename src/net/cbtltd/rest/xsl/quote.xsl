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

  <xsl:template match="/quote">
    <html>
      <head>
        <title>Quotation</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana; width: 600px;}
          table th, table td { text-align: left; padding: 5px 10px; }
          table th { background-color: #ddd;font-weight: bold; }
          div, table td { background-color: #fff; border-top: 1px solid #ddd; min-width: 160px; vertical-align: top;}
          table, tr, th, td {border-collapse: collapse; margin: 0; border: 0 solid gray; padding: 1px 4px;}
        </style>
      </head>
      <body>
        <xsl:if test="not(message)">
        <h2>Property Quotation</h2>
        <table>
          <thead><tr><th></th><th></th></tr></thead>
          <tbody>
            <tr><td>Property Name</td><td><xsl:value-of select="productname" /></td></tr>
            <tr><td>Arrival Date</td><td><xsl:value-of select="arrive" /></td></tr>
            <tr><td>Departure Date</td><td><xsl:value-of select="depart" /></td></tr>
            <tr><td>Rack Rate</td><td><xsl:value-of select="currency" />&#160;<xsl:value-of select="rack" /></td></tr>
            <tr><td>Quoted Price</td><td><xsl:value-of select="currency" />&#160;<xsl:value-of select="quote" /></td></tr>
            <tr><td>STO Rate</td><td><xsl:value-of select="currency" />&#160;<xsl:value-of select="sto" /></td></tr>
            <tr><td>Confirmation Deposit</td><td><xsl:value-of select="deposit" />&#160;%</td></tr>
            <tr><td>Terms &amp; Conditions</td><td><xsl:value-of select="terms" /></td></tr>
           </tbody>
          <tfoot><tr><th></th><th></th></tr></tfoot>
        </table>
        </xsl:if>
      </body>
    </html>
  </xsl:template>
  
</xsl:stylesheet>
