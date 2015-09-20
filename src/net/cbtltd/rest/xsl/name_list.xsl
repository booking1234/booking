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

  <!-- Styling for list of identity numbers and names in name sequence.  -->
  <xsl:template match="/items">
    <html>
      <head>
        <title><xsl:value-of select="type" /> <xsl:value-of select="entity" /> List</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
          table th, table td { text-align: left; padding: 5px 10px; }
          table th { background-color: #ddd;font-weight: bold; }
          table td { background-color: #fff; border-top: 1px solid #ddd; }
          table, tr, th, td {border-collapse: collapse; margin: 0; border: 0 solid gray; padding: 1px 4px;}
        </style>
      </head>
      <body>
<!--         <h2> -->
<!--           <xsl:value-of select="type" />&#160;<xsl:value-of select="entity" />&#160;List -->
<!--         </h2> -->
        <table>
          <thead>
            <tr><th>Name</th><th>ID</th></tr>
          </thead>
          <tbody>
        <xsl:apply-templates select="item">
          <xsl:sort select="name" />
        </xsl:apply-templates>
          </tbody>
    </table>
      </body>
    </html>
  </xsl:template>


  <!-- Styling for Name-Id pairs which is used for many entity lists. -->
  <xsl:template match="item">
    <tr><td>
    <xsl:value-of select="name" />
    </td><td>
    <xsl:value-of select="id" />
    </td></tr>
  </xsl:template>
  
</xsl:stylesheet>
