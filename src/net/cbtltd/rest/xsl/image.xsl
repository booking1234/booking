<?xml version="1.0" encoding="utf-8"?>
<!-- (c)2009-2012 CBT Limited controlling License at http://razor-cloud.com/License.html 
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

  <!-- Styling for images. -->
  <xsl:template match="/items">
    <html>
      <head>
        <title>Image Gallery</title>
        <style type="text/css">
          .cbt_image {
            padding-right: 5px;
            text-align: center;
          }
          .cbt_container {width: 540px;}
        </style>
      </head>
      <body>
        Click on an image to zoom to full size
        <p />
        <div class="cbt_container">
          <xsl:for-each select="item">
            <span class="cbt_image">
              <a>
                <xsl:attribute name="href">
                  http://razor-cloud.com/pictures/<xsl:value-of select="id" />
                </xsl:attribute>
<!-- to open in another window   <xsl:attribute name="target">big_img</xsl:attribute> -->
                <img>
                  <xsl:attribute name="src">
                    http://razor-cloud.com/pictures/<xsl:value-of select="id" />
                  </xsl:attribute>
                  <xsl:attribute name="width">100px</xsl:attribute>
                </img>
              </a>
            </span>
          </xsl:for-each>
        </div>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
