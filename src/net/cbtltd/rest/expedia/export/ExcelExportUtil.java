package net.cbtltd.rest.expedia.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.integration.LocationService;
import net.cbtltd.server.integration.PartyService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.shared.ChannelProductMap;
import net.cbtltd.shared.Product;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gwt.thirdparty.guava.common.base.Strings;

public class ExcelExportUtil {
	private static final Logger LOG = Logger.getLogger(ExcelExportUtil.class
			.getName());
	public static String XLSX_LOCAL_FILE_PATH;
	public static String TEMPLATE_XLSX_FILE;
	public static String DEFAULT_FILE_NAME="Bulk Property Upload.xlsx";
	private String fileName;
	private FileInputStream file;
	static{
		XLSX_LOCAL_FILE_PATH = System.getProperty("user.home")
				+ File.separator + "PMS" + File.separator + "xlsx"
				+ File.separator;
	}
	private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    finally {
	        is.close();
	        os.close();
	    }
	}
	public Workbook createXlsxFile(String file) throws IOException{
	    TEMPLATE_XLSX_FILE=ExcelExportUtil.class.getResource("/resources/template/BulkProperty_expedia.xlsx").getPath();
	    if(file==null) fileName=XLSX_LOCAL_FILE_PATH+DEFAULT_FILE_NAME;
	    else fileName=XLSX_LOCAL_FILE_PATH+file;
		copyFileUsingStream(new File(TEMPLATE_XLSX_FILE),new File(fileName));
		this.file=new FileInputStream(fileName);
	    return new XSSFWorkbook( this.file );
	}

	public void finishXlsxFile(Workbook workbook) throws IOException {
		this.file.close();
		FileOutputStream outFile = new FileOutputStream(new File(fileName),
				false);
		workbook.write(outFile);
		outFile.close();
	}
	
	List<ChannelProductMap> getProducts(SqlSession sqlSession,String id){
		
		List<ChannelProductMap> listChannelProductMap =new ArrayList<ChannelProductMap>();
		 List<String> products=ProductService.getInstance().activeProductIdListBySupplier(sqlSession,id);
		 for (String string : products) {
			 ChannelProductMap productMap=new ChannelProductMap();
				productMap.setProductId(string);
				productMap.setChannelProductId(id);
				listChannelProductMap.add(productMap);
		}
		 return listChannelProductMap;
	}
	
	public void generatePropertyExcelFromSupplier(String supplierid) {
		
		SqlSession sqlSession = null;
		List<ChannelProductMap> listChannelProductMap = null;
		Workbook workbook=null;
		try {
			workbook=createXlsxFile(DEFAULT_FILE_NAME);
			sqlSession = RazorServer.openSession();
			listChannelProductMap = getProducts(sqlSession,supplierid);
			int rowid=1;
			for (ChannelProductMap channelProductMap : listChannelProductMap) {
				exportProduct(sqlSession,workbook,rowid++,channelProductMap.getProductId());
			}
			finishXlsxFile(workbook);
		} catch (Throwable x) {
			if (sqlSession != null)
				sqlSession.rollback();
			LOG.error(x.getMessage(), x);
			x.printStackTrace();
			return;
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}

	public void generatePropertyExcelFromProduct(int... productids) {
		SqlSession sqlSession = null;
		Workbook workbook=null;
		try {
			workbook=createXlsxFile(DEFAULT_FILE_NAME);
			sqlSession = RazorServer.openSession();
			int rowid=1;
			for (int string : productids) {
				exportProduct(sqlSession,workbook,rowid,String.valueOf(string));
				exportProductInfo(sqlSession,workbook,rowid,String.valueOf(string));
				rowid++;
			}
			finishXlsxFile(workbook);
		} catch (Throwable x) {
			if (sqlSession != null)
				sqlSession.rollback();
			LOG.error(x.getMessage(), x);
			x.printStackTrace();
			return;
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}
	
	private void exportProduct(SqlSession sqlSession, Workbook workbook,int rowid,
			String productId) {
		Sheet extraCost=workbook.getSheet("Product Creation");
		Row row=extraCost.createRow(rowid);
		net.cbtltd.shared.Product product = ProductService.getInstance().fetchProduct(sqlSession, productId);
		net.cbtltd.shared.Location location=getLocation(sqlSession, product);
		net.cbtltd.shared.Party party=PartyService.getInstance().getParty(sqlSession, product.getSupplierid());
		setValue(row,0,product.getName());
		setValue(row,2,product.getPhysicaladdress()); 
		setValue(row,3,party.getPostalcode());
		setValue(row,4,Strings.isNullOrEmpty(location.getName())?location.getGname():location.getName() );
		setValue(row,5,Strings.isNullOrEmpty(location.getAdminarea_lvl_1())?location.getAdminarea_lvl_2():location.getAdminarea_lvl_1());
		setValue(row,6, location.getCountry());
		setValue(row,7,product.getCurrency());
		setValue(row,12,party.getName());
		setValue(row,13,party.getExtraname());
		setValue(row,14,party.getEmailaddress());
		setValue(row,15,party.getEmailaddress());
		setValue(row,16,party.getEmailaddress());
		setValue(row,22,product.getType());
		setValue(row,23,product.getId());
		setValue(row,24,product.getRoom());
		setValue(row,25,"");
		setValue(row,26,product.getPerson());
		setValue(row,27,product.getPerson());
		setValue(row,28,product.getChild());
		setValue(row,29,product.getInfant());
		
	}
	
	private void exportProductInfo(SqlSession sqlSession, Workbook workbook,int rowid,
			String productId) {
		Sheet extraCost=workbook.getSheet("Property Info");
		Row row=extraCost.createRow(rowid);
		net.cbtltd.shared.Product product = ProductService.getInstance().fetchProduct(sqlSession, productId);
		net.cbtltd.shared.Location location=getLocation(sqlSession, product);
		net.cbtltd.shared.Party party=PartyService.getInstance().getParty(sqlSession, product.getSupplierid());
		setValue(row,1,product.getName());
		setValue(row,3,product.getPhysicaladdress()); 
		setValue(row,4,party.getPostalcode());
		setValue(row,5,Strings.isNullOrEmpty(location.getName())?location.getGname():location.getName() );
		setValue(row,6,Strings.isNullOrEmpty(location.getAdminarea_lvl_1())?location.getAdminarea_lvl_2():location.getAdminarea_lvl_1());
		setValue(row,7, location.getCountry());
		setValue(row,8,party.getMobilephone());
		setValue(row,9,party.getFaxphone());
		setValue(row,14,product.getCurrency());
		
		setValue(row,21,party.getName());
		setValue(row,22,party.getExtraname());
		setValue(row,23,party.getEmailaddress());
		setValue(row,24,party.getEmailaddress());
		setValue(row,25,party.getEmailaddress());
		
	}
	
	private net.cbtltd.shared.Location getLocation(SqlSession sqlSession, Product product){
		if(product.getLocationid()==null) return null;
		return LocationService.getInstance().read(sqlSession, product.getLocationid());
	}
	
	private void setValue(Row row, int i, Object string) {
		row.createCell(i++).setCellValue(string.toString());		
	}
	
}
