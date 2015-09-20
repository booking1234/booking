/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.trial.server.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import marytts.util.string.StringUtils;

/**
 * Crawler4j is an open source Java Crawler which provides a simple interface for crawling the web.
 * There are two zip files that you need to download. One of them contains crawler4j jar file and 
 * config files and the other contains dependencies of crawler4j. Put all of these in your classpath.
 * 
 * Then, you need to create a crawler class that extends WebCrawler. This class decides which URLs 
 * should be crawled and handles the downloaded page.
 * @see http://code.google.com/p/crawler4j/
 * @see http://www.tours.com/travel_agents.htm
 */
public class ToursExtractor {

	public static void main(String[] args)
			throws IOException {

		FileReader fr = null;
		BufferedReader in = null;
		FileWriter fw = null;
		BufferedWriter out = null;

		try {
			fr = new FileReader("c:/temp/crawler/travel_agents.htm");
			in  = new BufferedReader(fr);
			fw = new FileWriter("c:/temp/crawler/travel_agents.txt", true);
			out  = new BufferedWriter(fw);

			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = in.readLine()) != null) {sb.append(line);}
			extract(out, sb.toString());
		}
		finally {
			if (in != null) {in.close();}
			if (out != null) {out.close();}
		}
	}

	private static void extract(BufferedWriter out, String text) {
		try {
			text = text.replaceAll("\"", "'");
			//out.write(text);
			int startindex = 0;
			int lastindex = -1;
			while (true) {
				if (startindex < lastindex) {break;}
				System.out.println("\n");
				out.write("\n");
				lastindex = startindex;
				startindex = inspect(out, startindex, 100, "<td class='b' nowrap='nowrap' width='230'>", "</td>", text);
				startindex = inspect(out, startindex, 100, "<td class='b s' nowrap='nowrap' valign='bottom' width='250'>", "</td>", text);
				startindex = inspect(out, startindex, 200, "<td class='s' nowrap='nowrap'>", "</td>", text);
				startindex = inspect(out, startindex, 100, "<a href='mailto:", "'>", text);
				startindex = inspect(out, startindex, 100, "<b>Website:</b> <a href='", "'>", text);
				startindex = inspect(out, startindex, 20, "<span class='skype_pnh_print_container_1324625301'>", "</span>", text);
				startindex = inspect(out, startindex, 20, "<b>Fax:</b> &nbsp; &nbsp; &nbsp; ", "</td>", text);
				startindex = inspect(out, startindex, 500, "<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>", "</div>", text);
//				startindex = extract(out, startindex, 50, "<td width='230' class='b' nowrap>", "<br>", text);
//				startindex = extract(out, startindex, 50, "<br>", "</td>", text);
//				startindex = extract(out, startindex, 50, "<td class='s' nowrap>", " </td>", text);
//				startindex = extract(out, startindex, 100, "<a href='mailto:", ">", text);
//				//startindex = extract(out, startindex, 100, "<b>Website:</b> <a href='", "'>", text);
//				startindex = extract(out, startindex, 20, "<b>Phone:</b>", "<br>", text);
//				startindex = extract(out, startindex, 20, "<b>Fax:</b> &nbsp; &nbsp; &nbsp; ", "</td>", text);
			}
		}
		catch(Throwable x) {System.out.println("Error " + x.getMessage());}
	}

	private static int skip(int startindex, String start, String text) {
		return text.indexOf(start, startindex) + start.length();
	}
	
	private static int inspect(BufferedWriter out, int startindex, int length, String start, String end, String text) throws IOException {
		int beginindex = text.indexOf(start, startindex) + start.length();
		int endindex = text.indexOf(end, beginindex); // - end.length();
		boolean valid = (endindex - beginindex <= length);
		String item = text.substring(beginindex, endindex).trim();
		item = StringUtils.replace(item, "'+'", "");
		item = StringUtils.replace(item, "<br>", "-");
		if (valid) {out.write("\"" + item + "\",");}

		System.out.println(beginindex + "," + endindex + "," + start.length() + "," + (endindex - beginindex) + "," + (valid ? item : ""));
		if (beginindex >= 0 && endindex > beginindex && endindex - beginindex <= length ) {return endindex;}
		else {return startindex;}
	}

	private static int extract(BufferedWriter out, int startindex, int length, String start, String end, String text) throws IOException {
		startindex = text.indexOf(start, startindex) + start.length();
		int endindex = text.indexOf(end, startindex); // - end.length();
		if (startindex >= 0 && endindex > startindex && endindex - startindex <= length ) {
			String item = text.substring(startindex, endindex).trim();
			item = StringUtils.replace(item, "'+'", "");
			out.write("\"" + item + "\",");
			System.out.print("\"" + item + "\",");
		}
		return endindex;
	}
}

//	/**
//	 * The main method.
//	 *
//	 * @param args the arguments
//	 * @throws Exception the exception
//	 */
//	public static void main(String[] args) throws Exception {
//		StringBuilder sb = new StringBuilder();
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap> <br>Affordable Tours</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>General Agent/Agency Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'><img src='/data/agents/2542.jpg'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>11150 Cash Rd.<br>Stafford, Texas 77477 </td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'sales'+'@'+'affordabletours'+'.'+'com'+''>'+'sales'+'@'+'affordabletours'+'.'+'com'+'</a>');</script><br><b>Website:</b> <a href='http://www.affordabletours.com'>www.affordabletours.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 800 935 2620<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 281 269 2690</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nDiscounted tours, cruises & river cruises to more than 100 destinations around the world. We receive exclusive offers from many of our travel operators & we pass the savings on to you.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p lbbg' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap> <br>America Israel Travel, Inc.</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Israel Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n");
//		sb.append("\n<td rowspan='2' align='right'><img src='/data/agents/3277.jpg'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>5000 N. Parkway Calabasas<br>Suite 104<br>Calabasas, California 91302 </td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'info'+'@'+'americaisrael'+'.'+'us'+''>'+'info'+'@'+'americaisrael'+'.'+'us'+'</a>');</script><br><b>Website:</b> <a href='http://www.americaisrael.us'>www.americaisrael.us</a></td>");
//		sb.append("\n");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 818 704 9888<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 818 704 9988</td>");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nA U.S based tour operator specializing in tours to biblical destinations providing tour programs and educational vacation packages to Israel, Egypt, Turkey, Jordan and Greece. Offers a wide variety of tours & vacation packages are perfect for groups or individuals, and can be customized, tailor made for you.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n");
//		sb.append("\n<div class='p ' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Dung Trinh Viet<br>Asiana Travel</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Southeast Asia Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'><img src='/data/agents/4675.jpg'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>159 Phuong Mai Street<br>Dong Da District<br>Ha Noi,  10000 Viet Nam</td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'info'+'@'+'asianatravel'+'.'+'com'+'.'+'vn'+''>'+'info'+'@'+'asianatravel'+'.'+'com'+'.'+'vn'+'</a>');</script><br><b>Website:</b> <a href='http://www.asianatravel.com.vn'>www.asianatravel.com.vn</a></td>");
//		sb.append("\n");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 84 4 35736748<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 84 4 35736750</td>");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nWe provide a multitude of travel services to Vietnam, Laos, Cambodia, Myanmar (Burma) and Thailand.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p lbbg' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Lotfi Hammad<br>Fly Well Travel</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Egypt Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'><img src='/data/agents/3844.jpg'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>23 (A) Ahmed Orabi St<br>Mohandiseen<br>Giza,   Egypt</td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'info'+'@'+'flywellegypt'+'.'+'com'+''>'+'info'+'@'+'flywellegypt'+'.'+'com'+'</a>');</script><br><b>Website:</b> <a href='http://www.flywellegypt.com'>www.flywellegypt.com</a></td>");
//		sb.append("\n");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 202 3305 8885<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 202 3345 0227</td>");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nSince 1985 providing a wide range of travel and tourism services specializing in safaris, Nile cruises, shore excursions, golfing, diving, fishing, honeymoons, archeology tours, student tours and eco-tourism.       ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p ' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Ms. Nguyen An<br>Green Papaya Travel</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Vietnam Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'><img src='/data/agents/4875.jpg'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>21B, Lo Duc street<br>Hanoi,   Vietnam</td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'vietnamtraveleasy'+'@'+'vnn'+'.'+'vn'+''>'+'vietnamtraveleasy'+'@'+'vnn'+'.'+'vn'+'</a>');</script><br><b>Website:</b> <a href='http://www.greenpapayatravel.com'>www.greenpapayatravel.com</a></td>");
//		sb.append("\n");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 84 913234507<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 844 39436434</td>");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\n");
//		sb.append("\nSpecialists for Vietnam, Laos & Cambodia!  Since 1997, serving a variety of groups and individuals arranging tours, accommodations, vehicle rentals, performance tickets and confirmation of airline and train tickets. ");
//		sb.append("\n      ");
//		sb.append("\n</div>");
//		sb.append("\n");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p lbbg' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap> <br>Tours4Fun.com</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>General Agent/Agency Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'><img src='/data/agents/4384.jpg'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>77 Las Tunas Dr.<br>Suite# 203<br>Arcadia, California 91007 </td>      ");
//		sb.append("\n");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'service'+'@'+'tours4fun'+'.'+'com'+''>'+'service'+'@'+'tours4fun'+'.'+'com'+'</a>');</script><br><b>Website:</b> <a href='http://www.tours4fun.com'>www.tours4fun.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 866 933 7368<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 626 552 3713</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nThe ultimate online travel superstore with the widest selection and best prices; our current products include tours and vacation packages worldwide. Tours4Fun means 'Total Customer Satisfaction'.  ");
//		sb.append("\n      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p ' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Richard Gertler<br>1-800-Traveler, LLC</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Gay & Lesbian Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>1303 Windham Lane<br>Wheaton, Maryland 20902 </td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'travler800'+'@'+'yahoo'+'.'+'com'+''>'+'travler800'+'@'+'yahoo'+'.'+'com'+'</a>');</script><br><b>Website:</b> N/A</td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 301 576 1654<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 202 318 4093</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nGay & Lesbian Travel Consultant - Full service agent with 27+ years experience. I am your travel professional of choice!      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p lbbg' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Corky Champagne-Einarsen<br>1AnchorsAway.com</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Alaska Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>PO Box 871723<br>Wasilla, Alaska 99687 </td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'info'+'@'+'1anchorsaway'+'.'+'com'+''>'+'info'+'@'+'1anchorsaway'+'.'+'com'+'</a>');</script><br><b>Website:</b> <a href='http://www.1anchorsaway.com'>www.1anchorsaway.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 800 580 3494<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 907 373 3498</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nAlaska specialist for 25 years. Resident expert for planning Alaska cruises, land adventures to include wildlife, glaciers, flightseeing, Denali, day cruises & group incentive programs.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p ' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Preston Griffin,  OSSN<br>1st Class Traveler</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Las Vegas - Nevada Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>PO Box 6487<br>Silver Spring, Maryland 20916 USA</td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'sales'+'@'+'1stclasstraveler'+'.'+'com'+''>'+'sales'+'@'+'1stclasstraveler'+'.'+'com'+'</a>');</script><br><b>Website:</b> <a href='http://www.1stclasstraveler.com'>www.1stclasstraveler.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 888 649 3135<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 301 946 0224</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nLas Vegas Certified, MGM Master Specialist, Royal Caribbean and Norweigan Cruise Line Specialist Certified, Universal Studios Specialist.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p lbbg' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Anthony Tozzi<br>4 Seasons Travel</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Hawaii Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>1167 E. Algonquin Rd<br>Des Plaines, Illinois 60016 USA</td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'season4travel'+'@'+'ameritech'+'.'+'net'+''>'+'season4travel'+'@'+'ameritech'+'.'+'net'+'</a>');</script><br><b>Website:</b> <a href='http://www.travel4seasons.com'>www.travel4seasons.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 847 824 4014<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 847 824 4039</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nOver 10 years experience booking packages, cruises, hotel reservations, rental car reservations, airline tickets, independent & group travel offering the most reputable suppliers.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p ' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Margie Seipt<br>4Winds Specialty Tours</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Europe Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>612 The Parkway<br>Richland, Washington 99352 US</td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'4winds'+'@'+'travelleaders247'+'.'+'com'+''>'+'4winds'+'@'+'travelleaders247'+'.'+'com'+'</a>');</script><br><b>Website:</b> <a href='http://www.4windstours.com'>www.4windstours.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 509 943 4686<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 509 943 5421</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nSpecializes in small group, barge, bike and barge/bike tours in Europe.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p lbbg' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Aydin Sekerin<br>7 Wonders Travel</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Turkey Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>Sedir Sok No 12<br>Balcova<br>Izmir,   Turkey</td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'info'+'@'+'7wonderstravel'+'.'+'com'+''>'+'info'+'@'+'7wonderstravel'+'.'+'com'+'</a>');</script><br><b>Website:</b> <a href='http://www.7wonderstravel.com'>www.7wonderstravel.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 90 232 279 2876<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 90 232 259 6036</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nSince 1997 specializing in tailormade tour packages for groups or individuals traveling throughout Turkey.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p ' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Eric Ardolino<br>A & S Travel Center</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>General Agent/Agency Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>970 North Colony Road<br>Unit D-6<br>Wallingford, Connecticut 06492 </td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'eric'+'@'+'astravelcenter'+'.'+'com'+''>'+'eric'+'@'+'astravelcenter'+'.'+'com'+'</a>');</script><br><b>Website:</b> <a href='http://www.astravelcenter.com'>www.astravelcenter.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 203 265 9293<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 203 294 0664</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nSince 1981 a full service travel agency specializing in corporate and group travel management.       ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p lbbg' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Sheri Fazleabas<br>A and S Travels Inc.</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Luxury Travel Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>1263 South Highland Avenue<br>Suite 2A<br>Lombard, Illinois 60148 </td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'info'+'@'+'anstravels'+'.'+'com'+''>'+'info'+'@'+'anstravels'+'.'+'com'+'</a>');</script><br><b>Website:</b> <a href='http://www.anstravels.com'>www.anstravels.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 630 889 8275<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 630 889-8278</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nWe specialize in luxury customized tour arrangements to Asia, Africa, South & Central America. We also cater for families, honeymooners, single travelers and adventure seekers.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p ' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Beverly Hajek<br>A Better Choice Travel</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Weddings Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>7507 Pearl Rd<br>Middleburg Hts, Ohio 44130 </td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'bev'+'@'+'betterchoicetravel'+'.'+'com'+''>'+'bev'+'@'+'betterchoicetravel'+'.'+'com'+'</a>');</script><br><b>Website:</b> <a href='http://www.betterchoicetravel.com'>www.betterchoicetravel.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 440 234 6300<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 440 234 2444</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nWe do it all, from an airline ticket to a hotel reservation to an around-the-world cruise & we believe in excellent customer service!      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p lbbg' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Paula Ramo<br>A Better Way To Travel</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Europe Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>11237 Sedlak Lane<br>Indianapolis, Indiana 46229 </td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'paula'+'@'+'abetterwaytotravel'+'.'+'net'+''>'+'paula'+'@'+'abetterwaytotravel'+'.'+'net'+'</a>');</script><br><b>Website:</b> <a href='http://www.abetterwaytotravel.net'>www.abetterwaytotravel.net</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 800 957 4428<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 317 894 4784</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nOver 14 years experience arranging any type of travel, from business to leisure, family groups, church groups, singles, gays and lesbian trips and cruises worldwide.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p ' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Helga Leonard, ACC, OSSN<br>A Dream Trip 4 U</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Groups Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>118 Alana Drive<br>Saxonburg, Pennsylvania 16056 </td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'helgaandrandy'+'@'+'zoominternet'+'.'+'net'+''>'+'helgaandrandy'+'@'+'zoominternet'+'.'+'net'+'</a>');</script><br><b>Website:</b> <a href='http://www.adreamtrip4u.com'>www.adreamtrip4u.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 724 234 2033<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; N/A</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nCLIA Accredited Cruise Counselor. Agent since 1995. Own company since 2005. Specialize in theme and cruise travel including music, family getaways, reunions, wine tasting, and more.       ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p lbbg' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Ronnelle Vanderbilt<br>A Glorious Holiday</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>General Agent/Agency Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>748 San Benito St.<br>Hollister, California 95023 USA</td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'roni'+'@'+'agloriousholiday'+'.'+'com'+''>'+'roni'+'@'+'agloriousholiday'+'.'+'com'+'</a>');</script><br><b>Website:</b> N/A</td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 831 630 1218<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 831 630 1208</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nOur knowledgeable and friendly staff will assist you with all your travel needs, from packages to Hawaii and Mexico, to that family getaway to Disneyland.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p ' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Jonathan Berzas<br>A Great Way to Charter, Tour and Travel</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>Group Travel Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>1209 S Main Street<br>Suite 420<br>Lindale, Texas 75771 </td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'jonathan'+'@'+'agreatwaytogo'+'.'+'com'+''>'+'jonathan'+'@'+'agreatwaytogo'+'.'+'com'+'</a>');</script><br><b>Website:</b> <a href='http://www.agreatwaytogo.com'>www.agreatwaytogo.com</a></td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 903 881 0561<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; 903 881 0563</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nOffers the industry’s most experienced travel coordinators who take the stress and guess work out of group travel.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n<div class='p lbbg' style='border-top: 1px solid #808080;'>      ");
//		sb.append("\n<table cellspacing='0' cellpadding='2' border='0'>");
//		sb.append("\n<tr>");
//		sb.append("\n<td width='230' class='b' nowrap>Myra Suarez<br>A Place to Remember</td>");
//		sb.append("\n<td width='250' valign='bottom' class='b s' nowrap>General Agent/Agency Specialist</td>");
//		sb.append("\n<td class='s' valign='bottom' nowrap></td>     ");
//		sb.append("\n<td rowspan='2' align='right'></td> ");
//		sb.append("\n</tr>");
//		sb.append("\n");
//		sb.append("\n<tr>");
//		sb.append("\n<td class='s' nowrap>4875 SW 92 Avenue<br>Miami, Florida 33165 </td>      ");
//		sb.append("\n<td class='s' nowrap><b>Email:</b> &nbsp;  &nbsp; &nbsp; <script>document.write('<a href='mailto:'+'mayrans'+'@'+'aol'+'.'+'com'+''>'+'mayrans'+'@'+'aol'+'.'+'com'+'</a>');</script><br><b>Website:</b> N/A</td>");
//		sb.append("\n<td class='s' nowrap><b>Phone:</b> 305 431 9427<br><b>Fax:</b> &nbsp; &nbsp; &nbsp; N/A</td>");
//		sb.append("\n");
//		sb.append("\n</tr>");
//		sb.append("\n</table>");
//		sb.append("\n<div class='s' style='padding: 2px 0px 0px 2px; border-top: 1px solid #d0d0d0;'>");
//		sb.append("\nSpecialist in all inclusive tours and cruises everywhere.      ");
//		sb.append("\n</div>");
//		sb.append("\n</div>");
//		sb.append("\n  <br>");
//		sb.append("\n  ");
//		sb.append("\n  <center>   ");
//		sb.append("\n  ");
//		sb.append("\n  <table width='100%' cellspacing='0' cellpadding='4' class='ltbg' style='border-top: 1px solid #606060'>");
//		sb.append("\n  <tr>");
//		sb.append("\n  <td class='b bg'>");
//		sb.append("\n    <span class='lg'>&laquo; Prev</span>");
//		sb.append("\n");
//		sb.append("\n    </td>");
//		sb.append("\n  <td align='center' class='bg'>");
//		sb.append("\n  <b>Page:</b>");
//		sb.append("\n  &nbsp;<span class='blk b'>1</span>&nbsp;&nbsp;<a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=1';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=1';' class='hvr'>2</a>&nbsp;&nbsp;<a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=2';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=2';' class='hvr'>3</a>&nbsp;&nbsp;<a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=3';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=3';' class='hvr'>4</a>&nbsp;&nbsp;<a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=4';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=4';' class='hvr'>5</a>&nbsp;&nbsp;<a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=5';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=5';' class='hvr'>6</a>&nbsp;&nbsp;<a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=6';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=6';' class='hvr'>7</a>&nbsp;&nbsp;<a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=7';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=7';' class='hvr'>8</a>&nbsp;&nbsp;<a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=8';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=8';' class='hvr'>9</a>&nbsp;&nbsp;<a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=9';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=9';' class='hvr'>10</a>&nbsp;&nbsp;<a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=10';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=10';' class='hvr'>11</a>&nbsp;&nbsp;<a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=11';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=11';' class='hvr'>...</a>&nbsp;  </td>");
//		sb.append("\n");
//		sb.append("\n  <td class='b bg' align='right'>");
//		sb.append("\n    <a href='#' onMouseOver='window.status='http://www.tours.com/travel_agents.htm?ord=company,lastname,firstname&pg=1';  return true;' onMouseOut='window.status=''; return true;' onClick='location.href='/travel_agents.htm?ord=company,lastname,firstname&pg=1';' class='hvr'>Next</a> &raquo;");
//		sb.append("\n    </td>");
//		sb.append("\n  </tr>");
//		sb.append("\n  </table>");
//		sb.append("\n");
//		extract(sb.toString());
//	}