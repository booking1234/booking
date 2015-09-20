package net.cbtltd.rest.flipkey.xmlfeed;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.script.Handleable;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;

import org.apache.ibatis.session.SqlSession;

public class Launcher implements Handleable {

	private String altpartyid;
	private boolean downloadFiles = true;
	private int numOfFilesToBeDownloaded = 100;
	private int numOfPropertiesToBePersisted = 5000;
	private List<String> listFilesToBePersisted = new ArrayList<String>();

	/**
	 * @param altpartyid
	 *            -- ALT Party id
	 * @param downloadFile
	 *            -- boolean to indicate whether the program need to download
	 *            the file using http get.DEfault set to true
	 * @param listFilesToBePersisted
	 *            -- In case downloadFile=false then user needs to provide the
	 *            list of file names to be read and persisted The above two
	 *            param can be used when the code failed to download a file.User
	 *            can manually download that file and feed in that file name so
	 *            that parsing of file and persiting can happend for that failed
	 *            file.
	 * @param numOfFilesToBeDownloaded
	 *            - to limit the num of files to be downloaded.Default to
	 *            100(Max limit)
	 * @param numOfPropertiesToBePersisted
	 *            - to limit the num of properties to be persisted within a
	 *            file.Default to 5000(Max only 3000 properties exists within a
	 *            file so set ut to 5000)
	 */
	public Launcher(String altpartyid, boolean downloadFile,
			List<String> listFilesToBePersisted, int numOfFilesToBeDownloaded,
			int numOfPropertiesToBePersisted) {
		super();
		this.altpartyid = altpartyid;
		this.downloadFiles = downloadFile;
		this.listFilesToBePersisted = listFilesToBePersisted;
		this.numOfFilesToBeDownloaded = numOfFilesToBeDownloaded;
		this.numOfPropertiesToBePersisted = numOfPropertiesToBePersisted;
	}

	@Override
	public void readProducts() {
		getHandler(this.downloadFiles, this.listFilesToBePersisted)
				.readProducts();

	}
	
	
	public void inquireReservation() {
		getHandler(this.downloadFiles, this.listFilesToBePersisted).inquireReservation( RazorServer.openSession(), null);
	}


	private A_Handler getHandler(boolean downloadFile,
			List<String> listFilesToBePersisted) {

		SqlSession sqlSession = RazorServer.openSession();
		// String altpartyid = "179769";
		Partner partner = sqlSession.getMapper(PartnerMapper.class).exists(
				altpartyid);
		if (partner == null) {
			throw new ServiceException(Error.party_id, altpartyid);
		}
		return new A_Handler(partner, downloadFile, listFilesToBePersisted,
				this.numOfFilesToBeDownloaded,
				this.numOfPropertiesToBePersisted);
	}

	@Override
	public void readPrices() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readSchedules() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readAlerts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readSpecials() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readLocations() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readDescriptions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readImages() {
		// TODO Auto-generated method stub

	}

	public String getAltpartyid() {
		return altpartyid;
	}

	public void setAltpartyid(String altpartyid) {
		this.altpartyid = altpartyid;
	}

	public boolean isDownloadFiles() {
		return downloadFiles;
	}

	public void setDownloadFiles(boolean downloadFiles) {
		this.downloadFiles = downloadFiles;
	}

	public List<String> getListFilesToBePersisted() {
		return listFilesToBePersisted;
	}

	public void setListFilesToBePersisted(List<String> listFilesToBePersisted) {
		this.listFilesToBePersisted = listFilesToBePersisted;
	}

	public static void main(String[] args) {
		if(args.length<4) { System.out.println("Some of the property is missing.."); return; }
		String altpartyid=args[0];
		boolean downloadFile="true".equalsIgnoreCase(args[1])?true:false;
		int numOfFilesToBeDownloaded=Integer.parseInt(args[2].trim());
		int numOfPropertiesToBePersisted=Integer.parseInt(args[3].trim());
		
		System.out.println("Altpartyid "+altpartyid);
		System.out.println("downloadFile ? "+downloadFile);
		System.out.println("numOfFiles to process = "+numOfFilesToBeDownloaded);
		System.out.println("numOfPropertiesToBePersisted="+numOfPropertiesToBePersisted);
		
		List<String> listFilesToBePersisted=null;
		if(args.length>4&& args[4]!=null&&!"".equalsIgnoreCase(args[4]))
		{
			String[] files=args[4].split(",");
			listFilesToBePersisted=java.util.Arrays.asList(files);
		}
		System.out.println("Starting launcher");
		new Launcher(altpartyid, downloadFile, listFilesToBePersisted, numOfFilesToBeDownloaded, numOfPropertiesToBePersisted)
		.readProducts();
		
		
//		List<String> listFileNames = new ArrayList<String>();
//		//listFileNames.add("property_data_11.xml.gz");
//		//listFileNames.add("property_data_0.xml.gz");
//		listFileNames.add("property_data_0");
//		new Launcher(altpartyid, false, listFileNames, numOfFilesToBeDownloaded, numOfPropertiesToBePersisted)
//		.readProducts();
	
	}
}
