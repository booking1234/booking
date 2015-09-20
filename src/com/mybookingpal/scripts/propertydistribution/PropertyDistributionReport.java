package com.mybookingpal.scripts.propertydistribution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import net.cbtltd.rest.nextpax.A_Handler;
import net.cbtltd.server.EmailService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.ManagerToChannelMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.ChannelDistribution;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.ManagerToChannel;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;

public class PropertyDistributionReport {

	/**
	 * @param args
	 */
    private  final static String DELIMTER = " | ";
	private static ArrayList<Partner> propertmanagmentsoftware;
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final String  EMAIL_ADDRESSES="isaac@mybookingpal.com,stephen@mybookingpal.com";
	public static void main(String[] args) {
		 final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
		 
		String file = "DistributionReport_" + DF.format(new Date()) + ".csv"; 
		String distributionReportLocation = generateDistributionReport(file);

		EmailService.emailWithAttachment( "Distribution Report" , file, EMAIL_ADDRESSES ,  Arrays.asList(distributionReportLocation)  );
		
		System.out.println("File Location: "+System.getProperty("user.dir"));
		
		deleteDistributionReport(distributionReportLocation);
	}

private static void deleteDistributionReport(String distributionReportLocation) {
	try{
		 
		File file = new File(distributionReportLocation);
		
		if(!file.delete()){
			LOG.error("File Not Deleted:"+distributionReportLocation);
			
		}

	}catch(Exception e){

		LOG.error("File Not Deleted:"+distributionReportLocation);

	}
		
	}

private static void elapsedTime(long starttime){
	System.out.println("Elapsed milliseconds: " +(new Date().getTime()  - starttime ));
}

	private static String  generateDistributionReport(String filename){
		SqlSession sqlSession = RazorServer.openSession();
		FileWriter writer = null;
		try {
			
			long lStartTime = new Date().getTime();
			
			 writer = new FileWriter(filename);


			
			Double commission;
			String propertycount;
			String name;
			String pmssoftware;

			String pmpartyid;

			Map<String, PropertyManager> pm = new LinkedHashMap <String, PropertyManager>();
			List<String> pmPartyIDS = new ArrayList<String>(); 
			
			List<Product> propertymanagers = sqlSession.getMapper(ProductMapper.class).activeListOfPropertyManagersAndCommision();

			propertmanagmentsoftware = sqlSession.getMapper(PartnerMapper.class).listPropertyManagementSoftware();

			// get a list of all the active property mangers and their commission {
			for (int i = 0; i < propertymanagers.size(); i++) {
				pmpartyid = propertymanagers.get(i).getSupplierid();

				// D. Get Commission
				commission = propertymanagers.get(i).getDiscount();

				// A. Get the property count
				propertycount = sqlSession.getMapper(ProductMapper.class).selectedProductCountBySupplier(pmpartyid);

				// B. Get Property mangers name.
				Party party = sqlSession.getMapper(PartyMapper.class).read(pmpartyid);
				name = (party == null ? "" : party.getName());

				if (party == null) {
					System.out.println(pmpartyid);
					continue;
				}

				// C. Get Property Managment Software.
				pmssoftware = getPropertyManagementSoftware(sqlSession, party.getId());

				pm.put(pmpartyid ,new PropertyManager(commission, propertycount, name, pmssoftware, pmpartyid) );
				pmPartyIDS.add(pmpartyid);
				//System.out.println(pmpartyid);
			}

////////////////////////////////////////////////////////////////////////////////////////////////			
			elapsedTime(lStartTime );
			lStartTime = new Date().getTime();
////////////////////////////////////////////////////////////////////////////////////////////////			
			
			List<ChannelPartner> channelPartnerList = sqlSession.getMapper(ChannelPartnerMapper.class).list(); 
			ArrayList<ManagerToChannel> availablePMForChannel; 
			String pmPartyID;
			
			writerHeaderFile(writer);
			for( ChannelPartner channelpartner :  channelPartnerList){
				
				// get property managers that are available 
				availablePMForChannel = sqlSession.getMapper(ManagerToChannelMapper.class).listPropertyManagersDistributedChannel(channelpartner.getId().toString());
				for( int i =0 ; i<availablePMForChannel.size();i++ ){
					pmPartyID = availablePMForChannel.get(i).getProperty_manager_id().toString();
					if(pm.containsKey(pmPartyID)){
						
						writer.append(channelpartner.getChannelName()+ DELIMTER + channelpartner.getCommission()+   DELIMTER   + pm.get(pmPartyID).toActiveCSVFormat());
						writer.append(System.getProperty("line.separator"));
						//System.out.println(channelpartner.getChannelName()+" , " + channelpartner.getCommission()+ " , "   + pm.get(pmPartyID).toActiveCSVFormat());
					}
				}
			
				//Get List of Property Mangers that are not being distributed 	
				ArrayList<String> pmtochannel     = sqlSession.getMapper(ManagerToChannelMapper.class).justPropertyManagersDistributedChannel(channelpartner.getId().toString());
				List<String> pmnotlistedonchannel = sqlSession.getMapper(ManagerToChannelMapper.class).listPMNotDistributedToChannelString(pmtochannel);
	
			   for( int i = 0 ; i< pmnotlistedonchannel.size() ;i++ ){ 
				   
				   if(pm.containsKey(pmnotlistedonchannel.get(i))){
						
						writer.append(channelpartner.getChannelName()+DELIMTER + channelpartner.getCommission()+ DELIMTER   + pm.get(pmnotlistedonchannel.get(i)).toInActiveCSVFormat());
						writer.append(System.getProperty("line.separator"));
						//System.out.println(channelpartner.getChannelName()+" , " + channelpartner.getCommission()+ " , "   + pm.get(pmPartyID).toActiveCSVFormat());
					}
				   
			   }
			
			
			}

		} catch (IOException e) {
			LOG.error(e.getStackTrace());
			e.printStackTrace();
		} finally{
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				LOG.error(e.getStackTrace());
				e.printStackTrace();			}
		}
    return System.getProperty("user.dir")+"\\"+filename; 
	}

	private static void writerHeaderFile(FileWriter writer) {
		try {
			
			writer.append("Channel Name " + DELIMTER+ "Channel Margin (MIN) " + DELIMTER + "Property Manager Name " + DELIMTER + " PMS Vendor " + DELIMTER+ 
			                 "Property Manager Margin (MAX)" + DELIMTER + "Number of Properties"
			+ DELIMTER+ " Number of Properties Distributed ");
			
			writer.append(System.getProperty("line.separator"));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static String getPropertyManagementSoftware(SqlSession sqlSession,
			String partyid) {

		if (partyid.equalsIgnoreCase("")) {
			return "Empty_Party_ID";
		}
		Partner organizationid = sqlSession.getMapper(PartnerMapper.class)
				.exists(partyid);

		if (organizationid == null)
			return "Razor-Cloud";

		for (Partner partner : propertmanagmentsoftware) {
			if (organizationid.getOrganizationid().equals(
					partner.getOrganizationid()))
				return partner.getPartyname();
		}
		return "Razor-Cloud";
	}

}
