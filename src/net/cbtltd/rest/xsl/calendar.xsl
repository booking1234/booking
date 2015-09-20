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

  <!-- Styling for availability.  -->
  <xsl:template match="/available">
    <html>
      <head>
        <title>Reservation Availability</title>
        <style type="text/css">
          body {font: 10pt verdana;}
          table {font: 10pt verdana;}
        </style>
      </head>
      <body>
        <xsl:if test="available='true'">
          Property <xsl:value-of select="productid" /> is available from <xsl:value-of select="arrive" /> to <xsl:value-of select="depart" />
        </xsl:if>
        <xsl:if test="available='false'">
          Property <xsl:value-of select="productid" /> is <b>NOT</b> available from <xsl:value-of select="arrive" /> to <xsl:value-of select="depart" />
        </xsl:if>
      </body>
    </html>
  </xsl:template>



  <!-- Styling for availability calendar.  -->
  <xsl:template match="/schedule">
    <html>
      <head>
        <title>Availability Calendar</title>
        <style type="text/css">
			body {font: 10pt verdana;}
			table {font: 10pt verdana;}
			.cell {
				font-family: Arial, Helvetica, sans-serif;
				font-size: 10px;
				color: #6666CC;
				text-align: center;
			}
        </style>
      </head>
      <body>
        <h2>
          Availability Calendar <xsl:value-of select="locationid" />
        </h2>
        <table border="0">
          <xsl:for-each select="row">
            <xsl:if test="position()=1">
              <tr bgcolor="#e8eef7">
                <xsl:for-each select="col">
                  <xsl:if test="position()=1">
                    <th>
                      Property Name / Date <xsl:value-of select="year" />-<xsl:value-of select="month" />-
                    </th>
                  </xsl:if>
	              <th>
		            <xsl:value-of select="day" />
		          </th>
	            </xsl:for-each>
	          </tr>
	        </xsl:if>
	           <tr>
	              <td>
	                <b>
	                  <i>
	                    <xsl:value-of select="name" />
	                  </i>
	                </b>
	              </td>
              
		      <xsl:for-each select="col">
		        <xsl:choose>
				  <xsl:when test="state='Available'">
                    <td class="cell" bgcolor="#DDDDDF">
                      &#160;
                    </td>
                  </xsl:when>
		          <xsl:when test="state='Created'">
                    <td class="cell" bgcolor="#FF0000">
                      &#160;
                    </td>
                  </xsl:when>
		          <xsl:when test="state='Reserved'">
                    <td class="cell" bgcolor="#FFA500">
                      &#160;
                    </td>
                  </xsl:when>
		          <xsl:when test="state='Confirmed'">
                    <td class="cell" bgcolor="#FFFF00">
                      &#160;
                    </td>
                  </xsl:when>
		          <xsl:when test="state='FullyPaid'">
                    <td class="cell" bgcolor="#008000">
                      &#160;
                    </td>
                  </xsl:when>
		          <xsl:when test="state='Briefed'">
                    <td class="cell" bgcolor="#0000FF">
                      &#160;
                    </td>
                  </xsl:when>
		          <xsl:when test="state='PreArrival'">
                    <td class="cell" bgcolor="#0000FF">
                      &#160;
                    </td>
                  </xsl:when>
		          <xsl:when test="state='Arrived'">
                    <td class="cell" bgcolor="#0000FF">
                      &#160;
                    </td>
                  </xsl:when>
		          <xsl:when test="state='PreDeparture'">
                    <td class="cell" bgcolor="#00FFFF">
                      &#160;
                    </td>
                  </xsl:when>
		          <xsl:when test="state='Departed'">
                    <td class="cell" bgcolor="#00FFFF">
                      &#160;
                    </td>
                  </xsl:when>
                  <xsl:otherwise>
                    <td width="15" class="cell">
		              &#160;
                    </td>
		          </xsl:otherwise>
               </xsl:choose>
             </xsl:for-each>
              
            </tr>
         <xsl:if test="position()=last()">
          <tr bgcolor="#e8eef7">
            <th>&#160;</th>
              <xsl:for-each select="col">
	          <th>
	            &#160;
	          </th>
                </xsl:for-each>
              </tr>
            </xsl:if>
          </xsl:for-each>
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
