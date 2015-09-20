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
    Styling for financial transactions.
  -->
  <xsl:template match="/accounts">
    <html>
      <head>
        <title>Financial Transactions</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana; width: 60em;}
          table th, table td { text-align: left; padding: 5px 10px; }
          table th { background-color: #ddd;font-weight: bold; }
          div, table td { background-color: #fff; border-top: 1px solid #ddd; vertical-align: top;}
          table, tr, th, td {border-collapse: collapse; margin: 0; border: 0 solid gray; padding: 1px 4px;}
          .notes {min-width: 40em;}
          .number {text-align: right;}
        </style>
      </head>
      <body>
        <table>
          <thead>
            <tr>
            <th>Account</th>
            <th>Date</th>
            <th>Process</th>
            <th>Reference</th>
            <th>Description</th>
            <th>Subsidiary Account</th>
            <th class="number">Quantity</th>
            <th>Unit</th>
            <th class="number">Debit</th>
            <th class="number">Credit</th>
            <th>Currency</th></tr>
          </thead>
          <tbody>
            <xsl:for-each select="account">
        	<td colspan="11"><xsl:value-of select="name" /></td>
 	            <xsl:apply-templates select="journal">
	              <xsl:sort select="date" />
	            </xsl:apply-templates>
            </xsl:for-each>
          </tbody>
          <tfoot><tr><th colspan="7"></th></tr></tfoot>     
		</table>
      </body>
    </html>
  </xsl:template>

  <!-- Styling for journal row -->
  <xsl:template match="account/journal">
      <tr>
        <td> </td>
      	<td><xsl:value-of select="substring-before(date, 'T')" /></td>
      	<td><xsl:value-of select="process" /></td>
      	<td><xsl:value-of select="eventname" /></td>
      	<td class="notes"><xsl:value-of select="description" /></td>
      	<td><xsl:value-of select="entityname" /></td>
      	<td class="number"><xsl:value-of select="format-number(quantity, '#')" /></td>
       	<td><xsl:value-of select="unit" /></td>
      	<td class="number"><xsl:value-of select="format-number(debitamount, '#.00')" /></td>
      	<td class="number"><xsl:value-of select="format-number(creditamount, '#.00')" /></td>
      	<td><xsl:value-of select="currency" /></td>
      	</tr>
  </xsl:template>
  
</xsl:stylesheet>