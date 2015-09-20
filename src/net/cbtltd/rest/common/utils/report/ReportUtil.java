package net.cbtltd.rest.common.utils.report;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class ReportUtil {
	
	static String HEADING="heading";
	static String SAME="same";
	static String DIFF="diff";
	
	static String WORKBOOK_START="<?xml version=\"1.0\"?><?mso-application progid=\"Excel.Sheet\"?><Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:html=\"http://www.w3.org/TR/REC-html40\">";
	static String WORKBOOK_END="</Workbook>";
	static String STYLES="<Styles><Style ss:ID=\"Default\" ss:Name=\"Normal\"><Alignment ss:Vertical=\"Bottom\"/><Borders/><Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"11\" ss:Color=\"#000000\"/><Interior/><NumberFormat/><Protection/></Style><Style ss:ID=\"heading\"><Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"11\" ss:Color=\"#000000\" ss:Bold=\"1\"/></Style><Style ss:ID=\"same\"><Borders><Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/></Borders><Interior ss:Color=\"#FFFF00\" ss:Pattern=\"Solid\"/></Style><Style ss:ID=\"diff\"><Borders><Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/></Borders><Interior ss:Color=\"#FF0000\" ss:Pattern=\"Solid\"/></Style></Styles>";
	static String WORKSHEET_END="</Worksheet>";
	static String WORKSHEETOPTIONS="<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\"><PageSetup><Header x:Margin=\"0.3\"/><Footer x:Margin=\"0.3\"/><PageMargins x:Bottom=\"0.75\" x:Left=\"0.7\" x:Right=\"0.7\" x:Top=\"0.75\"/></PageSetup><Unsynced/><Selected/><Panes><Pane><Number>3</Number><ActiveRow>4</ActiveRow><ActiveCol>6</ActiveCol></Pane></Panes><ProtectObjects>False</ProtectObjects><ProtectScenarios>False</ProtectScenarios></WorksheetOptions>";
	
	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		 
		    Worksheet object = new Worksheet("poui");
		    Table table=new Table();
		    Cell c1=new Cell(HEADING,"abc");
		    Cell c2=new Cell(HEADING,"cas");
		    Cell c3=new Cell(SAME,"sdfe");
		    Cell c4=new Cell(DIFF,"dfdbe");
		    Row row1=new Row();
		    row1.setCells(Arrays.asList(new Cell[]{c1,c2}));
		    Row row2=new Row();
		    row2.setCells(Arrays.asList(new Cell[]{c3,c4}));
		    
		    table.setRow(Arrays.asList(new Row[]{row1,row2}));
		    object.setTable(table);
		    
		    Worksheet object1 = new Worksheet("asdfasd");
		     Table table1=new Table();
		     table1.setColumnCount(5);
		     table1.setRowCount(2);
//		    
		     Cell c11=new Cell(HEADING,"cv");
		     Cell c21=new Cell(HEADING,"xcvw");
		     Cell c31=new Cell(SAME,"xcv");
		     Cell c41=new Cell(DIFF,"xcve");
		     Cell c51=new Cell("asd"); c51.setRowSpan(1);
		     Cell c61=new Cell("123"); c61.setColSpan(1);
		     
		     
		    Row  row11=new Row();
		    row11.setCells(Arrays.asList(new Cell[]{c11,c21,c51,c61}));
		    
		    Row row21=new Row();
		    row21.setCells(Arrays.asList(new Cell[]{c31,c41}));
		    
		    table1.setRow(Arrays.asList(new Row[]{row11,row21}));
		    object1.setTable(table1);
		    
		    generateFile(Arrays.asList(new Worksheet[]{object,object1}), "C:\\test.xml" );
		    
	}
	public static void generateFile(Worksheet sheet,String filename) throws JAXBException, FileNotFoundException {
		generateFile(Arrays.asList(new Worksheet[]{sheet}),filename);
	}
	
	public static void generateFile(List<Worksheet> sheets,String filename) throws JAXBException, FileNotFoundException {
		
	    StringBuilder b=new StringBuilder(WORKBOOK_START);
	    b.append(STYLES);
		for(Worksheet sheet:sheets){
			Writer str = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(Worksheet.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			m.marshal(sheet, str);
			b.append(str.toString());
			b.replace(b.length() - WORKSHEET_END.length(), b.length(),
					WORKSHEETOPTIONS);
			b.append(WORKSHEET_END);
		}
		b.append(WORKBOOK_END);
		PrintWriter pw=new PrintWriter(new FileOutputStream(filename,false));
	    pw.write(b.toString());
	    pw.close();
	}

	
	
	
	
}
